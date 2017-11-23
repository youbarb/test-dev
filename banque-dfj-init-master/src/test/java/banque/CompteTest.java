package banque;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import fr.orsys.banque.entity.Compte;
import fr.orsys.banque.entity.Operation;
import fr.orsys.banque.service.DebitNonAutoriseException;


public class CompteTest {

	Compte cpt = null;
	
	@Before
	public void setUp() throws Exception {
		cpt = new Compte(200f);
		assertNotNull(cpt);
	}

	@Test
	public void testCompte() {
		assertEquals(200.0f, cpt.getSolde(), 0.0f);
		assertEquals(200.0f, cpt.getDecouvertAutorise(), 0.0f);
		assertEquals(1, cpt.getLesOperations().size());
		assertEquals(Compte.getCompteurCompte(), cpt.getNumero()+1);
		assertThat(cpt.getSolde(), is(200.0f));
	}

	@Test
	public void testCrediter() {
		float soldeOld = cpt.getSolde();
		int nbOperation = cpt.getLesOperations().size();
		cpt.crediter(100);
		assertThat(cpt.getSolde(), is(soldeOld+100));
		assertThat(cpt.getLesOperations().size(), is(nbOperation+1));
		assertThat(cpt.getLesOperations().get(nbOperation).getType(), is(Operation.CREDIT));
		assertThat(cpt, equalTo(cpt));
		assertThat(cpt, sameInstance(cpt));
		assertThat(cpt, not(new Compte(10)));
		assertThat(null, nullValue());
	}

	
	@Test
	public void testDebiter2() {
		try {
			cpt.debiter(100);
			assertEquals(100.0f, cpt.getSolde(), 0.0f);
			assertEquals(200.0f, cpt.getDecouvertAutorise(), 0.0f);
			assertEquals(2, cpt.getLesOperations().size());
		}
		catch (DebitNonAutoriseException e) {
			fail("erreur : DebitNonAutoriseException généré");
		}	
	}
	
	
	@Test(expected=DebitNonAutoriseException.class)
	public void testTraiterDecouvertNonAutorise() throws DebitNonAutoriseException {
		try {
			cpt.debiter(401);
		} catch (DebitNonAutoriseException e) {
			// TODO Auto-generated catch block
			assertEquals(e.getCompte(), cpt);
			assertEquals(401.0f, e.getMontant(), 0.0f);
			assertEquals(200.0f, cpt.getSolde(), 0.0f);
			assertEquals(200.0f, cpt.getDecouvertAutorise(), 0.0f);
			assertEquals(1, cpt.getLesOperations().size());
			throw e;
		}
	}

}
