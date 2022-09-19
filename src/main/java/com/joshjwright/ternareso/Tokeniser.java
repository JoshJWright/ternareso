package com.joshjwright.ternareso;

import com.joshjwright.ternareso.model.Operation;
import com.joshjwright.ternareso.model.Ternary;

/**
 * This class processes a string and attempts to build a list of operations to perform.
 */
public final class Tokeniser {

	private Tokeniser() {}

	public static Ternary tokenise(final String inputString) {

		var index = 0;
		final char[] input = inputString.toCharArray();
		final Ternary root = new Ternary();
		var current = root;

		while (index < input.length) {

			switch (input[index]) {
				case '?' -> {
					current.setTest(true);
					final Ternary success = new Ternary();
					success.setParent(current);
					current.setSuccess(success);
					current = success;
				}
				case ':' -> {
					final Ternary failure = new Ternary();
					final Ternary parent = getParentForBranch(current, index);
					failure.setParent(parent);
					parent.setFailure(failure);
					current = failure;
				}
				case '+' -> current.getPreamble().add(Operation.INCREMENT);
				case '-' -> current.getPreamble().add(Operation.DECREMENT);
				case '<' -> current.getPreamble().add(Operation.LEFT);
				case '>' -> current.getPreamble().add(Operation.RIGHT);
				case '\\' -> current.getPreamble().add(Operation.RETURN);
				case '/' -> current.getPreamble().add(Operation.RECURSE);

				default -> throw new RuntimeException(
						String.format("Failed to recognise character %s at index %d", input[index], index));
			}
			index++;
		}
		return root;
	}

	private static Ternary getParentForBranch(final Ternary current, final int index) {
		if (current.getParent() == null) {
			throw new RuntimeException(
					String.format("':' encountered at index %d without corresponding '?'", index));
		}else if (current.getParent().isTest() && current.getParent().getFailure() == null) {
			return current.getParent();
		} else {
			return getParentForBranch(current.getParent(), index);
		}
	}

}
