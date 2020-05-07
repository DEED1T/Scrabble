import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@SuppressWarnings("hiding")
public class Dictionnaire<E extends Comparable<String>> extends ArbreBinaire<String> {
	
	public Dictionnaire()  {
		super();
		String dico1 = "src/Dico/dico_a-g.txt";
		String dico2 = "src/Dico/dico_h-z.txt";
		
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(dico1));
			BufferedReader br2 = new BufferedReader(new FileReader(dico2));
			String lignebr1;
			String lignebr2;
			while(((lignebr1 = br1.readLine()) != null)&&((lignebr2 = br2.readLine()) != null)) {
				this.inserer((String) lignebr1);
				this.inserer((String) lignebr2);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		Dictionnaire<String> d = new Dictionnaire<String>();
		
		
		
	}
}
	

