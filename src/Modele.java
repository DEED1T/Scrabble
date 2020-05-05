import java.util.ArrayList;
import java.util.Random;
import java.util.function.LongUnaryOperator;

import javafx.scene.chart.PieChartBuilder;
import javafx.scene.input.PickResult;

public class Modele {
	
	Sac s = new Sac();
	ArrayList<Lettre> pieces = s.get();
	ArrayList<Lettre> alphabet = s.alphabet();
	ArrayList<Lettre> j1 = new ArrayList<Lettre>();
	ArrayList<Lettre> mot_en_cours = new ArrayList<Lettre>();
	Random r = new Random(10);
	
	private enum Id {VIDE,LETTRE_EN_COURS,LETTRE_POSER,LETTREDOUBLE,LETTRETRIPLE,MOTDOUBLE,MOTTRIPLE,CASEDEPART};
	
	private int pivot = 0; 
	private boolean en_cours = false;
	private boolean sac_vide = false;
	private boolean mot_juste = false;
	public int vide_j1 = 7;
	private int first_i,first_j;
	private int longeur=0;
	public int score_total = 0;
	public int score_mot = 0;
	private Id dbtp = Id.VIDE;
	private int round = 0;
	
	static private int slots = 7;
	static private int mod_plateau[][] = { {6,0,0,3,0,0,0,6,0,0,0,3,0,0,6},
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
	
	public void print_charplat() {
		System.out.println();
		for(int i=0;i<plateau.length;i++) {
			for(int j=0;j<plateau.length;j++) {
				System.out.print(plat_char[j][i]+" ");
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
	
	public int get_LettrePts(char c) {
		for(int i=0;i<alphabet.size();i++) {
			if(c == alphabet.get(i).ch) {
				return alphabet.get(i).pts;
				}
		}
		return 0;
	}
	
	public boolean first_turn() {
		return Id.values()[plateau[7][7]] == Id.CASEDEPART;
	}
	
	//--------------------Fonction du jeu----------------------------------
	
	public void first_tirage() {
		round+=1;
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
	
	public int scoreCase(int ind, int jnd) {
		int m = 1;
		if(plateau[ind][jnd] == 3) {
			m = 2;
		}
		else if(plateau[ind][jnd] == 4) {
			m = 3;
		}
		else if(plateau[ind][jnd] == 5) {
			dbtp = Id.MOTDOUBLE;
		}
		else if(plateau[ind][jnd] == 6) {
			dbtp = Id.MOTTRIPLE;
		}
		
		return m;
	}
	
	public void lettre_poser(char c, int ind, int jnd) throws ExceptionDisposition {
		int score_lettre = get_LettrePts(c);
		
		if(!en_cours) {
			score_mot+=score_lettre*scoreCase(ind, jnd);
			plateau[ind][jnd] = 1;
			plat_char[ind][jnd] = c;
			en_cours = true;
			first_i=ind;
			first_j=jnd;
			
			}
		else {
			if(ind == first_i || jnd == first_j) {
				score_mot+=score_lettre*scoreCase(ind, jnd);
				plateau[ind][jnd] = 1;
				plat_char[ind][jnd] = c;
				
			}
			else {
				System.out.println("placer a coter de la derni�re lettre poser");
				reset();
			}
		}
		
		if(!(first_turn()) || (plateau[ind+1][jnd]!=2 && plateau[ind-1][jnd]!=2 && plateau[ind][jnd+1]!=2 && plateau[ind][jnd-1]!=2)) {
			if(verif_j1(c)) {
				mot_en_cours.add(j1.get(pivot));
				j1.remove(pivot);
			}
			vide_j1 +=1;
			longeur +=1;
			System.out.println(j1);
		}
		else {
			reset();
			throw new ExceptionDisposition();
		}
		
	}
	
	public void mot_fini() throws ExceptionDisposition {
		int cas = 0;
		mot_juste = false;
		int long_base = longeur;
		boolean dis = false;
		
		while(!mot_juste && !(cas>=4)) {
			switch(cas) {
				case 0 :
					if(first_j+1>=plateau[0].length) {
						cas+=1;
						break;
						}
					for(int i=first_j;i<first_j+longeur;i++) {
						if(plateau[first_i][i]==2) {
							score_mot+=get_LettrePts(plat_char[first_i][i]);
							longeur+=1;
						}
						if(plateau[first_i][i]!=1 && plateau[first_i][i]!=2 ) {
							cas+=1;
							break;
						}
					}
					if(cas==0) {
						mot_juste = true;
					}
					break;
				case 1 :
					if(first_j<=0) {
						cas+=1;
						break;
						}
					for(int i=first_j;i>first_j-longeur;i--) {
						
						if(plateau[first_i][i]==2) {
							score_mot+=get_LettrePts(plat_char[first_i][i]);
							longeur+=1;
						}
						if(plateau[first_i][i]!=1 && plateau[first_i][i]!=2) {
							cas+=1;
							break;
						}
					}
					if(cas==1) {
						mot_juste = true;
					}
					break;
				case 2 : 
					if(first_i+1>=plateau.length) {
						cas+=1;
						break;
					}
					for(int i=first_i;i<first_i+longeur;i++) {
						if(plateau[i][first_j]==2) {
							score_mot+=get_LettrePts(plat_char[i][first_j]);
							longeur+=1;
						}
						if(plateau[i][first_j]!=1 && plateau[i][first_j]!=2) {
							cas+=1;
							break;
						}
					}
					if(cas==2) {
						mot_juste = true;
					}
					break;
				case 3 :
					if(first_i<=0) {
						cas+=1;
						break;
					}
					for(int i=first_i;i>first_i-longeur;i--) {
						if(plateau[i][first_j]==2) {
							score_mot+=get_LettrePts(plat_char[i][first_j]);
							longeur+=1;
						}
						if(plateau[i][first_j]!=1 && plateau[i][first_j]!=2) {
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
		if(round !=1) {
			dis = (long_base == longeur);
		}
		
		if(mot_juste && !dis) {
			
			for(int i=0;i<plateau.length;i++) {
				for(int j=0;j<plateau.length;j++) {
					if(plateau[i][j]==1) {
						plateau[i][j]=2;
					}
				}
			}
			
			if(dbtp == Id.MOTDOUBLE) {
				score_mot = score_mot*2;
				}
			else if(dbtp == Id.MOTTRIPLE) {
				score_mot = score_mot*3;
			}
			score_total+=score_mot;
		}
		else {
			for(int i=0;i<mot_en_cours.size();i++){
				j1.add(mot_en_cours.get(i));
			}
			reset();
			throw new ExceptionDisposition();
		}
		
		mot_en_cours.clear();
		en_cours = false;
		first_i = 0;
		first_j = 0;
		longeur = 0;
		round+=1;
		score_mot = 0;
		dbtp = Id.VIDE;
		System.out.println(j1);
		System.out.println("Score "+score_total);
	}
	
	public void reset(){
		for(int i=0;i<plateau.length;i++){
			for(int j=0;j<plateau.length;j++){
				if(plateau[i][j]==1){
					plateau[i][j] = mod_plateau[i][j];
					plat_char[i][j] = '/';
				}
			}
		}
		
	}

	public void resetAll(){
		plateau = mod_plateau;
		round = 0;
	}
	
	
	public void pioche() {
		if(mot_juste) {
			if(pieces.size()>= 7) {
				for(int i=0;i<vide_j1;i++) {
					int out = r.nextInt(pieces.size());
					j1.add(pieces.get(out));
					pieces.remove(out);
				}
			}
			else{
				if(pieces.size()==0) {
					System.out.println("plus de pieces !");
				}
				else {
					for(int i=0;i<pieces.size();i++) {
						int out = r.nextInt(pieces.size());
						j1.add(pieces.get(out));
						pieces.remove(out);
					}
					
				}
			}
		}
		vide_j1 = 0;
		System.out.println(j1);
	}
	
	public static void main(String[] args) throws ExceptionDisposition {
		Modele m = new Modele();
		m.first_tirage();
		m.lettre_poser('p', 7, 7);
		m.lettre_poser('x', 7, 6);
		m.lettre_poser('d', 7, 5);
		m.lettre_poser('n', 7, 4);
		m.mot_fini();
		m.pioche();
		m.lettre_poser('j', 5, 5);
		m.lettre_poser('z', 6, 5);
		m.lettre_poser('i', 8, 5);
		m.mot_fini();
		m.pioche();
		m.lettre_poser('l', 5, 4);
		m.lettre_poser('r', 5, 6);
		m.lettre_poser('n', 5, 7);
		m.mot_fini();
		m.pioche();
		m.lettre_poser('d', 4, 7);
		m.lettre_poser('e', 6, 7);
		m.lettre_poser('a', 8, 7);
		m.mot_fini();
		m.pioche();
		m.lettre_poser('u', 3, 2);
		m.lettre_poser('m', 4, 2);
		m.lettre_poser('r', 5, 2);
		m.lettre_poser('e', 6, 2);
		m.mot_fini();
		m.pioche();
		
		
		
		
	
		m.print_plateau();
		System.out.println();
		m.print_charplat();
		
		
		}

}
