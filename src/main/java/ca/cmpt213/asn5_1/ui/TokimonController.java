package ca.cmpt213.asn5_1.ui;

import ca.cmpt213.asn5_1.model.Tokimon;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class TokimonController {
    @FXML
    private GridPane gridPane;

    @FXML
    private void initialize() {
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        loadTokimonData();
    }

    private void loadTokimonData() {
        try {
            URL url = new URL("http://localhost:8080/api/tokimon/all");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            ObjectMapper objectMapper = new ObjectMapper();
            List<Tokimon> tokimons = objectMapper.readValue(connection.getInputStream(), new TypeReference<List<Tokimon>>() {});
            connection.disconnect();

            int row = 0;
            int col = 0;
            for (Tokimon tokimon : tokimons) {
                VBox vbox = new VBox();
                vbox.setSpacing(5);

                ImageView imageView = new ImageView();
                Image image = new Image(tokimon.getPictureUrl());
                imageView.setImage(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                imageView.setOnMouseClicked(event -> showTokimonDetails(tokimon, event));

                Label nameLabel = new Label(tokimon.getName());

                vbox.getChildren().addAll(imageView, nameLabel);
                gridPane.add(vbox, col, row);

                col++;
                if (col == 4) { // Change this number to adjust the number of columns
                    col = 0;
                    row++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showTokimonDetails(Tokimon tokimon, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/tokimon_details_view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            TokimonDetailsController controller = loader.getController();
            controller.setTokimonDetails(tokimon);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(tokimon.getName() + " Details");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
