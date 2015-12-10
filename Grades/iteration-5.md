# Iteration 5 Evaluation - Group 11

**Evaluator: Scott Smith (mailto:scott@cs.jhu.edu)**

## Running the Project

* Your project fired up for me and ran for awhile but when I got 5 people in the game and hit the button I got

12-10 12:28:19.211 1742-1753/com.google.process.gapps E/StrictMode: A resource was acquired at attached stack trace but never released. See java.io.Closeable for information on avoiding resource leaks.
java.lang.Throwable: Explicit termination method 'end' not called
at dalvik.system.CloseGuard.open(CloseGuard.java:180)
at java.util.zip.Inflater.<init>(Inflater.java:82)
at com.android.okhttp.okio.GzipSource.<init>(GzipSource.java:62)
at com.android.okhttp.internal.http.HttpEngine.unzip(HttpEngine.java:645)

*-5 points*

## Testing

* Your tests are not hooked in, there was a compile error I had to fix (method name outdated).

* When I fixed the typo I got the following:

Running tests
Test running started
org.junit.experimental.theories.internal.ParameterizedAssertionError: testAddPlayersMax(data)

It looks from this that you are not actually running your tests - they are not very useful if you are not running them.

It looks like you didn't get too far on testing for the parse layer.

*-4 points*

## Iteration Plan

Good

## Code Quality

 * your JavaScript parse code is not a very elegant or object-oriented design.  This is one other problem with parse.  Try to clean this up for the final iteration -- see if you can pull apart some of the different components into different files/objects for example.  Turn some bigger blocks of code into methods (extract method), etc.

*-3 points*

## GitHub

Nice!

## Overall

* In your iteration plan you mentioned the problem of how hard it was to get things working given the order of events in the game.  It is important to make some sort of "cheat" for development to let you get around this kind of thing - for example a cheat button to jump to next phase of game, populating fields with some random values needed to get to the next state.

* In retrospect you probably would have been better off in SparkJava, you have a lot of computation server-side and Parse is not so good for that.  RESTful servers are easier to code and test independently.

**Grade: 88/100**
