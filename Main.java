public class Main {

    public static void main(String[] args) {
        String board = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        ChessBoard chessBoard = new ChessBoard(board);
        chessBoard.drawBoard();
    }
}
