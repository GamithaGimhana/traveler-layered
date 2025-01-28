package lk.ijse.gdse.traveler;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("/view/loadingFx.fxml"));
        stage.setScene(new Scene(load));
        stage.show();

        Task<Scene> loadingTask = new Task<Scene>() {
            @Override
            protected Scene call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/loginFx.fxml"));
                return new Scene(fxmlLoader.load());
            }
        };

        loadingTask.setOnSucceeded(event -> {
            Scene value = loadingTask.getValue();

            stage.setTitle("The Traveler - Travel Management System");
            Image image = new Image(getClass().getResourceAsStream("/images/iconPhoto.png"));
            stage.getIcons().add(image);

            stage.setResizable(false);

            stage.setScene(value);
        });

        new Thread(loadingTask).start();
    }

    public static void main(String[] args) {
        launch();
    }
}
