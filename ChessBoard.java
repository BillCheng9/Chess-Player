import java.util.Arrays;

import static java.lang.Long.bitCount;

public class ChessBoard {
    private final int[] ROOK_PSQT = {
            500, 500, 500, 500, 500, 500, 500, 500,
            520, 520, 520, 520, 520, 520, 520, 520,
            500, 500, 500, 500, 500, 500, 500, 500,
            500, 500, 500, 500, 500, 500, 500, 500,
            500, 500, 500, 500, 500, 500, 500, 500,
            500, 500, 500, 500, 500, 500, 500, 500,
            500, 500, 500, 500, 500, 500, 500, 500,
            500, 500, 500, 510, 510, 505, 500, 500,
    };
    private final int[] KNIGHT_PSQT = {
            290, 300, 300, 300, 300, 300, 300, 290,
            300, 305, 305, 305, 305, 305, 305, 300,
            300, 305, 325, 325, 325, 325, 305, 300,
            300, 305, 325, 325, 325, 325, 305, 300,
            300, 305, 325, 325, 325, 325, 305, 300,
            300, 305, 325, 325, 325, 325, 305, 300,
            300, 305, 305, 305, 305, 305, 305, 300,
            290, 310, 300, 300, 300, 300, 310, 290
    };
    private final int[] KING_PSQT = {
            2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000,
            2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000,
            2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000,
            2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000,
            2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000,
            2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000,
            2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000, 2000000,
            2000020, 2000020, 2000020, 2000020, 2000020, 2000020, 2000020, 2000020,
    };
    private final int[] BISHOP_PSQT = {
            300, 300, 300, 300, 300, 300, 300, 300,
            300, 305, 300, 300, 300, 300, 305, 300,
            300, 300, 310, 310, 310, 310, 300, 300,
            300, 300, 310, 310, 310, 310, 300, 300,
            300, 300, 325, 310, 310, 325, 300, 300,
            300, 300, 310, 310, 310, 310, 300, 300,
            300, 310, 300, 300, 300, 300, 310, 300,
            300, 300, 300, 300, 300, 300, 300, 300,
    };

    private final int[] QUEEN_PSQT = {
            900, 900, 900, 900, 900, 900, 900, 900,
            920, 920, 920, 920, 920, 920, 920, 920,
            900, 900, 900, 900, 900, 900, 900, 900,
            900, 900, 900, 900, 900, 900, 900, 900,
            900, 900, 900, 900, 900, 900, 900, 900,
            900, 900, 900, 900, 900, 900, 900, 900,
            900, 900, 900, 910, 910, 900, 900, 900,
            900, 900, 900, 910, 910, 900, 900, 900,
    };

    private long WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK;
    ChessBoard(String fen){
        String[] fenParts = fen.split(" ");
        setBoard(fenParts[0]);
    }
    ChessBoard(long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK){
        this.WP = WP;
        this.WN = WN;
        this.WB = WB;
        this.WR = WR;
        this.WQ = WQ;
        this.WK = WK;
        this.BP = BP;
        this.BN = BN;
        this.BB = BB;
        this.BR = BR;
        this.BQ = BQ;
        this.BK = BK;
    }
    public void setBoard(String position){
        int rank = 7;
        int file = 0;

        for (char c:position.toCharArray()){
            if(Character.isDigit(c)){
                file += Character.getNumericValue(c);
            } else if (c == '/') {
                rank--;
                file = 0;
            } else {
                long square = 1L << (rank * 8 + (7 - file));
                switch (c) {
                    case 'P' -> WP |= square;
                    case 'N' -> WN |= square;
                    case 'B' -> WB |= square;
                    case 'R' -> WR |= square;
                    case 'Q' -> WQ |= square;
                    case 'K' -> WK |= square;
                    case 'p' -> BP |= square;
                    case 'n' -> BN |= square;
                    case 'b' -> BB |= square;
                    case 'r' -> BR |= square;
                    case 'q' -> BQ |= square;
                    case 'k' -> BK |= square;
                }
                file++;
            }
        }
    }
    public static void drawArray(long bitboard) {
        int[][] chessBoard = new int[8][8];
        for (int i = 0; i < 64; i++) {
            chessBoard[7 - i / 8][7 - i % 8] = 0;
        }
        for (int i = 0; i < 64; i++) {
            if (((bitboard >> i) & 1) == 1) {
                chessBoard[7 - i / 8][7 - i % 8] = 1;
            }
        }
        for (int i = 0; i < 8; i++) {
            System.out.println(Arrays.toString(chessBoard[i]));
        }
    }
    public void drawBoard() {
        String chessBoard[][]=new String[8][8];
        String[] rank = {"8", "7", "6", "5", "4", "3", "2", "1"};
        String[] files = {"a", "b", "c", "d", "e", "f", "g", "h"};

        for (int i = 0; i < 64; i++) {
            chessBoard[7 - i / 8][7 - i % 8] = " ";
        }
        for (int i=0;i<64;i++) {
            if (((WP>>i)&1)==1) {chessBoard[7 - i / 8][7 - i % 8]="P";}
            if (((WN>>i)&1)==1) {chessBoard[7 - i / 8][7 - i % 8]="N";}
            if (((WB>>i)&1)==1) {chessBoard[7 - i / 8][7 - i % 8]="B";}
            if (((WR>>i)&1)==1) {chessBoard[7 - i / 8][7 - i % 8]="R";}
            if (((WQ>>i)&1)==1) {chessBoard[7 - i / 8][7 - i % 8]="Q";}
            if (((WK>>i)&1)==1) {chessBoard[7 - i / 8][7 - i % 8]="K";}
            if (((BP>>i)&1)==1) {chessBoard[7 - i / 8][7 - i % 8]="p";}
            if (((BN>>i)&1)==1) {chessBoard[7 - i / 8][7 - i % 8]="n";}
            if (((BB>>i)&1)==1) {chessBoard[7 - i / 8][7 - i % 8]="b";}
            if (((BR>>i)&1)==1) {chessBoard[7 - i / 8][7 - i % 8]="r";}
            if (((BQ>>i)&1)==1) {chessBoard[7 - i / 8][7 - i % 8]="q";}
            if (((BK>>i)&1)==1) {chessBoard[7 - i / 8][7 - i % 8]="k";}
        }
        // add the file and rank names
        for (int i = 0; i < 8; i++) {
            System.out.print(rank[i] + " ");
            System.out.println(Arrays.toString(chessBoard[i]));
        }
        System.out.print("   ");
        for (String file : files) {
            System.out.print(file + "  ");
        }
        System.out.println();
    }

