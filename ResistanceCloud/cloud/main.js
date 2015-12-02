/* Unit Testing Mock */
var message = require('cloud/message.js');
Parse.Cloud.define("get_message", function (request, response) {
  message.getMessage(request, response);
});

/*  adds "clouuuuuuud" to "Name" field of object
    {"Name" : String} Name to be cloudified*/
Parse.Cloud.define("cloudifyNameObject", function(request, response) {
  (function() {
    return findObject("NameObject",request.params.Name);
  }()).then(function(object){
    addCloudToName(object);
  }).then(function() {
    response.success();
  }), function(error) {
    response.error("something fucked up");
  };
});

/**
  Tallies a vote for missionaries.
  Checks if every player has voted.
  If necessary, calculates the result of voting.

  {"Name" : String} Name of the game
  {"PlayerName" : String} Name of the Player
  {"Vote" : Boolean} Vote */
Parse.Cloud.define("addVoteForMissionaries", function(request, response) {
  addVoteForMissionaries(request,response);
});

var addVoteForMissionaries = function(request, response) {
  (function() {
  return findGameWithMissionsAndRounds(request.params.Name);
  }()).then(function(game){
    return actuallyAddVoteForMissionaries(game, request.params.PlayerName,
      request.params.Vote);
  }).then(function() {
    console.log("response sucess");
    response.success();
  }), function(error) {
    response.error("something fucked up");
  };
}

var actuallyAddVoteForMissionaries = function(game, playerName, vote) {
  var promise = new Parse.Promise();
  
  //find current round
  var missions = game.get("Missions");
  var currentMission = missions[missions.length-1];
  var rounds = currentMission.get("Rounds");
  var currentRound = rounds[rounds.length-1];
  //add vote
  if (vote) 
    currentRound.add("Assentors", playerName);
  else {
    currentRound.add("Dissentors", playerName);
    console.log("added dissentors to current round:" + currentRound.id);
  }
  //calculate number of players and votes
  var numPlayers = game.get("Players").length;
  var numAssentors = typeof currentRound.get("Assentors") !== 'undefined' ? currentRound.get("Assentors").length : 0;
  var numDissentors = typeof currentRound.get("Dissentors") !== 'undefined' ? currentRound.get("Dissentors").length : 0;
  var numVotes = numAssentors + numDissentors;

  if (numVotes >= numPlayers) { //voting finished. update round
    (function() {
      if (numAssentors > numDissentors) 
        return passMissionaries(currentRound,game);
      else 
        return failMissionaries(currentRound,currentMission,game);
    }()).then(function() {
      promise.resolve(game);
    }), function(error) {
      promise.reject();
    };
  }
  else //save votes
    currentRound.save().then(function() {
      promise.resolve(game);
    });
  return promise;
}

//fails the mission
//tbd: check if 5 missions have passed and the game should be over
function failMission(currentMission,game) {
  currentMission.set("Passed", false);
  currentMission.save();
  addMission(game);
}

//fails the missionary vote
//creates a new round object, adds it to the rounds array in mission object
//changes game state to MISSION_LEADER_CHOOSING
function failMissionaries(currentRound, currentMission, game) {
  var promise = new Parse.Promise();
  if (currentMission.get("Rounds").length >= 5) {
    console.log("todo: mission should fail because " + game.get("Missions")[0].get("Rounds").length + " rounds were rejected.");
    failMission(currentMission,game);
  }
  currentRound.set("MissionariesAccepted", false);
  currentRound.save(); //assuming things, trololol
  var RoundObject = Parse.Object.extend("RoundObject");
  var nextRound = new RoundObject();
  nextRound.save().then(function(nextRound) {
    currentMission.add("Rounds",nextRound);
    return currentMission.save();
  }).then(function(currentMission) {
    return changeGameStatus(game,"MISSION_LEADER_CHOOSING");
  }).then(function() {
    promise.resolve(game);
  }), function(error) {
    promise.reject();
  };
  return promise;
}

