package com.hexatan.puissance4.Listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.hexatan.puissance4.FonctionUtil;
import com.hexatan.puissance4.Ecran.EcranJeu;
import com.hexatan.puissance4.Modele.Jeu;
import com.hexatan.puissance4.Modele.Pion;

/**
 * The Class SourisJeuProcesseur.
 */
public class SourisJeuProcesseur implements InputProcessor {

	/** The jeu. */
	private Jeu jeu;

	private boolean pionTouche;

	private Pion pion;

	private int largeurPion;

	private float echelleX;

	private float echelleY;

	private EcranJeu ecran;

	/**
	 * Instantiates a new souris jeu processeur.
	 * 
	 * @param jeu
	 *            the jeu
	 */
	public SourisJeuProcesseur(Jeu jeu, EcranJeu ecran) {
		pionTouche = false;
		this.jeu = jeu;
		if (jeu.isJoueurActif()) {
			pion = jeu.getJoueurLocal().getPion();
		} else {
			if (!jeu.isSolo()) {
				pion = jeu.getJoueurDistant().getPion();
			}
		}
		this.largeurPion = pion.getLargeurPion();
		echelleX = Gdx.graphics.getWidth() / 800f;
		echelleY = Gdx.graphics.getHeight() / 600f;
		this.ecran = ecran;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		int hauteurEcran = Gdx.graphics.getHeight();

		int[] tab = FonctionUtil.conversionEchelle(screenX, screenY, echelleX,
				echelleY, Gdx.graphics.getHeight());

		if (jeu.joueurActif() && screenX > 56 * echelleX
				&& screenX < (56 + largeurPion) * echelleX
				&& tab[1] < hauteurEcran - 90 * echelleY
				&& tab[1] > hauteurEcran - (90 + largeurPion) * echelleY) {
			pionTouche = true;
			pion = jeu.getJoueurLocal().getPion();
		} else if (!jeu.joueurActif() && screenX > 680 * echelleX
				&& screenX < (680 + largeurPion) * echelleX
				&& tab[1] < hauteurEcran - 90 * echelleY
				&& tab[1] > hauteurEcran - (90 + largeurPion) * echelleY) {
			pionTouche = true;
			pion = jeu.getJoueurDistant().getPion();
		}
		pion.setLargeurPion(largeurPion);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		int[] tab = FonctionUtil.conversionEchelle(screenX, screenY, echelleX,
				echelleY, Gdx.graphics.getHeight());

		int largeurGrille = (largeurPion * 7);

		int origine = (ecran.getLargeurEcran() - largeurGrille) / 2;

		if (screenX > origine && screenX < largeurGrille + origine
				&& pion.getX() + largeurPion > origine
				&& pion.getX() < largeurGrille + origine) {
			if (!jeu.isGagnant()) {
				jeu.ajoutJeton(screenX - origine, largeurPion);
			}
		}

		int[] tabPion;
		int x = 0;

		if (jeu.joueurActif()) {
			pion = jeu.getJoueurLocal().getPion();
			x = 56;
		} else {
			if (!jeu.isSolo()) {
				pion = jeu.getJoueurDistant().getPion();
				x = 680;
			}
		}

		tabPion = FonctionUtil.conversionEchelle(x, 450, echelleX, echelleY);
		pion.setX(tabPion[0]);
		pion.setY(tabPion[1]);

		pionTouche = false;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		int[] tab = FonctionUtil.conversionEchelle(screenX, screenY, echelleX,
				echelleY, Gdx.graphics.getHeight());

		if (pionTouche) {
			pion.setX(screenX - largeurPion / 2);
			pion.setY(Gdx.graphics.getHeight() - screenY - largeurPion / 2);
		}
		pion.setLargeurPion(largeurPion);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
