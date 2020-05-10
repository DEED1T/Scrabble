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
					
					actu();
					
					int x = (int)e.getX();
					int y = (int)e.getY();
					int colonne = (int) (x / Vue.TILE_WIDTH);
					int ligne   = (int) (y / (30 + Vue.TILE_HEIGHT));
					
					lettreValide = true;
					unselect(ligne);
					selected(ligne, colonne);
					
					char lettre = modl.j1.main.get(colonne).ch;
					
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
									if(modl.plateau[lig][col] != 1) {
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
										lettreValide = false;
										actu();
									}
									
								} catch (ExceptionDisposition e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
						}
						
					});
				}
				
			}

			//Fonction qui actualise la vue de la main du joueur dont c'est le tour.
			private void actu() {
				//System.out.println("newnewnewnewnewnewnewnewnewnewnewnew");
				char tabsLettres[][] = new char[2][7]; //2 car on a 2 joueurs
				for(int i = 0; i < modl.j1.main.size(); i++) {
				    tabsLettres[0][i] = modl.j1.main.get(i).ch;
				}
				for(int i = 0; i < modl.j2.main.size(); i++) {
				    tabsLettres[1][i] = modl.j2.main.get(i).ch;
				}
				
				int tour = (modl.round-1) % 2;
				int lettreRestantes;
				
				if(tour == 0) {
					lettreRestantes = modl.j1.main.size();
				}
				else {
					lettreRestantes = modl.j2.main.size();
				}
				
				//Afficher les lettres restantes
				for(int i=0; i<lettreRestantes; i++) {
					ImageView newLettre;
					if(tabsLettres[tour][i] == '*') {
						newLettre = new ImageView("Scrabble_images/Jocker.png");
					}
					else {
						newLettre = new ImageView("Scrabble_images/" + tabsLettres[tour][i] + ".png");
					}
					newLettre.setFitWidth(Vue.TILE_WIDTH);
					newLettre.setFitHeight(Vue.TILE_HEIGHT);
					newLettre.setLayoutX(i * Vue.TILE_WIDTH );
					newLettre.setLayoutY(30);
					//System.out.println("ajout: Scrabble_images/" + tabsLettres[tour][i] + ".png");
					jroot.getChildren().add(newLettre);
				}
				
				//afficher les rectangles afin de cacher les anciennes lettres
				for(int i=6; i>lettreRestantes-1; i--) {
					Rectangle rectangle = new Rectangle(i * Vue.TILE_WIDTH, 30, Vue.TILE_WIDTH, Vue.TILE_HEIGHT);
					//System.out.println("ajout: rectangle");
					jroot.getChildren().add(rectangle);
				}
			}
			
			
			private void printTabs(char[][] tabsLettres) {
				for(int i=0; i<tabsLettres.length; i++) {
					System.out.print("[");
					for(int j=0; j<tabsLettres[i].length; j++) {
						System.out.print(tabsLettres[i][j]);
					}
					System.out.println("]");
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
	
	public void mot_fini(Button button, Scene scene) {
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				try {
					modl.mot_fini();
					Image TabImage[] = Vue.creationImages();
					for(int ligne = 0; ligne < modl.plat_char.length; ligne++) {
						for(int colonne = 0; colonne < modl.plat_char.length; colonne++) {
							if(modl.plat_char[ligne][colonne] == '/') {
								int id = modl.mod_plateau[ligne][colonne];
								Image texture = TabImage[id];
								if(texture != null) {
									ImageView image = new ImageView(texture);
									image.setFitHeight(Vue.TILE_HEIGHT);
									image.setFitWidth(Vue.TILE_WIDTH);
									image.setLayoutX( colonne * Vue.TILE_WIDTH );
									image.setLayoutY( ligne * Vue.TILE_HEIGHT );
									Group root = (Group)scene.getRoot();
									root.getChildren().add(image);
							}
							}
						}
					}
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
					modl.pioche(modl.j1);
				}
				
			}
			
		});
	}
	
	public void btnRestart(Button btnRestart, Scene scene) {
		btnRestart.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				modl.resetAll();
				int plateau[][] = modl.mod_plateau;
				Image TabImage[] = Vue.creationImages();
				for(int lig = 0; lig < Vue.MAP_HEIGHT; lig++) {
					for(int col = 0; col < Vue.MAP_WIDTH; col++) {
						Group root = (Group)scene.getRoot();
						int id = plateau[lig][col];
						Image texture = TabImage[id];
						if(texture != null) {
							ImageView image = new ImageView(texture);
							image.setFitHeight(Vue.TILE_HEIGHT);
							image.setFitWidth(Vue.TILE_WIDTH);
							image.setLayoutX( col * Vue.TILE_WIDTH );
							image.setLayoutY( lig * Vue.TILE_HEIGHT );
							root.getChildren().add(image);
						}
					}
				}
				
			}
			
		});
	}
	
	public boolean voisins(char plat_char[][], int lig, int col) {
		return plat_char[lig-1][col] != '/' || plat_char[lig][col-1] != '/' || plat_char[lig+1][col] != '/' || plat_char[lig][col+1] != '/';
	}

}