package com.me.puissance4.Reseau;

public class Partie {

	private int nombrePions;
	private int nombreCouleurs;
	private int nombreCoups;

	public Partie(int nombrePions, int nombreCouleurs, int nombreCoups) {
		this.nombrePions = nombrePions;
		this.nombreCouleurs = nombreCouleurs;
		this.nombreCoups = nombreCoups;
	}

	public int getNombrePions() {
		return nombrePions;
	}

	public void setNombrePions(int nombrePions) {
		this.nombrePions = nombrePions;
	}

	public int getNombreCouleurs() {
		return nombreCouleurs;
	}

	public void setNombreCouleurs(int nombreCouleurs) {
		this.nombreCouleurs = nombreCouleurs;
	}

	public int getNombreCoups() {
		return nombreCoups;
	}

	public void setNombreCoups(int nombreCoups) {
		this.nombreCoups = nombreCoups;
	}
}
