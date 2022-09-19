package com.joshjwright.ternareso;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Entry class that takes a file path as an argument, and attempts to load and execute the program.
 */
public class TernaresoInterpreter {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Ternareso!");

        assert args.length >= 1;
        assert Files.exists(Path.of(args[0]));
        final String input = Files.readString(Path.of(args[0]));

    }
}