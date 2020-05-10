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
	
	static boolean lettreValide;
	
	private Modele modl;
	
	public Controleur(Modele model){
		this.modl  = model;
	}
	
	public void modif_scene(Scene scene, Scene jscene) throws ExceptionDisposition {
		jscene.setOnMouseClicked(new EventHandler<MouseEvent>() {

			Group jroot = (Group)jscene.getRoot();

			@Override
			public void handle(MouseEvent e) {
				if(e.getTarget().getClass() == ImageView.class) {
					
					char tabsLettres[][] = new char[2][7]; //2 car on a 2 joueurs
					for(int i = 0; i < modl.j1.size(); i++) {
					    tabsLettres[0][i] = modl.j1.get(i).ch;
					}
					/*for(int i = 0; i < modl.j1.size(); i++) { En attente j2
					    tabsLettres[1][i] = modl.j2.get(i).ch;
					}*/
					
					int tourJ = 0; // temporaire, à rempalcer par le joueur à qui c'est le tour
					//int tourJ = tour % 2 ( -> nbJoueur)
					
					lettreValide = false;
					
					int x = (int)e.getX();
					int y = (int)e.getY();
					int colonne = (int) (x / Vue.TILE_WIDTH);
					int ligne   = (int) (y / (30 + Vue.TILE_HEIGHT));
					
					if(ligne == tourJ) { // si on sélectionne bien une lettre dans la main du joueur actuel
						lettreValide = true;
						unselect(ligne);
						selected(ligne, colonne);
					}
					else {
						System.out.println("Veuillez choisir une lettre dans votre main");
					}
					
					char lettre = modl.j1.get(colonne).ch;
					
					scene.setOnMouseClicked(new EventHandler<MouseEvent>(){
						
						@Override
						public void handle(MouseEvent event) {
							if(lettreValide) {
								boolean lettrePlacee = false;
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
									if(modl.plat_char[lig][col] == lettre) {
										if(lettre != '*') {
											image_lettre = new Image("Scrabble_images/" + lettre +".png");
										}
										else {
											image_lettre = new Image("Scrabble_images/Jocker.png");
										}
										ImageView image = new ImageView(image_lettre);
										image.setFitHeight(Vue.TILE_HEIGHT);
										image.setFitWidth(Vue.TILE_WIDTH);
										image.setLayoutX( col * Vue.TILE_WIDTH );
										image.setLayoutY( lig * Vue.TILE_HEIGHT);
										root.getChildren().add(image);
										lettrePlacee = true;
									}
									/*if(modl.plat_char[7][7] == '/' && (lig != 7 && col !=7)) {
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
											lettrePlacee = true;
										}
										else {
											System.out.println("Erreur position");
										}
									}*/
									if(lettrePlacee) {
										unselect(ligne);
										actu(ligne, colonne);
										lettreValide = false;
									}
									
								} catch (ExceptionDisposition e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						}
						
						// Cette fonction est utilisée pour actualisé la main du joueur après avoir placé une lettre
						private void actu(int ligne, int colonne) {
							tabsLettres[ligne][colonne] = ' ';
							spaceLast(tabsLettres[ligne]);
							
							
							for(int col = 0; col < 7; col++) {
								if(tabsLettres[ligne][col] != ' ') {
									System.out.println("la lettre que l'on affiche est:"+tabsLettres[ligne][col]);
									//afficher les lettres
									ImageView newLettre;
									if(tabsLettres[ligne][col] == '*') {
										newLettre = new ImageView("Scrabble_images/Jocker.png");
									}
									else {
										newLettre = new ImageView("Scrabble_images/" + tabsLettres[ligne][col] + ".png");
									}
									
									newLettre.setFitWidth(Vue.TILE_WIDTH);
									newLettre.setFitHeight(Vue.TILE_HEIGHT);
									newLettre.setLayoutX(col * Vue.TILE_WIDTH );
									newLettre.setLayoutY(30);
									jroot.getChildren().add(newLettre);
								}
								else {
									System.out.println("un rectangle");
									//rectangle pour couvrir les anciennes lettres
									Rectangle rectangle = new Rectangle(col * Vue.TILE_WIDTH, ligne*(Vue.TILE_HEIGHT + 30)+30, Vue.TILE_WIDTH, Vue.TILE_HEIGHT);
									jroot.getChildren().add(rectangle);
								}
							}
							
						}
						
						// Cette fonction permet de placer null à la fin du tableau -> avoir les lettres au début
						private void spaceLast(char[] lettres) {
							boolean encore = true;
							int i = 0;
							while(encore){
								if(lettres[i] == ' ') {
									if(i == lettres.length-1) {
										encore = false;
									}
									else {
										if(lettres[i+1] != ' ') {
										lettres[i] = lettres[i+1];
										lettres[i+1] = ' ';
										}
										else {
											encore = false;
										}
									}
								}
								i += 1;
							}
						}
						
					});
				}
				
			}

			//Affiche un rectangle au dessus de la lettre sélectionnée
			private void selected(int ligne, int colonne) {
				Rectangle rectangle = new Rectangle(colonne * Vue.TILE_WIDTH, ligne*(Vue.TILE_HEIGHT+30)+20, Vue.TILE_WIDTH, 10);
				rectangle.setFill(Color.BLACK);
				jroot.getChildren().add(rectangle);
			}

			//supprime le rectangle au dessus de la lettre sélectionnée quand on place ou change de lettre
			private void unselect(int ligne) {
				Rectangle rectangle = new Rectangle(0, ligne*(Vue.TILE_HEIGHT+30)+20, 7 * Vue.TILE_WIDTH, 10); 
				rectangle.setFill(Color.AZURE);
				jroot.getChildren().add(rectangle);
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