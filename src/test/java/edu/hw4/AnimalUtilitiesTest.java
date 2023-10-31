package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

class AnimalUtilitiesTest {
    private static final Animal edvard =
        new Animal("Edvard Fon Neiman", Animal.Type.DOG, Animal.Sex.M, 13, 30, 12, true);
    private static final Animal algernon =
        new Animal("Algernon", Animal.Type.CAT, Animal.Sex.M, 4, 120, 9, true);
    private static final Animal gertrude =
        new Animal("Gertrude", Animal.Type.DOG, Animal.Sex.F, 5, 16, 22, true);
    private static final Animal thaddeus =
        new Animal("Thaddeus", Animal.Type.BIRD, Animal.Sex.M, 3, 8, 1, false);
    private static final Animal mortimer =
        new Animal("Mortimer", Animal.Type.FISH, Animal.Sex.M, 2, 5, 3, false);
    private static final Animal bartholomew =
        new Animal("Bartholomew", Animal.Type.SPIDER, Animal.Sex.M, 1, 12, 3, true);
    private static final Animal prudence =
        new Animal("Prudence", Animal.Type.CAT, Animal.Sex.F, 6, 15, 11, true);
    private static final Animal reginald =
        new Animal("Reginald", Animal.Type.DOG, Animal.Sex.M, 7, 19, 28, true);
    private static final Animal cornelia =
        new Animal("Cornelia", Animal.Type.BIRD, Animal.Sex.F, 4, 10, 4, false);
    private static final Animal oswald =
        new Animal("Oswald", Animal.Type.FISH, Animal.Sex.M, 3, 6, 4, false);
    private static final Animal eugenia =
        new Animal("Eugenia", Animal.Type.SPIDER, Animal.Sex.F, 8, 3, 2, false);

    private static final List<Animal> animalsList = List.of(
        edvard,
        algernon,
        gertrude,
        thaddeus,
        mortimer,
        bartholomew,
        prudence,
        reginald,
        cornelia,
        oswald,
        eugenia
    );

    @Test
    @DisplayName(
        "Test that the list of animals is sorted ascending by height and returned the list in the correct order")
    void testThatTheListOfAnimalsIsSortedAscendingByWeightAndReturnedTheListInTheCorrectOrder() {
        var expectedSortedListAnimal = List.of(
            eugenia,
            mortimer,
            oswald,
            thaddeus,
            cornelia,
            bartholomew,
            prudence,
            gertrude,
            reginald,
            edvard,
            algernon
        );

        var sortedListAnimal = AnimalUtilities.sortASCAnimalByHeight(animalsList);

        assertThat(sortedListAnimal).isEqualTo(expectedSortedListAnimal);
    }

    @Test
    @DisplayName(
        "Test that the list of animals is sorted descending order by weight and returned the list of k elements in the correct order")
    void testThatTheListOfAnimalsIsSortedDescendingOrderByWeightAndReturnedTheListOfKElementsInTheCorrectOrder() {
        int k = 5;
        var expectedSortedListAnimal = List.of(
            reginald,
            gertrude,
            edvard,
            prudence,
            algernon
        );

        var sortedListAnimal = AnimalUtilities.sortDESCAnimalByWeight(animalsList, k);

        assertThat(sortedListAnimal.size()).isEqualTo(k);
        assertThat(sortedListAnimal).isEqualTo(expectedSortedListAnimal);
    }

    @Test
    @DisplayName("Test that animals of each type are counted correctly and returned map with types and quantity")
    void testThatAnimalsOfEachTypeAreCountedCorrectlyAndReturnedMapWithTypesAndQuantity() {
        var expectedMap = Map.of(
            Animal.Type.CAT, 2,
            Animal.Type.DOG, 3,
            Animal.Type.BIRD, 2,
            Animal.Type.FISH, 2,
            Animal.Type.SPIDER, 2
        );

        var typeCountMap = AnimalUtilities.getMapAnimalTypes(animalsList);

        assertThat(typeCountMap).isEqualTo(expectedMap);
    }

