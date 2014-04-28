package com.hexatan.puissance4.Reseau;

import com.badlogic.gdx.graphics.Color;

// TODO: Auto-generated Javadoc
/**
 * The Class Protocole.
 */
public class Protocole extends XML {

	/** The message infos joueur. */
	public static String MESSAGE_INFOS_JOUEUR = "<InfosJoueur><Description><Id>[ID]</Id><Nom>[NOM]</Nom><Couleur>[COULEUR]</Couleur><PartiesJouees>[PARTIEJOUEES]</PartiesJouees><PartiesGagnees>[PARTIEGAGNEES]</PartiesGagnees></Description></InfosJoueur>";

	/** The message coup. */
	public static String MESSAGE_COUP = "<ListePions><Pion><Colonne>[COLONNE]</Colonne><Joueur>[JOUEUR]</Joueur></Pion></ListePions>";

	/** The message fin partie. */
	public static String MESSAGE_FIN_PARTIE = "<EtatPartie><Partie><Victoire>[GAGNER]</Victoire></partie></EtatPartie>";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hexatan.puissance4.Reseau.XML#interpreter()
	 */
	@Override
	public Object interpreter() {
		return null;
	}

	/**
	 * Preparer message initialisation joueur.
	 * 
	 * @param id
	 *            the id
	 * @param nom
	 *            the nom
	 * @param couleur
	 *            the couleur
	 * @param partiejouees
	 *            the partiejouees
	 * @param partiegagnees
	 *            the partiegagnees
	 * @return the string
	 */
	public static String preparerMessageInitialisationJoueur(int id,
			String nom, Color couleur, int partiejouees, int partiegagnees) {
		return Protocole.MESSAGE_INFOS_JOUEUR
				.replace("[ID]", String.valueOf(id)).replace("[NOM]", nom)
				.replace("[COULEUR]", couleur.toString())
				.replace("[PARTIEJOUEES]", String.valueOf(partiejouees))
				.replace("[PARTIEGAGNEES]", String.valueOf(partiegagnees));
	}

	/**
	 * Tester message initialisation joueur.
	 * 
	 * @return true, if successful
	 */
	public boolean testerMessageInitialisationJoueur() {
		return this.getNomRacine() == "InfosJoueur";
	}

	/**
	 * Preparer message coup.
	 * 
	 * @param coup
	 *            the coup
	 * @return the string
	 */
	public static String preparerMessageCoup(Coup coup) {
		return Protocole.MESSAGE_COUP.replace("[COLONNE]",
				String.valueOf(coup.getColonne())).replace("[JOUEUR]",
				String.valueOf(coup.getIdJoueur()));
	}

	/**
	 * Tester message coup.
	 * 
	 * @return true, if successful
	 */
	public boolean testerMessageCoup() {
		return this.getNomRacine() == "ListePions";
	}

	/**
	 * Preparer message fin.
	 * 
	 * @param fin
	 *            the fin
	 * @return the string
	 */
	public static String preparerMessageFin(boolean fin) {
		return Protocole.MESSAGE_COUP.replace("[GAGNER]", String.valueOf(fin));
	}

	/**
	 * Tester message fin.
	 * 
	 * @return true, if successful
	 */
	public boolean testerMessageFin() {
		return this.getNomRacine() == "EtatPartie";
	}
}