//passes the missionary vote
//changes the game state to MISSIONARIES_VOTE
function passMissionaries(currentRound, game) {
  var promise = new Parse.Promise();

  currentRound.set("MissionariesAccepted", true);
  currentRound.save().then(function() {
    return changeGameStatus(game,"MISSIONARIES_VOTE");
  }).then(function() {
    promise.resolve(game);
  }), function(error) {
    promise.reject();
  };
  return promise;
}
/* Updates all players with roles.  
   Creates the first mission and first round.  
   Sets the first mission leader at random.  
   Updates the game state to MISSION_LEADER_CHOOSING.

   {"Name" : String} Name of the game */
Parse.Cloud.define("startGame", function(request, response) {
  startGame(request,response);
});

var startGame = function(request, response) {
  (function() {
  return findObject("GameObject", request.params.Name);
  }()).then(function(game){
    // only uses promise for assumed slowest function.
    // should probably be parallel promises, but I'm too lazy to do it
    actuallySetPlayerRoles(game);  
    return addMission(game);
  }).then(function(game){
    return actuallySetRandomMissionLeader(game);
  }).then(function(game){
    return changeGameStatus(game,"MISSION_LEADER_CHOOSING");
  }).then(function() {
    console.log("response sucess");
    response.success();
  }), function(error) {
    response.error("something fucked up");
  };
}

var changeGameStatus = function(game, status) {
  game.set("State", status);
  return game.save();
}
// makes the first round for a mission.
// makes the mission and adds the first round to it.
// adds the mission to the game
function addMission(game) {
  var promise = new Parse.Promise();
  var RoundObject = Parse.Object.extend("RoundObject");
  var firstRound = new RoundObject();
  firstRound.save().then(function(firstRound) {
    var MissionObject = Parse.Object.extend("MissionObject");
    var mission = new MissionObject();
    mission.add("Rounds",firstRound);
    mission.set("Pass",0);
    mission.set("Fail",0);
    return mission.save();
  }).then(function(mission) {
    game.add("Missions", mission);
    game.save();
  }).then(function() {
    promise.resolve(game);
  }), function(error){
    response.error("fuck");
  };
  return promise;
}

/* determines player roles
   {"Name" : String} Name of the game */
Parse.Cloud.define("setPlayerRoles", function(request, response) {
  setPlayerRoles(request,response);
});

var setPlayerRoles = function(request,response) {
  (function() {
  return findObject("GameObject", request.params.Name);
  }()).then(function(game){
    actuallySetPlayerRoles(game);
  }).then(function() {
    response.success();
  }), function(error) {
    response.error("something fucked up");
  };
}

function actuallySetPlayerRoles(game) {
  var players = game.get("Players");
  var numPlayers = players.length;  
  var numSpies = ~~(numPlayers*.43); //magically calculates the correct number of spies
  var uniqueRandomNumbers = generateRandomNumbers(numSpies,0,numPlayers - 1);
  consoleString = "spies are players with indices";
  var roles = {};
  uniqueRandomNumbers.forEach(function(spyIndex) {
    roles[spyIndex] = 1;
    consoleString += " " + spyIndex;
  });
  for (i = 0; i < numPlayers; i++) {
    if (roles[i] == null)
      players[i].set("PlayerType", "RESISTOR");
    else
      players[i].set("PlayerType", "SPY");
    players[i].save();
  }
  consoleString += ".";
  console.log(consoleString);
}

/* chooses the first mission leader
   {"Name" : String} name of the game */
Parse.Cloud.define("setRandomMissionLeader", function(request, response) {
  setRandomMissionLeader(request,response);
});

var setRandomMissionLeader = function(request,response) {
  (function() {
  return findObject("GameObject", request.params.Name);
  }()).then(function(game){
    return actuallySetRandomMissionLeader(game);
  }).then(function() {
    response.success();
  }), function(error) {
    response.error("something fucked up");
  };
}

