package com.me.puissance4.Reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.me.puissance4.Puissance4;
import com.me.puissance4.Modele.Jeu;
import com.me.puissance4.Modele.Joueur;

/**
 * The Class ContactJoueur.
 */
public class ContactJoueur implements Runnable {

	/** The connexion. */
	Socket connexion = null;

	/** The lecteur. */
	BufferedReader lecteur = null;

	/** The imprimante. */
	PrintWriter imprimante = null;

	/** The joueur. */
	private Joueur joueur = null;

	/** The jeu. */
	private Jeu jeu;

	/** The decodeur message. */
	private Protocole decodeurMessage = null;

	private boolean initialise = false;

	/**
	 * Instantiates a new contact joueur.
	 * 
	 * @param connexion
	 *            the connexion
	 * @param joueur
	 *            the joueur
	 */
	public ContactJoueur(Socket connexion, Jeu jeu) {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log(Puissance4.LOG, "Reception d'un client par le serveur");
		decodeurMessage = new Protocole();
		this.jeu = jeu;
		joueur = new Joueur();
		this.connexion = connexion;
		try {
			this.imprimante = new PrintWriter(connexion.getOutputStream());
			this.lecteur = new BufferedReader(new InputStreamReader(
					connexion.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if (lecteur == null)
			return;
		String message;
		try {
			while ((message = lecteur.readLine()) != null) {
				Gdx.app.setLogLevel(Application.LOG_DEBUG);
				Gdx.app.log(Puissance4.LOG, "Voici le message recu " + message);
				this.decodeurMessage.charger(message);
				if (this.decodeurMessage.getNomRacine() == "ListePions") {
					ArrayList<String> infosCoups = Jeu
							.XMLParserColonneJouee(message);
					int idColonne = Integer.parseInt(infosCoups.get(0));
					int idJoueur = Integer.parseInt(infosCoups.get(1));
					Color couleur = jeu.getJoueur(idJoueur).getCouleur();
					jeu.getGrille().ajoutJeton(idColonne, idJoueur, couleur);
					jeu.changeJoueurActif();
				} else if (this.decodeurMessage
						.testerMessageInitialisationJoueur() && !initialise) {
					ArrayList<String> infosJoueur = Joueur
							.XMLParserJoueur(message);
					joueur.setId(Integer.parseInt(infosJoueur.get(0)));
					joueur.setNom(infosJoueur.get(1));
					joueur.setCouleur(Color.valueOf(infosJoueur.get(2)));
					joueur.setPartiesJouees(Integer.parseInt(infosJoueur.get(3)));
					joueur.setPartiesGagnees(Integer.parseInt(infosJoueur
							.get(4)));
					jeu.ajoutJoueur(joueur);
					jeu.setJoueurDistant(joueur);
					ArrayList<String> infos = Joueur.lireParametreJoueur();
					envoyerMessage(Protocole
							.preparerMessageInitialisationJoueur(
									Integer.parseInt(infos.get(0)),
									infos.get(1), Color.valueOf(infos.get(2)),
									Integer.parseInt(infos.get(3)),
									Integer.parseInt(infos.get(4))));
					initialise = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Envoyer message.
	 * 
	 * @param message
	 *            the message
	 */
	public void envoyerMessage(String message) {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log(Puissance4.LOG, "Envoi du message " + message);
		this.imprimante.println(message);
		this.imprimante.flush();
	}

	/**
	 * Initialiser.
	 */
	public void initialiser() {
		envoyerMessage(Protocole.preparerMessageInitialisationJoueur(
				joueur.getId(), joueur.getNom(), joueur.getCouleur(),
				joueur.getPartiesJouees(), joueur.getPartiesGagnees()));
	}

	public Jeu getJeu() {
		return jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}
}
