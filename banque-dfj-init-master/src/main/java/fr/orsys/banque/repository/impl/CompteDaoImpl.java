package fr.orsys.banque.repository.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import fr.orsys.banque.entity.Compte;
import fr.orsys.banque.entity.CompteEpargne;
import fr.orsys.banque.repository.CompteDao;


@Repository
@Transactional
public class CompteDaoImpl implements CompteDao {

//	@Autowired
//	EntityManagerFactory emf; //  Persistence.createEntityManagerFactory("jpa_stand_alone");
	@PersistenceContext
	EntityManager em;

	@Override
	public void save(Compte cpt) {
		//session.save(cpt.getLesOperations().get(0));
		em.persist(cpt);

	}

	@Override
	public void update(Compte cpt) {
		em.merge(cpt);
	}

	@Override
	public Compte load(Integer primaryKey) {
		Compte cpt =  em.find(Compte.class, primaryKey);
		return cpt;

	}

	@Override
	public List<Compte> loadAll() {
		List<Compte> lc = em.createQuery("select c from Compte c", Compte.class).getResultList();
		return lc;
		}

	@Override
	public void remove(Compte entity) {
		this.removeById(entity.getNumero());

	}

	@Override
	public void removeById(Integer id) {
		em.remove(em.find(Compte.class,id));
	}

	@Override
	public void removeAll(Collection<Compte> entities) {
		for(Compte c : entities)
			remove(c);

	}

	@Override
	public List<CompteEpargne> loadCompteEpargneAll() {
		List<CompteEpargne> lc =  em.createQuery("select c from CompteEpargne c", CompteEpargne.class).getResultList();
		return lc;
	}
	
	@Override
	public Compte findCompteWithOperations(Integer primaryKey) {
		String findCompteWithOperations = "select c from Compte c join fetch c.lesOperations where c.numero=:numero";
		TypedQuery<Compte> q = em.createQuery(findCompteWithOperations, Compte.class).setParameter("numero", primaryKey);
		Compte c =  q.getSingleResult();
		return c;
	}
}
