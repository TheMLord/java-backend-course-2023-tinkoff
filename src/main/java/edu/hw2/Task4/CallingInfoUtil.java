package edu.hw2.Task4;

import java.util.ArrayList;
import java.util.List;

/**
 * Class utility that returns the call stack.
 */
public class CallingInfoUtil {
    private CallingInfoUtil() {

    }

    /**
     * Method that returns the called classes and their methods.
     *
     * @return arrayList with CallingInfo where the called classes and their methods from the stack are described.
     */
    public static List<CallingInfo> callingInfo() {
        List<CallingInfo> callingInfoList = new ArrayList<>();

        Throwable throwable = new Throwable();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        for (var straceElement : stackTraceElements) {
            callingInfoList.add(new CallingInfo(straceElement.getClassName(), straceElement.getMethodName()));
        }
        return callingInfoList;
    }
}
