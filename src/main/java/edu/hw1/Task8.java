package edu.hw1;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Class with a static method that checks whether a knight can attack a knight
 */
public final class Task8 {

    /**
     * Class constructor.
     */
    private Task8() {

    }

    private static final int HORSE_CODE = 1;
    private static final int LOWER_BORDER_BOARD = 0;
    private static final int UPPER_BORDER_BOARD = 8;

    private static final int STEP_TWO = 2;
    private static final int STEP_ONE = 1;

    private static final List<Pair<Integer, Integer>> MOVES_HORSE = new ArrayList<>() {{
        add(Pair.of(-STEP_TWO, STEP_ONE));
        add(Pair.of(-STEP_ONE, STEP_TWO));
        add(Pair.of(STEP_ONE, STEP_TWO));
        add(Pair.of(STEP_TWO, STEP_ONE));
        add(Pair.of(STEP_TWO, -STEP_ONE));
        add(Pair.of(STEP_ONE, -STEP_TWO));
        add(Pair.of(-STEP_ONE, -STEP_TWO));
        add(Pair.of(-STEP_TWO, -STEP_ONE));
    }};

    /**
     * Method checks whether the knight can attack the knight.
     *
     * @param board chessboard 8 by 8.
     * @return true if the knight can attack the knight and false in other case.
     */
    public static boolean knightBoardCapture(int[][] board) {
        for (int i = LOWER_BORDER_BOARD; i < UPPER_BORDER_BOARD; i++) {
            for (int j = LOWER_BORDER_BOARD; j < UPPER_BORDER_BOARD; j++) {

                if (board[i][j] == HORSE_CODE) {

                    for (int k = LOWER_BORDER_BOARD; k < UPPER_BORDER_BOARD; k++) {
                        int positionI = i + MOVES_HORSE.get(k).getLeft();
                        int positionJ = j + MOVES_HORSE.get(k).getRight();

                        if (positionI >= LOWER_BORDER_BOARD && positionI < UPPER_BORDER_BOARD
                            && positionJ >= LOWER_BORDER_BOARD && positionJ < UPPER_BORDER_BOARD) {
                            if (board[positionI][positionJ] == HORSE_CODE) {
                                return false;
                            }
                        }
                    }

                }
            }
        }
        return true;
    }

}
