package eu.burbach.diplom.application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Parent root= FXMLLoader.load(getClass().getResource("/eu/burbach/diplom/application/Main.fxml"));
			Scene scene = new Scene(root,600,400);
			primaryStage.setTitle("Algorithmen zur parallelen Determinantenberechnung in Java 01.12.2017");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
