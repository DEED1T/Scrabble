import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.function.LongUnaryOperator;
import javafx.scene.chart.PieChartBuilder;
import javafx.scene.input.PickResult;
import javafx.scene.web.PromptData;


public class Modele extends Observable{
	
	Feed feed = new Feed();
	Sac s = new Sac();
	Joueur j1 = new Joueur();
	Joueur j2 = new Joueur();
	public Joueur[] liste_j = {j2,j1};
	private String[] liste_j_noms = {"j2","j1"};
	Dictionnaire<String> d = new Dictionnaire<String>();
	ArrayList<Lettre> pieces = s.get();
	ArrayList<Lettre> alphabet = s.alphabet();
	ArrayList<Lettre> pose_courante = new ArrayList<Lettre>();
	ArrayList<Character> main_courante = new ArrayList<Character>();
	
	Random r = new Random(127);
	
	private enum Id {VIDE,LETTRE_EN_COURS,LETTRE_POSER,LETTREDOUBLE,LETTRETRIPLE,MOTDOUBLE,MOTTRIPLE,CASEDEPART};
	private int pivot = 0; 
	private boolean en_cours = false;
	private boolean disposition_mot = false;
	private int first_i,first_j;
	private int last_i,last_j;
	private int longeur=0;
	public int score_mot = 0;
	private Id dbtp = Id.VIDE;
	public int round = 0;
	public boolean leMotEstBon;
	
	
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
	
	//--------------mï¿½thodes d'utilitï¿½-------------------------------------
	
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
	
	//--------------fonctions de suivis/vï¿½rification----------------------
	
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
	
	public boolean verif_j(Joueur j, char c) {
		for(int i=0;i<j.main.size();i++) {
			if(c == j.main.get(i).ch) {
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
			j1.main.add(pieces.get(ran));
			pieces.remove(ran);
		}
		for(int i=0; i<slots;i++) {
			int ran = r.nextInt(pieces.size());
			j2.main.add(pieces.get(ran));
			pieces.remove(ran);
		}
		j1.vide = 0;
		j2.vide = 0;
		
		System.out.println("j1 "+j1.main);
		System.out.println("j2 "+j2.main);
		
		
		
		
	}
	
	public void echange(Joueur j, char c) {
		int out = r.nextInt(pieces.size());
		if(verif_j(j, c)) {
			pieces.add(j1.main.get(pivot));
			j.main.remove(pivot);
			j.main.add(pieces.get(out));
		}
		else {
			feed.prompt("cette lettre ne t'appartient pas");
		}
		
		
	}
	
	public void echangeAll(Joueur j) {
		while(!(j.main.isEmpty())) {
			pieces.add(j1.main.get(0));
			j1.main.remove(0);
			}
		j.vide = 7;
		pioche(j);
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
		int turn = round%2;
		
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
				
				last_i = ind;
				last_j = jnd;
				
			}
			else {
				feed.prompt("placer a coter de la derniere lettre poser");
				reset();
			}
		}
		
