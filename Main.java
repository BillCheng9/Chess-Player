public class Main {
    public static void main(String[] args) {
        String board = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        ChessBoard chessBoard = new ChessBoard(board);

        long WP = chessBoard.getWP();
        long WN = chessBoard.getWN();
        long WB = chessBoard.getWB();
        long WR = chessBoard.getWR();
        long WQ = chessBoard.getWQ();
        long WK = chessBoard.getWK();
        long BP = chessBoard.getBP();
        long BN = chessBoard.getBN();
        long BB = chessBoard.getBB();
        long BR = chessBoard.getBR();
        long BQ = chessBoard.getBQ();
        long BK = chessBoard.getBK();

        long allWhite = WP|WN|WB|WR|WQ|WK;
        long allBlack = BP|BN|BB|BR|BQ|BK;

        long allBoard = allWhite|allBlack;

        chessBoard.drawArray(allWhite);
        System.out.println();
        chessBoard.drawArray(allBlack);
        System.out.println();
        chessBoard.drawArray(allBoard);
    }
}
