package com.me.puissance4.Reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.me.puissance4.Puissance4;
import com.me.puissance4.Modele.Jeu;
import com.me.puissance4.Modele.Joueur;

/**
 * The Class ClientPartage.
 */
public class ClientPartage {

	/** The connexion. */
	private Socket connexion = null;

	/** The imprimante. */
	private PrintWriter imprimante = null;

	/** The lecteur. */
	private BufferedReader lecteur = null;

	/** The joueur. */
	private Joueur joueur = null;

	/** The jeu. */
	private Jeu jeu;

	/** The decodeur message. */
	private Protocole decodeurMessage;

	/** The client. */
	static ClientPartage client = null;

	private boolean initialise = false;

	/**
	 * Instantiates a new client partage.
	 */
	public ClientPartage(String host, Jeu jeu) {
		this.decodeurMessage = new Protocole();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log(Puissance4.LOG, "Connection du client");
		joueur = new Joueur();
		this.jeu = jeu;
		try {
			this.connexion = new Socket(InetAddress.getByName(host),
					CoordonneesReseau.PORT_CLIENT_VERS_SERVEUR);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.imprimante = new PrintWriter(connexion.getOutputStream(), true);
			this.lecteur = new BufferedReader(new InputStreamReader(
					connexion.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the single instance of ClientPartage.
	 * 
	 * @return single instance of ClientPartage
	 */
	static public ClientPartage getInstance(String host, Jeu jeu) {
		if (null == client)
			client = new ClientPartage(host, jeu);
		return client;
	}

	/**
	 * Envoyer message.
	 * 
	 * @param message
	 *            the message
	 * @return true, if successful
	 */
	public boolean envoyerMessage(String message) {
		imprimante.println(message);
		imprimante.flush();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log(Puissance4.LOG, "Envoi du message " + message);
		return true;
	}

	/**
	 * Recevoir messages.
	 */
	public void recevoirMessages() {
		if (lecteur == null)
			return;
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log(Puissance4.LOG, "Recevoir messages");
		String message;
		try {
			while ((message = lecteur.readLine()) != null) {
				Gdx.app.log(Puissance4.LOG, "Reception du message " + message);
				decodeurMessage.charger(message);
				Gdx.app.log(Puissance4.LOG, String.valueOf(this.decodeurMessage
						.testerMessageInitialisationJoueur()));
				if (this.decodeurMessage.testerMessageInitialisationJoueur()
						&& !initialise) {
					ArrayList<String> infosJoueur = Joueur
							.XMLParserJoueur(message);
					joueur.setId(Integer.parseInt(infosJoueur.get(0)));
					joueur.setNom(infosJoueur.get(1));
					joueur.setCouleur(Color.valueOf(infosJoueur.get(2)));
					joueur.setPartiesJouees(Integer.parseInt(infosJoueur.get(3)));
					joueur.setPartiesGagnees(Integer.parseInt(infosJoueur
							.get(4)));
					jeu.ajoutJoueur(joueur);
					jeu.setJoueurDistant(jeu.getTabJoueurs().get(0));
					jeu.setJoueurLocal(joueur);
					jeu.setJoueurActif(false);
					ArrayList<String> infos = Joueur.lireParametreJoueur();
					envoyerMessage(Protocole
							.preparerMessageInitialisationJoueur(
									Integer.parseInt(infos.get(0)),
									infos.get(1), Color.valueOf(infos.get(2)),
									Integer.parseInt(infos.get(3)),
									Integer.parseInt(infos.get(4))));
					initialise = true;
				} else if (this.decodeurMessage.getNomRacine() == "ListePions") {
					ArrayList<String> infosCoups = Jeu
							.XMLParserColonneJouee(message);
					int idColonne = Integer.parseInt(infosCoups.get(0));
					int idJoueur = Integer.parseInt(infosCoups.get(1));
					Color couleur = jeu.getJoueur(idJoueur).getCouleur();
					jeu.getGrille().ajoutJeton(idColonne, idJoueur, couleur);
					jeu.changeJoueurActif();
				} else if (this.decodeurMessage.getNomRacine() == "EtatPartie") {
					Gdx.app.log(Puissance4.LOG, "EtatPartie");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Fermer.
	 * 
	 * @return true, if successful
	 */
	public boolean fermer() {
		try {
			this.connexion.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Sets the jeu.
	 * 
	 * @param jeu
	 *            the new jeu
	 */
	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

}
