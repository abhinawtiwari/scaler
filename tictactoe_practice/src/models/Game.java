package models;

import exceptions.InvalidGameConstructionParametersException;
import strategies.gamewinningstrategy.GameWinningStrategy;
import strategies.gamewinningstrategy.OrderOneGameWinningStrategy;

import java.util.ArrayList;
import java.util.List;
public class Game {
    private Board board;
    private List<Player> playerList;
    private List<Move> moveList;
    private GameStatus gameStatus;
    private int nextPlayerIndex;

    private GameWinningStrategy gameWinningStrategy;
    private Player winner;

    private Game() {

    }
    public static Builder getBuilder() {
        return new Builder();
    }
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Move> getMoveList() {
        return moveList;
    }

    public void setMoveList(List<Move> moveList) {
        this.moveList = moveList;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public GameWinningStrategy getGameWinningStrategy() {
        return gameWinningStrategy;
    }

    public void setGameWinningStrategy(GameWinningStrategy gameWinningStrategy) {
        this.gameWinningStrategy = gameWinningStrategy;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void undo() {}
    public void makeNextMove() {
        Player toMovePlayer = playerList.get(nextPlayerIndex);
        System.out.println("It is " + toMovePlayer.getName() + "'s turn");

        Move move = toMovePlayer.decideMove(board);

        // validate the move

        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        System.out.println("Move happened at: " + row + ", " + col + ".");

        // update board
        board.getBoard().get(row).get(col).setCellState(CellState.FILLED);
        board.getBoard().get(row).get(col).setPlayer(toMovePlayer);

        Move finalMove = new Move(
                toMovePlayer,
                board.getBoard().get(row).get(col)
        );

        moveList.add(finalMove);

        if(gameWinningStrategy.checkWinner(board,
                toMovePlayer, finalMove.getCell())){
            gameStatus = GameStatus.ENDED;
            winner = toMovePlayer;
        }

        nextPlayerIndex += 1;
        nextPlayerIndex %= playerList.size();
    }
    public void displayBoard() {
        this.board.display();
    }

    public static class Builder {
        private int dimension;
        private List<Player> playerList;

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setPlayerList(List<Player> playerList) {
            this.playerList = playerList;
            return this;
        }

        private boolean validate() throws InvalidGameConstructionParametersException {
            if(dimension < 3) {
                throw new InvalidGameConstructionParametersException("Dimension of game can't be less than 3");
            }
            if (this.playerList.size() != this.dimension -1) {
                throw new InvalidGameConstructionParametersException("Number of players should be 1 less than dimension");
            }
            return true;
        }

        public Game build() throws InvalidGameConstructionParametersException {
            validate();
            Game game = new Game();
            game.setGameStatus(GameStatus.IN_PROGRESS);
            game.setPlayerList(playerList);
            game.setMoveList(new ArrayList<>());
            game.setBoard(new Board(dimension));
            game.setGameWinningStrategy(new OrderOneGameWinningStrategy(dimension));

            game.setNextPlayerIndex(0);
            return game;
        }
    }
}
