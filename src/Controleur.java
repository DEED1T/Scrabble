import java.awt.Font;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Controleur {
	
	private Modele modl;
	
	public Controleur(Modele model){
		this.modl  = model;
	}
	
	public void modif_scene(Scene scene, Scene jscene) throws ExceptionDisposition {
		jscene.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				int x = (int)e.getX();
				int y = (int)e.getY();
				int colonne = (int) (x/Vue.TILE_WIDTH);
				int ligne = (int) (x / (30*Vue.TILE_HEIGHT));
				char lettre = modl.j1.get(colonne).ch;
				
				scene.setOnMouseClicked(new EventHandler<MouseEvent>(){

					@Override
					public void handle(MouseEvent event) {
						System.out.println("La lettre selectionne est : " + lettre);
						int x2 = (int)event.getX();
						int y2 = (int)event.getY();
						int col = (int) (Math.floor(x2 / Vue.TILE_WIDTH) + 1);
						int lig = (int) (Math.floor(y2 / Vue.TILE_HEIGHT) + 1);
						int id = modl.mod_plateau[lig - 1][col - 1];
						
						Group root = (Group)scene.getRoot();
						if(modl.plat_char[7][7] == '/' && lig-1 != 7 && col-1 != 7) {
							System.out.println("Vous devez commcencer à l'étoile");
						}
						//Random image_random = new Random();
						else if (modl.plat_char[7][7]  != '/' && modl.plat_char[lig-1][col-1] == '/' && voisins(modl.plat_char, lig-1, col-1) == true) {
							ImageView test = new ImageView();
							if(lettre != '*') {
								test = new ImageView("Scrabble_images/" + lettre + ".png");
							}
							else {
								test = new ImageView("Scrabble_images/Jocker.png");
							}
							modl.plat_char[lig-1][col-1] = lettre;
							try {
								modl.lettre_poser(lettre, lig-1, col-1);
							} catch (ExceptionDisposition e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							test.setFitWidth(Vue.TILE_WIDTH);
							test.setFitHeight(Vue.TILE_HEIGHT);
							test.setLayoutX( (col-1) * Vue.TILE_WIDTH );
							test.setLayoutY( (lig-1) * Vue.TILE_HEIGHT );
							root.getChildren().add(test);
						}
						else if(lig-1 == 7 && col - 1 == 7) {
							modl.plat_char[7][7] = lettre;
							try {
								modl.lettre_poser(lettre, 7, 7);
							} catch (ExceptionDisposition e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ImageView test = new ImageView();
							if(lettre != '*') {
								test = new ImageView("Scrabble_images/" + lettre + ".png");
							}
							else {
								test = new ImageView("Scrabble_images/Jocker.png");
							}
							test.setFitWidth(Vue.TILE_WIDTH);
							test.setFitHeight(Vue.TILE_HEIGHT);
							test.setLayoutX( (col-1) * Vue.TILE_WIDTH );
							test.setLayoutY( (lig-1) * Vue.TILE_HEIGHT );
							root.getChildren().add(test);
						}
					try {
						modl.mot_fini();
					} catch (ExceptionDisposition e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
			
				});
			}	
		});
		
	}
	
	public void pioche(Button bouton) {//Si on clique sur le bouton de pioche, appel de la fonction pioche
		bouton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if(e.getTarget().getClass() == Button.class) {
					modl.pioche();
				}
				
			}
			
		});
	}
	
	public boolean voisins(char plat_char[][], int lig, int col) {
		return plat_char[lig-1][col] != '/' || plat_char[lig][col-1] != '/' || plat_char[lig+1][col] != '/' || plat_char[lig][col+1] != '/';
	}

}
