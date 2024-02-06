public class Chess {
    public String Start = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    public Chessboard chess;

    public static class Piece{
        public static int Empty = 0;   //00000
        public static int Pawn = 1;    //00001
        public static int Knight = 2;  //00010
        public static int Bishop = 3;  //00011
        public static int Rook = 4;    //00100
        public static int Queen = 5;   //00101
        public static int King = 6;    //00110

        public static int Black = 8;   //01000
        public static int White = 16;  //10000
    }

    public static class Chessboard {

        public static int[] Square;
    
        public static void Board(){
            Square = new int[64];
        }

        public static void FenConverter(String fen){
            int i = 0;
            while(i < 64){
                String cur = fen.substring(i,1);
    
                switch (cur){
                    case "p":
                        Square[i++] = Piece.Pawn | Piece.Black;
                        i++;
                        break;
                    case "n":
                        Square[i++] = Piece.Knight | Piece.Black;
                        i++;
                        break;
                    case "b":
                        Square[i++] = Piece.Bishop | Piece.Black;
                        break;
                    case "r":
                        Square[i++] = Piece.Rook | Piece.Black;
                        break;
                    case "q":
                        Square[i++] = Piece.Queen | Piece.Black;
                        break;
                    case "k":
                        Square[i++] = Piece.King | Piece.Black;
                        break;
                    case "P":
                        Square[i++] = Piece.Pawn | Piece.White;
                        break;
                    case "N":
                        Square[i++] = Piece.Knight | Piece.White;
                        break;
                    case "B":
                        Square[i++] = Piece.Bishop | Piece.White;
                        break;
                    case "R":
                        Square[i++] = Piece.Rook | Piece.White;
                        break;
                    case "Q":
                        Square[i++] = Piece.Queen | Piece.White;
                        break;
                    case "K":
                        Square[i++] = Piece.King | Piece.White;
                        break;
                    default:
                        int empty = Integer.parseInt(cur);
                        while(empty > 0){
                            Square[i++] = Piece.Empty;
                            empty--;
                        }
                        break;
                }
            }
                
        }
     
    }

    
}
