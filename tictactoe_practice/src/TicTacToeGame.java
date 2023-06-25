import controllers.GameController;
import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class TicTacToeGame {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the dimension of the board: ");
        int dimension = in.nextInt();

        System.out.println("Are there any bots in this game? y/n");
        String isBotString = in.next();

        List<Player> players = new ArrayList<>();
        int toIterate = dimension - 1;

        if (isBotString.equals("y")) {
            toIterate -= 1;
            System.out.println("Enter the name of the bot: ");
            String name = in.next();

            System.out.println("Enter the symbol of the bot: ");
            char symbol = in.next().charAt(0);

            players.add(new Bot(name, symbol, BotDifficultyLevel.EASY));
        }
        for(int i=0;i<toIterate;i++){
            System.out.println("Enter the name of the player:" + (i+1));
            String name = in.next();

            System.out.println("Enter the symbol of the player:" + (i+1));
            char symbol = in.next().charAt(0);

            players.add(new Player(name, symbol, PlayerType.HUMAN));
        }

        GameController gameController = new GameController();
        Game game = gameController.createGame(
                dimension, players
        );

        while(gameController.getGameStatus(game).equals(GameStatus.IN_PROGRESS)){

            System.out.println("This is current state of the board:");

            gameController.displayBoard(game);

            System.out.println("Do you want to do a undo? y/n");
            String isUndo = in.next();

            if(isUndo.equals("y")){
                gameController.undo(game);
            }
            else{
                gameController.executeNextMove(game);
            }
        }

        // DRAW or ENDED
        System.out.println("Game has ended. The result was: ");
        if(game.getGameStatus().equals(GameStatus.ENDED)){
            System.out.println("The winner is: " + gameController.getWinner(game).getName());
            gameController.displayBoard(game);
        }

    }
}