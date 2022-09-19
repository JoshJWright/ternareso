package com.joshjwright.ternareso.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Ternary {
	private Ternary parent = null;
	private List<Operation> preamble = new ArrayList<>();
	private boolean test = false;
	private Ternary success;
	private Ternary failure;

	@Override
	public String toString() {
		if (test) {
			return String.format("%s \nIF ZERO: %s ELSE: %s", formatPreamble(),
								 success != null ? "\n" + success.toString().indent(2) : "",
								 failure != null ? "\n" + failure.toString().indent(2) : "");
		} else {
			return formatPreamble();
		}
	}

	private String formatPreamble() {
		return String.format("EXECUTE:%s",
							 preamble.stream().map(Enum::toString).map(s -> "\n\t" + s).collect(Collectors.joining()));
	}
}
