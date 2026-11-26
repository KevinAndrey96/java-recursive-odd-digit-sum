package com.kevinandrey.odddigits.cli;

import com.kevinandrey.odddigits.Figure;
import com.kevinandrey.odddigits.OddDigitSum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Command line entry point.
 *
 * <pre>
 * java -jar odd-digit-sum.jar 5 16796
 * java -jar odd-digit-sum.jar          (asks for N and C)
 * </pre>
 */
public final class OddDigitSumCli {

    static final int OK = 0;
    static final int INVALID_INPUT = 2;

    private static final String USAGE = "Uso: java -jar odd-digit-sum.jar <N> <C>";
    private static final int MAX_DIGITS_IN_BREAKDOWN = 40;

    private final BufferedReader in;
    private final PrintStream out;
    private final PrintStream err;

    OddDigitSumCli(InputStream in, PrintStream out, PrintStream err) {
        this.in = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        this.out = out;
        this.err = err;
    }

    public static void main(String[] args) {
        System.exit(new OddDigitSumCli(System.in, System.out, System.err).run(args));
    }

    int run(String[] args) {
        if (args.length == 1 && ("-h".equals(args[0]) || "--help".equals(args[0]))) {
            out.println(USAGE);
            return OK;
        }
        if (args.length != 0 && args.length != 2) {
            err.println(USAGE);
            return INVALID_INPUT;
        }
        try {
            int declaredDigits = parseDeclaredDigits(args.length == 2 ? args[0] : ask("Cantidad de dígitos N (mínimo 4): "));
            Figure figure = Figure.of(declaredDigits, args.length == 2 ? args[1] : ask("Cifra C de " + declaredDigits + " dígitos: "));
            print(figure);
            return OK;
        } catch (IllegalArgumentException e) {
            err.println("Error: " + e.getMessage());
            return INVALID_INPUT;
        }
    }

    static int parseDeclaredDigits(String text) {
        String value = text == null ? "" : text.strip();
        if (!value.matches("\\d{1,9}")) {
            throw new IllegalArgumentException("N debe ser un número entero mayor o igual a " + Figure.MINIMUM_DIGITS + ".");
        }
        return Integer.parseInt(value);
    }

    private String ask(String prompt) {
        out.print(prompt);
        out.flush();
        try {
            String line = in.readLine();
            if (line == null) {
                throw new IllegalArgumentException("No se recibió ningún valor.");
            }
            return line;
        } catch (IOException e) {
            throw new IllegalArgumentException("No se pudo leer la entrada.", e);
        }
    }

    private void print(Figure figure) {
        out.println("Cifra           : " + abbreviate(figure.digits()) + " (" + figure.length() + " dígitos)");
        out.println("Dígitos impares : " + breakdown(OddDigitSum.oddDigits(figure)));
        out.println("Suma            : " + OddDigitSum.of(figure));
    }

    static String breakdown(List<Integer> oddDigits) {
        if (oddDigits.isEmpty()) {
            return "ninguno";
        }
        if (oddDigits.size() > MAX_DIGITS_IN_BREAKDOWN) {
            return oddDigits.size() + " dígitos impares";
        }
        return oddDigits.stream().map(String::valueOf).collect(Collectors.joining(" + "));
    }

    private static String abbreviate(String digits) {
        return digits.length() <= MAX_DIGITS_IN_BREAKDOWN ? digits : digits.substring(0, MAX_DIGITS_IN_BREAKDOWN) + "...";
    }
}
