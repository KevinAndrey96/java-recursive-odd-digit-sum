package com.kevinandrey.odddigits;

import java.util.List;
import java.util.stream.Stream;

/**
 * Sums the odd digits of a figure using only functions and conditionals, as the exercise demands: no loops.
 *
 * <p>The recursion splits the figure in halves instead of peeling one digit per call, so the call depth is
 * {@code log2(N)} and a million-digit figure needs about 20 frames instead of a million.
 */
public final class OddDigitSum {

    private OddDigitSum() {
    }

    public static long of(Figure figure) {
        return sum(figure, 0, figure.length());
    }

    /** Odd digits in their original order, to show how the sum was built. */
    public static List<Integer> oddDigits(Figure figure) {
        return collect(figure, 0, figure.length());
    }

    private static long sum(Figure figure, int from, int to) {
        if (to - from == 1) {
            return isOdd(figure.digitAt(from)) ? figure.digitAt(from) : 0;
        }
        int middle = (from + to) >>> 1;
        return sum(figure, from, middle) + sum(figure, middle, to);
    }

    private static List<Integer> collect(Figure figure, int from, int to) {
        if (to - from == 1) {
            return isOdd(figure.digitAt(from)) ? List.of(figure.digitAt(from)) : List.of();
        }
        int middle = (from + to) >>> 1;
        return Stream.concat(collect(figure, from, middle).stream(), collect(figure, middle, to).stream()).toList();
    }

    private static boolean isOdd(int digit) {
        return digit % 2 != 0;
    }
}
