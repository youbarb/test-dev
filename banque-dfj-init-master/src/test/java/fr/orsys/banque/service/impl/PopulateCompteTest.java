package fr.orsys.banque.service.impl;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.orsys.banque.service.Banque;
import fr.orsys.banque.service.DebitNonAutoriseException;

public class PopulateCompteTest {
	static ConfigurableApplicationContext spring = new AnnotationConfigApplicationContext(SpringConfigTest.class);
	
	@Test
	public void run() throws DebitNonAutoriseException {
	
		
		Banque banque = spring.getBean(Banque.class);
		
		banque.ouvrirCompteEpargne(10000, 0.2f);
		int idcpt = banque.ouvrirCompte(1999f);
		

		banque.crediterCompte(idcpt, 201f);
		banque.debiterCompte(idcpt, 500f);
		
		
		System.out.println("\n**** Fin la création de comptes en base  *****\n");
			
	}
}
