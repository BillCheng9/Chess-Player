import java.util.List;

public class ChessAI {
    // Constants for initial alpha and beta values
    private static final int INITIAL_ALPHA = Integer.MIN_VALUE;
    private static final int INITIAL_BETA = Integer.MAX_VALUE;

    /**
     * Computes the best move for the given chessboard using the basic minimax algorithm with alpha-beta pruning.
     *
     * @param board The current state of the chessboard.
     * @param cutoffDepth The maximum depth to search in the game tree.
     * @param player The current player (true for white, false for black).
     * @return The best move to make based on the computed evaluation.
     */
    public static ChessBoard computeMove(ChessBoard board, int cutoffDepth, boolean player) {
        List<ChessBoard> possibleMoves = Move.getAllMoves(board, player);

        ChessBoard bestMove = null;
        int maxEval = Integer.MIN_VALUE;
        for (ChessBoard move : possibleMoves) {
            int eval = computeMin(move, 1, INITIAL_ALPHA, INITIAL_BETA, cutoffDepth, !player);
            if (eval > maxEval) { // depth 0 max evaluation
                maxEval = eval;
                bestMove = move;
            }
        }
        return bestMove;
    }

    /**
     * Recursively computes the maximum value for a given chessboard in the minimax algorithm.
     *
     * @param board The current state of the chessboard.
     * @param currDepth The current depth in the game tree.
     * @param alpha The best value for the maximizing player along the path to the root.
     * @param beta The best value for the minimizing player along the path to the root.
     * @param cutoffDepth The maximum depth to search in the game tree.
     * @param player The current player (true for white, false for black).
     * @return The maximum evaluation value for the given chessboard.
     */
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

    /**
     * Recursively computes the minimum value for a given chessboard in the minimax algorithm.
     *
     * @param board The current state of the chessboard.
     * @param currDepth The current depth in the game tree.
     * @param alpha The best value for the maximizing player along the path to the root.
     * @param beta The best value for the minimizing player along the path to the root.
     * @param cutoffDepth The maximum depth to search in the game tree.
     * @param player The current player (true for white, false for black).
     * @return The minimum evaluation value for the given chessboard.
     */
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
