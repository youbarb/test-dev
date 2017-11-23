package sandbox;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.orsys.banque.entity.Compte;
import fr.orsys.banque.entity.CompteEpargne;
import fr.orsys.banque.entity.Operation;

public class TestJpa {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_stand_alone");
		EntityManager em = emf.createEntityManager();
		
		Operation op = new Operation(1000f, Operation.CREDIT); // objet transient ....
		
		EntityTransaction tx =  em.getTransaction();
		tx.begin();
		em.persist(op); 	// // objet persistant dans le cache  ....
		int id = op.getId();
		em.persist(new Operation(10f, Operation.CREDIT));
		em.persist(new Operation(100f, Operation.DEBIT));
		em.persist(new Operation(1000, Operation.CREDIT));
		em.persist(new Operation(10000f, Operation.DEBIT));
		em.persist(new Operation(100000f, Operation.CREDIT));
		
		tx.commit();
		em.close();
		
		
		

		
		

		
		
		op = null;
		em = emf.createEntityManager();
		op = em.find(Operation.class, id);
		op.editer();
		em.close();
		
		op.setMontant(5000f);  // modification d'un objet détaché ....
		op.editer();
		
		em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(op);			// rattachement de op au cache de l'entityManager ....
		em.getTransaction().commit();
		em.close();
		
		em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(op));
		em.getTransaction().commit();
		
		em = emf.createEntityManager();
		em.getTransaction().begin();
		String requeteJPQL = "select o from Operation o where type=:type and montant>:montant";
		TypedQuery<Operation> query = em.createQuery(requeteJPQL, Operation.class);
		query.setParameter("type", Operation.CREDIT);	
		query.setParameter("montant", 0f);	
		
		for(Operation o : query.getResultList())
			em.remove(o); 
		
		query.setParameter("type", Operation.DEBIT);
		for(Operation o : query.getResultList())
			em.remove(o); 
		em.getTransaction().commit();
		em.close();
		
		Compte cpt = new Compte(2000f);
		CompteEpargne ce = new CompteEpargne(100f, 0.5f);
		
		em = emf.createEntityManager();
		em.getTransaction().begin();
		//session.save(cpt.getLesOperations().get(0));
		em.persist(cpt);
		em.persist(ce);
		em.getTransaction().commit();
		
		
		
		em = emf.createEntityManager();
		em.getTransaction().begin();
		
		cpt = em.find(Compte.class, cpt.getNumero());
		cpt.crediter(4000f);
		cpt.crediter(200f);  // objet détaché ...
		em.getTransaction().commit();
		
		em = emf.createEntityManager();
		String findCompteWithOperations = "select c from Compte c join fetch c.lesOperations where c.numero=:numero";
		TypedQuery<Compte> q = em.createQuery(findCompteWithOperations, Compte.class).setParameter("numero", cpt.getNumero());
		cpt = q.getSingleResult();
		em.close();
		
		cpt.editerReleve();
		
		
		emf.close();

	}

}
