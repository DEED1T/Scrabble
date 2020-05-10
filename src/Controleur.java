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
				if(e.getTarget().getClass() == ImageView.class) {
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
							int col = (int) (Math.floor(x2 / Vue.TILE_WIDTH));
							int lig = (int) (Math.floor(y2 / Vue.TILE_HEIGHT));
							int id = modl.mod_plateau[lig][col];
							
							Group root = (Group)scene.getRoot();
							try {
								modl.lettre_poser(lettre, lig, col);
								Image image_lettre;
								if(modl.plat_char[7][7] == '/' && (lig != 7 && col !=7)) {
									System.out.println("Vous devez débuter au centre");
								}
								else if(modl.plat_char[7][7] != '/') {
									if(lettre != '*') {
										image_lettre = new Image("Scrabble_images/" + lettre +".png");
									}
									else {
										image_lettre = new Image("Scrabble_images/Jocker.png");
									}
									if(modl.plat_char[lig][col] == lettre) {
										ImageView image = new ImageView(image_lettre);
										image.setFitHeight(Vue.TILE_HEIGHT);
										image.setFitWidth(Vue.TILE_WIDTH);
										image.setLayoutX( col * Vue.TILE_WIDTH );
										image.setLayoutY( lig * Vue.TILE_HEIGHT);
										root.getChildren().add(image);
									}
									else {
										System.out.println("Erreur position");
									}
								}
								
							} catch (ExceptionDisposition e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
				
					});
				}
				
			}
		});
	}
	
	public void mot_fini(Button button) {
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				try {
					modl.mot_fini();
				} catch (ExceptionDisposition e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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