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
	private boolean en_cours = false;
	public int vide_j1 = 7;
	private int first_i,first_j;
	private int longeur=0;
	
	static private int slots = 7;
	
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
	
	private char plat_char[][] = {	{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
									{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'}};
	
	//				   0         1              2            3            4           5        6        7
	private enum Id {VIDE,LETTRE_EN_COURS,LETTRE_POSER,LETTREDOUBLE,LETTRETRIPLE,MOTDOUBLE,MOTTRIPLE,CASEDEPART};
	
	public Modele() {}
	
	//--------------m�thodes d'utilit�-------------------------------------
	
	public void print_plateau() {
		System.out.println();
		for(int i=0;i<plateau.length;i++) {
			for(int j=0;j<plateau.length;j++) {
				System.out.print(plateau[j][i]+" ");
			}
			System.out.println();
		}
	}
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
		for(int i=0;i<j1.size();i++) {
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
					return true;
				}
			}
		}
		
		return false;
	}
	
	//--------------------Fonction du jeu----------------------------------
	
	public void first_tirage() {
			
		for(int i=0; i<slots;i++) {
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
		while(!(j1.isEmpty())) {
			pieces.add(j1.get(0));
			j1.remove(0);
			}
		first_tirage();
	}
	
	public void lettre_poser(char c, int ind, int jnd) {
		
		if(!en_cours) {
			plateau[ind][jnd] = 1;
			en_cours = true;
			first_i=ind;
			first_j=jnd;
			}
		else {
			if(ind == first_i || jnd == first_j) {
				plateau[ind][jnd] = 1;
			}
			else {
				System.out.println("placer a coter de la derni�re lettre poser");
			}
		}
		
		if(!(first_turn())) {
		if(verif_j1(c)) {
			j1.remove(pivot);
		}
		vide_j1 +=1;
		longeur +=1;
		System.out.println(j1);
		}
		else {
			System.out.println("il faut jouer au milieu");
		}
		
	}
	
	public void mot_fini() {
		int cas = 0;
		boolean mot_juste = false;
		
		while(!mot_juste && !(cas>=4)) {
			
			switch(cas) {
				case 0 :
					for(int i=first_j;i<first_j+longeur;i++) {
						
						if(plateau[first_i][i]!=1) {
							cas+=1;
							break;
						}
					}
					if(cas==0) {
						mot_juste = true;
					}
					break;
					
					
				case 1 :
					for(int i=first_j;i>first_j-longeur;i--) {
						
						if(plateau[first_i][i]!=1) {
							cas+=1;
							break;
						}
					}
					if(cas==1) {
						mot_juste = true;
					}
					break;
					
					
				case 2 : 
					for(int i=first_i;i<first_i+longeur;i++) {
						if(plateau[i][first_j]!=1) {
							cas+=1;
							break;
						}
					}
					if(cas==2) {
						mot_juste = true;
					}
					break;
					
					
				case 3 :
					for(int i=first_i;i>first_i-longeur;i--) {
						
						if(plateau[i][first_j]!=1) {
							cas+=1;
							break;
						}
					}
					if(cas==3) {
						mot_juste = true;
					}
					break;
					
					
					
			}
			
		}
		
		if(mot_juste) {
			
			for(int i=0;i<plateau.length;i++) {
				for(int j=0;j<plateau.length;j++) {
					if(plateau[i][j]==1) {
						
						plateau[i][j]=2;
					}
				}
			}
			
			en_cours = false;
			first_i = 0;
			first_j = 0;
		}
		else {
			System.out.println("mot mal dispos�");
		}
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
		m.lettre_poser('i',7,7);
		m.lettre_poser('j',7, 6);
		m.lettre_poser('k', 7, 5);
		m.lettre_poser('k', 7, 4);
		
		m.mot_fini();
		m.print_plateau();
		
		
		}

}
