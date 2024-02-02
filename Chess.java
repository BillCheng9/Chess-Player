public class Chess {
    public String Start = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    public static class Piece{
        public int Empty = 0;   //00000
        public int Pawn = 1;    //00001
        public int Knight = 2;  //00010
        public int Bishop = 3;  //00011
        public int Rook = 4;    //00100
        public int Queen = 5;   //00101
        public int King = 6;    //00110

        public int Black = 8;   //01000
        public int White = 16;  //10000
    }
}
