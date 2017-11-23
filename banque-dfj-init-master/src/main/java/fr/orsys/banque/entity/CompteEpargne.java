package fr.orsys.banque.entity;

import javax.persistence.Entity;

@Entity
public class CompteEpargne extends Compte {
	private float taux;
	private float cumulInterets;
	
	public CompteEpargne(float depotInitial, float taux) {
		super(depotInitial);
		this.taux = taux;
		cumulInterets =0;
		decouvertAutorise = 0;
	}
	public CompteEpargne() {}
	
	public void produireInterets() {
		float benefices = getSolde() * taux;
		cumulInterets += benefices;
		crediter(benefices);	
	}
	
	public String toString() {
		return super.toString()+ " taux= " + taux + "cumul des intérets" + cumulInterets; 	
	}

	public float getTaux() {
		// TODO Auto-generated method stub
		return taux;
	}

	public float getCumulInterets() {
		// TODO Auto-generated method stub
		return cumulInterets;
	}	
}
