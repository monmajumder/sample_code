/* Unit Testing Mock */
var message = require('cloud/message.js');
Parse.Cloud.define("get_message", function (request, response) {
  message.getMessage(request, response);
});


/**
  Gets the history of a game.

  @params
  -------
  {"Name" : String} Name of the game 
  
  @returns
  --------
  {"PlayerNames" : array} Player names
  {"numPlayers" : int} Number of players
  {"numMissions" : int} Number of missions
  {"missions" : Object[] } Information from each mission
      {"numRounds" : int } Number of rounds in the mission
      {"rounds" : Object[] } Information from each round
          {"assentors" : String[] } Players who approved the missionary selection
          {"dissentors" : String[] } Players who declined the missionary selection
          {"leader" : string } Name of the Mission leader
          {"missionaries" : String[] } Players selected to go on the mission
*/

Parse.Cloud.define("getHistory", function(request, response) {
  getHistory(request,response);
});

var getHistory = function(request, response) {
  findGameWithMissionsAndRounds(request.params.Name).then(function(game){
    return actuallyGetHistory(game);
  }).then(function(history) {
    response.success(history);
  }), function(error) {
    response.error("something fucked up");
  };
}

var actuallyGetHistory = function(game) {
  var promise = new Parse.Promise();
  var history = {};
  var players = game.get("Players");
  history.playerNames = players[0].get("Name");
  history.playerNames = getPlayerNames(game);
  history.numPlayers = game.get("Players").length;
  history.missions = getMissions(game);
  history.numMissions = game.get("Missions").length;
  promise = Parse.Promise.as(history);
  return promise;
}

function getMissions(game) {
  var missions = [];
  for (var i = 0; i < game.get("Missions").length; i++) { //for every mission
    missions.push(getMissionInfo(game.get("Missions")[i]));
  }
  return missions;
}

function getMissionInfo(mission) {
  var missionInfo = {};

  missionInfo.numRounds = mission.get("Rounds").length;
  missionInfo.rounds = [];
  for (var i = 0; i < mission.get("Rounds").length; i++) { 
    missionInfo.rounds.push(getRoundInfo(mission.get("Rounds")[i]));
  }
  return missionInfo;
}

function getRoundInfo(round) {
  var roundInfo = {};
  roundInfo.missionaries = getMissionaryNames(round);
  roundInfo.leader = round.get("Leader");
  roundInfo.assentors = isDefined(round.get("Assentors")) ? round.get("Assentors"): [];
  roundInfo.dissentors = isDefined(round.get("Dissentors")) ? round.get("Dissentors"): [];
return roundInfo;
}

//tbd: missions don't have missionaries yet
function getMissionaryNames(round) {
  var missionaries = [];
  if (isDefined(round.get("Missionaries")))
    for (var i = 0; i < round.get("Missionaries").length; i++)
      missionaries.push(round.get("Missionaries")[i].get("Name")); //not debugged, sadly
  return missionaries;
}

function getPlayerNames(game) {
  var playerNames = [];
  for (var i = 0; i < game.get("Players").length; i++)
    playerNames.push(game.get("Players")[i].get("Name"));
  return playerNames;
}

/**
  Tallies a mission vote.
  Checks if every player has voted.
  If necessary, calculates the result of voting.

  {"Name" : String} Name of the game
  {"Vote" : Boolean} Vote */
Parse.Cloud.define("addMissionVote", function(request, response) {
  addMissionVote(request,response);
});

var addMissionVote = function(request, response) {
  findGameWithMissionsAndRounds(request.params.Name).then(function(game){
    return actuallyAddMissionVote(game,request.params.Vote);
  }).then(function(game) {
    if (!gameIsOver(game))
      return changeGameStatus(game,"MISSION_LEADER_CHOOSING");
  }).then(function() {
    response.success();
  }), function(error) {
    response.error("something fucked up");
  };
}

