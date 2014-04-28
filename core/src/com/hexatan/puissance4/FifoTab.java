package com.hexatan.puissance4;

public class FifoTab {

	private Coordonnee head;
	private int taille = 0;

	public FifoTab(int val, int y) {
		head = new Coordonnee(val, y);
		taille++;
	}

	public void add(int val, int y) {
		Coordonnee tmpNode = head;
		while (tmpNode.getNext() != null) {
			tmpNode = tmpNode.getNext();
		}
		tmpNode.setNext(new Coordonnee(val, y));
		taille++;
	}

	public void delete(int val) {
		Coordonnee prevNode = null;
		Coordonnee currNode = head;

		if (head.getIdColonne() == val) {
			head = head.getNext();
			return;
		}

		while (currNode != null && currNode.getIdColonne() != val) {
			prevNode = currNode;
			currNode = currNode.getNext();
		}

		if (currNode == null) {
			System.out.println("A node with that value does not exist.");
		} else {
			prevNode.setNext(currNode.getNext());
			taille--;
		}
	}

	public void delete() {
		this.head = this.head.getNext();
		taille--;
	}

	public int getIdColonne() {
		int val = this.head.getIdColonne();
		return val;
	}

	public Coordonnee getHead() {
		return this.head;
	}

	public void print() {
		Coordonnee tmpNode = head;
		while (tmpNode != null) {
			System.out.print(tmpNode.getIdColonne() + tmpNode.getY() + " -> ");
			tmpNode = tmpNode.getNext();
		}
		System.out.print("null");
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public static void main(String[] args) {
		FifoTab coupEchecJoueur = new FifoTab(-1, -1);
		coupEchecJoueur.print();
		coupEchecJoueur.add(2, 2);
		coupEchecJoueur.add(5, 2);
		coupEchecJoueur.add(4, 1);
		coupEchecJoueur.add(10, 4);
		coupEchecJoueur.print();
		coupEchecJoueur.delete(10);
		coupEchecJoueur.print();
		coupEchecJoueur.add(50, 10);
		coupEchecJoueur.print();
		coupEchecJoueur.delete();
		coupEchecJoueur.print();
		System.out.println("\n" + coupEchecJoueur.getHead());
	}
}