public class Depart {
	
	int nbrJoueurs;
	Sac sac;
	Lettre[] player1 = new Lettre[7];
	Lettre[] player2 = new Lettre[7];
	
	public Depart() {
		this.nbrJoueurs = 2;
		this.sac = new Sac();
	}
	
	public void tirageAleatoire() {
		int positionLettrePiocheJ1 = 0;
		int positionLettrePiocheJ2 = 0;
		double rand_j1 = Math.random();
		double rand_j2 = Math.random();
		for(int i=0; i<7; i++) {
			positionLettrePiocheJ1 = (int)rand_j1 * this.sac.s_lettres.size();
			player1[i] = this.sac.s_lettres.get(i);
			this.sac.s_lettres.remove(positionLettrePiocheJ1);
			positionLettrePiocheJ2 = (int)rand_j2 * this.sac.s_lettres.size();
			player2[i] = this.sac.s_lettres.get(i);
			this.sac.s_lettres.remove(positionLettrePiocheJ2);
 		}
	}
	
	
}
