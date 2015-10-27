package logic;

import com.resistance.theresistance.logic.*;
import org.junit.Test;
import java.util.regex.Pattern;
import java.util.ArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

public class GameControllerTest {

    /*
     * Create New Game Testing
     */

    public enum PlayerType {UNDECIDED}

    @Test
    public void testTwoPlusTwo() {
        assertTrue(2+2 == 4);
    }
    //@Test
    //public void ILoveBigBrother() {
    //    assertTrue(2+2 == 5);
    //}

    @Test
    public void testNewGameCreation() {
        String myusername, gamekey;
        Game mygame;
        GameController controller = new GameController();
        ArrayList<Game> runningGames;

        myusername = "group11";
        gamekey = "Monica's GameRoom";
        controller.createGame(myusername, gamekey);

        runningGames = controller.getGames();
        mygame = runningGames.get(0);
        //the game is new and should have only 1 player - the host
        assert(mygame.getNumPlayers() == 1);
        //ensure that the game keyword is correct
        assert(mygame.getKeyword() == "Monica's GameRoom");
        //make sure that the player's user name is group11
        assert(mygame.getPlayers().get(0).getUsername() == "group11");
    }
}