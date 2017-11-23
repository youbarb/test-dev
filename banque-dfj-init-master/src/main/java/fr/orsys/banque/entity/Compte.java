package fr.orsys.banque.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.log4j.Logger;

import fr.orsys.banque.service.DebitNonAutoriseException;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Compte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Compte.class);

	@Id
	int numero;
	float solde;
	float decouvertAutorise;

	private static int compteurCompte = 1;

	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	@JoinColumn(name="COMPTE_ID")
	private List<Operation> lesOperations = new ArrayList<Operation>();

	public Compte(float depotInitial) {
		crediter(depotInitial);
		decouvertAutorise = depotInitial;
		numero = compteurCompte;
		compteurCompte++;
	}
	public Compte() {
		// TODO Auto-generated constructor stub
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	public void setSolde(float solde) {
		this.solde = solde;
	}
	public void setDecouvertAutorise(float decouvertAutorise) {
		this.decouvertAutorise = decouvertAutorise;
	}
	public void setLesOperations(List<Operation> lesOperations) {
		this.lesOperations = lesOperations;
	}
	public int getNumero() {
		return numero;
	}

	public float getSolde() {
		return solde;
	}

	public float getDecouvertAutorise() {
		return decouvertAutorise;
	}

	public void crediter(float montant) {
		if (logger.isInfoEnabled()) {
			logger.info("crediter(float)"); //$NON-NLS-1$
		}

		solde += montant;
		lesOperations.add(new Operation(montant, Operation.CREDIT));
	}

	public void debiter(float montant) throws DebitNonAutoriseException {
		if (logger.isInfoEnabled()) {
			logger.info("debiter(float)"); //$NON-NLS-1$
		}
		if ((solde + decouvertAutorise) >= montant) {
			solde -= montant;
			lesOperations.add(new Operation(montant, Operation.DEBIT));
			return;
		}
		throw new DebitNonAutoriseException(montant, this);
	}
	
	@Override
	public String toString() {
		return "No=" + numero + " solde=" + solde + " decouvert autorisé=" + decouvertAutorise;
	}
	
	public void editerReleve() {
		System.out.println("************************************");
		System.out.println( this);
		for(int i = 0; i < lesOperations.size(); i++) {
			lesOperations.get(i).editer();
		}
	}

	public List<Operation> getLesOperations() {
		// TODO Auto-generated method stub
		return lesOperations;
	}

	public static int getCompteurCompte() {
		return compteurCompte;
	}

	@SuppressWarnings("resource")
	public void traiterDecouvertNonAutorise(float montant) {
		if (logger.isInfoEnabled()) {
			logger.info("traiterDecouvertNonAutorise(float)"); //$NON-NLS-1$
		}
		System.out.println("**** traitement d'un débit non autorisé !! ******");
		editerReleve();
		System.out.println("Montant demandé :" + montant);
		System.out.println("accepter le débit ? " );
		String reponse = "oui";
		if(new Scanner(reponse).next().equals("oui")) {
			debitForce(montant);
		}
		
	}

	private void debitForce(float montant) {
		// TODO Auto-generated method stub
	}
	
}