    public long getWP() {
        return WP;
    }

    public long getWN() {
        return WN;
    }

    public long getWB() {
        return WB;
    }

    public long getWR() {
        return WR;
    }

    public long getWQ() {
        return WQ;
    }

    public long getWK() {
        return WK;
    }

    public long getBP() {
        return BP;
    }

    public long getBN() {
        return BN;
    }

    public long getBB() {
        return BB;
    }

    public long getBR() {
        return BR;
    }

    public long getBQ() {
        return BQ;
    }

    public long getBK() {
        return BK;
    }


    /**
     * Basic evaluation function using piece square tables
     * @return materialDiff: difference in material, own - opponent, in centipawns
     */
    public int evaluateWhite(boolean whiteToMove){
        int eval = 0;

        Long bb = this.BB;
        Long bn = this.BN;
        Long br = this.BR;
        Long bq = this.BQ;
        Long bk = this.BK;

        Long wb = this.WB;
        Long wn = this.WN;
        Long wr = this.WR;
        Long wq = this.WQ;
        Long wk = this.WK;

        // calculate own material
        // look up the material value of each piece based on the piece square table, then add it to the total
        while(wb != 0){ // while there are still white bishops on the board
            eval += BISHOP_PSQT[Long.numberOfLeadingZeros(wb)]; // use number of leading zeros to index into psqt
            wb = wb & (~Long.highestOneBit(wb)); // take one bishop off the board
        }
        while(wn != 0){
            eval += KNIGHT_PSQT[Long.numberOfLeadingZeros(wn)];
            wn = wn & (~Long.highestOneBit(wn));
        }
        while(wr != 0){
            eval += ROOK_PSQT[Long.numberOfLeadingZeros(wr)];
            wr = wr & (~Long.highestOneBit(wr));
        }
        while(wq != 0){
            eval += QUEEN_PSQT[Long.numberOfLeadingZeros(wq)];
            wq = wq & (~Long.highestOneBit(wq));
        }
        while(wk != 0){
            eval += KING_PSQT[Long.numberOfLeadingZeros(wk)];
            wk = wk & (~Long.highestOneBit(wk));
        }
        eval += bitCount(this.WP) * 100;

        // calculate opponent material, subtract it from eval
        while(bb != 0){
            Long piece = Long.highestOneBit(bb); // isolate one piece first
            eval -= BISHOP_PSQT[Long.numberOfTrailingZeros(bb)]; // use trailing zeros to flip the psqt to black's pov
            bb = bb & (~piece); // take the piece off
        }
        while(bn != 0){
            Long piece = Long.highestOneBit(bn);
            eval -= KNIGHT_PSQT[Long.numberOfTrailingZeros(piece)];
            bn = bn & (~piece);
        }
        while(br != 0){
            Long piece = Long.highestOneBit(br);
            eval -= ROOK_PSQT[Long.numberOfTrailingZeros(br)];
            br = br & (~piece);
        }
        while(bq != 0){
            Long piece = Long.highestOneBit(bq);
            eval -= QUEEN_PSQT[Long.numberOfTrailingZeros(bq)];
            bq = bq & (~piece);
        }
        while(bk != 0){
            Long piece = Long.highestOneBit(bk);
            eval -= KING_PSQT[Long.numberOfTrailingZeros(bk)];
            bk = bk & (~piece);
        }
        eval -= bitCount(this. BP) * 100;

        if (!whiteToMove) {
            eval = -eval;
        }

        return eval;
    }

