package com.hexatan.puissance4.Modele;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringBufferInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.hexatan.puissance4.Coordonnee;
import com.hexatan.puissance4.FifoTab;
import com.hexatan.puissance4.FonctionUtil;
import com.hexatan.puissance4.Puissance4;
import com.hexatan.puissance4.Reseau.ClientPartage;
import com.hexatan.puissance4.Reseau.Coup;
import com.hexatan.puissance4.Reseau.EnvoiCoup;
import com.hexatan.puissance4.Reseau.Protocole;
import com.hexatan.puissance4.Reseau.ServeurPartage;

/**
 * The Class Jeu.
 */
@SuppressWarnings("deprecation")
public class Jeu {

	/** The grille. */
	private Grille grille;

	/** The tab joueurs. */
	private ArrayList<Joueur> tabJoueurs;

	/** The joueur local. */
	private Joueur joueurLocal, joueurDistant;

	/** The bruitage. */
	private Sound bruitage;

	/** The gagnant. */
	private boolean gagnant;

	private boolean joueurActif;

	private boolean multi;

	private Joueur joueurGagnant;

	private boolean solo;

	private boolean serveur;

	private FifoTab coupEchecJoueur = new FifoTab(-1, -1);
	private FifoTab coupVaincreJoueur = new FifoTab(-1, -1);

