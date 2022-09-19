package com.joshjwright.ternareso;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokeniserTest {

	@Test
	void testParsing() throws Exception {
		final String input = "+++++?-----?>>>>>:<<<<<?\\\\\\\\\\:+-<>\\:+-<>\\";

		final Ternary output = Tokeniser.tokenise(input);

		Assertions.assertEquals("""
										EXECUTE:
											INCREMENT
											INCREMENT
											INCREMENT
											INCREMENT
											INCREMENT\s
										IF ZERO:\s
										  EXECUTE:
										  	DECREMENT
										  	DECREMENT
										  	DECREMENT
										  	DECREMENT
										  	DECREMENT\s
										  IF ZERO:\s
										    EXECUTE:
										    	RIGHT
										    	RIGHT
										    	RIGHT
										    	RIGHT
										    	RIGHT
										   ELSE:\s
										    EXECUTE:
										    	LEFT
										    	LEFT
										    	LEFT
										    	LEFT
										    	LEFT\s
										    IF ZERO:\s
										      EXECUTE:
										      		THEN RETURN 5 LEVELS
										     ELSE:\s
										      EXECUTE:
										      	INCREMENT
										      	DECREMENT
										      	LEFT
										      	RIGHT
										      		THEN RETURN 1 LEVELS
										 ELSE:\s
										  EXECUTE:
										  	INCREMENT
										  	DECREMENT
										  	LEFT
										  	RIGHT
										  		THEN RETURN 1 LEVELS
										""", output.toString());
	}

}
