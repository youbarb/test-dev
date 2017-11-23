package fr.orsys.banque.service.impl;
//classe Banque

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.orsys.banque.entity.Compte;
import fr.orsys.banque.entity.CompteEpargne;
import fr.orsys.banque.repository.CompteDao;
import fr.orsys.banque.service.Banque;
import fr.orsys.banque.service.DebitNonAutoriseException;

@Service
@Transactional
public class BanqueImpl implements Banque {
	
	private String nom;
	private String codeBanque;
	
	@Autowired
	CompteDao compteDao;
	
	@Autowired  // "LCL", "121212"
	public BanqueImpl(@Value("LCL") String nom, @Value("121212") String codeBanque) {
		this.nom = nom;
		this.codeBanque = codeBanque;
	}

	@Transactional(value=TxType.NOT_SUPPORTED)
	public String getNom() {
		return nom;
	}

	@Transactional(value=TxType.NOT_SUPPORTED)
	public String getCodeBanque() {
		return codeBanque;
	}
	
	public int ouvrirCompte(float depotInitial) {
		Compte compte = new Compte(depotInitial);
		compteDao.save(compte);
		return compte.getNumero();
		
	}

	@org.springframework.transaction.annotation.Transactional(readOnly=true)
	public Compte rechercherCompte(int numero) {
		return compteDao.findCompteWithOperations(numero);
	}
	
	public float fermerCompte(int numero) {
		 Compte c = compteDao.load(numero);
		 if(c!=null && c.getSolde()>=0) {
			 compteDao.removeById(numero);
			 return c.getSolde();
		 }
		 System.out.println("fermeture impossible !");
		 return 0;	 
	}
	
	@org.springframework.transaction.annotation.Transactional(readOnly=true)
	public void editerReleves() {
		for(Compte c : compteDao.loadAll()) {
			System.out.println("banque=" + nom + " code banque=" + codeBanque);
			c.editerReleve();
		}
	}
	
	public void produireInterets() {
		for(CompteEpargne c : compteDao.loadCompteEpargneAll()) {
			c.produireInterets();
		}
	}

	public int ouvrirCompteEpargne(float depotInitial, float taux) {
		Compte compte = new CompteEpargne(depotInitial, taux);
		compteDao.save(compte);
		return compte.getNumero();
	}
	
	
	@Transactional(rollbackOn=DebitNonAutoriseException.class)
	public void effectuerVirement(int compteSource, float 	
			montant, int compteDestination) throws DebitNonAutoriseException {
		
		crediterCompte(compteDestination, montant);
		debiterCompte(compteSource, montant);
	}


	@Override
	public void crediterCompte(int numero, float montant) {
		Compte cpt = compteDao.findCompteWithOperations(numero);
		cpt.crediter(montant);
		compteDao.update(cpt);
		
	}

	@Override
	public void debiterCompte(int numero, float montant)
			throws DebitNonAutoriseException {
		Compte cpt = compteDao.findCompteWithOperations(numero);
		cpt.debiter(montant);
		
	}

	@Override
	@org.springframework.transaction.annotation.Transactional(readOnly=true)
	public List<Compte> rechercherTousLesComptes() {
		return compteDao.loadAll();
	}
}
