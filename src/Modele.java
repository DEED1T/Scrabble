import java.util.ArrayList;
import java.util.Random;
import javafx.scene.chart.PieChartBuilder;
import javafx.scene.input.PickResult;

public class Modele {
	
	Sac s = new Sac();
	
	ArrayList<Lettre> pieces = s.get();
	ArrayList<Lettre> j1 = new ArrayList<Lettre>();
	Random r = new Random(10);
	
	private int pivot = 0; 
	public int vide_j1 = 7;
	
	private int plateau[][] = { {6,0,0,3,0,0,0,6,0,0,0,3,0,0,6},
								{0,5,0,0,0,4,0,0,0,4,0,0,0,5,0},
								{0,0,5,0,0,0,3,0,3,0,0,0,5,0,0},
								{3,0,0,5,0,0,0,3,0,0,0,5,0,0,3},
								{0,0,0,0,5,0,0,0,0,0,5,0,0,0,0},
								{0,4,0,0,0,4,0,0,0,4,0,0,0,4,0},
								{0,0,3,0,0,0,3,0,3,0,0,0,3,0,0},
								{6,0,0,3,0,0,0,7,0,0,0,3,0,0,6},
								{0,0,3,0,0,0,3,0,3,0,0,0,3,0,0},
								{0,4,0,0,0,4,0,0,0,4,0,0,0,4,0},
								{0,0,0,0,5,0,0,0,0,0,5,0,0,0,0},
								{3,0,0,5,0,0,0,3,0,0,0,5,0,0,3},
								{0,0,5,0,0,0,3,0,3,0,0,0,5,0,0},
								{0,5,0,0,0,4,0,0,0,4,0,0,0,5,0},
								{6,0,0,3,0,0,0,6,0,0,0,3,0,0,6}};
	
	//				   0         1              2            3            4           5        6        7
	private enum Id {VIDE,LETTRE_EN_COURS,LETTRE_POSER,LETTREDOUBLE,LETTRETRIPLE,MOTDOUBLE,MOTTRIPLE,CASEDEPART};
	
	public Modele() {}
	
	//--------------fonctions de suivis/v�rification----------------------
	
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
	
	public boolean first_turn() {
		for(int i=0;i<plateau.length;i++) {
			for(int j=0;j<plateau.length;j++) {
				if(Id.values()[plateau[i][j]] == Id.CASEDEPART){
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	//--------------------Fonction du jeu----------------------------------
	
	public void first_tirage() {
			
		for(int i=0; i<7;i++) {
			int ran = r.nextInt(pieces.size());
			j1.add(pieces.get(ran));
			pieces.remove(ran);
		}
		vide_j1 = 0;
		
		System.out.println(j1);
	}
	
	public void echange(char c) {
		int out = r.nextInt(pieces.size());
		if(verif_j1(c)) {
			pieces.add(j1.get(pivot));
			j1.remove(pivot);
			j1.add(pieces.get(out));
		}
		else {
			System.out.println("cette lettre ne t'appartient pas");
		}
		
		System.out.println(j1);
	}
	
	public void echangeAll() {
		
	}
	
	public void lettre_poser(char c) {
		if(verif_j1(c)) {
			j1.remove(pivot);
		}
		vide_j1 +=1;
		System.out.println(j1);
	}
	
	public void pioche() {
		for(int i=0;i<vide_j1;i++) {
			int out = r.nextInt(pieces.size());
			j1.add(pieces.get(out));
			pieces.remove(out);
		}
		System.out.println(j1);
	}
		
	
	public static void main(String[] args) {
		Modele m = new Modele();
		m.first_tirage();
		m.lettre_poser('i');
		m.lettre_poser('p');
		m.lettre_poser('d');
		m.lettre_poser('*');
		m.pioche();
		
		}

}