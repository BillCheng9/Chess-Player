public class Move {
    final private static long FILE_GH=217020518514230019L;
    final private static long FILE_AB=-4557430888798830400L;
    final private static long[] RankMasks =/*from rank1 to rank8*/
            {
                    0xFFL, 0xFF00L, 0xFF0000L, 0xFF000000L, 0xFF00000000L, 0xFF0000000000L, 0xFF000000000000L, 0xFF00000000000000L
            };
    final private static long[] FileMasks =/*from fileA to FileH*/
            {
                    0x101010101010101L, 0x202020202020202L, 0x404040404040404L, 0x808080808080808L,
                    0x1010101010101010L, 0x2020202020202020L, 0x4040404040404040L, 0x8080808080808080L
            };
    final private static long[] DiagonalMasks =/*from top left to bottom right*/
            {
                    0x1L, 0x102L, 0x10204L, 0x1020408L, 0x102040810L, 0x10204081020L, 0x1020408102040L,
                    0x102040810204080L, 0x204081020408000L, 0x408102040800000L, 0x810204080000000L,
                    0x1020408000000000L, 0x2040800000000000L, 0x4080000000000000L, 0x8000000000000000L
            };
    final private static long[] AntiDiagonalMasks =/*from top right to bottom left*/
            {
                    0x80L, 0x8040L, 0x804020L, 0x80402010L, 0x8040201008L, 0x804020100804L, 0x80402010080402L,
                    0x8040201008040201L, 0x4020100804020100L, 0x2010080402010000L, 0x1008040201000000L,
                    0x804020100000000L, 0x402010000000000L, 0x201000000000000L, 0x100000000000000L
            };
    public static long getKnightMoves(long n, long sameOccupied){
        long nm = 0L; // possible knight moves
        while(n!=0L) {
            long m = Long.highestOneBit(n); // single out one of the knights
            long fnm = (m << 6 | m << 10 | m << 15 | m << 17 | m >> 6 | m >> 10) & (~sameOccupied); // get possible knight moves
            // check if the piece is on the edge
            int n_zero = Long.numberOfTrailingZeros(m);
            if (n_zero%8 < 4) {
                fnm &= (~sameOccupied)&(~FILE_AB);
            }
            else {
                fnm &= (~sameOccupied)&(~FILE_GH);
            }
            nm |= fnm;
            n = (~m) & n; // remove the knight
        }
        return nm;
    }

    public long getPawnMoves(long myPawns, long sameOccupied, long otherOccupied){
        long pawnMoves = 0L;
        long fPawnMoves;
        while (myPawns != 0L){
            long pawn = Long.highestOneBit(myPawns);
            long singleStep = pawn << 8 & (~sameOccupied) & (~otherOccupied); // shift pawn up a rank
            long doubleStep = singleStep << 8 & (~sameOccupied) & (~otherOccupied); // shift pawn up two ranks
            long capture = (pawn << 7 | pawn << 9) & (otherOccupied); // shift pawn up diagonally as long as there is a black piece
            if (pawn <= 32768) { // if pawn is located on first rank
                fPawnMoves = singleStep | doubleStep | capture;
            }
            else {
                fPawnMoves = singleStep | capture;
            }
            pawnMoves |= fPawnMoves;
            sameOccupied = (~pawn) & sameOccupied;
        }
        return pawnMoves;
    }

    public static long getKingMoves(long k, long sameOccupied){
        long km = 0L;
        while(k!=0L){
            long m = Long.highestOneBit(k);
            long fkm = (m << 1 | m << 7 | m << 8 | m << 9 | m >> 1 | m >> 7 | m >> 8 | m >> 9 );
            int n_zero = Long.numberOfTrailingZeros(m);
            if (n_zero % 8 < 4) {
                fkm &= (~sameOccupied)&(~FILE_AB);
            }
            else {
                fkm &= (~sameOccupied)&(~FILE_GH);
            }
            km |= fkm;
            k = (~m) & k;
        }
        return km;
    }
    public static Long getBishopMoves(long b,long sameOccupied, long otherOccupied) {
        long bm = 0L;
        while(b!=0L){
            long m = Long.highestOneBit(b);
            int s = Long.numberOfTrailingZeros(m);
            long o = sameOccupied | otherOccupied;
            long possibilitiesDiagonal = (((o&DiagonalMasks[(s / 8) + (s % 8)]) - (2 * m)) ^ Long.reverse(Long.reverse(o&DiagonalMasks[(s / 8) + (s % 8)]) - (2 * Long.reverse(m))))&DiagonalMasks[(s / 8) + (s % 8)];
            long possibilitiesAntiDiagonal = (((o&AntiDiagonalMasks[(s / 8) + 7 - (s % 8)]) - (2 * m)) ^ Long.reverse(Long.reverse(o&AntiDiagonalMasks[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(m))))&AntiDiagonalMasks[(s / 8) + 7 - (s % 8)];
            long fbm = (possibilitiesDiagonal)&~sameOccupied | (possibilitiesAntiDiagonal)&~sameOccupied; // exclude the white pieces
            bm |= fbm;
            b = (~m) & b;
        }
        return bm;
    }
    public static Long getRookMoves(long r,long sameOccupied, long otherOccupied) {
        long rm = 0L;
        while(r!=0L){
            long m = Long.highestOneBit(r);
            int s = Long.numberOfTrailingZeros(m);
            long o = sameOccupied | otherOccupied;
            long possibilitiesHorizontal = ((o - 2 * m) ^ Long.reverse(Long.reverse(o) - 2 * Long.reverse(m)))&RankMasks[(s / 8)];
            long possibilitiesVertical = (((o&FileMasks[s % 8]) - (2 * m)) ^ Long.reverse(Long.reverse(o&FileMasks[s % 8]) - (2 * Long.reverse(m))))&FileMasks[(s % 8)];
            long frm = (possibilitiesHorizontal)&~sameOccupied | (possibilitiesVertical)&~sameOccupied; // exclude the white pieces
            rm |= frm;
            r = (~m) & r;
        }
        return rm;
    }
    public static Long getQueenMoves(long q,long sameOccupied, long otherOccupied) {
        long qm = 0L;
        long fqm = getRookMoves(q, sameOccupied, otherOccupied) | getBishopMoves(q,sameOccupied, otherOccupied);
        qm |= fqm;
        return qm;
    }
}
