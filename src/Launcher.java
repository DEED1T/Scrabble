import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Launcher extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Stage joueur = new Stage();
		
		//Creation Modele et Vue
		Modele modele = new Modele();
		modele.first_tirage();
		Vue vue = new Vue(modele);
		
		//Deux scènes : Main du Joueur et Map
		Scene scene = vue.getScene();
		Scene jscene = vue.getMainJoueurs();
		
		modele.addObserver(vue);
		
		Controleur ctrl = new Controleur(modele);
		
		stage.setScene(scene);
		Group root = (Group)scene.getRoot();
		
		//Ajout des boutons
		Button boutonPioche = vue.boutonPioche();
		Button bouton_mot_fini = vue.bouton_mot_fini();
		root.getChildren().add(boutonPioche);
		root.getChildren().add(bouton_mot_fini);
		
		//Ajout des controleurs
		new Controleur(modele).modif_scene(scene, jscene);
		new Controleur(modele).pioche(boutonPioche);
		new Controleur(modele).mot_fini(bouton_mot_fini, scene);
		
		stage.setResizable(false);
        stage.setTitle("Scrabble");
        stage.show();
        
        joueur.setScene(jscene);
        joueur.setTitle("Main des joueurs");
        joueur.show();
        
		exitApplication(stage);
	}
	
	private void exitApplication(Stage primaryStage) {
   	 
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {	
             public void handle(WindowEvent we) {
                 System.exit(0);
             }
         }); 	
	  }
}
