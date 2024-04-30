import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessAI {
    private static final Map<Long, TranspositionEntry> transpositionTable;

    static {
        transpositionTable = new HashMap<>();
    }

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
            if (eval > maxEval) {
                maxEval = eval;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private static int computeMax(ChessBoard board, int currDepth, int alpha, int beta, int cutoffDepth, boolean player) {
        Long hash = ZHashing.generateHashKey(board, player);
        if (transpositionTable.containsKey(hash)) {
            TranspositionEntry entry = transpositionTable.get(hash);
            if (entry.depth >= currDepth) {
                return entry.eval;
            }
        }

        if (currDepth >= cutoffDepth || board.isGameOver()) {
            return board.evaluateWhite(!player);
        }

        int maxEval = Integer.MIN_VALUE;
        List<ChessBoard> possibleMoves = Move.getAllMoves(board, player);

        for (ChessBoard move : possibleMoves) {
            int childVal = computeMin(move, currDepth + 1, alpha, beta, cutoffDepth, !player);
            maxEval = Math.max(maxEval, childVal);
            alpha = Math.max(alpha, childVal);
            if (alpha >= beta) {
                break;
            }
        }

        transpositionTable.put(hash, new TranspositionEntry(maxEval, currDepth));
        return maxEval;
    }

    private static int computeMin(ChessBoard board, int currDepth, int alpha, int beta, int cutoffDepth, boolean player) {
        Long hash = ZHashing.generateHashKey(board, player);
        if (transpositionTable.containsKey(hash)) {
            TranspositionEntry entry = transpositionTable.get(hash);
            if (entry.depth >= currDepth) {
                return entry.eval;
            }
        }

        if (currDepth >= cutoffDepth || board.isGameOver()) {
            return board.evaluateWhite(!player);
        }

        int minEval = Integer.MAX_VALUE;
        List<ChessBoard> possibleMoves = Move.getAllMoves(board, player);

        for (ChessBoard move : possibleMoves) {
            int childVal = computeMax(move, currDepth + 1, alpha, beta, cutoffDepth, !player);
            minEval = Math.min(minEval, childVal);
            beta = Math.min(beta, childVal);
            if (alpha >= beta) {
                break;
            }
        }

        transpositionTable.put(hash, new TranspositionEntry(minEval, currDepth));
        return minEval;
    }

    private record TranspositionEntry(int eval, int depth) {
    }

    public static void printTranspositionTable() {
        System.out.println("Transposition Table:");
        for (Map.Entry<Long, TranspositionEntry> entry : transpositionTable.entrySet()) {
            Long hash = entry.getKey();
            TranspositionEntry transpositionEntry = entry.getValue();
            System.out.println("Hash: " + hash + ", Transposition Entry: " + transpositionEntry);
        }
    }
}