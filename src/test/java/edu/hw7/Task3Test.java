package edu.hw7;

import edu.hw7.Task3.LockPeopleSearchService;
import edu.hw7.Task3.Person;
import edu.hw7.Task3.SynchronizedPeopleSearchService;
import java.security.SecureRandom;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {

    @Nested
    @DisplayName("Correct size tests")
    class SizeTest {
        @Test
        @DisplayName(
            "Test that the synchronized database is correctly populated in a multithreaded environment and returned the correct size")
        void testThatTheSynchronizedDatabaseIsCorrectlyPopulatedInAMultithreadedEnvironmentAndReturnedTheCorrectSize()
            throws InterruptedException {
            int exceptedDBSize = 1000;

            var db = new SynchronizedPeopleSearchService();

            var executors = Executors.newFixedThreadPool(25);

            for (int i = 0; i < 1000; i++) {
                var id = i;
                var name = "Person" + i;
                var address = "Ekb" + i;
                var phoneNumber = "Tel" + i;
                executors.submit(() -> db.add(new Person(id, name, address, phoneNumber)));
            }
            executors.shutdown();
            executors.awaitTermination(1, TimeUnit.MINUTES);

            int actualSize = db.getDataBaseSize();

            assertThat(actualSize).isEqualTo(exceptedDBSize);
        }

        @Test
        @DisplayName(
            "Test that the lock database is correctly populated in a multithreaded environment and returned the correct size")
        void testThatTheLockDatabaseIsCorrectlyPopulatedInAMultithreadedEnvironmentAndReturnedTheCorrectSize()
            throws InterruptedException {
            int exceptedDBSize = 1000;

            var db = new LockPeopleSearchService();

            var executors = Executors.newFixedThreadPool(25);

            for (int i = 0; i < 1000; i++) {
                var id = i;
                var name = "Person" + i;
                var address = "Ekb" + i;
                var phoneNumber = "Tel" + i;
                executors.submit(() -> db.add(new Person(id, name, address, phoneNumber)));
            }
            executors.shutdown();
            executors.awaitTermination(1, TimeUnit.MINUTES);

            int actualSize = db.getDataBaseSize();

            assertThat(actualSize).isEqualTo(exceptedDBSize);

        }

        @Test
        @DisplayName(
            "Test that the database with synchronization works correctly in a multithreaded environment with deletion operations and returned the correct remaining size")
        void testThatDatabaseSynchronizationWorksCorrectlyInAMultithreadedEnvironmentWithDeletionOperationsAndReturnedTheCorrectRemainingSize()
            throws InterruptedException {
            int exceptedDBSize = 0;

            var db = new SynchronizedPeopleSearchService();

            var executors = Executors.newFixedThreadPool(25);

            for (int i = 0; i < 1000; i++) {
                var id = i;
                var name = "Person" + i;
                var address = "Ekb" + i;
                var phoneNumber = "Tel" + i;
                executors.submit(() -> {
                    db.add(new Person(id, name, address, phoneNumber));
                    db.delete(id);
                });
            }
            executors.shutdown();
            executors.awaitTermination(1, TimeUnit.MINUTES);

            int actualSize = db.getDataBaseSize();

            assertThat(actualSize).isEqualTo(exceptedDBSize);
        }

        @Test
        @DisplayName(
            "Test that the database with Lock works correctly in a multithreaded environment with deletion operations and returned the correct remaining size")
        void testThatDatabaseLockWorksCorrectlyInAMultithreadedEnvironmentWithDeletionOperationsAndReturnedTheCorrectRemainingSize()
            throws InterruptedException {
            int exceptedDBSize = 0;

            var db = new LockPeopleSearchService();

            var executors = Executors.newFixedThreadPool(25);

            for (int i = 0; i < 1000; i++) {
                var id = i;
                var name = "Person" + i;
                var address = "Ekb" + i;
                var phoneNumber = "Tel" + i;
                executors.submit(() -> {
                    db.add(new Person(id, name, address, phoneNumber));
                    db.delete(id);
                });
            }
            executors.shutdown();
            executors.awaitTermination(1, TimeUnit.MINUTES);

            int actualSize = db.getDataBaseSize();

            assertThat(actualSize).isEqualTo(exceptedDBSize);
        }
    }

    @Nested
    @DisplayName("Find tests")
    class FindTest {
        @Test
        @DisplayName("Test that in the database with synchronization, only pearson with filled attributes are returned")
        void testThatInTheDatabaseWithSynchronizationOnlyPearsonWithFilledAttributesAreReturned()
            throws InterruptedException {
            var db = new SynchronizedPeopleSearchService();

            var executors = Executors.newFixedThreadPool(25);

            for (int i = 0; i < 1000; i++) {
                var id = i;
                var name = "Person" + i;
                var address = "Ekb" + i;
                var phoneNumber = "Tel" + i;
                executors.submit(() -> {
                    db.add(new Person(id, name, address, phoneNumber));
                });
            }

            var random = new SecureRandom();
            for (int i = 0; i < 1000; i++) {
                int id = random.nextInt(1000);
                boolean flag = random.nextBoolean();

                var name = "Person" + random.nextInt(1000);
                var address = "Ekb" + random.nextInt(1000);
                var phoneNumber = "Tel" + random.nextInt(1000);

                executors.submit(() -> {
                    if (flag) {
                        db.delete(id);
                    }

                    var listFindByName = db.findByName(name);
                    var listFindByAddress = db.findByAddress(address);
                    var listFindByPhone = db.findByPhone(phoneNumber);

                    for (var people : listFindByName) {
                        assertThat(
                            people.address() != null && people.name() != null && people.phoneNumber() != null)
                            .isTrue();
                    }

                    for (var people : listFindByAddress) {
                        assertThat(
                            people.address() != null && people.name() != null && people.phoneNumber() != null)
                            .isTrue();
                    }

                    for (var people : listFindByPhone) {
                        assertThat(
                            people.address() != null && people.name() != null && people.phoneNumber() != null)
                            .isTrue();
                    }

                });
            }

            executors.shutdown();
            executors.awaitTermination(1, TimeUnit.MINUTES);

        }

        @Test
        @DisplayName("Test that only people with filled attributes are returned in the c with lock database")
        void testThatOnlyPeopleWithFilledAttributesAreReturnedInTheCWithLockDatabase() throws InterruptedException {
            var db = new LockPeopleSearchService();

            var executors = Executors.newFixedThreadPool(25);

            for (int i = 0; i < 1000; i++) {
                var id = i;
                var name = "Person" + i;
                var address = "Ekb" + i;
                var phoneNumber = "Tel" + i;
                executors.submit(() -> {
                    db.add(new Person(id, name, address, phoneNumber));
                });
            }

            var random = new SecureRandom();
            for (int i = 0; i < 1000; i++) {
                int id = random.nextInt(1000);
                boolean flag = random.nextBoolean();

                var name = "Person" + random.nextInt(1000);
                var address = "Ekb" + random.nextInt(1000);
                var phoneNumber = "Tel" + random.nextInt(1000);

                executors.submit(() -> {
                    if (flag) {
                        db.delete(id);
                    }

                    var listFindByName = db.findByName(name);
                    var listFindByAddress = db.findByAddress(address);
                    var listFindByPhone = db.findByPhone(phoneNumber);

                    for (var people : listFindByName) {
                        assertThat(
                            people.address() != null && people.name() != null && people.phoneNumber() != null)
                            .isTrue();
                    }

                    for (var people : listFindByAddress) {
                        assertThat(
                            people.address() != null && people.name() != null && people.phoneNumber() != null)
                            .isTrue();
                    }

                    for (var people : listFindByPhone) {
                        assertThat(
                            people.address() != null && people.name() != null && people.phoneNumber() != null)
                            .isTrue();
                    }

                });
            }

            executors.shutdown();
            executors.awaitTermination(1, TimeUnit.MINUTES);

        }

    }

}

