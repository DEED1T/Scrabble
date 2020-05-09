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
	
	public void modif_scene(Scene scene, Scene jscene) {
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
						if(modl.plat_char[7][7] == '/' && lig-1 == col-1 && lig-1 == 7) {
							try {
								modl.lettre_poser(lettre, lig-1, col-1);
							} catch (ExceptionDisposition e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Image image = new Image("Scrabble_images/" + lettre + ".png");
							ImageView imageview = new ImageView(image);
							imageview.setFitHeight(Vue.TILE_HEIGHT);
							imageview.setFitWidth(Vue.TILE_WIDTH);
							imageview.setLayoutX( (col-1) * Vue.TILE_WIDTH );
							imageview.setLayoutY((lig-1) * Vue.TILE_HEIGHT);
							Group root = (Group)scene.getRoot();
							root.getChildren().add(imageview);
						}
						else if(modl.plat_char[lig-1][col-1] == '/' && modl.plat_char[7][7] != '/') {
							try {
								modl.lettre_poser(lettre, lig-1, col-1);
							} catch (ExceptionDisposition e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Image image = new Image("Scrabble_images/" + lettre + ".png");
							ImageView imageview = new ImageView(image);
							imageview.setFitHeight(Vue.TILE_HEIGHT);
							imageview.setFitWidth(Vue.TILE_WIDTH);
							imageview.setLayoutX( (col-1) * Vue.TILE_WIDTH );
							imageview.setLayoutY((lig-1) * Vue.TILE_HEIGHT);
							Group root = (Group)scene.getRoot();
							root.getChildren().add(imageview);
						}
						else if(modl.plat_char[7][7] == '/' && lig-1 == 7 && col-1 == 7) {
							System.out.println("Vous devez débuter à la position de départ");
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

}
