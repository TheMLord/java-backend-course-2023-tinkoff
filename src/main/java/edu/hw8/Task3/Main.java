package edu.hw8.Task3;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public final class Main {
    private static final int COUNT_THREAD = 8;
    private static final int MIN_PASSWORD_LENGTH = 1;
    private static final int MAX_PASSWORD_LENGTH = 4;

    private Main() {

    }

    public static void main(String[] args) throws InterruptedException {
        /*
        a.v.petrov  81dc9bdb52d04dc20036dbd8313ed055 // 1234
        v.v.belov   45517b8896f85a9d0e91325f68b4d0f4 // gh2
        a.s.ivanov  2510c39011c5be704182423e3a695e91 // h
        k.p.maslov  822050d9ae3c47f54bee71b85fce1487 // rt
         */

        Map<String, String> db = new HashMap<>() {{
            put("81dc9bdb52d04dc20036dbd8313ed055", "a.v.petrov");
            put("45517b8896f85a9d0e91325f68b4d0f4", " v.v.belov");
            put("2510c39011c5be704182423e3a695e91", "a.s.ivanov");
            put("822050d9ae3c47f54bee71b85fce1487", "k.p.maslov");
        }};

        var hack = new HackPassword(db, COUNT_THREAD, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
        var encodedDB = hack.hackDatabasePassword();
        try (var ps = new PrintStream(System.out)) {
            ps.println(encodedDB);
        }
    }
}
