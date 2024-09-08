package ca.cmpt213.asn5_1.ui;

import ca.cmpt213.asn5_1.model.Tokimon;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TokimonDetailsController {

    @FXML
    private ImageView tokimonImage;

    @FXML
    private Label nameLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label rarityLabel;

    @FXML
    private Label hpLabel;

    public void setTokimonDetails(Tokimon tokimon) {
        Image image = new Image(tokimon.getPictureUrl());
        tokimonImage.setImage(image);
        nameLabel.setText("Name: " + tokimon.getName());
        typeLabel.setText("Type: " + tokimon.getType());
        rarityLabel.setText("Rarity: " + tokimon.getRarity());
        hpLabel.setText("HP: " + tokimon.getHp());
    }
}
