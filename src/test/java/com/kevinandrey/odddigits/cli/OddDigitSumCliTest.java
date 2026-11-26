package com.kevinandrey.odddigits.cli;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OddDigitSumCliTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    private int run(String input, String... args) {
        OddDigitSumCli cli = new OddDigitSumCli(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)),
                new PrintStream(out, true, StandardCharsets.UTF_8), new PrintStream(err, true, StandardCharsets.UTF_8));
        return cli.run(args);
    }

    private String stdout() {
        return out.toString(StandardCharsets.UTF_8);
    }

    private String stderr() {
        return err.toString(StandardCharsets.UTF_8);
    }

    @Test
    void solvesTheExampleOfTheStatement() {
        assertEquals(OddDigitSumCli.OK, run("", "5", "16796"));

        assertTrue(stdout().contains("Cifra           : 16796 (5 dígitos)"), stdout());
        assertTrue(stdout().contains("Dígitos impares : 1 + 7 + 9"), stdout());
        assertTrue(stdout().contains("Suma            : 17"), stdout());
    }

    @Test
    void asksForNAndCWhenNoArgumentsAreGiven() {
        assertEquals(OddDigitSumCli.OK, run("4\n2468\n"));

        assertTrue(stdout().contains("Cifra C de 4 dígitos: "));
        assertTrue(stdout().contains("Dígitos impares : ninguno"), stdout());
        assertTrue(stdout().contains("Suma            : 0"), stdout());
    }

    @Test
    void reportsInvalidInputWithExitCodeTwo() {
        assertEquals(OddDigitSumCli.INVALID_INPUT, run("", "cuatro", "5674"));
        assertTrue(stderr().contains("N debe ser un número entero"), stderr());

        assertEquals(OddDigitSumCli.INVALID_INPUT, run("", "5", "5674"));
        assertTrue(stderr().contains("se esperaban 5"), stderr());

        assertEquals(OddDigitSumCli.INVALID_INPUT, run("", "5"));
        assertTrue(stderr().contains("Uso:"));

        assertEquals(OddDigitSumCli.INVALID_INPUT, run("4\n"));
        assertTrue(stderr().contains("No se recibió ningún valor."));
    }

    @Test
    void showsHelp() {
        assertEquals(OddDigitSumCli.OK, run("", "--help"));
        assertTrue(stdout().startsWith("Uso:"));
    }

    @Test
    void summarizesLongBreakdowns() {
        assertEquals("41 dígitos impares", OddDigitSumCli.breakdown(Collections.nCopies(41, 1)));
        assertEquals("3 + 5", OddDigitSumCli.breakdown(List.of(3, 5)));
    }
}
