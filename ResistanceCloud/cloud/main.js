
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

/* executes all the setup code to start the game
   {"Name" : String} Name of the game */
Parse.Cloud.define("startGame", function(request, response) {
  startGame(request,response);
});

var startGame = function(request, response) {
  (function() {
  return findObject("GameObject", request.params.Name);
  }()).then(function(game){
    // only use promise for assumed slowest function.
    // should probably be parallel promises
    actuallySetPlayerRoles(game);
    //set pointer in mission object to player object for mission leader
    //update game state
    return makeFirstMission(game);
  }).then(function() {
    console.log("response sucess");
    response.success();
  }), function(error) {
    response.error("something fucked up");
  };
}

// makes the first round.
// makes the first mission and adds the first round to it.
// adds the mission to the game
var makeFirstMission = function(game) {
  var promise = new Parse.Promise();
  var RoundObject = Parse.Object.extend("RoundObject");
  var firstRound = new RoundObject();
  firstRound.save().then(function(firstRound) {
    var MissionObject = Parse.Object.extend("MissionObject");
    var firstMission = new MissionObject();
    firstMission.add("Rounds",firstRound);
    return firstMission.save();
  }).then(function(firstMission) {
    game.add("Missions", firstMission);
    game.save();
  }).then(function() {
    promise.resolve();
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
  var players = game.get("Player");
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
  /* [query to find the player names] */
  /* [check to make sure the leader hasn't already been chosen] */
  /* [determine how many players there are] */
  var numPlayers = 7; //change to take the right argument
  var uniqueRandomNumbers = generateRandomNumbers(1,1,numPlayers);
  console.log("first mission leader has index " + uniqueRandomNumbers.pop());
  response.success();
});

/* use a beforeSave on vote objects to start the mission outcome calculation
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


