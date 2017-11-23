package sandbox;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import fr.orsys.banque.entity.Compte;
import fr.orsys.banque.entity.Operation;
import fr.orsys.banque.util.HibernateUtil;

public class TestHibernate {

	public static void main(String[] args) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.getCurrentSession();
		
		Operation op = new Operation(1000f, Operation.CREDIT); // objet transient ....
		
		Transaction tx =  session.beginTransaction();
		Integer id = (Integer)session.save(op); 	// // objet persistant dans le cache  ....
		session.save(new Operation(10f, Operation.CREDIT));
		session.save(new Operation(100f, Operation.DEBIT));
		session.save(new Operation(1000, Operation.CREDIT));
		session.save(new Operation(10000f, Operation.DEBIT));
		session.save(new Operation(100000f, Operation.CREDIT));
		
		tx.commit();
		session.close();
		
		
		op = null;
		session = sf.getCurrentSession();
		session.beginTransaction();
		op = session.load(Operation.class, id);
		op.editer();
		session.getTransaction().commit();
		session.close();
		
		op.setMontant(5000f);  // modification d'un objet détaché ....
		op.editer();
		
		session = sf.getCurrentSession();
		session.beginTransaction();
		session.merge(op);
		session.getTransaction().commit();
		
		session = sf.getCurrentSession();
		session.beginTransaction();
		session.delete(op);
		session.getTransaction().commit();
		
		session = sf.getCurrentSession();
		session.beginTransaction();
		String requeteHQL = "select o from Operation o where type=:type and montant>:montant";
		Query<Operation> query = session.createQuery(requeteHQL, Operation.class);
		query.setParameter("type", Operation.CREDIT);	
		query.setParameter("montant", 0f);	
		
		for(Operation o : query.list())
			session.delete(o); 
		
		query.setParameter("type", Operation.DEBIT);
		for(Operation o : query.list())
			session.delete(o); 
		session.getTransaction().commit();
		
		Compte cpt = new Compte(2000f);
		
		session = sf.getCurrentSession();
		session.beginTransaction();
		//session.save(cpt.getLesOperations().get(0));
		session.save(cpt);
		session.getTransaction().commit();
		
		
		
		session = sf.getCurrentSession();
		session.beginTransaction();
		
		cpt = session.load(Compte.class, cpt.getNumero());
		cpt.crediter(4000f);
		cpt.crediter(200f);  // objet détaché ...
		session.getTransaction().commit();
		
		session = sf.getCurrentSession();
		session.beginTransaction();
		cpt = session.get(Compte.class, cpt.getNumero());
		session.getTransaction().commit();
		
		cpt.editerReleve();
		
		
		
		
		
		
		
		
		
		
		
		
		sf.close();

	}

}
