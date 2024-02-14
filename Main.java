public class Main {
    public static void main(String[] args) {
        String board = "rnbqkbnr/pppppppp/8/8/3N4/8/PPPPPPPP/R1BQKB1R w KQkq - 0 1";
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

        long allwhite = WP|WN|WB|WR|WQ|WK;
        long allblack = BP|BN|BB|BR|BQ|BK;
        
        long WNM = (WN << 6 | WN << 10 | WN << 15 | WN << 17 | WN >> 6 | WN >> 10) & (~allwhite);

        ChessBoard.drawArray(WN);
        System.out.println("");
        ChessBoard.drawArray(WNM);
    }
}
