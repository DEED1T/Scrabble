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
		
		Modele modele = new Modele();
		modele.first_tirage();
		Vue vue = new Vue(modele);
		Scene scene = vue.getScene();
		Scene jscene = vue.getMainJoueurs();
		modele.addObserver(vue);
		//Controleur ctrl = new Controleur(modele);
		
		stage.setScene(scene);
		Group root = (Group)scene.getRoot();
		Button boutonPioche = vue.boutonPioche();
		root.getChildren().add(boutonPioche);
		new Controleur(modele).modif_scene(scene, jscene);
		new Controleur(modele).pioche(boutonPioche);
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
