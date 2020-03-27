import java.util.ArrayList;


public class Sac {
	
	public Object[] o = {}; 
	public ArrayList<Lettre> s_lettres = new ArrayList<Lettre>();
	
	public Sac() {
		create('a',1,9);
		create('b',3,2);
		create('c',3,2);
		create('d',2,3);
		create('e',1,15);
		create('f',4,2);
		create('g',2,2);
		create('h',4,2);
		create('i',1,8);
		create('j',8,1);
		create('k',10,1);
		create('l',1,5);
		create('m',2,3);
		create('n',1,6);
		create('o',1,6);
		create('p',3,2);
		create('q',8,1);
		create('r',1,6);
		create('s',1,6);
		create('t',1,6);
		create('u',1,6);
		create('v',4,6);
		create('w',10,1);
		create('x',10,1);
		create('y',10,1);
		create('z',10,1);
		create('*',0,2);
		}
	
	public void create(char c,int pt,int nb){
		for(int i = 0; i<nb; i++) {
			Lettre ln = new Lettre(c,pt);
			s_lettres.add(ln);
		}
	}
	
	
	public void remove(char c) {
		for(int i=0; i<s_lettres.size();i++) {
			Lettre p = s_lettres.get(i);
			if(p.ch == c){
				s_lettres.remove(i);
				break;
			}
		}
	}
	
	public ArrayList<Lettre> get(){
		return s_lettres;
	}
	
	
}
