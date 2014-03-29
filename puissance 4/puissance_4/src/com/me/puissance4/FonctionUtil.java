package com.me.puissance4;

import org.apache.commons.validator.routines.InetAddressValidator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.FloatArray;

/**
 * The Class FonctionUtil.
 */
public class FonctionUtil {

	/**
	 * Coordonne case.
	 * 
	 * @param colonne
	 *            the colonne
	 * @param ligne
	 *            the ligne
	 * @return the float array
	 */
	public static FloatArray coordonneCase(int colonne, int ligne,
			int largeurCase, int origineX, int orgineY) {
		float coordonne[] = { largeurCase * colonne + origineX,
				largeurCase * ligne + orgineY };
		return new FloatArray(coordonne);
	}

	public static int colonne(int x, int tailleCase) {
		int idColonne = -1;

		if (x >= 0 && x < tailleCase) {
			idColonne = 0;
		} else if (x >= tailleCase && x < tailleCase * 2) {
			idColonne = 1;
		} else if (x >= tailleCase * 2 && x < tailleCase * 3) {
			idColonne = 2;
		} else if (x >= tailleCase * 3 && x < tailleCase * 4) {
			idColonne = 3;
		} else if (x >= tailleCase * 4 && x < tailleCase * 5) {
			idColonne = 4;
		} else if (x >= tailleCase * 5 && x < tailleCase * 6) {
			idColonne = 5;
		} else if (x >= tailleCase * 6 && x < tailleCase * 7) {
			idColonne = 6;
		}
		return idColonne;
	}

	public static int[] conversionEchelle(int x, int y, float echelleX,
			float echelleY, int hauteur) {
		int[] tab = new int[2];
		tab[0] = (int) (x * echelleX);
		tab[1] = (int) (hauteur - y * echelleY);
		return tab;
	}

	public static int[] conversionEchelle(int x, int y, float echelleX,
			float echelleY) {
		int[] tab = new int[2];
		tab[0] = (int) (x * echelleX);
		tab[1] = (int) (y * echelleY);
		return tab;
	}

	/**
	 * Valider ip.
	 * 
	 * @param adresse
	 *            the adresse
	 * @return true, if successful
	 */
	public static boolean validerIP(String adresse) {
		return InetAddressValidator.getInstance().isValid(adresse);
	}

	/**
	 * Verification configuration joueur.
	 * 
	 * @return true, if successful
	 */
	public static boolean verificationConfigurationJoueur() {
		FileHandle file = Gdx.files.local("Parametre.xml");
		boolean existe = file.exists();
		return existe;
	}

}
