package edu.hw10.task1;

import edu.hw10.task1.models.Human;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        RandomObjectGenerator rog = new Ch();

        rog.nextObject(Human.class);

//        RandomObjectGenerator ch = new ChildrenRandom();
//        ch.nextObject(Human.class);
    }
}
