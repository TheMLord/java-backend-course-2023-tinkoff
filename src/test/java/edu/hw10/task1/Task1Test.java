package edu.hw10.task1;

import edu.hw10.task1.generators.ChildRandomObjectGenerator;
import edu.hw10.task1.generators.RandomObjectGenerator;
import edu.hw10.task1.test_models.ConstructorPOJOPeople;
import edu.hw10.task1.test_models.FabricPOJOPeople;
import edu.hw10.task1.test_models.InterestingNumber;
import edu.hw10.task1.test_models.RecordDog;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Task1Test {
    @Nested
    @DisplayName("Generation tests")
    class GenerationTests {
        @RepeatedTest(10)
        @DisplayName(
            "Test that an instance of record is being created and returned an object with the correct annotation fields")
        void testThatAnInstanceOfRecordIsBeingCreatedAndReturnedAnObjectWithTheCorrectAnnotationFields()
            throws InvocationTargetException, IllegalAccessException, InstantiationException {
            int lowerBoundExceptedAge = 3;
            int upperBoundExceptedAge = 10;
            var rog = new RandomObjectGenerator();

            RecordDog dogObject = (RecordDog) rog.nextObject(RecordDog.class);

            assertThat(dogObject.name()).isNotNull();

            assertThat(dogObject.age() >= lowerBoundExceptedAge && dogObject.age() <= upperBoundExceptedAge)
                .isTrue();
        }

        @RepeatedTest(10)
        @DisplayName(
            "Test that an instance of POJO is created through the factory method and returned an object with the correct fields by annotations")
        void testThatAnInstanceOfPojoIsCreatedThroughTheFactoryMethodAndReturnedAnObjectWithTheCorrectFieldsByAnnotations()
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
            var rog = new RandomObjectGenerator();

            FabricPOJOPeople people = (FabricPOJOPeople) rog.nextObject(FabricPOJOPeople.class, "create");

            assertThat(people.getName()).isNotNull();
            assertThat(people.getSurname()).isNull();
            assertThat(people.getSurname()).isNull();
        }

        @RepeatedTest(10)
        @DisplayName(
            "Test that an instance of POJO is created through the constructor and returned an object with the correct fields by annotations")
        void testThatAnInstanceOfPojoIsCreatedThroughTheConstructorAndReturnedAnObjectWithTheCorrectFieldsByAnnotations()
            throws InvocationTargetException, IllegalAccessException, InstantiationException {
            var rog = new RandomObjectGenerator();
            int upperBoundExceptedAge = 10;

            ConstructorPOJOPeople people = (ConstructorPOJOPeople) rog.nextObject(ConstructorPOJOPeople.class);

            assertThat(people.getName()).isNotNull();
            assertThat(people.getSurname()).isNotNull();
            assertThat(people.getAge() <= upperBoundExceptedAge).isTrue();
        }
    }

    @Nested
    @DisplayName("Demonstration of extensibility")
    class DemonstrationExtensibility {
        @RepeatedTest(10)
        @DisplayName(
            "Test that the heir can add new possible generated data types and returned a correctly generated object")
        void testThatTheHeirCanAddNewPossibleGeneratedDataTypesAndReturnedACorrectlyGeneratedObject()
            throws InvocationTargetException, IllegalAccessException, InstantiationException {
            RandomObjectGenerator parentRog = new RandomObjectGenerator();
            RandomObjectGenerator childRog = new ChildRandomObjectGenerator();
            double lowerBoundExceptedNumberValue = 14.3;

            assertThatThrownBy(() -> {
                InterestingNumber failedNumber = (InterestingNumber) parentRog.nextObject(InterestingNumber.class);
            })
                .isInstanceOf(NullPointerException.class);

            InterestingNumber number = (InterestingNumber) childRog.nextObject(InterestingNumber.class);

            assertThat(number.number() >= lowerBoundExceptedNumberValue).isTrue();
            assertThat(number.description()).isNotNull();
        }

        @RepeatedTest(10)
        @DisplayName(
            "Test that the heir can add override the methods of the parent and returned a correctly generated object")
        void testThatTheHeirCanAddOverrideTheMethodsOfTheParentAndReturnedACorrectlyGeneratedObject()
            throws InvocationTargetException, IllegalAccessException, InstantiationException {
            RandomObjectGenerator childRog = new ChildRandomObjectGenerator();
            var exceptedName = "Michael";

            var dog = (RecordDog) childRog.nextObject(RecordDog.class);

            assertThat(dog.name()).isEqualTo(exceptedName);
        }
    }
}
