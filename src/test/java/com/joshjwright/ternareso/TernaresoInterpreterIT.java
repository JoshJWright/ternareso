package com.joshjwright.ternareso;

import org.junit.jupiter.api.Test;

public class TernaresoInterpreterIT {

	@Test
	void testAdd() throws Exception {
		TernaresoInterpreter.main(new String[]{"src/test/resources/add.ter"});
	}

}