var actuallySetRandomMissionLeader = function(game) {
  var promise = new Parse.Promise();
  var players = game.get("Players");
  var numPlayers = players.length;
  var uniqueRandomNumbers = generateRandomNumbers(1,1,numPlayers);
  missionLeaderIndex = uniqueRandomNumbers.pop() - 1;
  console.log("first mission leader has index " + missionLeaderIndex);
  var missions = game.get("Missions");
  var PlayerObject = Parse.Object.extend("PlayerObject");
  var mockPlayer = new PlayerObject();
  //sets the first mission for the first mission. maybe not a good assumption
  //cant figure out how to use fields of type 'object', using the id for now
  missions[0].set("Leader", players[missionLeaderIndex].id); //tbd: this line is broken
  missions[0].save().then(function() {
    promise.resolve(game);
  }), function(error) {
    promise.reject();
  };
  return promise;
}

/* tbd: use a beforeSave on vote objects to start the mission outcome calculation
   at the right time */

/* determines whether the mission succeeds or fails 
   tbd: either pass the game name and query, or pass the object from
   before save reference */
function determineMissionOutcome() {
  /* [query to find the amount of yes and no votes] */
  var uniqueRandomNumbers = generateRandomNumbers(1,0,3);
  var failureVotes = uniqueRandomNumbers.pop();
  console.log("Mocking mission outcome voting with " 
    + failureVotes + " players voting to fail the mission.");
  if (failureVotes) {
    /* tbd: edit the mission object to fail the mission */
    console.log("The mission failed.");
  }
  else {
    /* tbd: edit the mission object to pass the mission */
    console.log("The mission succeeded.");
  }
}
/* temporary public method for testing */
Parse.Cloud.define("determineMissionOutcome", function(request, response) {
  determineMissionOutcome();
  response.success();
});

/* use a beforeSave on vote objects to start the missionary engagement 
calculation at the right time */

/* determines whether the missionary team has been approved or rejected
   tbd: either pass the game name and query, or pass the object from
   before save reference */
function determineMissionaryEngagement() {
  /* [query to find the amount of yes and no votes] */
  var uniqueRandomNumbers = generateRandomNumbers(1,0,10);
  var approveVotes = uniqueRandomNumbers.pop();
  var rejectVotes = 10 - approveVotes;
  console.log("Mocking missionary engagement voting with " 
    + approveVotes + " players voting to approve the team, and "
    + rejectVotes + " players voting to reject the team.");
  if (approveVotes > rejectVotes) {
    /* tbd: edit the mission object to approve the team */
    console.log("the team was approved.");
  }
  else {
    /* tbd: edit the mission object to reject the team */
    console.log("the team was rejected.");
  }
}
/* temporary public method for testing */
Parse.Cloud.define("determineMissionaryEngagement", function(request, response) {
  determineMissionaryEngagement();
  response.success();
});

/* finds an object 
   [typeName] name of object type
   [name] name of object 
   [callBack] function to execute once the query finishes*/
function findObject(typeName, name, callBack) {
  var query = new Parse.Query(Parse.Object.extend(typeName));
  query.equalTo("Name", name);
  return query.first();
}

function findGameWithMissionsAndRounds(name) {
  var query = new Parse.Query(Parse.Object.extend("GameObject"));
  query.equalTo("Name", name);
  query.include('Missions');
  query.include('Missions.Rounds');
  query.include('Missions.Rounds.Assentors');
  query.include('Missions.Rounds.Dissentors');
  return query.first();
}

function addCloudToName(object) {
  object.set("Name", "clouuuuuuud");
  object.save();
}

/* function blatantly copied from stackOverflow: 
    http://stackoverflow.com/questions/8378870/generating-unique-random-numbers-integers-between-0-and-x */
function generateRandomNumbers(amount, lowerBound, upperBound) {
  var limit = upperBound - lowerBound + 1;
  var uniqueRandomNumbers = [];

  if (amount > limit) amount = limit;  // prevent infinite loop

  while (uniqueRandomNumbers.length < amount) {
      var randomNumber = Math.round(Math.random()*(upperBound - lowerBound) + lowerBound);
      if (uniqueRandomNumbers.indexOf(randomNumber) == -1) {
          uniqueRandomNumbers.push(randomNumber);
      }
  }
  // unique_random_numbers is an array containing 3 unique numbers in the given range
  return uniqueRandomNumbers;
}


