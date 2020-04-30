import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.Random;

public class Test_affichage extends Application{
	
	static int screenWidth = (int) Screen.getPrimary().getBounds().getWidth(); 
    static int screenHeight = (int) Screen.getPrimary().getBounds().getHeight(); 
	
	
	//Dimensions de la fenêtre
	static int MAP_WIDTH = 15;
	static int MAP_HEIGHT = 15;
	
	//Dimensions de chaque Tile (Ici, nos cases du Scrabble)
	static int TILE_WIDTH = (int) ((screenHeight * 0.9) / 15);
	static int TILE_HEIGHT = (int) ((screenHeight * 0.9) / 15);
	
	public static char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}; 

	
	static private int mod_plateau[][] = { 
			{6,0,0,3,0,0,0,6,0,0,0,3,0,0,6},
			{0,5,0,0,0,4,0,0,0,4,0,0,0,5,0},
			{0,0,5,0,0,0,3,0,3,0,0,0,5,0,0},
			{3,0,0,5,0,0,0,3,0,0,0,5,0,0,3},
			{0,0,0,0,5,0,0,0,0,0,5,0,0,0,0},
			{0,4,0,0,0,4,0,0,0,4,0,0,0,4,0},
			{0,0,3,0,0,0,3,0,3,0,0,0,3,0,0},
			{6,0,0,3,0,0,0,7,0,0,0,3,0,0,6},
			{0,0,3,0,0,0,3,0,3,0,0,0,3,0,0},
			{0,4,0,0,0,4,0,0,0,4,0,0,0,4,0},
			{0,0,0,0,5,0,0,0,0,0,5,0,0,0,0},
			{3,0,0,5,0,0,0,3,0,0,0,5,0,0,3},
			{0,0,5,0,0,0,3,0,3,0,0,0,5,0,0},
			{0,5,0,0,0,4,0,0,0,4,0,0,0,5,0},
			{6,0,0,3,0,0,0,6,0,0,0,3,0,0,6}};

	public static void main(String[] args) {
        Application.launch(Test_affichage.class, args);
        System.out.println(TILE_WIDTH + " " + TILE_HEIGHT);
        System.out.println(screenWidth + " " + screenHeight);
    }
	
	@Override
	public void start(Stage stage)  {
		
		
		Group root = new Group();
		
		Scene scene = new Scene(root, screenHeight * 0.9, screenHeight*0.9, Color.LIGHTBLUE);
		
		stage.setScene(scene);
		
		Image case_vide = new Image("Scrabble_images/Case_vide.png");
		Image jocker = new Image("Scrabble_images/Jocker.png");
		Image mot_x2 = new Image("Scrabble_images/Mot_x2.png");
		Image mot_x3 = new Image("Scrabble_images/Mot_x3.png");
		Image lettre_x2 = new Image("Scrabble_images/Lettre_x2.png");
		Image lettre_x3 = new Image("Scrabble_images/Lettre_x3.png");
		Image case_depart = new Image("Scrabble_images/Depart.png");
		
		Image TabImage[] = {case_vide, null, null, lettre_x2, lettre_x3, mot_x2, mot_x3, case_depart};
		Image TabImageLettres[] = new Image[alphabet.length];
		
		for(int image = 0; image < alphabet.length; image++) {
			Image image_lettre = new Image("Scrabble_images/" + alphabet[image] + ".png");
			TabImageLettres[image] = image_lettre;
		}
		
		
		for(int ligne = 0; ligne < MAP_HEIGHT; ligne++) {
			for(int colonne = 0; colonne < MAP_WIDTH; colonne++) {
				int id = mod_plateau[ligne][colonne];
				Image texture = TabImage[id];
				if(texture != null) {
					ImageView image = new ImageView(texture);
					image.setFitHeight(TILE_HEIGHT);
					image.setFitWidth(TILE_WIDTH);
					image.setLayoutX( colonne * TILE_WIDTH );
					image.setLayoutY( ligne * TILE_HEIGHT );
					root.getChildren().add(image);
				}
			}
			
		}
		
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent recup_coord) {
				// TODO Auto-generated method stub
				double x = recup_coord.getX();
				double y = recup_coord.getY();
				
				int colonne = (int) (Math.floor(x / TILE_WIDTH) + 1);
				int ligne = (int) (Math.floor(y / TILE_HEIGHT) + 1);
				
				if(colonne >= 0 && colonne <= MAP_WIDTH && ligne >= 0 && ligne <= MAP_HEIGHT) {
					
					//Là je pourrais récupérer la position dans la matrice ->  id = plateau[ligne][colonne]
					System.out.println("ligne : " + ligne + " colonne : " + colonne);
					
					int id = mod_plateau[ligne - 1][colonne - 1];
					System.out.println("Case ID : " + id);
					Random image_random = new Random();
					ImageView test = new ImageView(TabImageLettres[image_random.nextInt(TabImageLettres.length)]);
					test.setFitWidth(TILE_WIDTH);
					test.setFitHeight(TILE_HEIGHT);
					test.setLayoutX( (colonne-1) * TILE_WIDTH );
					test.setLayoutY( (ligne-1) * TILE_HEIGHT );
					root.getChildren().add(test);
				}
			}
        	
        });
		
        stage.show();
	}
	
	

}
