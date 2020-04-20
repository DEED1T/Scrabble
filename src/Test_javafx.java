import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Test_javafx extends Application {

	//Dimensions de la fenêtre
	static int MAP_WIDTH = 800;
	static int MAP_HEIGHT = 600;
	
	//Dimensions de chaque Tile (Ici, nos cases du Scrabble)
	static int TILE_WIDTH = MAP_WIDTH / 20;
	static int TILE_HEIGHT = MAP_HEIGHT / 20;
	
    @Override
    public void start(Stage stage) {

    	
        initUI(stage);
        stage.show();
    }

    private void initUI(Stage stage) {

        Group root = new Group();

        Scene scene = new Scene(root, MAP_WIDTH, MAP_HEIGHT);

        Label lbl = new Label("Simple JavaFX application.");
        lbl.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
        lbl.setLayoutX(MAP_WIDTH/2 - 100);
        lbl.setLayoutY(MAP_HEIGHT/2);
        
        root.getChildren().add(lbl);

        stage.setTitle("Simple application");
        stage.setScene(scene);
        
        lbl.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent mon_test) {
				modif_du_titre(lbl);
			}
        	
        });
        
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent recup_coord) {
				// TODO Auto-generated method stub
				double x = recup_coord.getX();
				double y = recup_coord.getY();
				
				int colonne = (int) (Math.floor(x / TILE_WIDTH) + 1);
				int ligne = (int) (Math.floor(y / TILE_HEIGHT) + 1);
				
				if(colonne > 0 && colonne <= MAP_WIDTH && ligne > 0 && ligne <= MAP_HEIGHT) {
					
					//Là je pourrais récupérer la position dans la matrice ->  id = plateau[ligne][colonne]
					System.out.println("ligne : " + ligne + " colonne : " + colonne);
				}
			}
        	
        });
        
        Button btn = new Button();
        btn.setLayoutX(200);
        btn.setLayoutY(400);
        btn.setText("Hello World");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                System.out.println("Hello World");
            }
        });
        
        lbl.setFocusTraversable(true); 
        lbl.setOnKeyPressed(keyEvent -> System.out.printf("Touche enfoncée : %s", keyEvent.getCode()).println());
        
        root.getChildren().add(btn);
        
        
    }
    
    public void modif_du_titre(Label lb) {
    	lb.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}