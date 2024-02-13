public class Main {
    public static void main(String[] args) {
        String board = "rnbqkbnr/pppppppp/8/8/3N4/8/PPPPPPPP/R1BQKBNR w KQkq - 0 1";
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

        long w = WP|WN|WB|WR|WQ|WK;
        long b = BP|BN|BB|BR|BQ|BK;
        long m = Long.highestOneBit(WN);
        long km = (m << 6 | m << 10 | m << 15 | m << 17 | m >> 6 | m >> 10) & (~w);

        long WKM = Move.getWKnightMoves(WN, w);

        chessBoard.drawArray(WN);
        System.out.println("");
        chessBoard.drawArray(WKM);
        System.out.println("");
        chessBoard.drawArray(m);
        System.out.println("");
        chessBoard.drawArray(km);
        System.out.println("");

        System.out.print(Integer.valueOf(Long.numberOfTrailingZeros(m))%8);
        System.out.println("");
        System.out.print(Long.toBinaryString(m));
    }
}