var actuallyAddMissionVote = function(game, vote) {
  var promises = [];
  promises.push(Parse.Promise.as(game));
  //find current mission
  var missions = game.get("Missions");
  console.log("missions: " + missions);
  var currentMission = missions[missions.length-1];
  console.log("currentMission:" + currentMission);
  var numPass = currentMission.get("Pass");
  var numFail = currentMission.get("Fail");
  //add vote
  if (vote)
    currentMission.set("Pass", ++numPass);
  else {
    currentMission.set("Fail", ++numFail);
    console.log("adding a fail vote");
  }

  //calculate number of players and votes
  var numVotes = numPass + numFail;

  if (votingFinished(game,numVotes)) { //voting finished. update mission
    if (numFail == 0) 
      currentMission.set("Passed", true);
    else 
      currentMission.set("Passed", false);
    checkIfGameOver(game).then(function(game) {
      if (!gameIsOver(game))
        promises.push(addMissionAndSetMissionLeader(game));
    }), function(error) {
      promise.reject();
    };
  }
  promises.push(currentMission.save());
  return Parse.Promise.when(promises);
}

function votingFinished(game, numVotes) {
  return (numVotes >= 2); //tbd: actually check how many missionaries are on the team
}

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
  findGameWithMissionsAndRounds(request.params.Name).then(function(game){
    var missions = game.get("Missions");
    var rounds = missions[missions.length-1].get("Rounds");
    console.log("in addVoteForMissionaries. Rounds: " + rounds);
    return actuallyAddVoteForMissionaries(game, request.params.PlayerName,
      request.params.Vote);
  }).then(function() {
    console.log("addVoteForMissionaries giving response.success");
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
  }
  //calculate number of players and votes
  var numPlayers = game.get("Players").length;
  var numAssentors = isDefined(currentRound.get("Assentors")) ? currentRound.get("Assentors").length : 0;
  var numDissentors = isDefined(currentRound.get("Dissentors")) ? currentRound.get("Dissentors").length : 0;
  var numVotes = numAssentors + numDissentors;

  if (numVotes >= numPlayers) { //voting finished. update round
    (function() {
      promises = [];
      if (numAssentors > numDissentors) {
        promises.push(Parse.Promise.as("Missionaries Approved"));
        promises.push(passMissionaries(currentRound,game));
      }
      else {
        promises.push(Parse.Promise.as("Missionaries Not Approved"));
        promises.push(failMissionaries(currentRound,currentMission,game));
      }
      return Parse.Promise.when(promises);
    }()).then(function(string, game) {
      if (string === "Missionaries Approved")
        return changeGameStatus(game, "MISSIONARIES_VOTE");
      else
        return changeGameStatus(game,"MISSION_LEADER_CHOOSING");
    }).then(function(game) {
      promise.resolve(game);
    }), function(error) {
      promise.reject();
    };
  }
  else {//save votes
    currentRound.save().then(function() {
      promise.resolve(game);
    });
  }
  return promise;
}

function isDefined(object) {
  return typeof object !== 'undefined';
}

//fails the mission
function failMission(currentMission,game){
  var promise = new Parse.Promise();

  currentMission.set("Passed", false);
  currentMission.save().then(function() {
    return checkIfGameOver(game);
  }).then(function(game) {
    var missions = game.get("Missions");
    var rounds = missions[missions.length-1].get("Rounds");
    console.log("in failMission. We have  " + missions.length + " mission and Rounds: " + rounds);
    if (gameIsOver(game)) {
      console.log("the game is over.");
      return game.save();
    }
    else {
      return addMissionAndSetMissionLeader(game);
    }
  }).then(function() {
    promise.resolve();
  }), function(error) {
    promise.reject();
  };
  console.log("returning the fail mission promise");
  return promise;
}

