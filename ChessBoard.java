import java.util.Arrays;

import static java.lang.Long.bitCount;

public class ChessBoard {
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
     * Basic evaluation function that only looks at material
     * @return materialDiff - difference in material, white - black
     */
    public int evaluateWhite(boolean whiteToMove){
        int materialDiff;
        int opponentMaterial =
                (bitCount(this.BB) * 3) +
                        (bitCount(this.BN) * 3) +
                        (bitCount(this.BR) * 5) +
                        (bitCount(this.BQ) * 9) +
                        (bitCount(this.BK) * 2000000) +
                        (bitCount(this.BP));
        int ownMaterial =
                (bitCount(this.WB) * 3) +
                        (bitCount(this.WN) * 3) +
                        (bitCount(this.WR) * 5) +
                        (bitCount(this.WQ) * 9) +
                        (bitCount(this.WK) * 2000000) +
                        (bitCount(this.WP));
        if (whiteToMove) {
            materialDiff = ownMaterial - opponentMaterial;
        } else {
            materialDiff = opponentMaterial - ownMaterial;
        }
        return materialDiff;
    }

    /***
     * check to see if the game is over
     * @return true if the king is gone; false if it is not
     */
    public boolean isGameOver() {
        return (WK == 0L | BK == 0L);
    }

    /***
     * make the move the user picked
     * @return the chess board after the user's move
     */
    public ChessBoard performMove(String move){
        ChessBoard userMove = null;

        // get the starting and ending square
        int startFile = 9 - move.charAt(0) - 'a';
        int startRank = Character.getNumericValue(move.charAt(1)) - 1;
        int endFile = 9 - move.charAt(2) - 'a';
        int endRank = Character.getNumericValue(move.charAt(3)) - 1;

        long startSquare = 1L << (startRank * 8 + startFile);
        long targetSquare = 1L << (endRank * 8 + endFile);

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

    /*
     *
     * @return attacked - is this square attacked by any of black's pieces?

    public boolean attackedByBlack(long square){
        long allMoves =
        return attacked;
    }
    */

}
