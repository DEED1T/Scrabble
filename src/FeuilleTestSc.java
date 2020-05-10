import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class FeuilleTestSc {
	
	Modele m = new Modele();
	
	
	@Before
	public void setUp() {
		System.out.println("____________Test-suivant________________");
		m.first_tirage();
	}
	
	@Test 
	public void test1() throws ExceptionDisposition {
		m.lettre_poser('p', 7, 7);
		m.lettre_poser('x', 7, 6);
		m.lettre_poser('d', 7, 5);
		m.lettre_poser('n', 7, 4);
		m.mot_fini();
		assertEquals(m.j1.size(), 7-4);
	}
	
	@Test
	public void test2() throws ExceptionDisposition {
		m.lettre_poser('p', 7, 7);
		m.lettre_poser('x', 7, 6);
		m.lettre_poser('d', 7, 5);
		m.lettre_poser('n', 7, 4);
		m.mot_fini();
		m.pioche();
		m.lettre_poser('j', 5, 5);
		m.lettre_poser('z', 6, 5);
		m.lettre_poser('i', 8, 5);
		m.mot_fini();
		assertEquals(m.score_j1, 53);
	}
	
	@Test ( expected = ExceptionDisposition.class)
	public void test3() throws ExceptionDisposition {
		m.lettre_poser('p', 7, 7);
		m.lettre_poser('x', 7, 6);
		m.lettre_poser('d', 7, 5);
		m.lettre_poser('n', 7, 4);
		m.mot_fini();
		m.pioche();
		m.lettre_poser('j', 5, 5);
		m.lettre_poser('z', 6, 5);
		m.lettre_poser('i', 8, 5);
		m.mot_fini();
		m.pioche();
		m.lettre_poser('u', 3, 2);
		m.lettre_poser('m', 4, 2);
		m.lettre_poser('r', 5, 2);
		m.lettre_poser('e', 6, 2);
		m.mot_fini();
	}
	
	
	

}
