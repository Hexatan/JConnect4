package com.hexatan.puissance4.Reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.hexatan.puissance4.Puissance4;
import com.hexatan.puissance4.Modele.Jeu;
import com.hexatan.puissance4.Modele.Joueur;

public class EnvoiCoup {
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

	public EnvoiCoup(Socket connexion, Jeu jeu) {
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
}
