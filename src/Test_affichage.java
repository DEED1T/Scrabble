import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.Random;

public class Test_affichage extends Application{
	
	Modele modele;
	
	static int screenWidth = (int) Screen.getPrimary().getBounds().getWidth(); 
    static int screenHeight = (int) Screen.getPrimary().getBounds().getHeight(); 
	
    //Utilisé pour vérifier si on choisi une lettre dans la bonne main
    static boolean lettreValide;
	
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
	
	private char plat_char[][] = {
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'},
			{'/','/','/','/','/','/','/','/','/','/','/','/','/','/','/'}};

	public static void main(String[] args) {
        launch(args);
        //System.out.println(TILE_WIDTH + " " + TILE_HEIGHT);
        //System.out.println(screenWidth + " " + screenHeight);
    }
	
	@Override
	public void start(Stage stage)  {
		
		
		Group root = new Group();
		
		Scene scene = new Scene(root, screenHeight * 0.9, screenHeight*0.9, Color.LIGHTBLUE);
		
		stage.setScene(scene);
		
		//this.modele.first_tirage();
		
		Image case_vide = new Image("Scrabble_images/Case_vide.png");
		Image jocker = new Image("Scrabble_images/Jocker.png");
		Image mot_x2 = new Image("Scrabble_images/Mot_x2.png");
		Image mot_x3 = new Image("Scrabble_images/Mot_x3.png");
		Image lettre_x2 = new Image("Scrabble_images/Lettre_x2.png");
		Image lettre_x3 = new Image("Scrabble_images/Lettre_x3.png");
		Image case_depart = new Image("Scrabble_images/Depart.png");
		
		Image TabImage[] = {case_vide, null, null, lettre_x2, lettre_x3, mot_x2, mot_x3, case_depart};
		Image TabImageLettres[] = new Image[alphabet.length + 1];
		
		for(int image = 0; image < alphabet.length; image++) {
			Image image_lettre = new Image("Scrabble_images/" + alphabet[image] + ".png");
			TabImageLettres[image] = image_lettre;
		}
		
		TabImageLettres[TabImageLettres.length - 1] = jocker;
		
		
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
		
		/*scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

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
        	
        });*/
		
        stage.show();
        
        // main joueur
        
        int nbJoueurs = 4; // temporaire, à remplacer par le nombre de joueurs
        
        Group jroot = new Group();
        Scene jscene = new Scene(jroot, TILE_WIDTH*7, (TILE_HEIGHT+30)*nbJoueurs);  
        jscene.setFill(Color.AZURE);
        
        Stage joueur = new Stage();
        
        Lettre[][] tabsLettres= new Lettre[nbJoueurs][7];
        
        //labels
        
        Label lbl1 = new Label("main joueur 1:");
        lbl1.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
        lbl1.setTranslateY((TILE_HEIGHT+30)*0);
        jroot.getChildren().add(lbl1);
        
        Label lbl2 = new Label("main joueur 2:");
        lbl2.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
        lbl2.setTranslateY((TILE_HEIGHT+30)*1);
        jroot.getChildren().add(lbl2);
        
        if(nbJoueurs >= 3) {
        	Label lbl3 = new Label("main joueur 3:");
        	lbl3.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
        	lbl3.setTranslateY((TILE_HEIGHT+30)*2);
        	jroot.getChildren().add(lbl3);
        }
        
        if(nbJoueurs == 4) {
        	Label lbl4 = new Label("main joueur 4:");
        	lbl4.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
        	lbl4.setTranslateY((TILE_HEIGHT+30)*3);
        	jroot.getChildren().add(lbl4);
        }

    
        //lettres
        
        // /!\ Il faut encore enlever les lettres piochées du sac /!\  -> remplacer les lignes par celles en commentaire devrait faire l'affaire, à vérifier/arranger
        // dans les lignes commentées, remplacer "chemin" par le nom de la class où on crée le sac.
        
        // A enlever et remplacer par les fonctions du modèle?
        
        for(int i=0; i<nbJoueurs; i++) {
        	int numJ = i+1; // pour éviter 01 11 21 31 quand on fait print(i+1)
        	System.out.print("Lettres joueur" + numJ + " : " );
        	for(int j=0; j<7; j++) {
        		Random lettre_random = new Random();
        		int nbLettre = lettre_random.nextInt(alphabet.length); // temporaire, à remplacer par la ligne suivante
        		//int nbLettre = lettre_random.nextInt(chemin.Sac.s_lettre.size())
				Lettre actu = new Lettre(alphabet[nbLettre], 0); // temporaire, j'ai mis 0 pcq il fallait des points pour créer une lettre, à remplacer par la ligne suivante
        		// Lettre actu = chemin.Sac.get(nbLettre)
				tabsLettres[i][j] = actu;
				//chemin.Sac.remove(actu.ch) 
        		System.out.print(tabsLettres[i][j]);
        		
        		ImageView nextImage = new ImageView(TabImageLettres[nbLettre]);
        		nextImage.setFitWidth(TILE_WIDTH);
        		nextImage.setFitHeight(TILE_HEIGHT);
        		nextImage.setLayoutX( j * TILE_WIDTH );
        		nextImage.setLayoutY( i * (TILE_HEIGHT + 30) + 30);
        		jroot.getChildren().add(nextImage);
        	}
        	System.out.println();
        }
               
        jscene.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	
        	
			@Override
			public void handle(MouseEvent event) {
				if(event.getTarget().getClass() == ImageView.class) {
					
					int tourJ = 0; // temporaire, à rempalcer par le joueur à qui c'est le tour
					lettreValide = false;
					
					int x = (int)event.getX();
					int y = (int)event.getY();
					
					int colonne = (int) (x / TILE_WIDTH);
					int ligne = (int) (y / (30 + TILE_HEIGHT));
					if(ligne == tourJ) { // si on sélectionne bien une lettre dans la main du joueur actuel
						lettreValide = true;
					}
					else {
						System.out.println("Veuillez choisir une lettre dans votre main");
					}
					Lettre id = tabsLettres[ligne][colonne];
					System.out.println(id.ch);
					
					scene.setOnMouseClicked(new EventHandler<MouseEvent>(){

						@Override
						public void handle(MouseEvent event) {
							if(lettreValide) {
								int x2 = (int)event.getX();
								int y2 = (int)event.getY();
								int col = (int) (Math.floor(x2 / TILE_WIDTH) + 1);
								int lig = (int) (Math.floor(y2 / TILE_HEIGHT) + 1);
								int id2 = mod_plateau[lig - 1][col - 1];
								System.out.println("Case ID : " + id2);
								
								if(plat_char[7][7] == '/' && lig-1 != 7 && col-1 != 7) {
									System.out.println("Vous devez commcencer à l'étoile");
								}
								//Random image_random = new Random();
								else if (plat_char[7][7]  != '/' && plat_char[lig-1][col-1] == '/') {
									ImageView test = new ImageView();
									if(id.ch != '?') {
										test = new ImageView("Scrabble_images/" + id.ch + ".png");
									}
									else {
										test = new ImageView("Scrabble_images/Jocker.png");
									}
									plat_char[lig-1][col-1] = id.ch;
									test.setFitWidth(TILE_WIDTH);
									test.setFitHeight(TILE_HEIGHT);
									test.setLayoutX( (col-1) * TILE_WIDTH );
									test.setLayoutY( (lig-1) * TILE_HEIGHT );
									root.getChildren().add(test);
								}
								else if(lig-1 == 7 && col - 1 == 7) {
									plat_char[7][7] = id.ch;
									ImageView test = new ImageView();
									if(id.ch != '?') {
										test = new ImageView("Scrabble_images/" + id.ch + ".png");
									}
									else {
										test = new ImageView("Scrabble_images/Jocker.png");
									}
									test.setFitWidth(TILE_WIDTH);
									test.setFitHeight(TILE_HEIGHT);
									test.setLayoutX( (col-1) * TILE_WIDTH );
									test.setLayoutY( (lig-1) * TILE_HEIGHT );
									root.getChildren().add(test);
								}
							}
							
							
						}
						
					});
				}
				
			}
        	
        });
        
        joueur.setTitle("main joueurs");
        joueur.setScene(jscene);
        
        joueur.show();
	}
	
	

}