	/**
	 * Instantiates a new jeu.
	 * 
	 * @param debug
	 *            the debug
	 */
	public Jeu(boolean local) {
		multi = false;
		tabJoueurs = new ArrayList<Joueur>();
		grille = new Grille();
		try {
			this.chargerJoueurServeur();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ajoutJoueur(joueurLocal);
		if (local) {
			joueurDistant = new Joueur("Joueur 2", Color.YELLOW);
			ajoutJoueur(joueurDistant);

			joueurActif = true;
		}
		if (!local) {
			multi = true;
		}
		solo = false;
	}

	public Jeu(boolean local, boolean solo) {
		tabJoueurs = new ArrayList<Joueur>();
		grille = new Grille();
		try {
			this.chargerJoueurServeur();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ajoutJoueur(joueurLocal);
		if (local) {
			joueurDistant = new Joueur("Ordinateur", Color.YELLOW);
			ajoutJoueur(joueurDistant);

			joueurActif = true;
		}
		multi = local ? false : true;
		this.solo = solo;
	}

	/**
	 * Ajout jeton.
	 * 
	 * @param x
	 *            the x
	 */
	public void ajoutJeton(int x, int tailleCase) {

		int idJoueur = 0;
		Color couleurJoueur = null;

		if (joueurActif) {
			idJoueur = getJoueurLocal().getId();
			couleurJoueur = getJoueurLocal().getCouleur();
			joueurActif = false;
		} else if (!joueurActif) {
			idJoueur = getJoueurDistant().getId();
			couleurJoueur = getJoueurDistant().getCouleur();
			joueurActif = true;
		}

		int idColonne = FonctionUtil.colonne(x, tailleCase);

		if (idColonne != -1) {
			if (!solo) {
				grille.ajoutJeton(idColonne, idJoueur, couleurJoueur);
			}
			bruitage = Gdx.audio.newSound(Gdx.files
					.internal("Sound/placement.mp3"));
			bruitage.play(1);
			int Py = grille.getTabColonnes()[idColonne].getPosJeton();
			if (solo) {
				int i = coupIA(grille, idColonne, Py + 1);
				grille.ajoutJeton(idColonne, idJoueur, couleurJoueur);
			}
			int idGagnant = this.verifGain(idColonne, Py);
			if (idGagnant != 1) {
				for (Iterator<Joueur> iterator = tabJoueurs.iterator(); iterator
						.hasNext();) {
					Joueur joueur = (Joueur) iterator.next();
					if (joueur.getId() == idGagnant) {
						joueurGagnant = joueur;
					}
				}
			}

			if (multi) {
				if (!serveur) {
					ClientPartage client = ClientPartage.getInstance(
							Puissance4.hote, this);
					client.envoyerMessage(Protocole
							.preparerMessageCoup(new Coup(idColonne, idJoueur)));
				} else {
					ServeurPartage serveur = ServeurPartage.getInstance(this);

					EnvoiCoup envoi = new EnvoiCoup(serveur.getConnexion(),
							this);
					envoi.envoyerMessage(Protocole
							.preparerMessageCoup(new Coup(idColonne, idJoueur)));
				}
			}
		}
	}

	/**
	 * Ajout joueur.
	 * 
	 * @param joueur
	 *            the joueur
	 */
	public synchronized void ajoutJoueur(Joueur joueur) {
		if (!tabJoueurs.contains(joueur)) {
			tabJoueurs.add(joueur);
		}
	}

	public Joueur getJoueur(int idJoueur) {
		for (Iterator iterator = tabJoueurs.iterator(); iterator.hasNext();) {
			Joueur joueur = (Joueur) iterator.next();
			if (joueur.getId() == idJoueur) {
				return joueur;
			}
		}
		return null;
	}

	public void chargerJoueurServeur() throws FileNotFoundException {
		joueurLocal = Joueur.chargerJoueur();
	}

	/**
	 * Gets the grille.
	 * 
	 * @return the grille
	 */
	public Grille getGrille() {
		return grille;
	}

	/**
	 * Sets the grille.
	 * 
	 * @param grille
	 *            the new grille
	 */
	public void setGrille(Grille grille) {
		this.grille = grille;
	}

	public ArrayList<Joueur> getTabJoueurs() {
		return tabJoueurs;
	}

	public void setTabJoueurs(ArrayList<Joueur> tabJoueurs) {
		this.tabJoueurs = tabJoueurs;
	}

	/**
	 * Checks if is gagnant.
	 * 
	 * @return true, if is gagnant
	 */
	public boolean isGagnant() {
		return gagnant;
	}

	/**
	 * Sets the gagnant.
	 * 
	 * @param gagnant
	 *            the new gagnant
	 */
	public void setGagnant(boolean gagnant) {
		this.gagnant = gagnant;
	}

	public boolean joueurActif() {
		return joueurActif;
	}

	public void setJoueurActif(boolean joueurActif) {
		this.joueurActif = joueurActif;
	}

	public Joueur getJoueurGagnant() {
		return joueurGagnant;
	}

	public void setJoueurGagnant(Joueur joueurGagnant) {
		this.joueurGagnant = joueurGagnant;
	}

	public boolean isJoueurActif() {
		return joueurActif;
	}

	public boolean changeJoueurActif() {
		return joueurActif ? false : true;
	}

	public boolean isSolo() {
		return solo;
	}

	public boolean isMulti() {
		return multi;
	}

	public void setSolo(boolean solo) {
		this.solo = solo;
	}

	public Joueur getJoueurLocal() {
		return joueurLocal;
	}

	public void setJoueurLocal(Joueur joueurLocal) {
		this.joueurLocal = joueurLocal;
	}

	public Joueur getJoueurDistant() {
		return joueurDistant;
	}

	public void setJoueurDistant(Joueur joueurDistant) {
		this.joueurDistant = joueurDistant;
	}

	public boolean isServeur() {
		return serveur;
	}

	public void setServeur(boolean serveur) {
		this.serveur = serveur;
	}

	/**
	 * Verif gain.
	 * 
	 * @param idColonne
	 *            the id colonne
	 * @param Py
	 *            the py
	 * @return the int
	 */
	private int verifGain(int idColonne, int Py) {
		int nbJeton = 1;
		Case cRef = grille.getTabColonnes()[idColonne].getColCase(Py);
		Case cTest;

		// V�rif. SOUTH
		int x = Py - 1; // Pour v�rifier qu'il y ait une position � v�rifier
		boolean boucle = true;
		while ((!gagnant && boucle) && x >= 0) // Boucle + V�rification qu'il y
												// ait une position � v�rifier
		{

			cTest = grille.getTabColonnes()[idColonne].getColCase(x);

			if (cRef.getIdJoueur() == cTest.getIdJoueur()) {
				nbJeton++;
				if (nbJeton == 4) {
					System.out.println("Vainqueur : Joueur "
							+ cRef.getIdJoueur());
					gagnant = true;
					boucle = false;
					return cRef.getIdJoueur();
				} else {
					System.out.println("Align� nb:" + nbJeton);
				}
			} else {
				boucle = false;
			}
			x--;
		}

		// V�rif. EAST
		int eastWest = 0;
		nbJeton = 1;
		boucle = true;
		x = idColonne + 1;
		while ((!gagnant && boucle) && x <= 6) {
			System.out.println("Passe");

			cTest = grille.getTabColonnes()[x].getColCase(Py);

			if (cTest.isOccupe()) {
				System.out.println("Passe");
				if (cRef.getIdJoueur() == cTest.getIdJoueur()) {
					nbJeton++;
					eastWest++;
					if (nbJeton == 4) {
						System.out.println("Vainqueur : Joueur "
								+ cRef.getIdJoueur());
						gagnant = true;
						boucle = false;
						return cRef.getIdJoueur();
					} else {
						System.out.println("Align� nb:" + nbJeton);
					}
				} else {
					boucle = false;
				}
			} else {
				boucle = false;
			}
			x++;
		}

		// V�rif. WEST
		nbJeton = 1 + eastWest;
		x = idColonne - 1;
		boucle = true;
		while ((!gagnant && boucle) && x >= 0) {

			cTest = grille.getTabColonnes()[x].getColCase(Py);

			if (cTest.isOccupe()) {
				System.out.println("Passe");
				if (cRef.getIdJoueur() == cTest.getIdJoueur()) {
					nbJeton++;
					if (nbJeton == 4) {
						System.out.println("Vainqueur : Joueur "
								+ cRef.getIdJoueur());
						gagnant = true;
						return cRef.getIdJoueur();
					} else {
					}
				} else {
					boucle = false;
				}
			} else {
				boucle = false;
			}
			x--;
		}

		// V�rif. NORTH-EAST
		int y;
		int northeastSouthWest = 0;
		nbJeton = 1;
		x = idColonne + 1;
		y = Py + 1;
		boucle = true;
		while ((!gagnant && boucle) && x <= 6 && y <= 5) {
			System.out.println("Passe");

			cTest = grille.getTabColonnes()[x].getColCase(y);

			if (cTest.isOccupe()) {
				// System.out.println("Passe");
				// System.out.println("Boucle :"+boucle);
				if (cRef.getIdJoueur() == cTest.getIdJoueur()) {
					nbJeton++;
					northeastSouthWest++;
					if (nbJeton == 4) {
						System.out.println("Vainqueur : Joueur "
								+ cRef.getIdJoueur());
						gagnant = true;
						return cRef.getIdJoueur();
					} else {
					}
				} else {
					boucle = false;
				}
			} else {
				boucle = false;
			}
			x++;
			y++;
		}

		// V�rif. NORTH-WEST
		nbJeton = 1;
		x = idColonne - 1;
		int northwestSouthEast = 0;
		y = Py + 1;
		boucle = true;
		while ((!gagnant && boucle) && x >= 0 && y <= 5) {
			System.out.println("Passe");

			cTest = grille.getTabColonnes()[x].getColCase(y);

			if (cTest.isOccupe()) {
				if (cRef.getIdJoueur() == cTest.getIdJoueur()) {
					nbJeton++;
					northwestSouthEast++;
					if (nbJeton == 4) {
						System.out.println("Vainqueur : Joueur "
								+ cRef.getIdJoueur());
						gagnant = true;
						return cRef.getIdJoueur();
					} else {
					}
				} else {
					boucle = false;
				}
			} else {
				boucle = false;
			}
			x--;
			y++;

		}

		// V�rif. SOUTH-EAST
		nbJeton = 1 + northwestSouthEast;
		x = idColonne + 1;
		y = Py - 1;
		boucle = true;
		while ((!gagnant && boucle) && x <= 6 && y >= 0) {
			System.out.println("Passe");

			cTest = grille.getTabColonnes()[x].getColCase(y);

			if (cTest.isOccupe()) {
				// System.out.println("Passe");
				// System.out.println("Boucle :"+boucle);
				if (cRef.getIdJoueur() == cTest.getIdJoueur()) {
					nbJeton++;
					if (nbJeton == 4) {
						System.out.println("Vainqueur : Joueur "
								+ cRef.getIdJoueur());
						gagnant = true;
						return cRef.getIdJoueur();
					} else {
					}
				} else {
					boucle = false;
				}
			} else {
				boucle = false;
			}
			x++;
			y--;
		}

		// V�rif. SOUTH-WEST
		nbJeton = 1 + northeastSouthWest;
		x = idColonne - 1;
		y = Py - 1;
		boucle = true;
		while ((!gagnant && boucle) && x >= 0 && y >= 0) {
			System.out.println("Passe");

			cTest = grille.getTabColonnes()[x].getColCase(y);

			if (cTest.isOccupe()) {
				System.out.println("Passe");
				System.out.println("Boucle :" + boucle);
				if (cRef.getIdJoueur() == cTest.getIdJoueur()) {
					nbJeton++;
					if (nbJeton == 4) {
						System.out.println("Vainqueur : Joueur "
								+ cRef.getIdJoueur());
						gagnant = true;
						return cRef.getIdJoueur();
					} else {
					}
				} else {
					boucle = false;
				}
			} else {
				boucle = false;
			}
			x--;
			y--;
		}

		return -1;
	}

	/**
	 * XML parser jeu.
	 * 
	 * @param xml
	 *            the xml
	 * @return the string
	 */
	public static String XMLParserJeu(String xml) {

		String informations = null;

		try {

			DocumentBuilder parser = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document document = parser.parse(new StringBufferInputStream(xml));

			NodeList listePartie = document.getElementsByTagName("Partie");

			for (int position = 0; position < listePartie.getLength(); position++) {
				Node noeudPartie = listePartie.item(position);

				if (noeudPartie.getNodeType() == Node.ELEMENT_NODE) {
					Element elementPartie = (Element) noeudPartie;

					informations = getPartieAttribut(elementPartie, "Victoire");
				}
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return informations;
	}

	/**
	 * XML parser colonne jouee.
	 * 
	 * @param xml
	 *            the xml
	 * @return the string
	 */
	public static ArrayList<String> XMLParserColonneJouee(String xml) {

		ArrayList<String> informations = new ArrayList<String>();

		try {

			DocumentBuilder parser = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document document = parser.parse(new StringBufferInputStream(xml));
			NodeList listePion = document.getElementsByTagName("Pion");

			for (int position = 0; position < listePion.getLength(); position++) {
				Node noeudPion = listePion.item(position);

				if (noeudPion.getNodeType() == Node.ELEMENT_NODE) {
					Element elementPion = (Element) noeudPion;

					informations.add(getPionAttribut(elementPion, "Colonne"));
					informations.add(getPionAttribut(elementPion, "Joueur"));

				}
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return informations;
	}

	/**
	 * Gets the pion attribut.
	 * 
	 * @param pion
	 *            the pion
	 * @param balise
	 *            the balise
	 * @return the pion attribut
	 */
	public static String getPionAttribut(Element pion, String balise) {
		NodeList typeList = pion.getElementsByTagName(balise);
		Element elementType = (Element) typeList.item(0); // premiere balise
															// type
		String valeur = elementType.getChildNodes().item(0).getNodeValue();

		return valeur;
	}

	/**
	 * Envoi xml colonne.
	 * 
	 * @param pion
	 *            the pion
	 * @return true, if successful
	 */
	public static boolean envoiXMLColonne(String pion) {

		String xml = "<ListePions><Pion><Colonne>" + pion
				+ "</Colonne></Pion></ListePions>";

		System.out.println(xml);

		Socket envoi;
		try {
			System.out.println("Connection du serveur pour envoyer du xml");
			envoi = new Socket(InetAddress.getLocalHost(), 2001);
			PrintWriter out = new PrintWriter(envoi.getOutputStream(), true);
			out.println(xml);
			out.flush();
			System.out.println("Envoi du xml");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Gets the partie attribut.
	 * 
	 * @param partie
	 *            the partie
	 * @param balise
	 *            the balise
	 * @return the partie attribut
	 */
	public static String getPartieAttribut(Element partie, String balise) {
		NodeList typeList = partie.getElementsByTagName(balise);
		Element elementType = (Element) typeList.item(0); // premiere balise
															// type
		String valeur = elementType.getChildNodes().item(0).getNodeValue();

		return valeur;
	}

	/**
	 * Envoi xml partie.
	 * 
	 * @param victoire
	 *            the victoire
	 * @return true, if successful
	 */
	public static boolean envoiXMLPartie(String victoire) {

		String xml = "<EtatPartie><Partie><Victoire>" + victoire
				+ "</Victoire></partie></EtatPartie>";

		System.out.println(xml);

		Socket envoi;
		try {
			System.out.println("Connection du serveur pour envoyer du xml");
			envoi = new Socket(InetAddress.getLocalHost(), 2001);
			PrintWriter out = new PrintWriter(envoi.getOutputStream(), true);
			out.println(xml);
			out.flush();
			System.out.println("Envoi du xml");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Permet de donner l'ID de la colonne qua l'IA va jouer
	 * 
	 * @param jeu
	 *            La grille de jeu
	 * @param idColonne
	 *            Colonne jou� par le joueur Humain
	 * @param Py
	 *            Hauteur � laquel le jeton se trouve
	 * @return Le coup de l'IA
	 */
	public int coupIA(Grille jeu, int idColonne, int Py) {
		int nbJeton = verifLigne(idColonne, Py);
		int[] echecJoueur;
		if (nbJeton >= 2) {
			echecJoueur = this.mettreJoueurEchec(idColonne, Py);
			if (echecJoueur[0] != -1 && echecJoueur[2] != -1) {
				if (coupPossible(echecJoueur[0], echecJoueur[1])) {
					if (coupEchecJoueur.getIdColonne() == -1) {
						coupEchecJoueur.add(echecJoueur[2], echecJoueur[3]);
						coupEchecJoueur.delete();
					} else {
						coupEchecJoueur.add(echecJoueur[2], echecJoueur[3]);
					}
					return echecJoueur[0];
				}
			}
			if (echecJoueur[0] == -1 && echecJoueur[2] != -1) {
				if (coupPossible(echecJoueur[2], echecJoueur[3])) {
					return echecJoueur[2];
				} else {
					coupEchecJoueur.add(echecJoueur[2], echecJoueur[3]);
				}
			}
			if (echecJoueur[0] != -1 && echecJoueur[2] == -1) {
				if (coupPossible(echecJoueur[0], echecJoueur[1])) {
					return echecJoueur[0];
				} else {
					coupEchecJoueur.add(echecJoueur[0], echecJoueur[1]);
				}
			}
			if (echecJoueur[0] == -1 && echecJoueur[1] == -1) {
				Coordonnee tmpNode = coupEchecJoueur.getHead();
				while (tmpNode != null) {
					if (this.coupPossible(tmpNode)) {
						return tmpNode.getIdColonne();
					} else {
						tmpNode = tmpNode.getNext();
					}
				}
				return coupAleatoire();
			}
		}
		return coupAleatoire();
	}

	/**
	 * Permet de g�n�rer un coup al�atoire
	 * 
	 * @return rand Id de la colonne que l'IA va jouer
	 */
	public int coupAleatoire() {
		boolean[] colonnePossible = new boolean[7];
		for (int i = 0; i <= 6; i++) {
			if (this.grille.getTabColonnes()[i].getIdLibre() <= 6) {
				colonnePossible[i] = true;
			} else {
				colonnePossible[i] = false;
			}
		}
		int rand = (int) (Math.random() * 7);
		while (colonnePossible[rand] == false) {
			rand = (int) (Math.random() * 7);
		}
		return rand;
	}

	/**
	 * Permet de v�rifier si il y a possibilit� de gain
	 * 
	 * @param idColonne
	 * @param Py
	 * @return
	 */
	public int verifLigne(int idColonne, int Py) {
		int nbJeton = 1;
		Case cRef = grille.getTabColonnes()[idColonne].getColCase(Py);
		Case cTest;

		// V�rif. EAST
		nbJeton = 1;
		int eastWest = 0;
		boolean boucle = true;
		int x = idColonne + 1;
		System.out.println("V�rif. EAST");
		while ((!gagnant && boucle) && x <= 6) {
			System.out.println("Passe");

			cTest = grille.getTabColonnes()[x].getColCase(Py);

			if (cTest.isOccupe()) {
				System.out.println("Passe");
				if (cRef.getIdJoueur() == cTest.getIdJoueur()) {
					nbJeton++;
					eastWest++;
				} else {
					System.out.println("V�rification EAST fini");
					boucle = false;
				}
			} else {
				boucle = false;
			}
			x++;
		}

		// V�rif. WEST
		nbJeton = 1 + eastWest;
		x = idColonne - 1;
		boucle = true;
		System.out.println("V�rif. WEST");
		while ((!gagnant && boucle) && x >= 0) {
			System.out.println("Passe");

			cTest = grille.getTabColonnes()[x].getColCase(Py);
			if (cTest.isOccupe()) {
				System.out.println("Passe");
				if (cRef.getIdJoueur() == cTest.getIdJoueur()) {
					nbJeton++;
					System.out.println("Align� nb:" + nbJeton);
				} else {
					System.out.println("V�rification WEST fini");
					boucle = false;
				}
			} else {
				boucle = false;
			}
			x--;
		}
		return nbJeton;

	}

	public int[] mettreJoueurEchec(int idColonne, int Py) {
		int[] coordonneeJouer = new int[4];
		coordonneeJouer[0] = -1;
		coordonneeJouer[1] = -1;
		coordonneeJouer[2] = -1;
		coordonneeJouer[3] = -1;
		Case cRef = grille.getTabColonnes()[idColonne].getColCase(Py);
		Case cTest;

		// Id de la Colonne pour mettre en echec par la droite si possible
		int x = idColonne + 1;
		boolean boucle = true;
		while ((!gagnant && boucle) && x <= 6) {

			cTest = grille.getTabColonnes()[x].getColCase(Py);

			if (cTest.isOccupe()) {
				if (cRef.getIdJoueur() == cTest.getIdJoueur()) {
					x++;
				} else {
					System.out
							.println("V�rification de mise en �chec Droite fini");
					coordonneeJouer[0] = x;
					coordonneeJouer[1] = Py;
				}
			} else {
				boucle = false;
			}

			x++;
		}

		// Id de la Colonne pour mettre en echec par la Gauche si possible
		x = idColonne - 1;
		boucle = true;
		while ((!gagnant && boucle) && x >= 0) {
			cTest = grille.getTabColonnes()[x].getColCase(Py);

			if (cTest.isOccupe()) {
				if (cRef.getIdJoueur() == cTest.getIdJoueur()) {
					x--;
				} else {
					System.out
							.println("V�rification de mise en �chec Gauche fini");
					coordonneeJouer[2] = x;
					coordonneeJouer[3] = Py;
				}
			} else {
				boucle = false;
			}

			x--;
		}
		return coordonneeJouer;
	}

	public boolean coupPossible(int idColonne, int Py) {
		if (grille.getTabColonnes()[idColonne].getIdLibre() == Py)
			return true;
		return false;
	}

	public boolean coupPossible(Coordonnee n) {
		if (grille.getTabColonnes()[n.getIdColonne()].getIdLibre() == n.getY())
			return true;
		return false;
	}
}
