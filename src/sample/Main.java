package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.datamodel.DataUtils;

public class Main extends Application {
    public static MainController controller;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = FXMLLoader.load(getClass().getResource("Layouts/main.fxml"));
        primaryStage.setTitle("Your Weather");
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getBounds();
        double width = bounds.getWidth();
        double height = bounds.getHeight() - 65;
        primaryStage.setScene(new Scene(root, width, height));
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void init() throws Exception {
        super.init();
        DataUtils.getInstance().loadLocations();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DataUtils.getInstance().storeLocations();
    }
}
