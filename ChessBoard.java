import java.util.Arrays;

import static java.lang.Long.bitCount;

public class ChessBoard {
    private long WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK;
    ChessBoard(String fen){
        String[] fenParts = fen.split(" ");
        setBoard(fenParts[0]);
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
    public int evaluateWhite(){
        int blackMaterial =
                (bitCount(this.BB) * 3) +
                (bitCount(this.BN) * 3) +
                (bitCount(this.BR) * 5) +
                (bitCount(this.BQ) * 9) +
                (bitCount(this.BP));
        int whiteMaterial =
                (bitCount(this.WB) * 3) +
                (bitCount(this.WN) * 3) +
                (bitCount(this.WR) * 5) +
                (bitCount(this.WQ) * 9) +
                (bitCount(this.WP));
        int materialDiff = whiteMaterial - blackMaterial;
        return materialDiff;
    }
}
