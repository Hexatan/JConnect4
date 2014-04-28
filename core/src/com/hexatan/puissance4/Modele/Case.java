package com.hexatan.puissance4.Modele;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hexatan.puissance4.FonctionUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class Case.
 */
public class Case {

	/** The occupe. */
	private boolean occupe;

	/** The colonne. */
	private int colonne;

	/** The ligne. */
	private int ligne;

	/** The couleur. */
	private Color couleur;

	/** The id joueur. */
	private int idJoueur;

	/** The texture case. */
	private Texture textureCase;

	/** The sprite pion. */
	private Sprite spritePion;

	/**
	 * Instantiates a new case.
	 * 
	 * @param occupe
	 *            the occupe
	 * @param couleur
	 *            the couleur
	 * @param colonne
	 *            the colonne
	 * @param ligne
	 *            the ligne
	 */
	public Case(boolean occupe, Color couleur, int colonne, int ligne) {
		this.occupe = occupe;
		this.couleur = couleur;
		textureCase = new Texture(Gdx.files.internal("Textures/vide.png"));
		spritePion = new Sprite(new Texture(
				Gdx.files.internal("Textures/pionB.png")));
		this.ligne = ligne;
		this.colonne = colonne;
	}

	/**
	 * Checks if is occupe.
	 * 
	 * @return true, if is occupe
	 */
	public boolean isOccupe() {
		return occupe;
	}

	/**
	 * Sets the occupe.
	 * 
	 * @param occupe
	 *            the new occupe
	 */
	public void setOccupe(boolean occupe) {
		this.occupe = occupe;
	}

	/**
	 * Gets the couleur.
	 * 
	 * @return the couleur
	 */
	public Color getCouleur() {
		return couleur;
	}

	/**
	 * Sets the couleur.
	 * 
	 * @param couleur
	 *            the new couleur
	 */
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
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
	 * @param idJoueur
	 *            the new id joueur
	 */
	public void setIdJoueur(int idJoueur) {
		this.idJoueur = idJoueur;
	}

	/**
	 * Dessine.
	 * 
	 * @param batch
	 *            the batch
	 */
	public void dessine(SpriteBatch batch, int largeurCase, int origineX,
			int origineY) {

		float x = FonctionUtil.coordonneCase(colonne, ligne, largeurCase,
				origineX, origineY).get(0);
		float y = FonctionUtil.coordonneCase(colonne, ligne, largeurCase,
				origineX, origineY).get(1);

		batch.draw(textureCase, x, y, largeurCase, largeurCase, 0, 0, 64, 64,
				false, false);
		if (isOccupe()) {
			spritePion.setPosition(x, y);
			spritePion.setColor(couleur);
			spritePion.setSize(largeurCase, largeurCase);
			spritePion.draw(batch);
		}
	}
}
