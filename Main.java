import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n" + "------ Test Knight moves ------" + "\n");
        String board = "rnbqkbnr/pppppppp/8/8/3N4/8/PPPPPPPP/RNBQKB1R w KQkq - 0 1";
        ChessBoard chessBoard = new ChessBoard(board);

        long WP = chessBoard.getWP();
        long WN = chessBoard.getWN();
        long WB = chessBoard.getWB();
        long WR = chessBoard.getWR();
        long WQ = chessBoard.getWQ();
        long WK = chessBoard.getWK();
        long BP = chessBoard.getBP();
        long BN = chessBoard.getBN();
        long BB = chessBoard.getBB();
        long BR = chessBoard.getBR();
        long BQ = chessBoard.getBQ();
        long BK = chessBoard.getBK();

        long w = WP|WN|WB|WR|WQ|WK;
        long b = BP|BN|BB|BR|BQ|BK;

        long WNM = Move.getKnightMoves(WN, w);

        ChessBoard.drawArray(WN);
        System.out.println("");
        ChessBoard.drawArray(WNM);
        System.out.println("");

        // test pawn move generation
        String pawnTestBoard = "rnbkqb1r/8/8/8/8/3N3n/PPPPPPP1/RNBKQB1R w KQkq - 0 1";
        ChessBoard chessBoardPawnTest = new ChessBoard(pawnTestBoard);

        long pawnTestWP = chessBoardPawnTest.getWP();
        long pawnTestWN = chessBoardPawnTest.getWN();
        long pawnTestWB = chessBoardPawnTest.getWB();
        long pawnTestWR = chessBoardPawnTest.getWR();
        long pawnTestWQ = chessBoardPawnTest.getWQ();
        long pawnTestWK = chessBoardPawnTest.getWK();
        long pawnTestBP = chessBoardPawnTest.getBP();
        long pawnTestBN = chessBoardPawnTest.getBN();
        long pawnTestBB = chessBoardPawnTest.getBB();
        long pawnTestBR = chessBoardPawnTest.getBR();
        long pawnTestBQ = chessBoardPawnTest.getBQ();
        long pawnTestBK = chessBoardPawnTest.getBK();

        long wPawnTest = pawnTestWP|pawnTestWN|pawnTestWB|pawnTestWR|pawnTestWQ|pawnTestWK;
        long bPawnTest = pawnTestBP|pawnTestBN|pawnTestBB|pawnTestBR|pawnTestBQ|pawnTestBK;

        System.out.println("\n" + "------ Test pawn moves ------" + "\n");
        ChessBoard.drawArray(pawnTestWP);
        System.out.println("");
        Move move = new Move();
        long WPM = move.getWhitePawnMoves(pawnTestWP, wPawnTest, bPawnTest);
        ChessBoard.drawArray(WPM);

        System.out.println("");
        System.out.println("------ Test evaluation function ------");
        System.out.println("Should be 0, because all pieces are on the board");
        System.out.println(chessBoard.evaluateWhite(true));
        System.out.println("Should be 7, because white is up by 7 pawns");
        System.out.println(chessBoardPawnTest.evaluateWhite(true));

        System.out.println("-------Test King moves-------" + "\n");
        String kboard = "rnbqkbnr/pppppppp/8/8/3N4/8/3P1p2/4K3 w KQkq - 0 1";
        ChessBoard kchessBoard = new ChessBoard(kboard);
        long KWP = kchessBoard.getWP();
        long KWN = kchessBoard.getWN();
        long KWB = kchessBoard.getWB();
        long KWR = kchessBoard.getWR();
        long KWQ = kchessBoard.getWQ();
        long KWK = kchessBoard.getWK();
        long KBP = kchessBoard.getBP();
        long KBN = kchessBoard.getBN();
        long KBB = kchessBoard.getBB();
        long KBR = kchessBoard.getBR();
        long KBQ = kchessBoard.getBQ();
        long KBK = kchessBoard.getBK();

        long wKTest = KWB | KWR | KWP | KWN | KWQ | KWK;
        long bKTest = KBP | KBN | KBB | KBR | KBQ | KBK;

        System.out.println("White king position");
        ChessBoard.drawArray(KWK);
        System.out.println("White king moves");
        long WKkM = Move.getKingMoves(KWK, wKTest);
        ChessBoard.drawArray(WKkM);

        System.out.println("-------Test Bishop moves-------" + "\n");
        String bboard = "rnbqkbnr/pppppppp/8/3B4/4B3/8/PPPPPPPP/8 w KQkq - 0 1";
        ChessBoard bchessBoard = new ChessBoard(bboard);
        long BWP = bchessBoard.getWP();
        long BWN = bchessBoard.getWN();
        long BWB = bchessBoard.getWB();
        long BWR = bchessBoard.getWR();
        long BWQ = bchessBoard.getWQ();
        long BWK = bchessBoard.getWK();
        long BBP = bchessBoard.getBP();
        long BBN = bchessBoard.getBN();
        long BBB = bchessBoard.getBB();
        long BBR = bchessBoard.getBR();
        long BBQ = bchessBoard.getBQ();
        long BBK = bchessBoard.getBK();

        long wBTest = BWP | BWN | BWB | BWR | BWQ | BWK;
        long bBTest = BBP | BBN | BBB | BBR | BBQ | BBK;

        System.out.println("White bishop");
        ChessBoard.drawArray(BWB);
        System.out.println("White pieces");
        ChessBoard.drawArray(wBTest);
        System.out.println("Black pieces");
        ChessBoard.drawArray(bBTest);
        System.out.println("White bishop moves");
        long WBbm = Move.getBishopMoves(BWB, wBTest,bBTest);
        ChessBoard.drawArray(WBbm);

        System.out.println("-------Test Rook moves-------" + "\n");
        String rboard = "rnbqkbnr/pppppppp/8/1p1R1P2/7R/8/PPPPPPPP/8 w KQkq - 0 1";
        ChessBoard rchessBoard = new ChessBoard(rboard);
        long RWP = rchessBoard.getWP();
        long RWN = rchessBoard.getWN();
        long RWB = rchessBoard.getWB();
        long RWR = rchessBoard.getWR();
        long RWQ = rchessBoard.getWQ();
        long RWK = rchessBoard.getWK();
        long RBP = rchessBoard.getBP();
        long RBN = rchessBoard.getBN();
        long RBB = rchessBoard.getBB();
        long RBR = rchessBoard.getBR();
        long RBQ = rchessBoard.getBQ();
        long RBK = rchessBoard.getBK();

        long wRTest = RWP | RWN | RWB | RWR | RWQ | RWK;
        long bRTest = RBP | RBN | RBB | RBR | RBQ | RBK;

        System.out.println("White rook");
        ChessBoard.drawArray(RWR);
        System.out.println("White pieces");
        ChessBoard.drawArray(wRTest);
        System.out.println("Black pieces");
        ChessBoard.drawArray(bRTest);
        System.out.println("White Rook moves");
        long WRbm = Move.getRookMoves(RWR, wRTest,bRTest);
        ChessBoard.drawArray(WRbm);

        System.out.println("-------Test Queen moves-------" + "\n");
        String qboard = "rnbqkbnr/pppppppp/8/1p1Q1P2/8/6Q1/PPPPPPPP/8 w KQkq - 0 1";
        ChessBoard qchessBoard = new ChessBoard(qboard);
        long QWP = qchessBoard.getWP();
        long QWN = qchessBoard.getWN();
        long QWB = qchessBoard.getWB();
        long QWR = qchessBoard.getWR();
        long QWQ = qchessBoard.getWQ();
        long QWK = qchessBoard.getWK();
        long QBP = qchessBoard.getBP();
        long QBN = qchessBoard.getBN();
        long QBB = qchessBoard.getBB();
        long QBR = qchessBoard.getBR();
        long QBQ = qchessBoard.getBQ();
        long QBK = qchessBoard.getBK();

        long wQTest = QWP | QWN | QWB | QWR | QWQ | QWK;
        long bQTest = QBP | QBN | QBB | QBR | QBQ | QBK;

        System.out.println("White queen");
        ChessBoard.drawArray(QWQ);
        System.out.println("White pieces");
        ChessBoard.drawArray(wQTest);
        System.out.println("Black pieces");
        ChessBoard.drawArray(bQTest);
        System.out.println("White queen moves");
        long WQm = Move.getQueenMoves(QWQ, wQTest, bQTest);
        ChessBoard.drawArray(WQm);


        System.out.println("Test attacked by function: everything on 6th rank should be attacked by black");
        ChessBoard.drawArray(140737488355328L);
        System.out.println(chessBoard.attackedBy(140737488355328L, 'b'));
        ChessBoard.drawArray(8796093022208L);
        System.out.println(chessBoard.attackedBy(8796093022208L, 'b'));
        System.out.println("Square on 5th rank should not be attacked by black");
        ChessBoard.drawArray(68719476736L);
        System.out.println(chessBoard.attackedBy(68719476736L, 'b'));
        System.out.println("Square on 3rd rank should be attacked by white");
        ChessBoard.drawArray(262144L);
        System.out.println(chessBoard.attackedBy(262144L, 'w'));

        System.out.println("\n" + "------ Test Move list ------" + "\n");
        System.out.println("rnbqkbnr/pppppppp/8/8/3N4/8/PPPPPPPP/RNBQKB1R w KQkq - 0 1" + "\n");
        List<ChessBoard> allMoves = Move.getAllMoves(chessBoard, true);
        int length = allMoves.size();
        System.out.println("Length of the list: " + length); // should be 24
        chessBoard.drawBoard();
        System.out.println("");
        allMoves.get(0).drawBoard();
        System.out.println("");
        allMoves.get(1).drawBoard();
        System.out.println("");
        allMoves.get(2).drawBoard();
        System.out.println("");
        allMoves.get(3).drawBoard();
        System.out.println("");

        System.out.println("\n" + "------ Test Minimax ------" + "\n");
        ChessBoard initialBoard = new ChessBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 1");
        ChessBoard nextMove = initialBoard;
        int cutoffDepth = 4;
        boolean whiteToMove = true;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Current Board:");
        initialBoard.drawBoard();

        while (!nextMove.isGameOver()) {
            if (whiteToMove) {
                nextMove = ChessAI.computeMove(initialBoard, cutoffDepth, true);
            } else {
                System.out.print("Enter your move (e.g., 'e7e5'): ");
                String userMove = scanner.nextLine();
                if (userMove.equals("forfeit")) {
                    break;
                }
                nextMove = initialBoard.performMove(userMove);
            }

            System.out.println("\n Move:");
            nextMove.drawBoard();

            initialBoard = nextMove;

            whiteToMove = !whiteToMove;
        }

        scanner.close();

        if (whiteToMove){
            System.out.println("Game Over. Black wins");
        } else {
            System.out.println("Game Over. White wins");
        }
    }
}
