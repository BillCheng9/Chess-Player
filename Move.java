import java.util.ArrayList;
import java.util.List;

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
            myPawns = (~pawn) & myPawns;
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

    public static long getSKnightMoves(long n, long sameOccupied){ // single piece
        long nm = 0L; // possible knight moves
        long m = Long.highestOneBit(n); // single out one of the knights
        long fnm = (m << 6 | m << 10 | m << 15 | m << 17 | m >> 6 | m >> 10 | m >> 15 | m >> 17) & (~sameOccupied); // get possible knight moves
        // check if the piece is on the edge
        int n_zero = Long.numberOfTrailingZeros(m);
        if (n_zero%8 < 4) {
            fnm &= (~sameOccupied)&(~FILE_AB);
        }
        else {
            fnm &= (~sameOccupied)&(~FILE_GH);
        }
        nm |= fnm;
        return nm;
    }

    public static long getSPawnMoves(long wPawns, long sameOccupied, long otherOccupied){
        long pawnMoves = 0L;
        long fPawnMoves;
        long pawn = Long.highestOneBit(wPawns);
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

        return pawnMoves;
    }

    public static long getSKingMoves(long k, long sameOccupied){
        long km = 0L;
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

        return km;
    }
    public static Long getSBishopMoves(long b,long sameOccupied, long otherOccupied) {
        long bm = 0L;
        long m = Long.highestOneBit(b);
        int s = Long.numberOfTrailingZeros(m);
        long o = sameOccupied | otherOccupied;
        long possibilitiesDiagonal = (((o&DiagonalMasks[(s / 8) + (s % 8)]) - (2 * m)) ^ Long.reverse(Long.reverse(o&DiagonalMasks[(s / 8) + (s % 8)]) - (2 * Long.reverse(m))))&DiagonalMasks[(s / 8) + (s % 8)];
        long possibilitiesAntiDiagonal = (((o&AntiDiagonalMasks[(s / 8) + 7 - (s % 8)]) - (2 * m)) ^ Long.reverse(Long.reverse(o&AntiDiagonalMasks[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(m))))&AntiDiagonalMasks[(s / 8) + 7 - (s % 8)];
        long fbm = (possibilitiesDiagonal)&~sameOccupied | (possibilitiesAntiDiagonal)&~sameOccupied; // exclude the white pieces
        bm |= fbm;
        return bm;
    }
    public static Long getSRookMoves(long r,long sameOccupied, long otherOccupied) {
        long rm = 0L;
        long m = Long.highestOneBit(r);
        int s = Long.numberOfTrailingZeros(m);
        long o = sameOccupied | otherOccupied;
        long possibilitiesHorizontal = ((o - 2 * m) ^ Long.reverse(Long.reverse(o) - 2 * Long.reverse(m)))&RankMasks[(s / 8)];
        long possibilitiesVertical = (((o&FileMasks[s % 8]) - (2 * m)) ^ Long.reverse(Long.reverse(o&FileMasks[s % 8]) - (2 * Long.reverse(m))))&FileMasks[(s % 8)];
        long frm = (possibilitiesHorizontal)&~sameOccupied | (possibilitiesVertical)&~sameOccupied; // exclude the white pieces
        rm |= frm;
        return rm;
    }
    public static Long getSQueenMoves(long q,long sameOccupied, long otherOccupied) {
        long qm = 0L;
        long fqm = getSRookMoves(q, sameOccupied, otherOccupied) | getSBishopMoves(q,sameOccupied, otherOccupied);
        qm |= fqm;

        return qm;
    }
    public static List<ChessBoard> getAllMoves(long wp, long wn, long wb, long wr, long wq, long wk,
                                               long bp, long bn, long bb, long br, long bq, long bk){
        List<ChessBoard> allMoves = new ArrayList<>();
        Long[] opponentBoard = {bp, bn, bk, br, bb, bq};
        long wo = wp | wn | wk | wr | wb | wq;
        long bo = bp | bn | bk | br | bb | bq;

        // get the initial board
        long initialWP = wp;
        long initialWN = wn;
        long initialWB = wb;
        long initialWR = wr;
        long initialWQ = wq;
        long initialWK = wk;
        long initialBP = bp;
        long initialBN = bn;
        long initialBB = bb;
        long initialBR = br;
        long initialBQ = bq;
        long initialBK = bk;

        // get pawn moves
        if (initialWP != 0L) {
            long ip = initialWP;
            while (ip != 0L) {
                long m = Long.highestOneBit(ip);
                long PMoves = getSPawnMoves(ip, wo, bo);
                while (PMoves != 0L) {
                    long z = Long.highestOneBit(PMoves);
                    // check capture
                    for (int i = 0; i < 6; i++) {
                        long attacked = opponentBoard[i];
                        if ((z & attacked) != 0L) {
                            attacked = (~z) & attacked;
                            switch (i) {
                                case 0 -> bp = attacked;
                                case 1 -> bn = attacked;
                                case 2 -> bk = attacked;
                                case 3 -> br = attacked;
                                case 4 -> bb = attacked;
                                case 5 -> bq = attacked;
                            }
                        }
                    }
                    wp = wp & (~m) | z;
                    allMoves.add(new ChessBoard(wp, wn, wb, wr, wq, wk, bp, bn, bb, br, bq, bk));
                    wp = initialWP;
                    bp = initialBP;
                    bn = initialBN;
                    bb = initialBB;
                    br = initialBR;
                    bq = initialBQ;
                    bk = initialBK;
                    PMoves = (~z) & PMoves;
                }
                ip = (~m) & ip;
            }
        }

        // get knight moves
        if (initialWN != 0L) {
            long in = initialWN;
            while (in != 0L) {
                long m = Long.highestOneBit(in);
                long NMoves = getSKnightMoves(in, wo);
                while (NMoves != 0L) {
                    long z = Long.highestOneBit(NMoves);
                    // check capture
                    for (int i = 0; i < 6; i++) {
                        long attacked = opponentBoard[i];
                        if ((z & attacked) != 0L) {
                            attacked = (~z) & attacked;
                            switch (i) {
                                case 0 -> bp = attacked;
                                case 1 -> bn = attacked;
                                case 2 -> bk = attacked;
                                case 3 -> br = attacked;
                                case 4 -> bb = attacked;
                                case 5 -> bq = attacked;
                            }
                        }
                    }
                    wn = wn & (~m) | z;
                    allMoves.add(new ChessBoard(wp, wn, wb, wr, wq, wk, bp, bn, bb, br, bq, bk));
                    wn = initialWN;
                    bp = initialBP;
                    bn = initialBN;
                    bb = initialBB;
                    br = initialBR;
                    bq = initialBQ;
                    bk = initialBK;
                    NMoves = (~z) & NMoves;
                }
                in = (~m) & in;
            }
        }

        // get bishop moves
        if (initialWB != 0L) {
            long ib = initialWB;
            while (ib != 0L) {
                long m = Long.highestOneBit(ib);
                long BMoves = getSBishopMoves(ib, wo, bo);
                while (BMoves != 0L) {
                    long z = Long.highestOneBit(BMoves);
                    // check capture
                    for (int i = 0; i < 6; i++) {
                        long attacked = opponentBoard[i];
                        if ((z & attacked) != 0L) {
                            attacked = (~z) & attacked;
                            switch (i) {
                                case 0 -> bp = attacked;
                                case 1 -> bn = attacked;
                                case 2 -> bk = attacked;
                                case 3 -> br = attacked;
                                case 4 -> bb = attacked;
                                case 5 -> bq = attacked;
                            }
                        }
                    }
                    wb = wb & (~m) | z;
                    allMoves.add(new ChessBoard(wp, wn, wb, wr, wq, wk, bp, bn, bb, br, bq, bk));
                    wb = initialWB;
                    bp = initialBP;
                    bn = initialBN;
                    bb = initialBB;
                    br = initialBR;
                    bq = initialBQ;
                    bk = initialBK;
                    BMoves = (~z) & BMoves;
                }
                ib = (~m) & ib;
            }
        }

        // get rook moves
        if (initialWR != 0L) {
            long ir = initialWR;
            while (ir != 0L) {
                long m = Long.highestOneBit(ir);
                long RMoves = getSRookMoves(ir, wo, bo);
                while (RMoves != 0L) {
                    long z = Long.highestOneBit(RMoves);
                    // check capture
                    for (int i = 0; i < 6; i++) {
                        long attacked = opponentBoard[i];
                        if ((z & attacked) != 0L) {
                            attacked = (~z) & attacked;
                            switch (i) {
                                case 0 -> bp = attacked;
                                case 1 -> bn = attacked;
                                case 2 -> bk = attacked;
                                case 3 -> br = attacked;
                                case 4 -> bb = attacked;
                                case 5 -> bq = attacked;
                            }
                        }
                    }
                    wr = wr & (~m) | z;
                    allMoves.add(new ChessBoard(wp, wn, wb, wr, wq, wk, bp, bn, bb, br, bq, bk));
                    wr = initialWR;
                    bp = initialBP;
                    bn = initialBN;
                    bb = initialBB;
                    br = initialBR;
                    bq = initialBQ;
                    bk = initialBK;
                    RMoves = (~z) & RMoves;
                }
                ir = (~m) & ir;
            }
        }

        // get queen moves
        if (initialWQ != 0L) {
            long iq = initialWQ;
            while (iq != 0L) {
                long m = Long.highestOneBit(iq);
                long QMoves = getSQueenMoves(iq, wo, bo);
                while (QMoves != 0L) {
                    long z = Long.highestOneBit(QMoves);
                    // check capture
                    for (int i = 0; i < 6; i++) {
                        long attacked = opponentBoard[i];
                        if ((z & attacked) != 0L) {
                            attacked = (~z) & attacked;
                            switch (i) {
                                case 0 -> bp = attacked;
                                case 1 -> bn = attacked;
                                case 2 -> bk = attacked;
                                case 3 -> br = attacked;
                                case 4 -> bb = attacked;
                                case 5 -> bq = attacked;
                            }
                        }
                    }
                    wq = wq & (~m) | z;
                    allMoves.add(new ChessBoard(wp, wn, wb, wr, wq, wk, bp, bn, bb, br, bq, bk));
                    wq = initialWQ;
                    bp = initialBP;
                    bn = initialBN;
                    bb = initialBB;
                    br = initialBR;
                    bq = initialBQ;
                    bk = initialBK;
                    QMoves = (~z) & QMoves;
                }
                iq = (~m) & iq;
            }
        }

        // get king moves
        if (initialWK != 0L) {
            long ik = initialWK;
            while (ik != 0L) {
                long m = Long.highestOneBit(ik);
                long KMoves = getSKingMoves(ik, wo);
                while (KMoves != 0L) {
                    long z = Long.highestOneBit(KMoves);
                    // check capture
                    for (int i = 0; i < 6; i++) {
                        long attacked = opponentBoard[i];
                        if ((z & attacked) != 0L) {
                            attacked = (~z) & attacked;
                            switch (i) {
                                case 0 -> bp = attacked;
                                case 1 -> bn = attacked;
                                case 2 -> bk = attacked;
                                case 3 -> br = attacked;
                                case 4 -> bb = attacked;
                                case 5 -> bq = attacked;
                            }
                        }
                    }
                    wk = wk & (~m) | z;
                    allMoves.add(new ChessBoard(wp, wn, wb, wr, wq, wk, bp, bn, bb, br, bq, bk));
                    wk = initialWK;
                    bp = initialBP;
                    bn = initialBN;
                    bb = initialBB;
                    br = initialBR;
                    bq = initialBQ;
                    bk = initialBK;
                    KMoves = (~z) & KMoves;
                }
                ik = (~m) & ik;
            }
        }

        return allMoves;
    }
}
