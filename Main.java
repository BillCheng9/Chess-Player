public class Main {
    public static void main(String[] args) {
        String board = "rnbqkbnr/pppppppp/8/8/3N4/8/PPPPPPPP/RNBQKB1R w KQkq - 0 1";
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
        long nm = (m << 6 | m << 10 | m << 15 | m << 17 | m >> 6 | m >> 10) & (~w);

        long WNM = Move.getWKnightMoves(WN, w);

        ChessBoard.drawArray(WN);
        System.out.println("");
        ChessBoard.drawArray(WNM);
        System.out.println("");
        ChessBoard.drawArray(m);
        System.out.println("");
        ChessBoard.drawArray(nm);
        System.out.println("");

        System.out.print(Integer.valueOf(Long.numberOfTrailingZeros(m))%8);
        System.out.println("");
        System.out.print(Long.toBinaryString(m));

        // test pawn move generation
        String pawnTestBoard = "rnbkqb1r/8/8/8/8/3N3n/PPPPPPP1/RNBKQB1R w KQkq - 0 1";
        ChessBoard chessBoardPawnTest = new ChessBoard(pawnTestBoard);

        long pawnTestWP = chessBoardPawnTest.getWP();
        long pawnTestWN = chessBoardPawnTest.getWN();
        long pawnTestWB = chessBoardPawnTest.getWB();
        long pawnTestWR = chessBoardPawnTest.getWR();
        long pawnTestWQ = chessBoardPawnTest.getWQ();
        long pawnTestWK = chessBoardPawnTest.getWK();
        long pawnTestBP = chessBoardPawnTest.getBP();
        long pawnTestBN = chessBoardPawnTest.getBN();
        long pawnTestBB = chessBoardPawnTest.getBB();
        long pawnTestBR = chessBoardPawnTest.getBR();
        long pawnTestBQ = chessBoardPawnTest.getBQ();
        long pawnTestBK = chessBoardPawnTest.getBK();

        long wPawnTest = pawnTestWP|pawnTestWN|pawnTestWB|pawnTestWR|pawnTestWQ|pawnTestWK;
        long bPawnTest = pawnTestBP|pawnTestBN|pawnTestBB|pawnTestBR|pawnTestBQ|pawnTestBK;

        System.out.println("\n" + "------ Test pawn moves ------" + "\n");
        ChessBoard.drawArray(pawnTestWP);
        System.out.println("");
        Move move = new Move();
        long WPM = move.getWPawnMoves(pawnTestWP, wPawnTest, bPawnTest);
        ChessBoard.drawArray(WPM);

        System.out.println("");
        System.out.println("------ Test evaluation function ------");
        System.out.println("Should be 0, because all pieces are on the board");
        System.out.println(chessBoard.evaluateWhite());
        System.out.println("Should be 7, because white is up by 7 pawns");
        System.out.println(chessBoardPawnTest.evaluateWhite());

    }
}
