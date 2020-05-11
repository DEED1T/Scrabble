import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Vue implements Observer{

	Modele modl;
	
	public Vue(Modele modl) {
		this.modl = modl;
	}
	
	static int screenWidth = (int) Screen.getPrimary().getBounds().getWidth(); 
    static int screenHeight = (int) Screen.getPrimary().getBounds().getHeight(); 
	
	
	//Dimensions de la fenêtre
	static int MAP_WIDTH = 15;
	static int MAP_HEIGHT = 15;
	
	//Dimensions de chaque Tile (Ici, nos cases du Scrabble)
	static int TILE_WIDTH = (int) ((screenHeight * 0.9) / 15);
	static int TILE_HEIGHT = (int) ((screenHeight * 0.9) / 15);
	
	public static char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}; 
	
	//Chargement des images
	static Image TabImage[] = creationImages();
	Image TabImageLettre[] = creationImagesLettres();
	
	public Scene getScene() {
		
		Group root = new Group();
		Scene scene = new Scene(root, screenHeight, screenHeight*0.9, Color.LIGHTBLUE);
		int plateau[][] = modl.mod_plateau;
		
		
		for(int ligne = 0; ligne < MAP_WIDTH; ligne++) {
			for(int colonne = 0; colonne < MAP_HEIGHT; colonne++) {
				int id = plateau[ligne][colonne];
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
		
		return scene;
		
	}
	
	public Scene getMainJoueurs() {
		int nbJoueurs = 1; // temporaire, à remplacer par le nombre de joueurs
        
        Group jroot = new Group();
        Scene jscene = new Scene(jroot, TILE_WIDTH*7, (TILE_HEIGHT+30)*nbJoueurs);  
        jscene.setFill(Color.AZURE);
        
        char[][] tabsLettres= new char[nbJoueurs][7];
        
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
        for(int i=0; i<nbJoueurs; i++) {
        	int numJ = i+1; // pour éviter 01 11 21 31 quand on fait print(i+1)
        	System.out.print("Lettres joueur" + numJ + " : " );
        	for(int j=0; j<7; j++) {
        		Random lettre_random = new Random();
        		//int nbLettre = lettre_random.nextInt(alphabet.length); // temporaire, à remplacer par la ligne suivante
        		//int nbLettre = lettre_random.nextInt(chemin.Sac.s_lettre.size())
				char actu = modl.j1.main.get(j).ch; // temporaire, j'ai mis 0 pcq il fallait des points pour créer une lettre, à remplacer par la ligne suivante
        		// Lettre actu = chemin.Sac.get(nbLettre)
				tabsLettres[i][j] = actu;
				//chemin.Sac.remove(actu.ch) 
        		System.out.print(tabsLettres[i][j]);
        		Image image_lettre;
        		if(actu != '*') {
        			image_lettre = new Image("Scrabble_images/" + Character.toUpperCase(actu) + ".png");
        		}
        		else {
        			image_lettre = new Image("Scrabble_images/Jocker.png");
        		}
        		
        		ImageView nextImage = new ImageView(image_lettre);
        		nextImage.setFitWidth(TILE_WIDTH);
        		nextImage.setFitHeight(TILE_HEIGHT);
        		nextImage.setLayoutX( j * TILE_WIDTH );
        		nextImage.setLayoutY( i * (TILE_HEIGHT + 30) + 30);
        		jroot.getChildren().add(nextImage);
        	}
        	System.out.println();
        }

        return jscene;
	}
	
	public Button boutonPioche() {
		Button btnPioche = new Button("Pioche");
		btnPioche.setLayoutX(MAP_WIDTH * TILE_WIDTH);
		btnPioche.setLayoutY((MAP_HEIGHT * TILE_HEIGHT) - TILE_HEIGHT);
		btnPioche.setPrefSize(2*TILE_WIDTH, TILE_HEIGHT);
		return btnPioche;
		
	}
	
	public Button bouton_mot_fini() {
		Button btnMotFini = new Button("Mot_fini");
		btnMotFini.setLayoutX(MAP_WIDTH * TILE_WIDTH);
		btnMotFini.setLayoutY((MAP_HEIGHT * TILE_HEIGHT) - 2 * TILE_HEIGHT);
		btnMotFini.setPrefSize(2*TILE_WIDTH, TILE_HEIGHT);
		return btnMotFini;
		
	}
	
	public Button boutonRestart() {
		Button btnRestart = new Button("Restart");
		btnRestart.setLayoutX(MAP_WIDTH * TILE_WIDTH);
		btnRestart.setLayoutY((MAP_HEIGHT * TILE_HEIGHT) - 3 * TILE_HEIGHT);
		btnRestart.setPrefSize(2*TILE_WIDTH, TILE_HEIGHT);
		return btnRestart;
		
	}
	
	public Button bouton_fin_tour() {
		Button btnEndTurn = new Button("Fin Tour");
		btnEndTurn.setLayoutX(MAP_WIDTH * TILE_WIDTH);
		btnEndTurn.setLayoutY((MAP_HEIGHT * TILE_HEIGHT) - 4 * TILE_HEIGHT);
		btnEndTurn.setPrefSize(2*TILE_WIDTH, TILE_HEIGHT);
		return btnEndTurn;
		
	}
	
	public static Image[] creationImages() {
		Image case_vide = new Image("Scrabble_images/Case_vide.png");
		Image mot_x2 = new Image("Scrabble_images/Mot_x2.png");
		Image mot_x3 = new Image("Scrabble_images/Mot_x3.png");
		Image lettre_x2 = new Image("Scrabble_images/Lettre_x2.png");
		Image lettre_x3 = new Image("Scrabble_images/Lettre_x3.png");
		Image case_depart = new Image("Scrabble_images/Depart.png");
		Image TabImage[] = {case_vide, null, null, lettre_x2, lettre_x3, mot_x2, mot_x3, case_depart};
		return TabImage;
	}
	
	public Image[] creationImagesLettres() {
		Image TabImageLettres[] = new Image[alphabet.length];
		
		for(int image = 0; image < alphabet.length; image++) {
			Image image_lettre = new Image("Scrabble_images/" + alphabet[image] + ".png");
			TabImageLettres[image] = image_lettre;
		}
		return TabImageLettres;
	}

	@Override
	public void update(Observable obs, Object obj) {
		
	}
}