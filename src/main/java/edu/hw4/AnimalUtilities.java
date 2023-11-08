package edu.hw4;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Animal utility class.
 */
public final class AnimalUtilities {
    private static final int PARAMETER_HUNDRED = 10;

    /**
     * Class constructor.
     */
    private AnimalUtilities() {

    }

    /**
     * Task1
     * Sort animals by height from smallest to largest.
     */
    public static List<Animal> sortASCAnimalByHeight(@NotNull List<Animal> animals) {
        return animals.stream().sorted(Comparator.comparing(Animal::height)).toList();
    }

    /**
     * Task2
     * Sort animals by weight from heaviest to lightest, select k first.
     */
    public static List<Animal> sortDESCAnimalByWeight(@NotNull List<Animal> animals, int k) {
        return animals.stream().sorted(Comparator.comparing(Animal::weight).reversed()).limit(k).toList();
    }

    /**
     * Task3
     * How many animals of each species.
     */
    public static Map<Animal.Type, Integer> getMapAnimalTypes(@NotNull List<Animal> animals) {
        return animals.stream().collect(
            HashMap::new,
            (animalMap, animal) -> animalMap.merge(animal.type(), 1, Integer::sum),
            HashMap::putAll
        );
    }

    /**
     * Task4
     * Which animal has the longest name.
     */
    public static @NotNull Animal getAnimalWithLongestName(List<Animal> animals) {
        return animals.stream().max(Comparator.comparing(animal -> animal.name().length())).get();
    }

    /**
     * Task5
     * Which animals are more: males or females.
     */
    public static Animal.Sex getMostCommonSex(@NotNull List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    /**
     * Task6
     * The heaviest animal of each species.
     */
    public static Map<Animal.Type, Animal> getMapWithMostHeaviestAnimalEachType(@NotNull List<Animal> animals) {
        return animals.stream().collect(
            Collectors.groupingBy(
                Animal::type,
                Collectors.collectingAndThen(
                    Collectors.maxBy(Comparator.comparing(Animal::weight)),
                    optional -> optional.orElse(null)
                )
            )
        );
    }

    /**
     * Task7
     * K-th oldest animal.
     */
    public static Animal getKOldestAnimal(@NotNull List<Animal> animals, int k) {
        return animals.stream().sorted(Comparator.comparing(Animal::age).reversed()).toList().get(k);
    }

    /**
     * Task8
     * The heaviest animal among animals below k cm.
     */
    public static Optional<Animal> getHeaviestAnimalBelowKParameter(@NotNull List<Animal> animals, int k) {
        return animals.stream().filter((animal) -> (animal.height() < k)).max(Comparator.comparing(Animal::weight));
    }

    /**
     * Task9
     * How many paws do the animals in the list have in total.
     */
    public static Integer getAmountAnimalPaws(@NotNull List<Animal> animals) {
        return animals.stream().mapToInt(Animal::paws).sum();
    }

    /**
     * Task10
     * List of animals whose age does not match the number of paws.
     */
    public static List<Animal> getAnimalsWhoseAgeNotEqualNumberPaws(@NotNull List<Animal> animals) {
        return animals.stream().filter((animal -> (animal.age() != animal.paws()))).toList();
    }

    /**
     * Task11
     * List of animals that can bite (bites == true) and whose height exceeds 100 cm.
     */
    public static List<Animal> getBitingAnimals(@NotNull List<Animal> animals) {
        return animals.stream().filter(
                (
                    animal -> ((animal.bites()) && animal.height() > PARAMETER_HUNDRED)
                )
            )
            .toList();
    }

    /**
     * Task12
     * How many animals are in the list whose weight exceeds height.
     */
    public static Integer getAnimalWhoseWeightMoreHeight(@NotNull List<Animal> animals) {
        return ((Long) animals.stream()
            .filter((animal -> animal.weight() > animal.height()))
            .count())
            .intValue();
    }

    /**
     * Task13
     * List of animals whose names consist of more than two words.
     */
    public static List<Animal> getAnimalsWithNameConsistMoreThanTwoWords(@NotNull List<Animal> animals) {
        return animals.stream().filter((animal -> animal.name().split(" ").length > 2)).toList();
    }

    /**
     * Task14
     * Is there a dog in the list with a height of more than k cm.
     */
    public static @NotNull Boolean isDogWithHeightMoreK(@NotNull List<Animal> animals, int k) {
        return animals.stream().anyMatch(animal -> animal.type().equals(Animal.Type.DOG) && animal.height() > k);
    }

    /**
     * Task15
     * Find the total weight of animals of each species that are from k to l years old.
     */
    public static Map<Animal.Type, Integer> findTotalWeightEachTypeAnimalsAgedFromKtoL(
        @NotNull List<Animal> animals,
        int k,
        int l
    ) {
        return animals.stream().filter(animal -> animal.age() > k && animal.age() < l)
            .collect(
                Collectors.groupingBy(
                    Animal::type,
                    Collectors.summingInt(Animal::weight)
                )
            );
    }

    /**
     * Task16
     * Список животных, отсортированный по виду, затем по полу, затем по имени.
     */
    public static List<Animal> sortAnimalsByTypeAndThenSexAndThenName(@NotNull List<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparing(Animal::type).thenComparing(Animal::sex).thenComparing(Animal::name)).toList();
    }

