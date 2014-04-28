package com.hexatan.puissance4;

public class Coordonnee {
	private Coordonnee next;
	private int idColonne;
	private int y;

	public Coordonnee(int val, int y) {
		this.idColonne = val;
		this.y = y;
		next = null;
	}

	public Coordonnee getNext() {
		return this.next;
	}

	public void setNext(Coordonnee next) {
		this.next = next;
	}

	public Coordonnee getNextNext() {
		return this.next.next;
	}

	public void setNextNext(Coordonnee next) {
		this.next.next = next;
	}

	public int getIdColonne() {
		return idColonne;
	}

	public void setIdColonne(int num) {
		this.idColonne = num;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
