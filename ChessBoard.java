import java.util.Arrays;

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

    /**
     * Helper for evaluation function - counts number of pieces in a bitboard
     * @return pieceCount
     */
    public int countPieces(long bitboard){
        int pieceCount = 0;
        while (bitboard != 0L){
            long piece = Long.highestOneBit(bitboard); // get a piece
            bitboard = (~piece) & bitboard; // take piece off of bitboard
            pieceCount++;
        }
        return pieceCount;
    }

    /**
     * Basic evaluation function that only looks at material
     * @return materialDiff - difference in material, white - black
     */
    public int evaluateWhite(){
        int blackMaterial =
                (countPieces(this.BB) * 3) +
                (countPieces(this.BN) * 3) +
                (countPieces(this.BR) * 5) +
                (countPieces(this.BQ) * 9) +
                (countPieces(this.BP));
        int whiteMaterial =
                (countPieces(this.WB) * 3) +
                (countPieces(this.WN) * 3) +
                (countPieces(this.WR) * 5) +
                (countPieces(this.WQ) * 9) +
                (countPieces(this.WP));
        int materialDiff = whiteMaterial - blackMaterial;
        return materialDiff;
    }
}
