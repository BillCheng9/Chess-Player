public class Main {
    public static void main(String[] args) {
        System.out.println("\n" + "------ Test Knight moves ------" + "\n");
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

        long WNM = Move.getWKnightMoves(WN, w);

        ChessBoard.drawArray(WN);
        System.out.println("");
        ChessBoard.drawArray(WNM);
        System.out.println("");

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

        System.out.println("-------Test King moves-------" + "\n");
        String kboard = "rnbqkbnr/pppppppp/8/8/3N4/8/8/4K3 w KQkq - 0 1";
        ChessBoard kchessBoard = new ChessBoard(kboard);
        long KWP = kchessBoard.getWP();
        long KWN = kchessBoard.getWN();
        long KWB = kchessBoard.getWB();
        long KWR = kchessBoard.getWR();
        long KWQ = kchessBoard.getWQ();
        long KWK = kchessBoard.getWK();
        long KBP = kchessBoard.getBP();
        long KBN = kchessBoard.getBN();
        long KBB = kchessBoard.getBB();
        long KBR = kchessBoard.getBR();
        long KBQ = kchessBoard.getBQ();
        long KBK = kchessBoard.getBK();

        long wKTest = KWB | KWR | KWP | KWN | KWQ | KWK;
        long bKTest = KBP | KBN | KBB | KBR | KBQ | KBK;

        System.out.println("White king position");
        kchessBoard.drawArray(KWK);
        System.out.println("White king moves");
        long WKkM = Move.getWKingMoves(KWK, wKTest);
        kchessBoard.drawArray(WKkM);

        System.out.println("-------Test Bishop moves-------" + "\n");
        String bboard = "rnbqkbnr/pppppppp/8/3B4/8/8/PPPPPPPP/8 w KQkq - 0 1";
        ChessBoard bchessBoard = new ChessBoard(bboard);
        long BWP = bchessBoard.getWP();
        long BWN = bchessBoard.getWN();
        long BWB = bchessBoard.getWB();
        long BWR = bchessBoard.getWR();
        long BWQ = bchessBoard.getWQ();
        long BWK = bchessBoard.getWK();
        long BBP = bchessBoard.getBP();
        long BBN = bchessBoard.getBN();
        long BBB = bchessBoard.getBB();
        long BBR = bchessBoard.getBR();
        long BBQ = bchessBoard.getBQ();
        long BBK = bchessBoard.getBK();

        long wBTest = BWB | BWR | BWP | BWN | BWQ | BWK;
        long bBTest = BBP | BBN | BBB | BBR | BBQ | BBK;

        System.out.println("White bishop");
        bchessBoard.drawArray(BWB);
        System.out.println("White pieces");
        bchessBoard.drawArray(wBTest);
        System.out.println("Black pieces");
        bchessBoard.drawArray(bBTest);
        System.out.println("White bishop moves");
        long WBbm = Move.getWBishopMoves(BWB, wBTest,bBTest);
        bchessBoard.drawArray(WBbm);

    }
}
