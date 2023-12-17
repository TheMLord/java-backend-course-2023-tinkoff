package edu.hw11.task1;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException,
        NoSuchMethodException, InvocationTargetException {

        try (var ps = new PrintWriter(System.out)) {
            var buddyClass = Task1.createLoadedBuddyClass();
            ps.println(buddyClass);
        }
    }
}
