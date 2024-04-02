import java.util.Random;

public class ZHashing {
    private static final long[][] zobristKeys = new long[64][13];

    static {
        initializeZobristKeys();
    }

    private static void initializeZobristKeys() {
        Random random = new Random();
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 13; j++) {
                zobristKeys[i][j] = random.nextLong();
            }
        }
    }

    public static long generateHashKey(ChessBoard board) {
        long hashKey = 0;

        for (int i = 0; i < 64; i++) {
            int piece = getPieceHash(board, i);
            hashKey ^= zobristKeys[i][piece];
        }

        return hashKey;
    }

    private static int getPieceHash(ChessBoard board, int square) {
        long pieceMask = 1L << square;
        if ((board.getWP() & pieceMask) != 0) return 1;
        if ((board.getWN() & pieceMask) != 0) return 2;
        if ((board.getWB() & pieceMask) != 0) return 3;
        if ((board.getWR() & pieceMask) != 0) return 4;
        if ((board.getWQ() & pieceMask) != 0) return 5;
        if ((board.getWK() & pieceMask) != 0) return 6;
        if ((board.getBP() & pieceMask) != 0) return 7;
        if ((board.getBN() & pieceMask) != 0) return 8;
        if ((board.getBB() & pieceMask) != 0) return 9;
        if ((board.getBR() & pieceMask) != 0) return 10;
        if ((board.getBQ() & pieceMask) != 0) return 11;
        if ((board.getBK() & pieceMask) != 0) return 12;
        return 0;
    }
}