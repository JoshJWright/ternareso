package com.joshjwright.ternareso;

import com.joshjwright.ternareso.model.Ternary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokeniserTest {

	@Test
	void testParsing() {
		final String input = "+++++?-----?>>>>>:<<<<<?/////:\\\\\\\\\\:+-<>/\\";

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
										      	RECURSE
										      	RECURSE
										      	RECURSE
										      	RECURSE
										      	RECURSE
										     ELSE:\s
										      EXECUTE:
										      	RETURN
										      	RETURN
										      	RETURN
										      	RETURN
										      	RETURN
										 ELSE:\s
										  EXECUTE:
										  	INCREMENT
										  	DECREMENT
										  	LEFT
										  	RIGHT
										  	RECURSE
										  	RETURN
										""", output.toString());
	}

}
