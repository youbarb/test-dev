package banque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import fr.orsys.banque.entity.CompteEpargne;

public class CompteEpargneTest {
	CompteEpargne cpt = null;

	@Before
	public void setUp() throws Exception {
		cpt = new  CompteEpargne(200, 0.2f);
		assertNotNull(cpt);
	}
	
	@Test
	public void testCalculerInterets() {
		cpt.produireInterets();
		assertEquals(240.0f, cpt.getSolde(), 0.0f);
	}

}
