package fr.orsys.banque.repository;

import java.util.List;

import javax.transaction.Transactional;

import fr.orsys.banque.entity.Compte;
import fr.orsys.banque.entity.CompteEpargne;

@Transactional
public interface CompteDao extends Dao<Integer, Compte> {
	public List<CompteEpargne> loadCompteEpargneAll();
	public Compte findCompteWithOperations(Integer primaryKey);

}
