package edu.hw2;

import edu.hw2.Task4.CallingInfo;
import edu.hw2.Task4.CallingInfoUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Test calling info stack is not empty")
    void testCallingInfoStackIsNotEmpty() {
        List<CallingInfo> callingInfoList = CallingInfoUtil.callingInfo();
        assertThat(callingInfoList.isEmpty()).isFalse();
    }

}
