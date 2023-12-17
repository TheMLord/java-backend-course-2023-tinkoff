package edu.hw10.task1;

import edu.hw10.task1.generators.ChildRandomObjectGenerator;
import edu.hw10.task1.generators.RandomObjectGenerator;
import edu.hw10.task1.models.Human;
import edu.hw10.task1.models.People;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public final class Main {
    private Main() {

    }

    public static void main(String[] args)
        throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        try (var pw = new PrintWriter(System.out)) {
            RandomObjectGenerator rog = new ChildRandomObjectGenerator();

            People people = (People) rog.nextObject(People.class);
            pw.println(Arrays.toString(Human.class.getDeclaredConstructors()));
            Human human = (Human) rog.nextObject(Human.class, "createHuman");
            pw.println("Human age: " + human.getAge() + " Human name: " + human.getName());
        }
    }
}