    /***
     * check to see if the game is over
     * For now, implemented as a check to see if the king is captured (which is not a legal event in chess)
     * Later, we will implement this method to check if one side has been checkmated 
     * @return true if the king is gone; false if it is not
     */
    public boolean isGameOver() {
        return (WK == 0L | BK == 0L);
    }

    /***
     * make the move the user picked on a bitboard
     * (not complete: it does not check if the user input is valid or if the moves is legal for the user)
     *
     * @return the chess board after the user's move
     */
    public ChessBoard performMove(Long startSquare, Long targetSquare){
        ChessBoard userMove = null;

        // make the black move
        if ((startSquare & BP) != 0) {
            BP &= ~startSquare;
            BP |= targetSquare;
        } else if ((startSquare & BN) != 0) {
            BN &= ~startSquare;
            BN |= targetSquare;
        } else if ((startSquare & BB) != 0) {
            BB &= ~startSquare;
            BB |= targetSquare;
        } else if ((startSquare & BR) != 0) {
            BR &= ~startSquare;
            BR |= targetSquare;
        } else if ((startSquare & BQ) != 0) {
            BQ &= ~startSquare;
            BQ |= targetSquare;
        } else if ((startSquare & BK) != 0) {
            BK &= ~startSquare;
            BK |= targetSquare;
        }
        // remove the captured pieces
        if ((targetSquare & WP) != 0) {
            WP &= ~targetSquare;
        } else if ((targetSquare & WN) != 0) {
            WN &= ~targetSquare;
        } else if ((targetSquare & WB) != 0) {
            WB &= ~targetSquare;
        } else if ((targetSquare & WR) != 0) {
            WR &= ~targetSquare;
        } else if ((targetSquare & WQ) != 0) {
            WQ &= ~targetSquare;
        } else if ((targetSquare & WK) != 0) {
            WK &= ~targetSquare;
        }

        // create the board after the move
        userMove = new ChessBoard(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK);

        return userMove;
    }

    /**
     *
     * @return attacked - is this square attacked by any of black's pieces?
     */

    public boolean attackedBy(long square, char side){
        boolean attacked = false;
        long same; // my pieces
        long other; // opponent's pieces (the side we are checking for attacks from)
        long otherPawns;
        long otherKnights;
        long otherBishops;
        long otherRooks;
        long otherQueens;

        long W = this.WP | this.WN | this.WB | this.WR | this.WQ | this.WK; // white occupancy
        long B = this.BP | this.BN | this.BB | this.BR | this.BQ | this.BK; // black occupancy

        if (side == 'b'){
            otherPawns = this.BP;
            otherKnights = this.BN;
            otherBishops = this.BB;
            otherRooks = this.BR;
            otherQueens = this.BQ;
            same = W;
            other = B;
        }
        else {
            otherPawns = this.WP;
            otherKnights = this.WN;
            otherBishops = this.WB;
            otherRooks = this.WR;
            otherQueens = this.WQ;
            same = B;
            other = W;
        }

        /* pretend there is friendly piece on the square -
        *  if it can capture the same piece type from black, that black piece is attacking the square
        */
        for (int i = 0; i < 5; i++){
            if (i == 0){
                if (side == 'b'){
                if ((Move.getWhitePawnCapture(square, other) & otherPawns) != 0L){
                    attacked = true;
                    System.out.println("pawn");
                    break;
                }
            }
                else {
                    if ((Move.getBlackPawnCapture(square, same, other) & otherPawns) != 0L) {
                        attacked = true;
                        System.out.println("pawn");
                        break;
                    }
                }
                }
            else if (i == 1){
                if ((Move.getKnightMoves(square, same) & otherKnights) != 0L){
                    attacked = true;
                    System.out.println("knight");
                    break;
                }
            }
            else if (i == 2){
                if ((Move.getBishopMoves(square, same, other) & otherBishops) != 0L){
                    attacked = true;
                    System.out.println("bishop");
                    break;
                }
            }
            else if (i == 3){
                if ((Move.getRookMoves(square, same, other) & otherRooks) != 0L){
                    attacked = true;
                    System.out.println("rook");
                    break;
                }
            }
            else {
                if ((Move.getQueenMoves(square, same, other) & otherQueens) != 0L){
                    attacked = true;
                    System.out.println("queen");
                    break;
                }
            }
        }
        return attacked;
    }
}
