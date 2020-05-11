import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

public class Dictionnaire<E> extends TreeSet<String> {
	
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
				if(lignebr2 != null) {
					this.add(lignebr2);
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	
	
	public static void main(String[] args) {
		Dictionnaire<String> d = new Dictionnaire<String>();		
		System.out.println(d.contains("ses"));
	}
	

}