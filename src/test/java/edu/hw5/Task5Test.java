package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task5Test {
    private static Stream<Arguments> validLicensePlates() {
        return Stream.of(
            Arguments.of("А123ВЕ777"),
            Arguments.of("О777ОО177"),
            Arguments.of("А001АА77"),
            Arguments.of("В002ВВ59"),
            Arguments.of("Е003ЕЕ34"),
            Arguments.of("Р004РР152"),
            Arguments.of("Т005ТТ073"),
            Arguments.of("О006ОО022"),
            Arguments.of("У007УУ091"),
            Arguments.of("К008КК068"),
            Arguments.of("М009ММ042"),
            Arguments.of("Х010ХХ012"),
            Arguments.of("С011СС085"),
            Arguments.of("Н012НН007"),
            Arguments.of("Л013ЛЛ031"),
            Arguments.of("З014ЗЗ064"),
            Arguments.of("И015ИИ055"),
            Arguments.of("Г016ГГ043"),
            Arguments.of("В017ВВ020"),
            Arguments.of("У018УУ088"),
            Arguments.of("О019ОО077"),
            Arguments.of("А020АА046"),
            Arguments.of("Н021НН002"),
            Arguments.of("Т022ТТ079"),
            Arguments.of("Е023ЕЕ051"),
            Arguments.of("Х024ХХ065"),
            Arguments.of("К025КК013"),
            Arguments.of("М026ММ033"),
            Arguments.of("С027СС049"),
            Arguments.of("О028ОО027"),
            Arguments.of("З029ЗЗ006"),
            Arguments.of("Г030ГГ038"),
            Arguments.of("Р031РР057"),
            Arguments.of("В032ВВ092"),
            Arguments.of("И033ИИ014"),
            Arguments.of("Л034ЛЛ031"),
            Arguments.of("Н035НН02"),
            Arguments.of("А036АА075"),
            Arguments.of("Е037ЕЕ061"),
            Arguments.of("Т038ТТ009"),
            Arguments.of("У039УУ045"),
            Arguments.of("К040КК80"),
            Arguments.of("М041ММ01"),
            Arguments.of("С042СС053"),
            Arguments.of("З043ЗЗ40"),
            Arguments.of("Г044ГГ172"),
            Arguments.of("О045ОО03"),
            Arguments.of("Х046ХХ096"),
            Arguments.of("В047ВВ04"),
            Arguments.of("Н048НН016"),
            Arguments.of("И049ИИ89"),
            Arguments.of("Л050ЛЛ017")
        );
    }

    private static Stream<Arguments> invalidLicensePlates() {
        return Stream.of(
            Arguments.of("А13ВЕ777"),
            Arguments.of("О777ОOО177"),
            Arguments.of("AА001АА77"),
            Arguments.of("ВBB002ВВ59"),
            Arguments.of("Е003ЕЕ"),
            Arguments.of("Р04Р152"),
            Arguments.of("05ТТ073"),
            Arguments.of("О6ОО022"),
            Arguments.of("О6ОО2"),
            Arguments.of("123АВЕ777"),
            Arguments.of("А123ВГ7"),
            Arguments.of("А123ВЕ7777")
        );
    }

    @ParameterizedTest
    @MethodSource("validLicensePlates")
    @DisplayName("Test that the method validates Russian plates correctly and returned true for really valid plates")
    void testThatTheMethodValidatesRussianPlatesCorrectlyAndReturnedTrueForReallyValidNumbers(String plate) {
        var isValid = Task5.isValidLicensePlate(plate);

        assertThat(isValid).isTrue();
    }

    @ParameterizedTest
    @MethodSource("invalidLicensePlates")
    @DisplayName("Test that the method validates Russian plates correctly and returned false for really invalid plates")
    void testThatTheMethodValidatesRussianPlatesCorrectlyAndReturnedFalseForReallyInvalidPlates(String plate) {
        var isValid = Task5.isValidLicensePlate(plate);

        assertThat(isValid).isFalse();
    }

}
