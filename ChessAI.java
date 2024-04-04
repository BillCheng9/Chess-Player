import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ChessAI {
    private static Map<Long, Integer> transpositionTable;
    static {
        transpositionTable = new HashMap<>();
    }

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

        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        ChessBoard bestMove = null;
        int maxEval = Integer.MIN_VALUE;
        for (ChessBoard move : possibleMoves) {
            int eval = computeMin(move, 1, alpha, beta, cutoffDepth, !player);
            if (eval > alpha) {
                alpha = eval; // update alpha
            }
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
        // Check if the board position is already evaluated
        Long hash = ZHashing.generateHashKey(board);
        if (transpositionTable.containsKey(hash)) {
            return transpositionTable.get(hash);
        }

        if (currDepth >= cutoffDepth || board.isGameOver()) {
            return board.evaluateWhite(!player);
        }

        int maxEval = Integer.MIN_VALUE;
        List<ChessBoard> possibleMoves = Move.getAllMoves(board, player);

        for (ChessBoard move : possibleMoves) {
            int childVal = computeMin(move, currDepth + 1, alpha, beta, cutoffDepth, !player);
            maxEval = Math.max(maxEval, childVal);
            if (childVal > alpha) {
                alpha = childVal; // update alpha
            }
            if (beta <= alpha) {
                break; // beta pruning
            }
        }

        // Store the evaluated position in the transposition table
        transpositionTable.put(hash, maxEval);
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
        // Check if the board position is already evaluated
        Long hash = ZHashing.generateHashKey(board);
        if (transpositionTable.containsKey(hash)) {
            return transpositionTable.get(hash);
        }

        if (currDepth >= cutoffDepth || board.isGameOver()) {
            return board.evaluateWhite(!player);
        }

        int minEval = Integer.MAX_VALUE;
        List<ChessBoard> possibleMoves = Move.getAllMoves(board, player);

        for (ChessBoard move : possibleMoves) {
            int childVal = computeMax(move, currDepth + 1, alpha, beta, cutoffDepth, !player);
            minEval = Math.min(minEval, childVal);
            if (childVal < beta) {
                beta = childVal; // update beta
            }
            if (beta <= alpha) {
                break; //  alpha pruning
            }
        }

        // Store the evaluated position in the transposition table
        transpositionTable.put(hash, minEval);
        return minEval;
    }
}
