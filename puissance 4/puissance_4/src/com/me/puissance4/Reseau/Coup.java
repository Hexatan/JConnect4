package com.me.puissance4.Reseau;

// TODO: Auto-generated Javadoc
/**
 * The Class Coup.
 */
public class Coup {

	/** The colonne. */
	private int colonne;
	
	/** The id joueur. */
	private int idJoueur;

	/**
	 * Instantiates a new coup.
	 *
	 * @param colonne the colonne
	 * @param idJoueur the id joueur
	 */
	public Coup(int colonne, int idJoueur) {
		super();
		this.colonne = colonne;
		this.idJoueur = idJoueur;
	}

	/**
	 * Gets the colonne.
	 *
	 * @return the colonne
	 */
	public int getColonne() {
		return colonne;
	}

	/**
	 * Sets the colonne.
	 *
	 * @param colonne the new colonne
	 */
	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	/**
	 * Gets the id joueur.
	 *
	 * @return the id joueur
	 */
	public int getIdJoueur() {
		return idJoueur;
	}

	/**
	 * Sets the id joueur.
	 *
	 * @param idJoueur the new id joueur
	 */
	public void setIdJoueur(int idJoueur) {
		this.idJoueur = idJoueur;
	}
}
