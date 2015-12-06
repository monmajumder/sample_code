package com.resistance.theresistance.logic;

import android.util.Log;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.resistance.theresistance.activities.GamePlayActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * GameController class is in charge of controlling the Game.
 * Deals with Game related functions including starting and playing a Game.
 * @author Group 11
 *
 */
public class GameController {

    /**
     * Constructor for GameController.
     */
    public GameController(){
        super();
    }

    /**
     * Checks whether or not a player is the host.
     * @param gameName Name of the game
     * @param playerName Name of the player
     * @return True if player is the host, false otherwise
     */
    public static boolean checkHost(String gameName, String playerName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("checkHost game", "The retrieval succeeded");
            Game gameObject = (Game)object;
            if (gameObject.getHost().equals(playerName)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            Log.d("checkHost game", "The retrieval failed");
            return false;
        }
    }

    /**
     * Extracts the name of the players currently in a game.
     * @param gameName Name of the game
     * @return ArrayList containing the names of the Players in a game
     */
    public static ArrayList<String> updatePlayers(String gameName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("updatePlayers game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            ArrayList<String> allPlayers = new ArrayList<>();

            List<Player> listOfPlayers = gameObject.fetchIfNeeded().getList("Players");
            for (Player player : listOfPlayers) {
                allPlayers.add(player.fetchIfNeeded().getString("Name"));
            }
            return allPlayers;
        } catch (ParseException e) {
            Log.d("updatePlayers game", "The retrieval failed");
            return null;
        }
    }

    /**
     * Checks whether a game is started.
     * @param gameName Name of the game
     * @return True if game has started, false otherwise
     */
    public static boolean checkStarted(String gameName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("checkStarted game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            if (gameObject.getGameState() == Game.State.WAITING_FOR_PLAYERS) {
                return false;
            }
            return true;
        } catch (ParseException e) {
            Log.d("checkStarted game", "The retrieval failed");
            return false;
        }
    }

    /**
     * Checks whether a certain player is Resistance.
     * @param playerName Name of player
     * @return True if player is Resistor, false otherwise
     */
    public static boolean isResistance(String playerName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PlayerObject");
        query.whereEqualTo("Name", playerName);
        try {
            ParseObject object = query.getFirst();
            Log.d("isResistance player", "The retrieval succeeded");
            Player playerObject = (Player) object;
            if (playerObject.getPlayerType() == Player.PlayerType.RESISTOR) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            Log.d("isResistance player", "The retrieval failed");
            return false;
        }
    }

    /**
     * Checks whether a certain player is the Mission Leader.
     * @param gameName Name of the game
     * @param playerName Name of the player
     * @return True if yes, false otherwise
     */
    public static boolean checkLeader(String gameName, String playerName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("checkLeader game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            if (gameObject.getCurrentLeader().equals(playerName)) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            Log.d("checkLeader game", "The retrieval failed");
            return false;
        }
    }

    /**
     * Checks whether a certain player is a Missionary.
     * @param gameName Name of the game
     * @param playerName Name of the player
     * @return True if yes, false otherwise
     */
    public static boolean checkMissionary(String gameName, String playerName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("checkMissionary game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            if (gameObject.getCurrentMission().getCurrentRound().getMissionaries().contains(playerName)) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            Log.d("checkMissionary game", "The retrieval failed");
            return false;
        }
    }

    /**
     * Checks whether a Mission Leader has finished choosing Missionary Team.
     * @param gameName Name of the game
     * @return True if Mission Leader is finished choosing, false otherwise
     */
    public static boolean checkMissionLeaderDoneChoosing(String gameName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("checkStarted game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            if (gameObject.getGameState() == Game.State.WAITING_FOR_PLAYERS) {
                return false;
            }
            return true;
        } catch (ParseException e) {
            Log.d("checkStarted game", "The retrieval failed");
            return false;
        }
    }

    /**
     * Returns the current Mission object
     * @param gameName Name of the game
     * @return Mission object that is the current mission
     */
    public static Mission getCurrentMission(String gameName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("getCurrentMission game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            return gameObject.getCurrentMission();
        } catch (ParseException e) {
            Log.d("getCurrentMission game", "The retrieval failed");
        }
        return null;
    }

    /**
     * Queries for the game and changes the game state
     * @param gameName Name of the game
     * @param state New game state
     */
    public static void changeState(String gameName, Game.State state) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("changeState game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            gameObject.setGameState(state);
        } catch (ParseException e) {
            Log.d("changeState game", "The retrieval failed");
        }
    }

    /**
     * Adds the chosen missionaries to the Round object
     * @param gameName Name of the game
     * @param missionaries List of player names who were chosen to be missionaries
     */
    public static void addChosenMissionaries(String gameName, ArrayList<String> missionaries) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("getCurrentMission game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            gameObject.getCurrentMission().getCurrentRound().setMissionaries(missionaries);
        } catch (ParseException e) {
            Log.d("getCurrentMission game", "The retrieval failed");
        }
    }


    public static void ifMissionLeaderDoneChoosing(String gameName) {
        //Every few seconds, query for game state
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("playGame game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            if (gameObject.getGameState() == Game.State.VOTE_FOR_MISSIONARIES) {
                //Change visibilities to display the chosen missionary team
                //Change visibilities to vote on missionary team
                return;
            }
        } catch (ParseException e) {
            Log.d("playGame game", "The retrieval failed");
        }
    }

    public static void ifEveryoneDoneVoting(String gameName) {
        //Every few seconds, query for game state
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("playGame game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            if (gameObject.getGameState() == Game.State.MISSION_LEADER_CHOOSING) {
                //CREATE NEW ROUND OBJECT AND ADD TO CURRENT MISSION OBJECT
                //CHOOSE NEXT MISSION LEADER
                //CHECK LEADER -> CHANGE VISIBILITIES
                return;
            } else if (gameObject.getGameState() == Game.State.MISSIONARIES_VOTING) {
                //CHECK MISSIONARY -> CHANGE VISIBILITIES

                return;
            }
        } catch (ParseException e) {
            Log.d("playGame game", "The retrieval failed");
        }
    }

    public static void ifMissionariesDoneVoting(String gameName) {
        //Every few seconds, query for game state
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("playGame game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            if (gameObject.getGameState() == Game.State.SPIES_WIN) {
                // CHANGE VISIBILITIES
                // do something to restart game
                return;
            } else if (gameObject.getGameState() == Game.State.RESISTANCE_WINS) {
                //CHANGE VISIBILITIES
                // do something to restart game
                return;
            } else if (gameObject.getGameState() == Game.State.MISSION_PASSED) {
                //CHANGE VISIBILITIES
                // call cloud code?
                return;
            } else if (gameObject.getGameState() == Game.State.MISSION_FAILED) {
                //CHANGE VISIBILITIES
                //call cloud code?
                return;
            }
        } catch (ParseException e) {
            Log.d("playGame game", "The retrieval failed");
        }
    }

}