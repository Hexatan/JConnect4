package com.me.puissance4.Modele;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The Class Pion.
 */
public class Pion {

	/** The sprite pion. */
	private Sprite spritePion;

	/** The x. */
	private float x;

	/** The y. */
	private float y;

	/** The couleur. */
	private Color couleur;

	private int largeurPion;

	/**
	 * Instantiates a new pion.
	 * 
	 * @param sprite
	 *            the sprite
	 * @param couleur
	 *            the couleur
	 */
	public Pion(Sprite sprite, Color couleur, float x, float y, int taille) {
		this.spritePion = sprite;
		this.x = x;
		this.y = y;
		this.couleur = couleur;
		largeurPion = taille;
	}

	/**
	 * Update.
	 */
	public void update() {

	}

	/**
	 * Dessine.
	 * 
	 * @param batch
	 *            the batch
	 */
	public void dessine(SpriteBatch batch, float taille) {
		spritePion.setPosition(x, y);
		spritePion.setSize(taille, taille);
		spritePion.setColor(couleur);
		spritePion.draw(batch);
	}

	/**
	 * Gets the x.
	 * 
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the x.
	 * 
	 * @param x
	 *            the new x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Gets the y.
	 * 
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the y.
	 * 
	 * @param y
	 *            the new y
	 */
	public void setY(float y) {
		this.y = y;
	}

	public int getLargeurPion() {
		return largeurPion;
	}

	public void setLargeurPion(int largeurPion) {
		this.largeurPion = largeurPion;
	}
}
