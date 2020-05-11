import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Feed {
	public JTextArea log;
	
	public Feed() {
		JFrame f = new JFrame();
		log = new JTextArea();
		f.setSize(200,250);
		f.add(log);
		f.setVisible(true);
	}
	
	public void prompt(String msg) {
		log.append(msg+"\n");
	}
}
