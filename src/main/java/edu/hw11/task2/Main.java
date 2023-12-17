package edu.hw11.task2;

import edu.hw11.task2.utils.ArithmeticUtils;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        try (var ps = new PrintWriter(System.out)) {
            var au = new ArithmeticUtils();
            ps.println(au.sum(2, 5));
            Task2.reloadArithmeticUtils();
            ps.println(au.sum(2, 5));
        }
    }
}
