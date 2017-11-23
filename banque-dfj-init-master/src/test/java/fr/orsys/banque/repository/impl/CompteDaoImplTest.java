package fr.orsys.banque.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import fr.orsys.banque.entity.Compte;
import fr.orsys.banque.repository.CompteDao;

public class CompteDaoImplTest {
	static ConfigurableApplicationContext spring = new AnnotationConfigApplicationContext(SpringConfigTest.class);
	CompteDao dao = spring.getBean(CompteDao.class) ;
	int nbCompte =0;
	
	@Before
	public void init() {
		dao.save(new Compte(100f));
		nbCompte = dao.loadAll().size();
	}

	@Test
	public void testSave() {
		dao.save(new Compte(100f));
		assertEquals((long)nbCompte+1, (long)dao.loadAll().size());
	}

	@Test
	public void testUpdate() {
		Compte cpt = dao.loadAll().get(0);
		cpt = dao.findCompteWithOperations(cpt.getNumero());
		float oldSolde = cpt.getSolde();
		cpt.crediter(100f);
		dao.update(cpt);
		assertEquals((double)oldSolde+100f, (double)dao.load(cpt.getNumero()).getSolde(),0);
	}

	@Test
	public void testLoad() {
		Compte cpt = dao.load(1);
		assertNotNull(cpt);
	}

	@Test
	public void testLoadAll() {
		assertTrue(dao.loadAll().size() > 0);
	}

	@Test
	public void testRemoveById() {
		dao.removeById(dao.loadAll().get(0).getNumero());
		assertTrue(dao.loadAll().size() == nbCompte-1);
	}

	@Test
	public void testRemoveAll() {
		dao.removeAll(dao.loadAll());
		assertTrue(dao.loadAll().size() == 0);
	}

	@Test
	public void testLoadCompteEpargneAll() {
		assertTrue(dao.loadCompteEpargneAll().size() == 0);
	}

	@Test
	public void testFindCompteWithOperations() {
		assertTrue(dao.findCompteWithOperations(1).getLesOperations().size() >0);
	}

}