    @Test
    @DisplayName(
        "Test that in among the animals there is an animal with the maximum name in length and this object is returned from the list")
    void testThatInAmongTheAnimalsThereIsAnAnimalWithTheMaximumNameInLengthAndThisObjectIsReturnedFromTheList() {
        var expectedAnimalWithMaxLengthName = edvard;

        var maxLengthNameAnimal = AnimalUtilities.getAnimalWithLongestName(animalsList);

        assertThat(maxLengthNameAnimal).isEqualTo(expectedAnimalWithMaxLengthName);
    }

    @Test
    @DisplayName("Test that is the most common sex among animals and returned correctly this type")
    void testThatIsTheMostCommonSexAmongAnimalsAndReturnedCorrectlyThisType() {
        var expectedMostCommonSex = Animal.Sex.M;

        var mostCommonSex = AnimalUtilities.getMostCommonSex(animalsList);

        assertThat(mostCommonSex).isEqualTo(expectedMostCommonSex);
    }

    @Test
    @DisplayName("Test that Maps with heavy animals of each species are formed and returned the correct Map")
    void testThatMapsWithHeavyAnimalsOfEachSpeciesAreFormedAndReturnedTheCorrectMap() {
        var expectedMap = Map.of(
            Animal.Type.CAT, prudence,
            Animal.Type.DOG, reginald,
            Animal.Type.BIRD, cornelia,
            Animal.Type.SPIDER, bartholomew,
            Animal.Type.FISH, oswald
        );

        var heavyAnimals = AnimalUtilities.getMapWithMostHeaviestAnimalEachType(animalsList);

        assertThat(heavyAnimals).isEqualTo(expectedMap);
    }

    @Test
    @DisplayName("Test that what is found and returned oldest k animal")
    void testThatWhatIsFoundAndReturnedKOldestAnimal() {
        var expectedOldestAnimal = algernon;

        int kIndex = 5;
        var oldestAnimal = AnimalUtilities.getKOldestAnimal(animalsList, kIndex);

        assertThat(oldestAnimal).isEqualTo(expectedOldestAnimal);
    }

    @Test
    @DisplayName("Test that is the heaviest animal among animals below k cm and returned this animal")
    void testThatIsTheHeaviestAnimalAmongAnimalsBelowKCmAndReturnedThisAnimal() {
        var k1 = 100;
        var k2 = 1;

        Animal expectedAnimalTest1 = reginald;

        var animal1 = AnimalUtilities.getHeaviestAnimalBelowKParameter(animalsList, k1);
        var animal2 = AnimalUtilities.getHeaviestAnimalBelowKParameter(animalsList, k2);

        assertThat(animal1.get()).isEqualTo(expectedAnimalTest1);
        assertThat(animal2).isEmpty();
    }

    @Test
    @DisplayName(
        "Test that is correctly considered the number of paws of all animals in the list returned an integer the number of paws")
    void testThatIsCorrectlyConsideredTheNumberOfPawsOfAllAnimalsInTheListReturnedAnIntegerTheNumberOfPaws() {
        Integer expectedCountPaws = 40;

        var countAnimalPawsInList = AnimalUtilities.getAmountAnimalPaws(animalsList);

        assertThat(countAnimalPawsInList).isEqualTo(expectedCountPaws);
    }

    @Test
    @DisplayName(
        "Test that there are animals with an age that does not match the number of llamas and returned the list of these animals correctly")
    void testThatThereAreAnimalsWithAnAgeThatDoesNotMatchTheNumberOfLlamasAndReturnedTheListOfTheseAnimalsCorrectly() {
        var expectedListAnimals = List.of(
            edvard,
            thaddeus,
            mortimer,
            bartholomew,
            prudence,
            reginald,
            cornelia,
            oswald
        );

        var resultListAnimals = AnimalUtilities.getAnimalsWhoseAgeNotEqualNumberPaws(animalsList);

        assertThat(resultListAnimals).containsAll(expectedListAnimals);
        assertThat(resultListAnimals).doesNotContain(algernon, eugenia);
    }

