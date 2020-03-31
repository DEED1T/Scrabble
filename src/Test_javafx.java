import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
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

    @Override
    public void start(Stage stage) {

        initUI(stage);
    }

    private void initUI(Stage stage) {

        StackPane root = new StackPane();

        Scene scene = new Scene(root, 300, 250);

        Label lbl = new Label("Simple JavaFX application.");
        lbl.setFont(Font.font("Serif", FontWeight.NORMAL, 20));
        root.getChildren().add(lbl);

        stage.setTitle("Simple application");
        stage.setScene(scene);
        stage.show();
        
        lbl.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent mon_test) {
				modif_du_titre(lbl);
			}
        	
        });
        
        lbl.setFocusTraversable(true); 
        lbl.setOnKeyPressed(keyEvent -> System.out.printf("Touche enfoncée : %s", keyEvent.getCode()).println());
        root.getChildren().setAll(lbl);
    }
    
    public void modif_du_titre(Label lb) {
    	lb.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));
    }

    public static void main(String[] args) {
        launch(args);
    }
}