    /**
     * Task17
     * Список животных, отсортированный по виду, затем по полу, затем по имени.
     */
    public static Boolean isSpiderBitesMoreOftenThanDogs(@NotNull List<Animal> animals) {
        return animals.stream().filter(animal -> animal.bites() && animal.type().equals(Animal.Type.DOG)).count()
            < animals.stream().filter(animal -> animal.bites() && animal.type().equals(Animal.Type.SPIDER)).count();
    }

    /**
     * Task18
     * Найти самую тяжелую рыбку в 2-х или более списках.
     */
    public static Animal getHeaviestFish(@NotNull List<List<Animal>> animals) {
        return animals.stream().flatMap(List::stream).filter(animal -> animal.type().equals(Animal.Type.FISH))
            .max(Comparator.comparing(Animal::height)).orElse(null);
    }

    /**
     * Task19
     * Животные, в записях о которых есть ошибки: вернуть имя и список ошибок.
     */
    public static Map<String, Set<ValidationError>> getAnimalsWithErrorsInField(@NotNull List<Animal> animals) {
        return animals.stream().collect(Collectors.groupingBy(
            Animal::name,
            Collectors.flatMapping(animal -> validationAnimalCheck(animal).stream(), Collectors.toSet())
        ));
    }

    /**
     * Task20
     * Сделать результат предыдущего задания более читабельным: вернуть
     * имя и названия полей с ошибками, объединенные в строку.
     */
    public static Map<String, String> getAnimalsWithErrorsInFieldPrettyPrint(@NotNull List<Animal> animals) {
        return getAnimalsWithErrorsInField(animals).entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                animalErrorLog -> animalErrorLog.getValue().stream()
                    .map(ValidationError::getInvalidField)
                    .sorted()
                    .collect(Collectors.joining(", "))
            ));
    }

    private static Set<ValidationError> validationAnimalCheck(Animal animal) {
        Set<ValidationError> validationErrorSetErrors = new HashSet<>();
        if (animal.age() < 0) {
            validationErrorSetErrors.add(
                new ValidationError(
                    ValidationError.ErrorType.INVALID_AGE,
                    ValidationError.InvalidField.AGE_FIELD
                )
            );
        }
        if (animal.height() < 0) {
            validationErrorSetErrors.add(
                new ValidationError(
                    ValidationError.ErrorType.INVALID_HEIGHT,
                    ValidationError.InvalidField.HEIGHT_FIELD
                )
            );
        }
        if (animal.weight() < 0) {
            validationErrorSetErrors.add(
                new ValidationError(
                    ValidationError.ErrorType.INVALID_WEIGHT,
                    ValidationError.InvalidField.WEIGHT_FIELD
                )
            );
        }
        return validationErrorSetErrors;
    }
}
