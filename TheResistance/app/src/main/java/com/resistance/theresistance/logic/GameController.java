package com.resistance.theresistance.logic;

import android.util.Log;

import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.resistance.theresistance.activities.GamePlayActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * GameController class is in charge of controlling the Game.
 * Deals with Game related functions including starting and playing a Game.
 * @author Group 11
 *
 */
public class GameController {

    private static HashMap<Integer, List<Integer>> numMissionariesRequired;

    /**
     * Constructor for GameController.
     */
    public GameController(){
        this.numMissionariesRequired = new HashMap<>();

        //DELETE THE HASH MAP WITH KEYS 1-4 BECAUSE YOU CANNOT HAVE 1-4 PLAYERS. ONLY HERE FOR TESTING PURPOSES.
        numMissionariesRequired.put(1, Arrays.asList(1, 1, 1, 1, 1));
        numMissionariesRequired.put(2, Arrays.asList(1, 1, 1, 1, 1));
        numMissionariesRequired.put(3, Arrays.asList(1, 1, 1, 1, 1));
        numMissionariesRequired.put(4, Arrays.asList(1, 1, 1, 1, 1));

        numMissionariesRequired.put(5, Arrays.asList(2, 3, 2, 3, 3));
        numMissionariesRequired.put(6, Arrays.asList(2, 3, 4, 3, 4));
        numMissionariesRequired.put(7, Arrays.asList(2, 3, 3, 4, 4));
        numMissionariesRequired.put(8, Arrays.asList(3, 4, 4, 5, 5));
        numMissionariesRequired.put(9, Arrays.asList(3, 4, 4, 5, 5));
        numMissionariesRequired.put(10, Arrays.asList(3, 4, 4, 5, 5));
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
     * Checks if there are enough players in a game to start.
     * @param gameName Name of game
     * @return True if 5 or more players, False otherwise
     */
    public static boolean checkEnoughPlayers(String gameName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("checkEnoughPlayers game", "The retrieval succeeded");
            Game gameObject = (Game)object;
            if (gameObject.getNumPlayers() >= 5) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            Log.d("checkEnoughPlayers game", "The retrieval failed");
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
        query.include("Missions");
        query.include("Missions.Rounds");
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
     * Gets the number of missionaries required to go on a certain mission.
     * @param gameName Name of the game
     * @return Number of missionaries necessary
     */
    public static int getMissionariesRequired(String gameName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("getMissionariesRequired", "The retrieval succeeded");
            Game gameObject = (Game) object;
            int numPlayers = gameObject.getNumPlayers();
            int missionNumber = gameObject.getCurrentMissionNumber();
            return numMissionariesRequired.get(numPlayers).get(missionNumber-1);
        } catch (ParseException e) {
            Log.d("getMissionariesRequired", "The retrieval failed");
        }
        return 0;
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
            if (gameObject.getGameState() == Game.State.MISSION_LEADER_CHOOSING) {
                return false;
            }
            //add exceptions for wrong state
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

    public static List<String> getChosenMissionaries(String gameName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("getCurrentMission game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            return gameObject.getCurrentMission().getCurrentRound().getMissionaries();
        } catch (ParseException e) {
            Log.d("getCurrentMission game", "The retrieval failed");
            return null;
            //ADD ERROR FOR NULL?
        }
    }

    public static void addVoteForMissionaries(boolean vote, String gameName, String playerName) {
        //Call Cloud function
        HashMap<String,Object> arguments = new HashMap<>();
        arguments.put("Name", gameName);
        arguments.put("PlayerName", playerName);
        arguments.put("Vote", vote);
        try {
            ParseCloud.callFunction("addVoteForMissionaries", arguments);
        } catch (ParseException e) {
            Log.d("startGame", "Cloud function did not call successfully");
        }
    }

    /**
     * Checks if everyone has voted by checking the state of the game. Returns the game of the state when everyone has voted.
     * @param gameName Name of game
     * @return Game State Enum, Mission leader choosing or missionaries voting
     */
    public static Game.State ifEveryoneDoneVoting(String gameName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("ifEveryoneDoneVoting", "The retrieval succeeded");
            Game gameObject = (Game) object;
            if (gameObject.getGameState() == Game.State.MISSION_LEADER_CHOOSING) {
                return Game.State.MISSION_LEADER_CHOOSING;
            } else if (gameObject.getGameState() == Game.State.MISSIONARIES_VOTING) {
                return Game.State.MISSIONARIES_VOTING;
            }
        } catch (ParseException e) {
            Log.d("ifEveryoneDoneVoting", "The retrieval failed");
        }
        return null;
    }

    public static void addPassFailForMission(boolean vote, String gameName) {
        //Call Cloud function
    }

    public static Game.State ifMissionariesDoneVoting(GamePlayActivity activity, String gameName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("playGame game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            Game.State state = gameObject.getGameState();
            if (state == Game.State.SPIES_WIN || state == Game.State.RESISTANCE_WINS || state == Game.State.MISSION_LEADER_CHOOSING) {
                if (state == Game.State.MISSION_LEADER_CHOOSING) {
                    List<Mission> missions = gameObject.getMissions();
                    if (missions.get(missions.size()-2).getPassed()) {
                        activity.showMissionPassed(missions.size()-1);
                    } else {
                        activity.showMissionFailed(missions.size()-1);
                    }
                }
                return state;
            }
        } catch (ParseException e) {
            Log.d("playGame game", "The retrieval failed");
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

}