    @Test
    @DisplayName(
        "Test that what are the animals from the list of animals that can bite and their height is more than 100 cm returned the list of such animals")
    void testThatWhatAreTheAnimalsFromTheListOfAnimalsThatCanBiteAndTheirHeightIsMoreThan100CmReturnedTheListOfSuchAnimals() {
        var expectedListBitingAnimals = List.of(
            algernon
        );

        var listBitingAnimals = AnimalUtilities.getBitingAnimals(animalsList);

        assertThat(listBitingAnimals).containsAll(expectedListBitingAnimals);
    }

    @Test
    @DisplayName(
        "Test that are the animals from the count of animals whose weight exceeds height returned this count is correctly")
    void testThatAreTheAnimalsFromTheListOfAnimalsWhoseWeightExceedsHeightReturnedThisListIsCorrectly() {
        var expectedAnimalsCount = 2;

        var resultAnimalsCount = AnimalUtilities.getAnimalWhoseWeightMoreHeight(animalsList);

        assertThat(resultAnimalsCount).isEqualTo(expectedAnimalsCount);
    }

    @Test
    @DisplayName(
        "Test that there are animals with names consisting of more than two words the list of such animals is returned correctly")
    void testThatThereAreAnimalsWithNamesConsistingOfMoreThanTwoWordsTheListOfSuchAnimalsIsReturnedCorrectly() {
        var expectedList = List.of(
            edvard
        );

        var resultListAnimal = AnimalUtilities.getAnimalsWithNameConsistMoreThanTwoWords(animalsList);

        assertThat(resultListAnimal).containsAll(expectedList);
    }

    @Test
    @DisplayName(
        "Test that the method correctly determines whether there is a dog with a height of more than k in the list and returns true if so and falls otherwise")
    void testThatTheMethodCorrectlyDeterminesWhetherThereIsADogWithAHeightOfMoreThanKInTheListAndReturnsTrueIfSoAndFallsOtherwise() {
        int k1 = 2;
        int k2 = 200;

        boolean queryK1 = AnimalUtilities.isDogWithHeightMoreK(animalsList, k1);
        boolean queryK2 = AnimalUtilities.isDogWithHeightMoreK(animalsList, k2);

        assertThat(queryK1).isTrue();
        assertThat(queryK2).isFalse();
    }

    @Test
    @DisplayName(
        "Test that correctly searches for the sum of the weight of each type  animals whose age ranges from k to l returned map with type and this sum")
    void testThatCorrectlySearchesForTheSumOfTheWeightOfEachTypeAnimalsWhoseAgeRangesFromKToLReturnedMapWithTypeAndThisSum() {
        Map<Animal.Type, Integer> expectedQuery1 = new HashMap<>() {{
            put(Animal.Type.BIRD, 5);
            put(Animal.Type.CAT, 20);
            put(Animal.Type.FISH, 7);
            put(Animal.Type.SPIDER, 2);
            put(Animal.Type.DOG, 50);
        }};
        Map<Animal.Type, Integer> expectedQuery2 = new HashMap<>() {};
        int k1 = 1;
        int l1 = 10;
        int k2 = 50;
        int l2 = 800;

        var animalsEachTypeWeightSumQuery1 =
            AnimalUtilities.findTotalWeightEachTypeAnimalsAgedFromKtoL(animalsList, k1, l1);
        var animalsEachTypeWeightSumQuery2 =
            AnimalUtilities.findTotalWeightEachTypeAnimalsAgedFromKtoL(animalsList, k2, l2);

        assertThat(animalsEachTypeWeightSumQuery1).isEqualTo(expectedQuery1);
        assertThat(animalsEachTypeWeightSumQuery2).isEqualTo(expectedQuery2);
    }

