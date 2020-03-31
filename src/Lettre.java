public class Lettre{
	
	public char ch;
	public int pts;
	public int nbrs;
	//public static char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}; 
	
	public Lettre(char c, int p) {
		this.ch = c;
		this.pts = p;
		
	}
	
	@Override
	public String toString() {
		return this.ch +" "+ this.pts;
	}
	
	public int get_P() {
		return this.pts;
	}
	
	
}
