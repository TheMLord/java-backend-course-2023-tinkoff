package edu.hw8;

import edu.hw8.Task1.Client;
import edu.hw8.Task1.QuoteServer;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    private static QuoteServer quoteServer;

    @BeforeAll
    private static void startService() throws IOException, InterruptedException {
        quoteServer = QuoteServer.buildQuoteServer();
        var threadServer = new Thread(() -> quoteServer.startServer());
        threadServer.start();
        Thread.sleep(1000);
    }

    @Nested
    @DisplayName("the test of sending and receiving messages")
    class SendMessageTests {
        private static Stream<Arguments> testData() {
            return Stream.of(
                Arguments.of(
                    "глупый",
                    "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма."
                ),
                Arguments.of("личности", "Не переходи на личности там, где их нет"),
                Arguments.of(
                    "оскорбления",
                    "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами"
                ),
                Arguments.of("интеллект", "Чем ниже интеллект, тем громче оскорбления"),

                Arguments.of("ИнТеЛЛект", "Чем ниже интеллект, тем громче оскорбления"),
                Arguments.of(
                    " ",
                    "Поскольку мои-то программы – ясное дело – всегда идеальны, я понял, что тут дело в другом. "
                        + "Пришлось пойти дальше и дизассемблировать операционную систему."
                )

            );
        }

        @ParameterizedTest
        @MethodSource("testData")
        @DisplayName(
            "Test that the client sent a message with the word and received a response returned a correct response")
        void testThatTheClientSentAMessageWithTheWordAndReceivedAResponseReturnedACorrectResponse(
            String request,
            String response
        ) throws IOException {
            var client = Client.createClient("localhost", 10101);

            var actualMessage = client.sendMessage(request);
            client.closeClient();

            assertThat(actualMessage).isEqualTo(response);
        }

    }

    @Nested
    @DisplayName("Multithreaded send request to server tests")
    class MultithreadedTests {
        @RepeatedTest(5)
        @DisplayName("Test that all customers received a response for their request and returned the correct answer")
        void testThatAllCustomersReceivedAResponseForTheirRequestAndReturnedTheCorrectAnswer()
            throws InterruptedException {
            var countRequest = 100;
            var exceptedResponse =
                "Поскольку мои-то программы – ясное дело – всегда идеальны, я понял, что тут дело в другом. "
                    + "Пришлось пойти дальше и дизассемблировать операционную систему.";

            var actualCountResponse = getCountResponse(countRequest, exceptedResponse);

            assertThat(actualCountResponse.get()).isEqualTo(countRequest);
        }

    }

    private AtomicInteger getCountResponse(int countRequest, String exceptedResponse) throws InterruptedException {
        var actualCountResponse = new AtomicInteger(0);

        List<Thread> threads = Stream.generate(() ->
                new Thread(() -> {
                    try {
                        var client = Client.createClient("localhost", 10101);
                        var actualResponse = client.sendMessage(" ");
                        assertThat(actualResponse).isEqualTo(exceptedResponse);
                        actualCountResponse.incrementAndGet();
                        client.closeClient();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
            )
            .limit(countRequest)
            .toList();
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        return actualCountResponse;
    }

    @AfterAll
    private static void stopService() throws IOException {
        quoteServer.closeServer();
    }
}
