import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.function.LongUnaryOperator;
import javafx.scene.chart.PieChartBuilder;
import javafx.scene.input.PickResult;


public class Modele extends Observable{
	
	Sac s = new Sac();
	Dictionnaire<String> d = new Dictionnaire<String>();
	ArrayList<Lettre> pieces = s.get();
	ArrayList<Lettre> alphabet = s.alphabet();
	ArrayList<Lettre> j1 = new ArrayList<Lettre>();
	ArrayList<Lettre> pose_courante = new ArrayList<Lettre>();
	ArrayList<Character> main_courante = new ArrayList<Character>();
	
	Random r = new Random(127);
	
	private enum Id {VIDE,LETTRE_EN_COURS,LETTRE_POSER,LETTREDOUBLE,LETTRETRIPLE,MOTDOUBLE,MOTTRIPLE,CASEDEPART};
	private int pivot = 0; 
	private boolean en_cours = false;
	private boolean disposition_mot = false;
	public int vide_j1 = 7;
	public int vide_j2 = 7;
	private int first_i,first_j;
	private int longeur=0;
	public int score_j1 = 0;
	public int score_j2 = 0;
	public int score_mot = 0;
	private Id dbtp = Id.VIDE;
	public int round = 0;
	
	
	static private int slots = 7;
	static int mod_plateau[][] = { {6,0,0,3,0,0,0,6,0,0,0,3,0,0,6},
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

	public int plateau[][] = { {6,0,0,3,0,0,0,6,0,0,0,3,0,0,6},
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
	
	public char plat_char[][] = {	{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
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
				System.out.print(plateau[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public void print_charplat() {
		System.out.println();
		for(int i=0;i<plateau.length;i++) {
			for(int j=0;j<plateau.length;j++) {
				System.out.print(plat_char[i][j]+" ");
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
	
	public boolean verif_j(char c) {
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
	
	public Lettre get_Lettre(char c) {
		for(int i=0;i<alphabet.size();i++) {
			if(c == alphabet.get(i).ch) {
				return alphabet.get(i);
				}
		}
		return null;
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
	}
	
	public void echange(char c) {
		int out = r.nextInt(pieces.size());
		if(verif_j(c)) {
			pieces.add(j1.get(pivot));
			j1.remove(pivot);
			j1.add(pieces.get(out));
		}
		else {
			System.out.println("cette lettre ne t'appartient pas");
		}
		
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
			if(verif_j(c)) {
				pose_courante.add(j1.get(pivot));
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
		disposition_mot = false;
		boolean lettre_unique = longeur == 1 && round !=1;
		int cas = 0;
		int long_base = longeur;
		boolean dis = false;
		
		if(lettre_unique) {longeur+=1;}
		
		while(!disposition_mot && !(cas>=4)) {
			switch(cas) {
				case 0 :
					if(first_j+1>=plateau[0].length) {
						cas+=1;
						break;
						}
					for(int i=first_j;i<first_j+longeur;i++) {
						if(plateau[first_i][i]==1) {
							main_courante.add(plat_char[first_i][i]);
						}
						if(plateau[first_i][i]==2) {
							score_mot+=get_LettrePts(plat_char[first_i][i]);
							main_courante.add(plat_char[first_i][i]);
							if(!lettre_unique) {
								longeur+=1;
							}
						}
						if(plateau[first_i][i]!=1 && plateau[first_i][i]!=2 ) {
							cas+=1;
							main_courante.clear();
							break;
						}
					}
					if(cas==0) {
						disposition_mot = true;
					}
					break;
				case 1 :
					if(first_j<=0) {
						cas+=1;
						break;
						}
					for(int i=first_j;i>first_j-longeur;i--) {
						if(plateau[first_i][i]==1) {
							main_courante.add(plat_char[first_i][i]);
						}
						if(plateau[first_i][i]==2) {
							score_mot+=get_LettrePts(plat_char[first_i][i]);
							main_courante.add(plat_char[first_i][i]);
							if(!lettre_unique) {
							longeur+=1;
							}
						}
						if(plateau[first_i][i]!=1 && plateau[first_i][i]!=2) {
							cas+=1;
							main_courante.clear();
							break;
						}
					}
					if(cas==1) {
						disposition_mot = true;
					}
					break;
				case 2 : 
					if(first_i+1>=plateau.length) {
						cas+=1;
						break;
					}
					for(int i=first_i;i<first_i+longeur;i++) {
						if(plateau[i][first_j]==1) {
							main_courante.add(plat_char[i][first_j]);
						}
						if(plateau[i][first_j]==2) {
							score_mot+=get_LettrePts(plat_char[i][first_j]);
							main_courante.add(plat_char[i][first_j]);
							if(!lettre_unique) {
								longeur+=1;
							}
						}
						if(plateau[i][first_j]!=1 && plateau[i][first_j]!=2) {
							cas+=1;
							main_courante.clear();
							break;
						}
					}
					if(cas==2) {
						disposition_mot = true;
					}
					break;
				case 3 :
					if(first_i<=0) {
						cas+=1;
						break;
					}
					for(int i=first_i;i>first_i-longeur;i--) {
						if(plateau[i][first_j]==1) {
							main_courante.add(plat_char[i][first_j]);
						}
						if(plateau[i][first_j]==2) {
							score_mot+=get_LettrePts(plat_char[i][first_j]);
							main_courante.add(plat_char[i][first_j]);
							if(!lettre_unique) {
								longeur+=1;
							}
						}
						if(plateau[i][first_j]!=1 && plateau[i][first_j]!=2) {
							cas+=1;
							main_courante.clear();
							break;
						}
					}
					if(cas==3) {
						disposition_mot = true;
					}
					break;
			}
		}
		
		
		
		if(round !=1) {
			dis = (long_base == longeur);
		}
		
		
		
		
		if(disposition_mot && !dis) {
			if(mot_valide(main_courante)) {
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
				score_j1+=score_mot;
			}
			else {
				reset();
				System.out.println("mot n'existe pas");
				
			}
		}
		else {
			for(int i=0;i<pose_courante.size();i++){
				j1.add(pose_courante.get(i));
			}
			reset();
			throw new ExceptionDisposition();
			
		}
		
		
		
		pose_courante.clear();
		main_courante.clear();
		en_cours = false;
		first_i = 0;
		first_j = 0;
		longeur = 0;
		round+=1;
		score_mot = 0;
		dbtp = Id.VIDE;
		
		
	}
	
	public boolean mot_valide(ArrayList<Character> l) {
		String md = "";
		String mg = "";
		
		for(int i=0;i<l.size();i++) {
			mg+=l.get(i);
		}
		for(int i=l.size()-1;i>=0;i--) {
			md+=l.get(i);
		}
		
		return d.contains(mg) || d.contains(md);
	
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
		for(int i=0;i<plat_char.length;i++) {
			for(int j=0;j<plat_char.length;j++) {
				plat_char[i][j] = '/';
			}
		}
		
		pose_courante.clear();
		main_courante.clear();
		j1.clear();
		en_cours = false;
		first_i = 0;
		first_j = 0;
		longeur = 0;
		round=0;
		score_mot = 0;
		score_j1 = 0;
		score_j2 = 0;
		pivot = 0;
		dbtp = Id.VIDE;
		
		s = new Sac();
		pieces = s.get();
		
		
		
	}
	
	
	public void pioche() {
		if(disposition_mot) {
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
		
	}
	
	public static void main(String[] args) throws ExceptionDisposition {
		Modele m = new Modele();
		m.first_tirage();
		m.echange('c');
		m.echange('*');
		m.lettre_poser('a', 7, 7);
		m.lettre_poser('m', 7, 6);
		m.lettre_poser('i', 7, 5);
		m.mot_fini();
		m.pioche();
		m.lettre_poser('s', 6, 7);
		m.lettre_poser('t', 8, 7);
		m.mot_fini();
		//System.out.println(m.pieces.size());
		//m.resetAll();
		//m.first_tirage();
		
		
		
	
		m.print_plateau();
		System.out.println();
		m.print_charplat();
		
		
		}

}
