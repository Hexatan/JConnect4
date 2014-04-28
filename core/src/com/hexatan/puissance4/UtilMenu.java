package com.hexatan.puissance4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

// TODO: Auto-generated Javadoc
/**
 * The Class UtilMenu.
 * 
 * @author gaetan
 */
public class UtilMenu {

	/**
	 * Make button.
	 * 
	 * @param str
	 *            Texte du bouton
	 * @param style
	 *            TextButtonStyle a ajout� au bouton
	 * @param y
	 *            position en y du bouton
	 * @param il
	 *            inputLIstener a appliquer au bouton
	 * @return Retourne le bouton cr�er
	 */
	public static Actor makeButton(String str, TextButtonStyle style, float y,
			InputListener il) {
		TextButton button = new TextButton(str, style);
		button.setWidth(400);
		button.setHeight(100);
		button.setX(Gdx.graphics.getWidth() / 2 - button.getWidth() / 2);
		button.setY(y);
		button.addListener(il);
		return button;
	}

	/**
	 * Make button.
	 * 
	 * @param str
	 *            Texte du bouton
	 * @param style
	 *            Style a ajout� au bouton
	 * @param x
	 *            position en x du bouton
	 * @param y
	 *            position en y du bouton
	 * @param width
	 *            largeur du bouton
	 * @param height
	 *            hauteur du bouton
	 * @param il
	 *            inputLIstener a appliquer au bouton
	 * @return the actor
	 */
	public static Actor makeButton(String str, TextButtonStyle style, float x,
			float y, float width, float height, InputListener il) {
		TextButton button = new TextButton(str, style);
		button.setWidth(width);
		button.setHeight(height);
		button.setX(x);
		button.setY(y);
		button.addListener(il);
		return button;
	}

	/**
	 * Make style.
	 * 
	 * @param style
	 *            TextButtonStyle � modifier
	 * @param skin
	 *            Skin dans lequel r�cuperer les images
	 * @param font
	 *            BitmapFont � ajouter dans le style
	 * @param img
	 *            String repr�sentant l'image du bouton dans l'atlas
	 * @param imgPress
	 *            String repr�sentant l'image du bouton press� dans l'atlas
	 */
	public static void makeStyle(TextButtonStyle style, Skin skin,
			BitmapFont font, String img, String imgPress) {
		style.up = skin.getDrawable(img);
		style.down = skin.getDrawable(imgPress);
		style.font = font;
	}

	/**
	 * Make style.
	 * 
	 * @param style
	 *            TextFieldStyle � modifier
	 * @param skin
	 *            Skin dans lequel r�cuperer les images
	 * @param font
	 *            BitmapFont � ajouter dans le style
	 * @param img
	 *            String repr�sentant l'image du bouton dans l'atlas
	 */
	public static void makeStyle(TextFieldStyle style, Skin skin,
			BitmapFont font, String img) {
		style.background = skin.getDrawable(img);
		style.font = font;
	}

	/**
	 * Make style.
	 * 
	 * @param style
	 *            style TextFieldStyle � modifier
	 * @param font
	 *            BitmapFont � ajouter dans le style
	 */
	public static void makeStyle(TextFieldStyle style, BitmapFont font) {
		style.font = font;
	}
}