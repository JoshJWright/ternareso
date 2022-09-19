package com.joshjwright.ternareso;

import lombok.Getter;

import java.util.Arrays;

/**
 * This class holds and manages the memory state of the program.
 */
public class State {
	private int tapeLength = 1;
	private int[] tape;
	private int address;

	public State(final int initialTapeLength) {
		this.tapeLength = initialTapeLength;
		this.tape = new int[tapeLength];
		this.address = 0;
	}

	public State() {
		this(1);
	}

	public void apply(final Operation operation) throws TernaryStateException {
		switch(operation) {
			case INCREMENT -> tape[address]++;
			case DECREMENT -> tape[address]--;
			case LEFT -> {
				if (address == 0) {
					throw new TernaryStateException("Attempted to move to a negative address.");
				} else {
					address--;
				}
			}
			case RIGHT -> {
				address++;
				if (address == tapeLength) {
					tapeLength *= 2;
					tape = Arrays.copyOf(tape, tapeLength);
				}
			}
			case RETURN -> {}
		}
	}

	public boolean test() {
		return tape[address] == 0;
	}

	public int[] getTape() {
		return tape.clone();
	}

}
