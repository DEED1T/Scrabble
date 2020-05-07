import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@SuppressWarnings("hiding")
public class Dictionnaire<E extends Comparable<String>> extends ArbreBinaire<String> {
	
	public Dictionnaire() {
		super();
		String dico1 = "src/Dico/dico_a-g.txt";
		String dico2 = "src/Dico/dico_h-z.txt";
		
		
		
	}
	
	public void inserer(String e) {
		if(this.est_vide()) {
			this.etiquette = e;
			this.fd = new ArbreBinaire<>();
			this.fg = new ArbreBinaire<>();
		}
		else {
			if(e.compareTo(this.etiquette)<0) {
				((Dictionnaire<String>)this.sag()).inserer(e);
			}
			else{
				((Dictionnaire<String>)this.sad()).inserer(e);
			}
		}
	}
	
	public boolean mot_valide(String e) {
		if(this.est_vide()) {
			return false;
		}
		else {
			if(e.compareTo(this.etiquette)==0) {
				return true;
			}
			else {
				if(e.compareTo(this.etiquette)<0) {
					((Dictionnaire<String>)this.sag()).mot_valide(e);
				}
				else{
					((Dictionnaire<String>)this.sad()).mot_valide(e);
				}
			}
		}
		
		return false;
		
	}
	
	public static void main(String[] args) {
		Dictionnaire<Comparable<String>> d = new Dictionnaire<Comparable<String>>();
		
		
		
	}
}
	

