package com.me.puissance4.Reseau;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * Classe LecteurXML
 * 
 * Cette classe donne un exemple de lecture de xml sous format chaîne et
 * d'extraction de celle-ci des informations à propos d'une liste d'objets.
 */
// parce que StringBufferInputStream n'a pas de remplacement valable
// http://www.velocityreviews.com/forums/t367242-what-replaces-stringbufferinputstream.html
@SuppressWarnings("deprecation")
public abstract class XML {

	/** The parseur. */
	DocumentBuilder parseur;

	/** The document. */
	Document document;

	/**
	 * Instantiates a new xml.
	 */
	public XML() {
		try {
			parseur = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Charger.
	 * 
	 * @param xml
	 *            the xml
	 */
	public void charger(String xml) {
		try {
			// document est un arbre DOM comme le document du JavaScript
			document = parseur.parse(new StringBufferInputStream(xml));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lister noeuds selon balise.
	 * 
	 * @param balise
	 *            the balise
	 * @return the vector
	 */
	public Vector<Node> listerNoeudsSelonBalise(String balise) {
		Vector<Node> liste = new Vector<Node>();
		NodeList listeForme = document.getElementsByTagName(balise);

		for (int position = 0; position < listeForme.getLength(); position++) {
			Node noeudForme = listeForme.item(position);

			if (noeudForme.getNodeType() == Node.ELEMENT_NODE) {
				liste.add(noeudForme);
			}
		}
		return liste;
	}

	/**
	 * Lister noeuds du noeud selon balise.
	 * 
	 * @param noeud
	 *            the noeud
	 * @param balise
	 *            the balise
	 * @return the vector
	 */
	static public Vector<Node> listerNoeudsDuNoeudSelonBalise(Node noeud,
			String balise) {
		Vector<Node> liste = new Vector<Node>();
		NodeList listeForme = ((Element) noeud).getElementsByTagName(balise);

		for (int position = 0; position < listeForme.getLength(); position++) {
			Node noeudForme = listeForme.item(position);

			if (noeudForme.getNodeType() == Node.ELEMENT_NODE) {
				liste.add(noeudForme);
			}
		}
		return liste;
	}

	/**
	 * Lister noeuds selon chemin.
	 * 
	 * @param chemin
	 *            the chemin
	 * @return the vector
	 */
	public Vector<Node> listerNoeudsSelonChemin(String chemin) {
		String[] balises = chemin.split("/");
		Vector<Node> liste = null;
		for (int profondeur = 0; profondeur < balises.length; profondeur++) {
			if (0 == profondeur) {
				liste = this.listerNoeudsSelonBalise(balises[profondeur]);
			} else {
				for (Node noeud : liste) {
					liste.addAll(XML.listerNoeudsDuNoeudSelonBalise(noeud,
							balises[profondeur]));
					liste.remove(noeud);
				}
			}
		}
		return liste;
	}

	/**
	 * Gets the texte du chemin.
	 * 
	 * @param chemin
	 *            the chemin
	 * @return the texte du chemin
	 */
	public String getTexteDuChemin(String chemin) {
		String[] balises = chemin.split("/");
		Vector<Node> liste = null;
		for (int profondeur = 0; profondeur < balises.length; profondeur++) {
			if (0 == profondeur) {
				liste = this.listerNoeudsSelonBalise(balises[profondeur]);
			} else if ((balises.length - 1) == profondeur) {
				return getTexteDuSousNoeud(liste.get(0), balises[profondeur]);
			} else {
				for (Node noeud : liste) {
					liste.addAll(XML.listerNoeudsDuNoeudSelonBalise(noeud,
							balises[profondeur]));
					liste.remove(noeud);
				}
			}
		}
		return "";
	}

	/**
	 * Gets the texte du noeud.
	 * 
	 * @param noeud
	 *            the noeud
	 * @param balise
	 *            the balise
	 * @return the texte du noeud
	 */
	public String getTexteDuNoeud(Node noeud, String balise) {
		return ((Element) noeud).getChildNodes().item(0).getNodeValue();
	}

	/**
	 * Gets the texte du sous noeud.
	 * 
	 * @param noeud
	 *            the noeud
	 * @param balise
	 *            the balise
	 * @return the texte du sous noeud
	 */
	public String getTexteDuSousNoeud(Node noeud, String balise) {
		NodeList typeList = ((Element) noeud).getElementsByTagName(balise);
		Element elementType = (Element) typeList.item(0);
		return elementType.getChildNodes().item(0).getNodeValue();
	}

	/**
	 * Gets the nom racine.
	 * 
	 * @return the nom racine
	 */
	public String getNomRacine() {
		return document.getDocumentElement().getNodeName();
	}

	/**
	 * Interpreter.
	 * 
	 * @return the object
	 */
	public abstract Object interpreter();

}
