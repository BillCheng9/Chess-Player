public class Move {
    final private static long FILE_GH=217020518514230019L;
    final private static long FILE_AB=-4557430888798830400L;
    public static long getWKnightMoves(long k, long w){
        long km = 0L;
        while(k!=0L) {
            long m = Long.highestOneBit(k);
            long fkm = (m << 6 | m << 10 | m << 15 | m << 17 | m >> 6 | m >> 10) & (~w);
            int n_zero = Long.numberOfTrailingZeros(m);
            if (n_zero%8 < 4) {
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
}
