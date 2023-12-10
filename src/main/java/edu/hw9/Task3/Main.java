package edu.hw9.Task3;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@SuppressWarnings({"MagicNumber"})
public final class Main {

    private Main() {

    }

    public static void main(String[] args) {
        try (var pw = new PrintWriter(System.out)) {
            List<List<Integer>> massAdj = new ArrayList<>();
            massAdj.add(List.of(1)); // 0
            massAdj.add(List.of(0, 3, 2)); // 1
            massAdj.add(List.of(3, 2, 1, 0)); // 2
            massAdj.add(List.of(4, 1, 2, 3, 0)); // 3
            massAdj.add(List.of(5)); // 4
            massAdj.add(List.of(1, 2, 3)); //5

            var dfs = new RecursiveDFS(massAdj, 0, 5, new boolean[massAdj.size()]);

            try (var fjDFS = new ForkJoinPool()) {
                var isPathExist = fjDFS.invoke(dfs);
                pw.println(isPathExist);
            }
        }
    }

}
