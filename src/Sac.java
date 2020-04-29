import java.util.ArrayList;


public class Sac {
	
	public Object[] o = {}; 
	public ArrayList<Lettre> s_lettres = new ArrayList<Lettre>();
	public ArrayList<Lettre> a = new ArrayList<Lettre>();
	
	public Sac() {
		create('a',1,9,s_lettres);
		create('b',3,2,s_lettres);
		create('c',3,2,s_lettres);
		create('d',2,3,s_lettres);
		create('e',1,15,s_lettres);
		create('f',4,2,s_lettres);
		create('g',2,2,s_lettres);
		create('h',4,2,s_lettres);
		create('i',1,8,s_lettres);
		create('j',8,1,s_lettres);
		create('k',10,1,s_lettres);
		create('l',1,5,s_lettres);
		create('m',2,3,s_lettres);
		create('n',1,6,s_lettres);
		create('o',1,6,s_lettres);
		create('p',3,2,s_lettres);
		create('q',8,1,s_lettres);
		create('r',1,6,s_lettres);
		create('s',1,6,s_lettres);
		create('t',1,6,s_lettres);
		create('u',1,6,s_lettres);
		create('v',4,6,s_lettres);
		create('w',10,1,s_lettres);
		create('x',10,1,s_lettres);
		create('y',10,1,s_lettres);
		create('z',10,1,s_lettres);
		create('*',0,2,s_lettres);
		}
	
	public void create(char c,int pt,int nb,ArrayList<Lettre> s){
		for(int i = 0; i<nb; i++) {
			Lettre ln = new Lettre(c,pt);
			s.add(ln);
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
	
	public ArrayList<Lettre> alphabet(){
		create('a',1,1,a);
		create('b',3,1,a);
		create('c',3,1,a);
		create('d',2,1,a);
		create('e',1,1,a);
		create('f',4,1,a);
		create('g',2,1,a);
		create('h',4,1,a);
		create('i',1,1,a);
		create('j',8,1,a);
		create('k',10,1,a);
		create('l',1,1,a);
		create('m',2,1,a);
		create('n',1,1,a);
		create('o',1,1,a);
		create('p',3,1,a);
		create('q',8,1,a);
		create('r',1,1,a);
		create('s',1,1,a);
		create('t',1,1,a);
		create('u',1,1,a);
		create('v',4,1,a);
		create('w',10,1,a);
		create('x',10,1,a);
		create('y',10,1,a);
		create('z',10,1,a);
		create('*',0,1,a);
		return a;
	}
	
	
}
