package fr.orsys.banque.service.impl;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.orsys.banque.service.Banque;
import fr.orsys.banque.service.DebitNonAutoriseException;

public class MyTest {
	static ConfigurableApplicationContext spring = new AnnotationConfigApplicationContext(SpringConfigTest.class);
	
	@Test
	public void run() {
	
		
		Banque banque = spring.getBean(Banque.class);
		
		int id1 = banque.ouvrirCompte(100);
		int id2 = banque.ouvrirCompteEpargne(10000, 0.2f);
		
		banque.crediterCompte(id1, 10);
		try {
			banque.debiterCompte(id1, 100);
		} catch (DebitNonAutoriseException e) {
			e.getCompte().traiterDecouvertNonAutorise(e.getMontant());
		}
		banque.crediterCompte(id2, 1000);
		
		try {
			banque.debiterCompte(id2, 21000);
		} catch (DebitNonAutoriseException e) {
			e.getCompte().traiterDecouvertNonAutorise(e.getMontant());
		}
		try {
			banque.debiterCompte(id1, 1);
		} catch (DebitNonAutoriseException e) {
			e.getCompte().traiterDecouvertNonAutorise(e.getMontant());
		}
		try {
			banque.debiterCompte(id2, 1);
		} catch (DebitNonAutoriseException e) {
			e.getCompte().traiterDecouvertNonAutorise(e.getMontant());
		}
		
		banque.editerReleves();
		banque.produireInterets();
		banque.editerReleves();
		
		System.out.println("\n**** Fin simulation de la banque *****\n");
			
	}
}
