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
}