function addMissionAndSetMissionLeader(game) {
  var promise = new Parse.Promise();

  console.log("adding a new mission in addMissionAndSetMissionLeader to game [" + game.id + "]");
  var missions = game.get("Missions");
  var rounds = missions[missions.length-1].get("Rounds");
  console.log("in addMissionAndSetMissionLeader. We have " + missions.length + " missions and Rounds: " + rounds);
  addMission(game).then(function(game,round) {
    console.log("finished the first promise in failMission");
    var missions = game.get("Missions");
    console.log("in addMissionAndSetMissionLeader we have " + missions.length + " missions, second has id " + missions[1].id);
    console.log("in addMissionAndSetMissionLeader the mission's rounds are " + missions[missions.length-1].get("Rounds"));
    return setNextMissionLeader(game,round);
  }).then(function(game,round) {
    promise.resolve(game,round);
  }), function(error) {
    promise.reject();
  };
  return promise;
}

function gameIsOver(game) {
  return ((game.get("State") === "SPIES_WIN") || (game.get("State") === "RESISTANCE_WINS"));
}

//checks if game is over
//changes the game status if it is
function checkIfGameOver(game) {
  var missions = game.get("Missions");
  var numMissionsPassed = 0;
  var numMissionsFailed = 0;
  for (var i = 0; i < missions.length; i++) {
    if (missions[i].get("Passed") === true)
      numMissionsPassed++;
    else if (missions[i].get("Passed") === false)
      numMissionsFailed++;
  }
  console.log("checking if game is over with" + numMissionsPassed + " missions passed and " + numMissionsFailed + " numMissionsFailed");
  if (numMissionsPassed >= 3) {
    return changeGameStatus(game, "RESISTANCE_WINS");
  }
  else if (numMissionsFailed >= 3) {
    return changeGameStatus(game, "SPIES_WIN");
  }
  else
    return Parse.Promise.as(game);
}

//fails the missionary vote
//creates a new round object, adds it to the rounds array in mission object
//changes game state to MISSION_LEADER_CHOOSING
function failMissionaries(currentRound, currentMission, game) {
  var promise = new Parse.Promise();

  currentRound.set("MissionariesAccepted", false);
  currentRound.save().then(function() {
    if (currentMission.get("Rounds").length >= 5) {
      console.log("mission fails because " + game.get("Missions")[0].get("Rounds").length + " rounds were rejected.");
      console.log("calling failMission with game: " + game.id);
      var missions = game.get("Missions");
      var rounds = missions[missions.length-1].get("Rounds");
      console.log("in failMissionaries. Rounds: " + rounds);
      return failMission(currentMission,game);
    }
    else {
      return makeNextRoundInMission(game,currentMission);
    }
  }).then(function() {
    promise.resolve();
  }), function(error) {
    promise.reject();
  };
  return promise;
}

