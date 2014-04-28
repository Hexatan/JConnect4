package com.hexatan.puissance4.Modele;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// TODO: Auto-generated Javadoc
/**
 * The Class Grille.
 */
public class Grille {

	/** The tab colonnes. */
	private Colonne[] tabColonnes = new Colonne[7];

	/**
	 * Instantiates a new grille.
	 */
	public Grille() {
		for (int i = 0; i < 7; i++) {
			tabColonnes[i] = new Colonne(i);
		}
	}

	/**
	 * Ajout jeton.
	 * 
	 * @param idColonne
	 *            the id colonne
	 * @param idJoueur
	 *            the id joueur
	 * @param couleur
	 *            the couleur
	 */
	public void ajoutJeton(int idColonne, int idJoueur, Color couleur) {
		tabColonnes[idColonne].ajoutJeton(idJoueur, couleur);
	}

	/**
	 * Dessine.
	 * 
	 * @param batch
	 *            the batch
	 */
	public void dessine(SpriteBatch batch, int largeurCase, int origineX,
			int origineY) {
		for (int i = 0; i < 7; i++) {
			tabColonnes[i].dessine(batch, largeurCase, origineX, origineY);
		}
	}

	/**
	 * Gets the tab colonnes.
	 * 
	 * @return the tab colonnes
	 */
	public Colonne[] getTabColonnes() {
		return tabColonnes;
	}

	/**
	 * Sets the tab colonnes.
	 * 
	 * @param tabColonnes
	 *            the new tab colonnes
	 */
	public void setTabColonnes(Colonne[] tabColonnes) {
		this.tabColonnes = tabColonnes;
	}

}
