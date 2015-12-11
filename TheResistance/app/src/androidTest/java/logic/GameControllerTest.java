package logic;

import android.util.Log;

import com.resistance.theresistance.logic.*;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.runners.Parameterized;

import com.parse.ParseException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.ArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Testing GameController.
 */
public class GameControllerTest {

    /**
     * Tests if checkHost works correctly.
     */
    @Test
    public void testCheckHost() {
        String playerName = "Candace";
        String gameName = "Game1";

        Game game = new Game();
        assertTrue(true);
        game.setKeyword(gameName);
        game.setHost(playerName);
        game.saveInBackground();
        assertTrue(GameController.checkHost(gameName, playerName));
    }


    /**
     * Test when there are too few players to start.
     */
    @Test
    public void testCheckNotEnoughPlayers() {
        String gameName = "Game2";

        Game game = new Game();
        game.setKeyword(gameName);
        game.setNumPlayers(1);
        game.saveInBackground();
        assertFalse(GameController.checkEnoughPlayers(gameName));
    }

    /**
     * Test when there are enough players to start.
     */
    @Test
    public void testCheckEnoughPlayers() {
        String gameName = "Game3";

        Game game = new Game();
        game.setKeyword(gameName);
        game.setNumPlayers(5);
        game.saveInBackground();
        assertTrue(GameController.checkEnoughPlayers(gameName));
    }

    @Test
    public void testUpdatePlayers() {
        String gameName = "Game4";
        Game game = new Game();
        game.setKeyword(gameName);

        Player player1 = new Player();
        player1.setUsername("Candace");
        player1.saveInBackground();

        Player player2 = new Player();
        player2.setUsername("Andrew");
        player2.saveInBackground();

        game.addPlayer(player1);
        game.addPlayer(player2);
        game.saveInBackground();

        ArrayList<String> playerNames = new ArrayList<>();
        playerNames.add("Candace");
        playerNames.add("Andrew");

        assertEquals(playerNames, GameController.updatePlayers(gameName));
    }

    @Test
    public void testCheckStarted() {

    }

    /**
     * Create new game testing.
     */
    @Test
    public void testNewGameCreation() {
        String myusername, gamekey;
        Game mygame;
        GameController controller = new GameController();
        ArrayList<Game> runningGames;

        myusername = "group11";
        gamekey = "Monica's GameRoom";
       /* controller.createGame(myusername, gamekey);

        runningGames = controller.getGames();
        mygame = runningGames.get(0);
        //the game is new and should have only 1 player - the host
        assert(mygame.getNumPlayers() == 1);
        //ensure that the game keyword is correct
        assert(mygame.getKeyword() == "Monica's GameRoom");
        //make sure that the player's user name is group11
        assert(mygame.getPlayers().get(0).getUsername() == "group11"); */
    }
}