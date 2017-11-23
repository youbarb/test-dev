package sandbox;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.orsys.banque.entity.Compte;

public class testYBA {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_stand_alone");
		EntityManager em = emf.createEntityManager();

		List<Compte> lc = em.createQuery("select c from Compte c", Compte.class).getResultList();

		System.out.println("Nombre de comptes : "+lc.size());
		
		em.close();
	}
		
}