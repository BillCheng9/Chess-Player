import java.util.List;

public class ChessAI {
    private static final int INITIAL_ALPHA = Integer.MIN_VALUE;
    private static final int INITIAL_BETA = Integer.MAX_VALUE;
    public static ChessBoard computeMove(ChessBoard board, int cutoffDepth, boolean player) {
        List<ChessBoard> possibleMoves = Move.getAllMoves(board, player);

        ChessBoard bestMove = null;
        int maxEval = Integer.MIN_VALUE;
        for (ChessBoard move : possibleMoves) {
            int eval = computeMin(move, 1, INITIAL_ALPHA, INITIAL_BETA, cutoffDepth, !player);
            if (eval > maxEval) {
                maxEval = eval;
                bestMove = move;
            }
        }
        return bestMove;
    }
    private static int computeMax(ChessBoard board, int currDepth, int alpha, int beta, int cutoffDepth, boolean player) {
        if (currDepth >= cutoffDepth || board.isGameOver()) {
            return board.evaluateWhite(!player);
        }

        int maxEval = Integer.MIN_VALUE;
        List<ChessBoard> possibleMoves = Move.getAllMoves(board, player);

        for (ChessBoard move : possibleMoves) {
            int childVal = computeMin(move, currDepth + 1, alpha, beta, cutoffDepth, !player);
            maxEval = Math.max(maxEval, childVal);
            if (childVal > alpha) {
                alpha = childVal;
            }
            if (beta <= alpha) {
                break;
            }
        }

        return maxEval;
    }
    private static int computeMin(ChessBoard board, int currDepth, int alpha, int beta, int cutoffDepth, boolean player) {
        if (currDepth >= cutoffDepth || board.isGameOver()) {
            return board.evaluateWhite(!player);
        }

        int minEval = Integer.MAX_VALUE;
        List<ChessBoard> possibleMoves = Move.getAllMoves(board, player);

        for (ChessBoard move : possibleMoves) {
            int childVal = computeMax(move, currDepth + 1, alpha, beta, cutoffDepth, !player);
            minEval = Math.min(minEval, childVal);
            if (childVal < beta) {
                beta = childVal;
            }
            if (beta <= alpha) {
                break;
            }
        }

        return minEval;
    }
}
