package strategies.gamewinningstrategy;

import models.Board;
import models.Cell;
import models.Player;

public class OrderOneGameWinningStrategy implements GameWinningStrategy {
    public OrderOneGameWinningStrategy(int dimension) {
    }

    @Override
    public boolean checkWinner(Board board, Player player, Cell cell) {
        return false;
    }
}
