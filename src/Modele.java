import java.util.ArrayList;
import java.util.Random;

import javafx.scene.chart.PieChartBuilder;
import javafx.scene.input.PickResult;

public class Modele {
	
	Sac s = new Sac();
	
	ArrayList<Lettre> pieces = s.get();
	ArrayList<Lettre> j1 = new ArrayList<Lettre>();
	Random r = new Random(10);
	
	int pivot = 0; 
	
	public Modele() {}
	
	//--------------fonctions de suivis/vérification----------------------
	
	public char get_char(int ind) {
		Lettre c = s.get().get(ind);
		return c.ch;
	}
	
	public int get_point(int ind) {
		Lettre p = s.get().get(ind);
		return p.pts;
	}
	
	public int get_remainNbs(char c) {
		int cpt = 0;
		for(int i=0;i<pieces.size();i++) {
			Lettre p = pieces.get(i);
			if(c == p.ch) {
				cpt +=1;
			}
		}
		return cpt;
	}
	
	
	
	public boolean verif_j1(char c) {
		for(int i=0;i<7;i++) {
			if(c == j1.get(i).ch) {
				pivot = i;
				return true;
			}
		}
		
		return false;
	}
	
	
	
	//--------------------Fonction du jeu----------------------------------
	
	public void first_tirage() {
			
			for(int i=0; i<7;i++) {
				int ran = r.nextInt(pieces.size());
				j1.add(pieces.get(ran));
				pieces.remove(ran);
			}
			
			System.out.println(j1);
		}
	
	public void pioche(char c) {
		int out = r.nextInt(pieces.size());
		if(verif_j1(c)) {
			pieces.add(j1.get(pivot));
			j1.remove(pivot);
			j1.add(pieces.get(out));
		}
		
		System.out.println(j1);
	}
	
	public void lettre_poser(char c) {
		s.remove(c);
		}
		
	
	
	
	public static void main(String[] args) {
		Modele m = new Modele();
		System.out.println(m.get_char(0));
		System.out.println(m.get_remainNbs('a'));
		m.lettre_poser('a');
		System.out.println(m.get_remainNbs('a'));
		m.first_tirage();
		m.pioche('n');
		System.out.println(m.pieces);
		
		
	}

}
