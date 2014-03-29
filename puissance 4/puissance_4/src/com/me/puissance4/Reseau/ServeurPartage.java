package com.me.puissance4.Reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.me.puissance4.Puissance4;
import com.me.puissance4.Modele.Jeu;

/**
 * The Class ServeurPartage.
 */
public class ServeurPartage implements Runnable {

	/** The ecoute. */
	private ServerSocket ecoute = null;

	/** The jeu. */
	private Jeu jeu;

	/** The client. */
	static ServeurPartage serveur = null;

	/** The lecteur. */
	BufferedReader lecteur = null;

	/** The imprimante. */
	PrintWriter imprimante = null;

	Socket connexion = null;

	/**
	 * Instantiates a new serveur partage.
	 */
	public ServeurPartage(Jeu jeu) {
		this.jeu = jeu;
		jeu.setServeur(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		this.ouvrir();
		this.ecouter();
	}

	/**
	 * Ouvrir.
	 * 
	 * @return true, if successful
	 */
	public boolean ouvrir() {
		try {
			this.ecoute = new ServerSocket(
					CoordonneesReseau.PORT_CLIENT_VERS_SERVEUR);
			Gdx.app.setLogLevel(Application.LOG_DEBUG);
			Gdx.app.log(Puissance4.LOG,
					"Lancement de l'ecoute du serveur avec partage de ressources");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Ecouter.
	 */
	public void ecouter() {
		if (ecoute != null) {
			try {
				if ((connexion = ecoute.accept()) != null) {

					ContactJoueur contactJoueur = new ContactJoueur(connexion,
							jeu);

					Thread thread = new Thread(contactJoueur);
					thread.start();

					try {
						this.imprimante = new PrintWriter(
								connexion.getOutputStream(), true);
						this.lecteur = new BufferedReader(
								new InputStreamReader(
										connexion.getInputStream()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				while (ecoute.accept() != null) {
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets the single instance of ClientPartage.
	 * 
	 * @return single instance of ClientPartage
	 */
	static public ServeurPartage getInstance(Jeu jeu) {
		if (null == serveur)
			serveur = new ServeurPartage(jeu);
		return serveur;
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
	 * Fermer.
	 * 
	 * @return true, if successful
	 */
	public boolean fermer() {
		try {
			this.ecoute.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Recup adresse ip.
	 * 
	 * @return the string
	 */
	public static String recupAdresseIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return null;
		}
	}

	public Jeu getJeu() {
		return jeu;
	}

	public Socket getConnexion() {
		return connexion;
	}

	public void setConnexion(Socket connexion) {
		this.connexion = connexion;
	}
}
