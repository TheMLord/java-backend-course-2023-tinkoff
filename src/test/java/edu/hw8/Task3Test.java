package edu.hw8;

import edu.hw8.Task3.HackPassword;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Task3Test {
    private static final double NANO_T0_SECONDS = 0.000000001;

    @Test
    @DisplayName(
        "Test of the average solution acceleration time depending on the number of threads for selecting passwords of length from 1 to 3")
    void testOfTheAverageSolutionAccelerationTimeDependingOnTheNumberOfThreadsForSelectingPasswordsOfLengthFrom1To3()
        throws InterruptedException {

        var time1Thread = getTimeWorkAlgorithm(1);
        var time2Thread = getTimeWorkAlgorithm(2);

        var time3Thread = getTimeWorkAlgorithm(3);

        printResultTest(time1Thread, time2Thread, time3Thread);

    }

    private double getTimeWorkAlgorithm(int countThread) throws InterruptedException {
        Map<String, String> db = new HashMap<>() {{
            put("45517b8896f85a9d0e91325f68b4d0f4", " v.v.belov");
            put("2510c39011c5be704182423e3a695e91", "a.s.ivanov");
            put("822050d9ae3c47f54bee71b85fce1487", "k.p.maslov");
        }};
        int minLen = 1;
        int maxLen = 3;

        var tStart = System.nanoTime();
        var hack = new HackPassword(db, countThread, minLen, maxLen);
        hack.hackDatabasePassword();
        var tEnd = System.nanoTime();
        return ((tEnd - tStart) * NANO_T0_SECONDS);
    }

    private void printResultTest(double time1, double time2, double time3) {

        var ps = new PrintStream(System.out);

        double speedup2 = time1 / time2;
        double speedup3 = time1 / time3;

        double averageSpeedup = (speedup2 + speedup3) / 3;

        ps.println("Average Speedup: " + averageSpeedup);
        ps.println("Count threads    | Time Interval");
        ps.println("-----------------|--------------");
        ps.println("1                | " + time1);
        ps.println("2                | " + time2);
        ps.println("3                | " + time3);
    }

}
