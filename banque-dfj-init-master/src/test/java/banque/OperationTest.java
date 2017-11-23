package banque;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.orsys.banque.entity.Operation;

public class OperationTest {

	@Test
	public void testOperation() {
		Operation op = new Operation(100f, Operation.CREDIT);
		assertEquals(100f, op.getMontant(), 0.0f);
	}

}
