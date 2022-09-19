package com.joshjwright.ternareso;

/**
 * This class processes a string and attempts to build a list of operations to perform.
 */
public final class Tokeniser {

	private Tokeniser() {}

	public static Ternary tokenise(final String inputString) throws TernaryParseException {

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
				case '+' -> addOperation(Operation.INCREMENT, current, index);
				case '-' -> addOperation(Operation.DECREMENT, current, index);
				case '<' -> addOperation(Operation.LEFT, current, index);
				case '>' -> addOperation(Operation.RIGHT, current, index);
				case '\\' -> {
					current.setReturnLevel(current.getReturnLevel() + 1);
				}

				default -> {} // Ignore all other characters
			}
			index++;
		}
		return root;
	}

	private static void addOperation(final Operation op, final Ternary current, final int index) throws TernaryParseException {
		if (current.getReturnLevel() != 0) {
			throw new TernaryParseException(String.format(
					"Cannot perform any operations after a return statement. Attempting to perform %s at index %d.", op,
					index));
		} else {
			current.getPreamble().add(op);
		}
	}

	private static Ternary getParentForBranch(final Ternary current, final int index) throws TernaryParseException {
		if (current.getParent() == null) {
			throw new TernaryParseException(
					String.format("':' encountered at index %d without corresponding '?'", index));
		} else if (current.getParent().isTest() && current.getParent().getFailure() == null) {
			return current.getParent();
		} else {
			return getParentForBranch(current.getParent(), index);
		}
	}

}
