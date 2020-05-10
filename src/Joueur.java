import java.util.ArrayList;

public class Joueur {
	public ArrayList<Lettre> main = new ArrayList<Lettre>();
	public int score = 0;
	public int vide = 7;
	
	public Joueur() {}
	
	public void reset() {
		main.clear();
		score = 0;
		vide = 7;
	}
	
}
