package fr.orsys.banque.service;

import fr.orsys.banque.entity.Compte;

public class DebitNonAutoriseException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	float montant;
	Compte compte;
	
	public DebitNonAutoriseException(float montant, Compte compte) {
		this.montant = montant;
		this.compte = compte;
	}

	public float getMontant() {
		return montant;
	}

	public Compte getCompte() {
		return compte;	
	}
}