function makeNextRoundInMission(game, currentMission) {
  var promise = new Parse.Promise();
  var RoundObject = Parse.Object.extend("RoundObject");
  var nextRound = new RoundObject();
  nextRound.save().then(function(nextRound) {
    currentMission.add("Rounds",nextRound);
    currentMission.save(); //not synchronous...
    return setNextMissionLeader(game,nextRound);
  }).then(function(game,round) {
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
    return findGameWithPlayers(request.params.Name);
  }()).then(function(game){
    return actuallySetPlayerRoles(game);
  }).then(function(game){
    return addMission(game);
  }).then(function(game,round) {
    console.log("after addMission in startGame");
    var missions = game.get("Missions");
    console.log("we have " + missions.length + " missions, first has id " + missions[0].id);
    console.log("the mission's rounds are " + missions[missions.length-1].get("Rounds"));
    return setRandomMissionLeader(game,round);
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

  console.log("in fcn addMission");
  var RoundObject = Parse.Object.extend("RoundObject");
  var firstRound = new RoundObject();
  firstRound.save().then(function(firstRound) { //sets and saves round
    console.log("saved the first round successfully");
    var MissionObject = Parse.Object.extend("MissionObject");
    var mission = new MissionObject();
    mission.add("Rounds",firstRound);
    mission.set("Pass",0);
    mission.set("Fail",0);
    return mission.save();
  }).then(function(mission) {
    game.add("Missions", mission);
    var missions = game.get("Missions");
    var rounds = missions[missions.length-1].get("Rounds");
    console.log("in addMission before saving game. We have  " + missions.length + " mission and Rounds: " + rounds);
    return game.save();
  }).then(function(game) {
    return findGameWithMissionsAndRounds(game.get("Name")); //game.save doesn't contain rounds...
  }).then(function(game) {
    console.log("resolving add mission promise");
    var missions = game.get("Missions");
    var rounds = missions[missions.length-1].get("Rounds");
    console.log("in addMission. We have  " + missions.length + " mission and Rounds: " + rounds);
    promise.resolve(game,firstRound);
  }), function(error){
    response.error("fuck");
  };
  return promise;
}

function actuallySetPlayerRoles(game) {
  var promises = [];
  console.log("in set player roles");
  promises.push(Parse.Promise.as(game)); //push the game onto the stack of promises so we have it later in the stack of function calls
  var players = game.get("Players");
  var numPlayers = players.length;  
  console.log("numPlayers " + numPlayers);
  var numSpies = ~~(numPlayers*.43); //magically calculates the correct number of spies
  var uniqueRandomNumbers = generateRandomNumbers(numSpies,0,numPlayers - 1);
  consoleString = "spies are players with indices";
  var roles = {};
  uniqueRandomNumbers.forEach(function(spyIndex) {
    roles[spyIndex] = 1;
    consoleString += " " + spyIndex;
  });
  console.log(consoleString + ".");
  for (i = 0; i < numPlayers; i++) {
    if (roles[i] == null)
      players[i].set("PlayerType", "RESISTOR");
    else
      players[i].set("PlayerType", "SPY");
    promises.push(players[i].save());
  }
  return Parse.Promise.when(promises);
}

// sets a random mission leader for the roung passed
// saves the round
var setRandomMissionLeader = function(game,round) {
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
  round.set("Leader", players[missionLeaderIndex].get("Name"));
  round.save().then(function() {
    promise.resolve(game);
  }), function(error) {
    promise.reject();
  };
  return promise;
}

// sets the mission leader to the player with the next array index
// loops around, of course
// assumes that the mission leader of the previous round
var setNextMissionLeader = function(game,round) {
  var promise = new Parse.Promise();

  var missions = game.get("Missions");
  var currentMission = missions[missions.length-1];  
  var rounds = currentMission.get("Rounds");
  console.log("rounds: " + rounds);
  console.log("rounds.length: " + rounds.length);
  if (rounds.length === 1) { //the current round is the first round of a new mission
    var previousMission = missions[missions.length - 2];
    var previousRounds = previousMission.get("Rounds");
    previousRound = previousRounds[previousRounds.length - 1];
  }
  else {
    var previousRound = rounds[rounds.length-2];
  }
  console.log("previous round:" + previousRound);
  if (typeof previousRound === 'undefined') throw "Can't set next mission leader without previous round.";
  
  var previousLeader = previousRound.get("Leader");
  var newLeader;
  var players = game.get("Players");
  for (var i = 0; i < players.length; i++) {
    if (players[i].get("Name") === previousLeader)
      newLeader = players[(i+1)%players.length].get("Name");
  }
  console.log("previous round had leader with name " + previousLeader);
  if (!isDefined(newLeader))
    throw "Couldn't find previous leader";
  round.set("Leader", newLeader);
  round.save().then(function() {
    promise.resolve(game,round);
  }), function(error) {
    promise.reject();
  };
  return promise;

}

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
  query.include('Players');
  //somehow the query breaks when you include these things
  query.include('Missions.Rounds');
  //query.include('Missions.Rounds.Assentors');
  //query.include('Missions.Rounds.Dissentors');
  //query.include('Missions.Rounds.Leader');
  return query.first();
}

function findGameWithPlayers(name) {
  var query = new Parse.Query(Parse.Object.extend("GameObject"));
  query.equalTo("Name", name);
  query.include('Players');
  return query.first();
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