    @Test
    @DisplayName("Test triple sorting")
    void testTripleSorting() {
        var expectedSort = List.of(
            algernon,
            prudence,
            edvard,
            reginald,
            gertrude,
            thaddeus,
            cornelia,
            mortimer,
            oswald,
            bartholomew,
            eugenia
        );

        var tripleSorting = AnimalUtilities.sortAnimalsByTypeAndThenSexAndThenName(animalsList);

        assertThat(tripleSorting).isEqualTo(expectedSort);
    }

    @Test
    @DisplayName(
        "Test that a check is performed correctly that spiders bite more often than dogs returned a boolean value")
    void testThatACheckIsPerformedCorrectlyThatSpidersBiteMoreOftenThanDogsReturnedABooleanValue() {
        var isThisFact = AnimalUtilities.isSpiderBitesMoreOftenThanDogs(animalsList);

        assertThat(isThisFact).isFalse();
    }

    @Test
    @DisplayName("Test that is the heaviest fish being searched for from different lists returned this fish")
    void testThatIsTheHeaviestFishBeingSearchedForFromDifferentListsReturnedThisFish() {
        var testFish1 = new Animal("bobo", Animal.Type.FISH, Animal.Sex.M, 150, 50, 120, true);
        var testFish2 = new Animal("bobo", Animal.Type.FISH, Animal.Sex.F, 12, 88, 44, true);
        var listsAnimals = List.of(
            List.of(
                edvard,
                oswald
            ),
            List.of(
                testFish1,
                cornelia
            ),
            List.of(
                testFish2
            )
        );
        var expectedFish = testFish2;

        var heaviestFish = AnimalUtilities.getHeaviestFish(listsAnimals);

        assertThat(heaviestFish).isEqualTo(expectedFish);
    }

    @Test
    @DisplayName(
        "Test that is performed correctly checking animals for incorrect fields returns a Map with errors and the name of the animal")
    void testThatIsPerformedCorrectlyCheckingAnimalsForIncorrectFieldsReturnsAMapWithErrorsAndTheNameOfTheAnimal() {
        var animalsListWithInvalidField = List.of(
            new Animal("Bim", Animal.Type.SPIDER, Animal.Sex.M, -4, -5, -7, true),
            new Animal("Gojo", Animal.Type.BIRD, Animal.Sex.F, 4, 5, -44, true)
        );

        var animalsErrorMap = AnimalUtilities.getAnimalsWithErrorsInField(animalsListWithInvalidField);

        assertThat(animalsErrorMap)
            .containsKey("Bim")
            .satisfies(errorSet -> {
                Set<ValidationError> errors = errorSet.get("Bim");
                assertThat(errors)
                    .extracting(ValidationError::getMessage)
                    .containsExactlyInAnyOrder(
                        "height must be a positive integer",
                        "weight must be a positive integer",
                        "age must be a positive integer"
                    );
            });

        assertThat(animalsErrorMap)
            .containsKey("Gojo")
            .satisfies(errorSet -> {
                Set<ValidationError> errors = errorSet.get("Gojo");
                assertThat(errors)
                    .extracting(ValidationError::getMessage)
                    .containsExactly("weight must be a positive integer");
            });
    }

    @Test
    @DisplayName(
        "Test that the method returns the name of the animal with a list of its fields in which errors are found returned the Map with the name and errors correct")
    void testThatTheMethodReturnsTheNameOfTheAnimalWithAListOfItsFieldsInWhichErrorsAreFoundReturnedTheMapWithTheNameAndErrorsCorrect() {
        var animalsListWithInvalidField = List.of(
            new Animal("Bim", Animal.Type.SPIDER, Animal.Sex.M, -4, -5, -7, true),
            new Animal("Gojo", Animal.Type.BIRD, Animal.Sex.F, 4, 5, -44, true)
        );

        var prettyPrintedErrors = AnimalUtilities.getAnimalsWithErrorsInFieldPrettyPrint(animalsListWithInvalidField);

        assertThat(prettyPrintedErrors.entrySet())
            .containsOnly(
                entry("Bim", "age, height, weight"),
                entry("Gojo", "weight")
            );
    }
}
