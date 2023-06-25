package strategies.gamewinningstrategy;

import models.Board;
import models.Cell;
import models.Player;

public interface GameWinningStrategy {
    boolean checkWinner(Board board, Player player, Cell cell);
}
