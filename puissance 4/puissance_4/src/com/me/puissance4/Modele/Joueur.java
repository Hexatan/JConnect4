package com.me.puissance4.Modele;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;

/**
 * The Class Joueur.
 */
public class Joueur {

	/** The id. */
	private int id;

	/** Nom du joueur. */
	private String nom;

	/** Couleur du joueur. */
	private Color couleur;

	/** Nombres de partie gagnées par le joueur. */
	private int partiesGagnees;

	/** Nombres de parties jouées par le joueur. */
	private int partiesJouees;

	private Pion pion;

	public Joueur() {

	}

	/**
	 * Instantie un nouveau joueur.
	 * 
	 * @param nom
	 *            the nom
	 * @param couleur
	 *            the couleur
	 */
	public Joueur(String nom, Color couleur) {
		this.couleur = couleur;
		this.nom = nom;
		Random r = new Random();
		id = r.nextInt();
	}

	public Joueur(int id, String nom, Color couleur, int partiesGagnees,
			int partiesJouees) {
		this.id = id;
		this.couleur = couleur;
		this.nom = nom;
		this.partiesGagnees = partiesGagnees;
		this.partiesJouees = partiesJouees;
	}

	/**
	 * XML parser joueur.
	 * 
	 * @param xml
	 *            the xml
	 * @return the array list
	 */
	public static ArrayList<String> XMLParserJoueur(String xml) {

		ArrayList<String> informations = null;

		try {

			DocumentBuilder parser = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document document = parser.parse(new StringBufferInputStream(xml));

			NodeList listeJoueur = document.getElementsByTagName("Description");
			for (int position = 0; position < listeJoueur.getLength(); position++) {
				Node noeudJoueur = listeJoueur.item(position);

				if (noeudJoueur.getNodeType() == Node.ELEMENT_NODE) {
					Element elementJoueur = (Element) noeudJoueur;

					informations = new ArrayList<String>();

					informations.add(getJoueurAttribut(elementJoueur, "Id"));

					informations.add(getJoueurAttribut(elementJoueur, "Nom"));
					informations
							.add(getJoueurAttribut(elementJoueur, "Couleur"));
					informations.add(getJoueurAttribut(elementJoueur,
							"PartiesJouees"));
					informations.add(getJoueurAttribut(elementJoueur,
							"PartiesGagnees"));
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
	 * Gets the joueur attribut.
	 * 
	 * @param description
	 *            the description
	 * @param balise
	 *            the balise
	 * @return the joueur attribut
	 */
	public static String getJoueurAttribut(Element description, String balise) {
		NodeList typeList = description.getElementsByTagName(balise);
		Element elementType = (Element) typeList.item(0); // premiere balise
															// type
		String valeur = elementType.getChildNodes().item(0).getNodeValue();

		return valeur;
	}

	/**
	 * Ecrire xml joueur.
	 * 
	 * @param nom
	 *            the nom
	 * @param couleur
	 *            the couleur
	 * @param partiesJouees
	 *            the parties jouees
	 * @param partiesGagnees
	 *            the parties gagnees
	 */
	public static void ecrireXMLJoueur(String id, String nom, String couleur,
			String partiesJouees, String partiesGagnees) {

		String xml = "<InfosJoueur><Description><Id>" + id + "</Id><Nom>" + nom
				+ "</Nom><Couleur>" + couleur + "</Couleur><PartiesJouees>"
				+ partiesJouees + "</PartiesJouees><PartiesGagnees>"
				+ partiesGagnees
				+ "</PartiesGagnees></Description></InfosJoueur>";

		// Permet d'écrire dans un fichier. C'est de l'info XML.

		FileHandle fichier = Gdx.files.local("Parametre.xml");

		fichier.writeString(xml, false);
	}

	/**
	 * Lire parametre joueur.
	 * 
	 * @return the array list
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	public static ArrayList<String> lireParametreJoueur() {
		FileHandle file = Gdx.files.local("Parametre.xml");
		String str = file.readString();
		return XMLParserJoueur(str);
	}

	public static Joueur chargerJoueur() {
		ArrayList<String> infosJoueurs = lireParametreJoueur();
		return new Joueur(Integer.parseInt(infosJoueurs.get(0)),
				infosJoueurs.get(1), Color.valueOf(infosJoueurs.get(2)),
				Integer.parseInt(infosJoueurs.get(3)),
				Integer.parseInt(infosJoueurs.get(4)));
	}

	/**
	 * Gets the nom.
	 * 
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Sets the nom.
	 * 
	 * @param nom
	 *            the new nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Gets the couleur.
	 * 
	 * @return the couleur
	 */
	public Color getCouleur() {
		return couleur;
	}

	/**
	 * Sets the couleur.
	 * 
	 * @param couleur
	 *            the new couleur
	 */
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	/**
	 * Gets the partie gagnees.
	 * 
	 * @return the partie gagnees
	 */
	public int getPartiesGagnees() {
		return partiesGagnees;
	}

	/**
	 * Sets the parties gagnees.
	 * 
	 * @param partieGagnees
	 *            the new parties gagnees
	 */
	public void setPartiesGagnees(int partieGagnees) {
		this.partiesGagnees = partieGagnees;
	}

	/**
	 * Gets the parties jouees.
	 * 
	 * @return the parties jouees
	 */
	public int getPartiesJouees() {
		return partiesJouees;
	}

	/**
	 * Sets the parties jouees.
	 * 
	 * @param partiesJouees
	 *            the new parties jouees
	 */
	public void setPartiesJouees(int partiesJouees) {
		this.partiesJouees = partiesJouees;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	public Pion getPion() {
		return pion;
	}

	public void setPion(Pion pion) {
		this.pion = pion;
	}

}
