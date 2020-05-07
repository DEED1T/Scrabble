

import java.io.File;
import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

public class ArbreBinaire<E> implements Iterable<E> {
	
	public class ABIterateur implements Iterator<E>{
		
		ArrayDeque<ArbreBinaire<E>> f;
		
		public ABIterateur(ArbreBinaire<E> ab) {
			ArrayDeque<E> f = new ArrayDeque<E>();
			f.add((E) ab);
		}
		
		@Override
		public boolean hasNext() {
			if (f.size() <= 1) {
				return false;
			}
			return true;
		}

		@Override
		public E next() {
			ArbreBinaire<E> ab = f.poll();
			if(!ab.est_vide()) {
				
			
				if (!(ab.sad().est_vide())) {
					f.add(ab.sad());
					}
				if(!(ab.sag().est_vide())) {
					f.add(ab.sad());
					}
			}
			return (E) ab.racine();
		}
		
	}
	
	E etiquette;
	ArbreBinaire<E> fg;
	ArbreBinaire<E> fd;
	
	
	public ArbreBinaire() {
		this.etiquette = null;
		 
	}
	
	public boolean est_vide() {
		return this.etiquette == null;
	}
	
	public ArbreBinaire(E r, ArbreBinaire<E> g, ArbreBinaire<E> d){
		this.etiquette = r;
		this.fg = g;
		this.fd = d;
		
	}
	
	public E racine() {
		return this.etiquette;
	}
	
	public ArbreBinaire<E> sad(){
		if(this.etiquette == null) {
			System.out.println("erreur");
			}
		else {
			return this.fd;
		}
		return null;
	}
	
	public ArbreBinaire<E> sag(){
		if(this.etiquette == null) {
			System.out.println("erreur");
			}
		return this.fg;
		
	}
	
	public int taille() {
		if(this.est_vide()) {
			return 0;
		}
		else {
			return 1+this.fg.taille()+this.fd.taille();
		}
	}
	
	public int hauteur() {
		if (est_vide()) {
			return 0;
		}
		else {
			return 1+Math.max(this.fg.hauteur(), this.fd.hauteur());
		}
	}
	
	public ArbreBinaire<E> parcoursProfondeurInfixe() {
		
		if (!(this.fg.est_vide())) {
			this.fg.parcoursProfondeurInfixe();
		}
		
		System.out.println(this.racine());
		
		if (!(this.fd.est_vide())) {
			this.fd.parcoursProfondeurInfixe();
		}
		
		return null;
	}
	
	public ArrayList<E> parcoursLargeur(){
		ArbreBinaire<E> a = new ArbreBinaire<E>();
		ArrayList<E> l  = new ArrayList<E>();
		ArrayDeque<ArbreBinaire<E>> f = new ArrayDeque<ArbreBinaire<E>>();
		f.add(this);
		while(!(f.isEmpty())) {
			a = (ArbreBinaire<E>) f.getFirst();
			f.remove();
			if (!(a.est_vide())) {
				l.add((E) a.racine());
				f.add(a.sag());
				f.add(a.sad());
				}
		}
		return l;
		
	}
	
	@Override
	public Iterator<E> iterator() {
		return new ABIterateur(this);
	}

	
	public static void main(String[] args) {}
}
	
