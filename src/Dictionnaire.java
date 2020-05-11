import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

public class Dictionnaire<E> extends TreeSet<String> {
	
	public File fichierout = new File("dico.xml");
	
	public Dictionnaire()  {
		super();
		String dico1 = "src/Dico/dico_a-g.txt";
		String dico2 = "src/Dico/dico_h-z.txt";
		
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(dico1));
			BufferedReader br2 = new BufferedReader(new FileReader(dico2));
			String lignebr1;
			String lignebr2;
			while(((lignebr1 = br1.readLine()) != null)){
				lignebr2 = br2.readLine();
				this.add(lignebr1);
				//enregistrer(lignebr1);
				if(lignebr2 != null) {
					this.add(lignebr2);
					//enregistrer(lignebr2);
				}
				
			}
			
			br1.close();
			br2.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	
	public void enregistrer(String s) {
		XMLEncoder encoder = null;
		
		try {
			FileOutputStream f_out = new FileOutputStream(fichierout);
			BufferedOutputStream b_out = new BufferedOutputStream(f_out);
			encoder = new XMLEncoder(b_out);
			encoder.writeObject(s);
			encoder.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(encoder != null) {
			encoder.close();
		}
		
	}
	
	
	public static void main(String[] args) {
		Dictionnaire<String> d = new Dictionnaire<String>();		
		System.out.println(d.contains("ses"));
	}
	

}