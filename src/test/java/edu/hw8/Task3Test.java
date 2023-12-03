package edu.hw8;

import edu.hw8.Task3.HackPassword;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Task3Test {
    private static final double NANO_T0_SECONDS = 0.000000001;
    private static final String RESULT_WORK_MESSAGE = "Время работы с %d потоком/и - %f секунд\n";

    @Test
    @DisplayName(
        "Test of the average solution acceleration time depending on the number of threads for selecting passwords of length from 1 to 3")
    void testOfTheAverageSolutionAccelerationTimeDependingOnTheNumberOfThreadsForSelectingPasswordsOfLengthFrom1To3()
        throws InterruptedException {
        var ps = new PrintStream(System.out);

        var time1Thread = getTimeWorkAlgorithm(1);
        ps.printf(RESULT_WORK_MESSAGE, 1, time1Thread);
        var time2Thread = getTimeWorkAlgorithm(2);
        ps.printf(RESULT_WORK_MESSAGE, 2, time2Thread);

        var time3Thread = getTimeWorkAlgorithm(3);
        ps.printf(RESULT_WORK_MESSAGE, 3, time3Thread);

        var time4Thread = getTimeWorkAlgorithm(4);
        ps.printf(RESULT_WORK_MESSAGE, 4, time4Thread);

        var avr12 = time1Thread / time2Thread;
        var avr13 = time1Thread / time3Thread;
        var avr14 = time1Thread / time4Thread;

        ps.println("Среднее время ускорения при увеличении потоков (по сравнению с однопоточным исполнением) - " +
            (avr12 + avr13 + avr14) / 3);

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
        var encodedDB = hack.hackDatabasePassword();
        var tEnd = System.nanoTime();
        return ((tEnd - tStart) * NANO_T0_SECONDS);

    }

}
