package com.joshjwright.ternareso;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class TernaresoInterpreterIT {

	@ParameterizedTest
	@ValueSource(strings = {
			"hello-world",
			"add",
			"subtract",
			"equality",
			"inequality"
	})
	void examples(final String testCaseName) throws Exception {
		System.out.printf("%n\tTest case: %s%n", testCaseName);

		// GIVEN an input program
		final String input = getTestData(testCaseName, "input");
		System.out.printf("Input: %s%n", input);

		// AND the tokenised program
		final Ternary program = Tokeniser.tokenise(input);

		// WHEN executing the program
		final State state = program.execute(new State());
		System.out.printf("Output: %s%n%n", Arrays.toString(state.getTape()));

		// THEN the state is as expected
		Assertions.assertEquals(getTestData(testCaseName, "output"), state.toString());
	}

	private String getTestData(final String testCaseName, final String dataType) throws IOException {
		return Files.readString(Path.of(String.format("src/test/resources/%s/%s.txt", dataType, testCaseName)));
	}

}
