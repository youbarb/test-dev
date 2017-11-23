package fr.orsys.banque.service;
import java.util.List;


//classe Banque
import fr.orsys.banque.entity.Compte;

public interface Banque {
	public int ouvrirCompte(float depotInitial);
	public Compte rechercherCompte(int numero);
	public List<Compte> rechercherTousLesComptes();
	public void crediterCompte(int numero, float montant);
	public void debiterCompte(int numero, float montant) throws DebitNonAutoriseException;
	public float fermerCompte(int numero);
	public void editerReleves(); 
	public void produireInterets();
	public int ouvrirCompteEpargne(float depotInitial, float taux);
	public void effectuerVirement(int compteSource, float montant, int compteDestination) throws DebitNonAutoriseException;
}
