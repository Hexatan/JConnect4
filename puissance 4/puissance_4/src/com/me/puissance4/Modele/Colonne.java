package com.me.puissance4.Modele;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// TODO: Auto-generated Javadoc
/**
 * The Class Colonne.
 */
public class Colonne {

	/** The id. */
	private int id;

	/** The id libre. */
	private int idLibre;

	/** The pos jeton. */
	private int posJeton;

	/** The tab cases. */
	private Case[] tabCases = new Case[6];

	/**
	 * Instantiates a new colonne.
	 * 
	 * @param id
	 *            the id
	 */
	public Colonne(int id) {
		this.id = id;
		this.idLibre = 0;
		this.posJeton = -1;
		for (int i = 0; i < 6; i++) {
			tabCases[i] = new Case(false, null, id, i);
		}
	}

	/**
	 * Ajout jeton.
	 * 
	 * @param idJoueur
	 *            the id joueur
	 * @param couleur
	 *            the couleur
	 */
	public void ajoutJeton(int idJoueur, Color couleur) {
		if (idLibre < 6) {
			this.tabCases[idLibre].setOccupe(true);
			this.tabCases[idLibre].setCouleur(couleur);
			this.tabCases[idLibre].setIdJoueur(idJoueur);
			this.idLibre++;
			this.posJeton++;
		} else {
		}
	}

	/**
	 * Dessine.
	 * 
	 * @param batch
	 *            the batch
	 */
	public void dessine(SpriteBatch batch, int largeurCase, int origineX,
			int origineY) {
		for (int i = 0; i < 6; i++) {
			tabCases[i].dessine(batch, largeurCase, origineX, origineY);
		}
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the id libre.
	 * 
	 * @return the id libre
	 */
	public int getIdLibre() {
		return idLibre;
	}

	/**
	 * Sets the id libre.
	 * 
	 * @param idLibre
	 *            the new id libre
	 */
	public void setIdLibre(int idLibre) {
		this.idLibre = idLibre;
	}

	/**
	 * Gets the col case.
	 * 
	 * @param id
	 *            the id
	 * @return the col case
	 */
	public Case getColCase(int id) {
		return tabCases[id];
	}

	/**
	 * Sets the col case.
	 * 
	 * @param id
	 *            the id
	 * @param nouvCase
	 *            the nouv case
	 */
	public void setColCase(int id, Case nouvCase) {
		this.tabCases[id] = nouvCase;
	}

	/**
	 * Gets the pos jeton.
	 * 
	 * @return the pos jeton
	 */
	public int getPosJeton() {
		return posJeton;
	}

	/**
	 * Sets the pos jeton.
	 * 
	 * @param posJeton
	 *            the new pos jeton
	 */
	public void setPosJeton(int posJeton) {
		this.posJeton = posJeton;
	}
}