		if(!(first_turn()) || (plateau[ind+1][jnd]!=2 && plateau[ind-1][jnd]!=2 && plateau[ind][jnd+1]!=2 && plateau[ind][jnd-1]!=2)) {
			if(verif_j(liste_j[turn], c)) {
				pose_courante.add(liste_j[turn].main.get(pivot));
				liste_j[turn].main.remove(pivot);
			}
			liste_j[turn].vide +=1;
			longeur +=1;
			
			
		}
		else {
			reset();
			throw new ExceptionDisposition();
		}
		
	}
	
	public void mot_fini() throws ExceptionDisposition {
		disposition_mot = false;
		boolean lettre_unique = longeur == 1 && round !=1;
		boolean bord = false;
		int cas = 0;
		int long_base = longeur;
		boolean dis = false;
		int turn = round%2;
		char brd = 0;
		
		
		for(int i=first_i-1;i<=first_i;i++) {
			for(int j=first_j-1;j<=first_j;j++) {
				if(plateau[i][j]==2) {
					bord = true;
					brd = plat_char[i][j];
				}
			}
		}
		for(int i=last_i-1;i<=last_i;i++) {
			for(int j=last_j-1;j<=last_j;j++) {
				if(plateau[i][j]==2) {
					bord = true;
					brd = plat_char[i][j];
				}
			}
		}
		
		if(lettre_unique) {longeur+=1;}
		//if(bord) {longeur+=1;}
		
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
							bord = false;
							score_mot+=get_LettrePts(plat_char[first_i][i]);
							main_courante.add(plat_char[first_i][i]);
							if(!lettre_unique) {
								longeur+=1;
							}
						}
						if(plateau[first_i][i]!=1 && plateau[first_i][i]!=2 ) {
							cas+=1;
							main_courante.clear();
							longeur = long_base;
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
							bord = false;
							score_mot+=get_LettrePts(plat_char[first_i][i]);
							main_courante.add(plat_char[first_i][i]);
							if(!lettre_unique) {
							longeur+=1;
							}
						}
						if(plateau[first_i][i]!=1 && plateau[first_i][i]!=2) {
							cas+=1;
							main_courante.clear();
							longeur = long_base;
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
							bord = false;
							score_mot+=get_LettrePts(plat_char[i][first_j]);
							main_courante.add(plat_char[i][first_j]);
							if(!lettre_unique) {
								longeur+=1;
							}
						}
						if(plateau[i][first_j]!=1 && plateau[i][first_j]!=2) {
							cas+=1;
							main_courante.clear();
							longeur = long_base;
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
							bord = false;
							score_mot+=get_LettrePts(plat_char[i][first_j]);
							main_courante.add(plat_char[i][first_j]);
							if(!lettre_unique) {
								longeur+=1;
							}
						}
						if(plateau[i][first_j]!=1 && plateau[i][first_j]!=2) {
							cas+=1;
							main_courante.clear();
							longeur = long_base;
							break;
						}
					}
					if(cas==3) {
						disposition_mot = true;
					}
					break;
			}
		}
		
		if(bord) {
			main_courante.add(brd);
		}
		
		if(round !=1) {
			dis = (long_base == longeur);
		}
		System.out.println("longueur "+longeur);
		System.out.println("main courante "+ main_courante);
		
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
				liste_j[turn].score+=score_mot;
				next_turn();
			}
			else {
				for(int i=0;i<pose_courante.size();i++){
					liste_j[turn].main.add(pose_courante.get(i));
				}
				
				reset();
				feed.prompt("mot n'existe pas");
				
			}
		}
		else {
			for(int i=0;i<pose_courante.size();i++){
				liste_j[turn].main.add(pose_courante.get(i));
			}
			
			reset();
			feed.prompt("Mot mal disposer");
			
		}
		
		
		
		
		pose_courante.clear();
		main_courante.clear();
		en_cours = false;
		first_i = 0;
		first_j = 0;
		longeur = 0;
		//round+=1;
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
		
		if(d.contains(mg)) {feed.prompt(liste_j_noms[round%2]+" à jouer le mot '"+mg+"'");}
		if(d.contains(md)) {feed.prompt(liste_j_noms[round%2]+" à jouer le mot '"+md+"'");}
		
		return (d.contains(mg) || d.contains(md)); 
			
	
	
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
		en_cours = false;
		first_i = 0;
		first_j = 0;
		longeur = 0;
		round=0;
		score_mot = 0;
		pivot = 0;
		dbtp = Id.VIDE;
		
		for(Joueur j : liste_j) {
			j.reset();
		}
		
		s = new Sac();
		pieces = s.get();
		
		
		
	}
	
	
	public void next_turn() {
		feed.prompt("Le score de "+liste_j_noms[round%2]+" est de "+liste_j[round%2].score);
		round+=1;
		feed.prompt("à "+liste_j_noms[round%2]+" de jouer !");
	}
	
	public void pioche(Joueur j) {
		
		if(disposition_mot) {
			if(pieces.size()>= 7) {
				for(int i=0;i<j.vide;i++) {
					int out = r.nextInt(pieces.size());
					j.main.add(pieces.get(out));
					pieces.remove(out);
				}
			}
			else{
				if(pieces.size()==0) {
					feed.prompt("plus de pieces !");
				}
				else {
					for(int i=0;i<pieces.size();i++) {
						int out = r.nextInt(pieces.size());
						j.main.add(pieces.get(out));
						pieces.remove(out);
					}
					
				}
			}
		}
		
		feed.prompt("il reste "+s.s_lettres.size()+" pieces dans le sac");
		j.vide = 0;
		
		
		
	}
	
	public static void main(String[] args) throws ExceptionDisposition {
		Modele m = new Modele();
		m.first_tirage();
		m.echange(m.liste_j[m.round%2],'c');
		m.echange(m.liste_j[m.round%2],'*');
		m.lettre_poser('d', 7, 7);
		m.lettre_poser('a', 7, 6);
		m.lettre_poser('n', 7, 5);
		m.lettre_poser('s', 7, 4);
		m.mot_fini();
		m.pioche(m.liste_j[m.round%2]);
		m.next_turn();
		m.lettre_poser('e', 6, 4);
		m.lettre_poser('s', 5, 4);
		m.mot_fini();
		/*m.lettre_poser('s', 8, 5);
		m.lettre_poser('e', 6, 5);
		m.lettre_poser('i', 5, 5);
		m.lettre_poser('v', 4, 5);*/
		
		//m.mot_fini();
		//m.pioche(m.liste_j[m.round%2]);
		//System.out.println(m.pieces.size());
		//m.resetAll();
		//m.first_tirage();
		
		
		
	
		m.print_plateau();
		System.out.println();
		m.print_charplat();
		
		
		}

}
