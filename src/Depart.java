import java.util.Random;

public class Depart {
	
	int nbrJoueurs;
	Sac sac;
	Random rand_j1 = new Random();
	Random rand_j2 = new Random();
	Lettre[] player1 = new Lettre[7];
	Lettre[] player2 = new Lettre[7];
	
	public Depart() {
		this.nbrJoueurs = 2;
		this.sac = new Sac();
	}
	
	public void tirageAleatoire() {
		for(int i=0; i<7; i++) {
			rand_j1 = nextInt(this.sac.s_lettres.size());
			player1[i] = rand_j1;
			rand_j2 = nextInt(this.sac.s_lettres.size());
			player2[i] = rand_j2;
		}
	}
	
	
}
