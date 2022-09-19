package com.joshjwright.ternareso;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Ternary {
	private Ternary parent = null;
	private List<Operation> preamble = new ArrayList<>();
	private int returnLevel = 0;
	private boolean test = false;
	private Ternary success;
	private Ternary failure;

	public State execute(final State state) throws TernaryExecutionException {

		for (Operation op : preamble) {
			switch (op) {
				case INCREMENT, DECREMENT, LEFT, RIGHT -> {
					try {
						state.apply(op);
					} catch (TernaryStateException e) {
						throw new TernaryExecutionException(e);
					}
				}
			}
		}

		if (returnLevel > 0) {
			return returnLevels(state, returnLevel);
		}

		if (test) {
			if (state.test()) {
				return success.execute(state);
			} else {
				return failure.execute(state);
			}
		}

		return state;
	}

	private State returnLevels(final State state, final int levels) throws TernaryExecutionException {
		if (levels == 0) {
			return execute(state);
		} else if (parent != null) {
			return parent.returnLevels(state, levels - 1);
		}
		return state;
	}

	@Override
	public String toString() {
		if (test) {
			return String.format("%s \nIF ZERO: %sELSE: %s", formatPreamble(),
								 success != null ? "\n" + success.toString().indent(2) : "",
								 failure != null ? "\n" + failure.toString().indent(2) : "");
		} else {
			return formatPreamble();
		}
	}

	private String formatPreamble() {
		return String.format("EXECUTE:%s%s",
							 preamble.stream().map(Enum::toString).map(s -> "\n\t" + s).collect(Collectors.joining()),
							 returnLevel > 0 ? String.format("\n\t\tTHEN RETURN %d LEVELS", returnLevel) : "");
	}
}
