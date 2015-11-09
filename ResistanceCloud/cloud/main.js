
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});

Parse.Cloud.define("cloudifyNameObject", function(request, response) {
  var playerName = request.params.Name;
  var query = new Parse.Query(Parse.Object.extend("NameObject"));
  query.equalTo("Name", playerName);
  query.first().then(function(object) {
        if (!object)
          response.error("no object by this name");
        object.set("Name", "clouuuuuuud");
        object.save();
        console.log("changed player name");
        response.success();
      }, function(error) {
        response.error("Something fucked up.");
    });
});

Parse.Cloud.define("determinePlayerRoles", function(request, response) {
  /* [query to find the player names] */
  /* [determine how many players there are] */
  var numPlayers = 7; //change to take the right argument
  var numSpies = ~~(numPlayers*.43);
  var uniqueRandomNumbers = generateRandomNumbers(numSpies,1,numPlayers);
  var consoleString = "";
  consoleString += "spies are players with indices";
  uniqueRandomNumbers.forEach(function(number) {
    consoleString += (" " + number);
  });
  consoleString += ".";
  console.log(consoleString);
  response.success();
});

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


