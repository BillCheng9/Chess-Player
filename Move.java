public class Move {
    final private static long FILE_GH=217020518514230019L;
    final private static long FILE_AB=-4557430888798830400L;
    final private static long[] DiagonalMasks8 =/*from top left to bottom right*/
            {
                    0x1L, 0x102L, 0x10204L, 0x1020408L, 0x102040810L, 0x10204081020L, 0x1020408102040L,
                    0x102040810204080L, 0x204081020408000L, 0x408102040800000L, 0x810204080000000L,
                    0x1020408000000000L, 0x2040800000000000L, 0x4080000000000000L, 0x8000000000000000L
            };
    final private static long[] AntiDiagonalMasks8 =/*from top right to bottom left*/
            {
                    0x80L, 0x8040L, 0x804020L, 0x80402010L, 0x8040201008L, 0x804020100804L, 0x80402010080402L,
                    0x8040201008040201L, 0x4020100804020100L, 0x2010080402010000L, 0x1008040201000000L,
                    0x804020100000000L, 0x402010000000000L, 0x201000000000000L, 0x100000000000000L
            };
    public static long getWKnightMoves(long n, long w){
        long nm = 0L; // possible knight moves
        while(n!=0L) {
            long m = Long.highestOneBit(n); // single out one of the knights
            long fnm = (m << 6 | m << 10 | m << 15 | m << 17 | m >> 6 | m >> 10) & (~w); // get possible knight moves
            // check if the piece is on the edge
            int n_zero = Long.numberOfTrailingZeros(m);
            if (n_zero%8 < 4) {
                fnm &= (~w)&(~FILE_AB);
            }
            else {
                fnm &= (~w)&(~FILE_GH);
            }
            nm |= fnm;
            n = (~m) & n; // remove the knight
        }
        return nm;
    }

    public long getWPawnMoves(long wPawns, long wPieces, long bPieces){
        long pawnMoves = 0L;
        long fPawnMoves;
        while (wPawns != 0L){
            long pawn = Long.highestOneBit(wPawns);
            long singleStep = pawn << 8 & (~wPieces) & (~bPieces); // shift pawn up a rank
            long doubleStep = singleStep << 8 & (~wPieces) & (~bPieces); // shift pawn up two ranks
            long capture = (pawn << 7 | pawn << 9) & (bPieces); // shift pawn up diagonally as long as there is a black piece
            if (pawn <= 32768) { // if pawn is located on first rank
                fPawnMoves = singleStep | doubleStep | capture;
            }
            else {
                fPawnMoves = singleStep | capture;
            }

            pawnMoves |= fPawnMoves;
            wPawns = (~pawn) & wPawns;
        }
        return pawnMoves;
    }

    public static long getWKingMoves(long k, long w){
        long km = 0L;
        while(k!=0L){
            long m = Long.highestOneBit(k);
            long fkm = (m << 1 | m << 7 | m << 8 | m << 9 | m >> 1 | m >> 7 | m >> 8 | m >> 9 );
            int n_zero = Long.numberOfTrailingZeros(m);
            if (n_zero % 8 < 4) {
                fkm &= (~w)&(~FILE_AB);
            }
            else {
                fkm &= (~w)&(~FILE_GH);
            }
            km |= fkm;
            k = (~m) & k;
        }
        return km;
    }
    public static Long getWBishopMoves(long b,long wo, long bo) {
        long bm = 0L;
        while(b!=0L){
            long m = Long.highestOneBit(b);
            int s = Long.numberOfTrailingZeros(m);
            long o = wo | bo;
            long possibilitiesDiagonal = (((o&DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * b)) ^ Long.reverse(Long.reverse(o&DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * Long.reverse(b))))&DiagonalMasks8[(s / 8) + (s % 8)];
            long possibilitiesAntiDiagonal = (((o&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * b)) ^ Long.reverse(Long.reverse(o&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(b))))&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)];
            long fbm = (possibilitiesDiagonal)&~wo | (possibilitiesAntiDiagonal)&~wo; // exclude the white pieces
            bm |= fbm;
            b = (~m) & b;
        }
        return bm;
    }
}
