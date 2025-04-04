package vidmot;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import vinnsla.Floskur;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class FloskurController implements Initializable {
    private static final String RANGT = "rangt-inntak";

    @FXML private TextField fxFloskur;
    @FXML private TextField fxDosir;
    @FXML private Label fxISKFloskur;
    @FXML private Label fxISKDosir;
    @FXML private Label fxSamtalsFjoldi;
    @FXML private Label fxSamtalsVirdi;
    @FXML private Label fxHeildFjoldi;
    @FXML private Label fxHeildVirdi;
    @FXML private Button fxLanguageToggle;
    @FXML private ChoiceBox<String> fxBottleType;
    @FXML private Label fxCansLabel;
    @FXML private Label fxBottlesLabel;
    @FXML private Label fxTotalLabel;
    @FXML private Button fxPayButton;
    @FXML private Button fxClearButton;
    @FXML private Label fxTotalCountLabel;
    @FXML private Label fxTotalValueLabel;


    private final Floskur floskur = new Floskur();
    private ResourceBundle messages;
    private boolean isIcelandic = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        messages = ResourceBundle.getBundle("messages", new Locale("is"));
        updateBottleTypes(); // Populate ChoiceBox with localized strings
        fxBottleType.setValue(messages.getString("bottle.type.plastic"));
        updateLanguage();
    }

    @FXML
    protected void onToggleLanguage(ActionEvent event) {
        isIcelandic = !isIcelandic;
        messages = ResourceBundle.getBundle("messages", isIcelandic ? new Locale("is") : new Locale("en"));
        fxLanguageToggle.setText(isIcelandic ? "EN" : "IS");
        updateLanguage();
        updateBottleTypes(); // Update ChoiceBox items when language changes
    }

    private void updateLanguage() {
        fxDosir.setPromptText(messages.getString("enter.number.prompt"));
        fxFloskur.setPromptText(messages.getString("enter.number.prompt"));
        fxCansLabel.setText(messages.getString("cans.label"));
        fxBottlesLabel.setText(messages.getString("bottles.label"));
        fxTotalLabel.setText(messages.getString("total.label"));
        fxPayButton.setText(messages.getString("pay.button"));
        fxClearButton.setText(messages.getString("clear.button"));
        fxTotalCountLabel.setText(messages.getString("total.count.label"));
        fxTotalValueLabel.setText(messages.getString("total.value.label"));
        setSamtals();
        fxISKDosir.setText(floskur.getISKDosir() + "");
        fxISKFloskur.setText(floskur.getISKFloskur() + "");
        fxHeildFjoldi.setText(floskur.getHeildFjoldi() + "");
        fxHeildVirdi.setText(floskur.getHeildVirdi() + "");
    }

    private void updateBottleTypes() {
        String selected = fxBottleType.getValue(); // Preserve the selected item
        fxBottleType.getItems().clear();
        fxBottleType.getItems().addAll(
                messages.getString("bottle.type.plastic"),
                messages.getString("bottle.type.glass")
        );
        // Restore the selected item (if it matches one of the new values)
        if (selected != null) {
            String newSelected = selected.equals("Plastic") || selected.equals(messages.getString("bottle.type.plastic"))
                    ? messages.getString("bottle.type.plastic")
                    : messages.getString("bottle.type.glass");
            fxBottleType.setValue(newSelected);
        }
    }

    @FXML
    protected void onFloskur(ActionEvent ignoredEvent) {
        try {
            int fjoldi = Integer.parseInt(fxFloskur.getText());
            if (fjoldi > 0) {
                // Map the localized string back to the model’s expected value
                String bottleType = fxBottleType.getValue().equals(messages.getString("bottle.type.plastic")) ? "Plastic" : "Glass";
                floskur.setFjoldiFloskur(fjoldi, bottleType);
                fxISKFloskur.setText(floskur.getISKFloskur() + "");
                setSamtals();
            } else {
                neikvaedurFjoldi(fxFloskur);
            }
        } catch (NumberFormatException e) {
            fxFloskur.getStyleClass().add(RANGT);
        }
    }

    @FXML
    protected void onDosir(ActionEvent actionEvent) {
        try {
            int fjoldi = Integer.parseInt(fxDosir.getText());
            if (fjoldi > 0) {
                floskur.setFjoldiDosir(fjoldi);
                fxISKDosir.setText(floskur.getISKDosir() + "");
                setSamtals();
            } else {
                neikvaedurFjoldi(fxDosir);
            }
        } catch (NumberFormatException e) {
            fxDosir.getStyleClass().add(RANGT);
        }
    }

    @FXML
    protected void onHreinsa(ActionEvent actionEvent) {
        floskur.hreinsa();
        fxDosir.setText(floskur.getFjoldiDosir() + "");
        eydaRanga(fxDosir.getStyleClass());
        fxFloskur.setText(floskur.getFjoldiFloskur() + "");
        eydaRanga(fxFloskur.getStyleClass());
        fxISKDosir.setText(floskur.getISKDosir() + "");
        fxISKFloskur.setText(floskur.getISKFloskur() + "");
        setSamtals();
    }

    @FXML
    protected void onGreida(ActionEvent actionEvent) {
        setSamtals();
        floskur.greida();
        Platform.runLater(() -> {
            fxHeildFjoldi.setText(floskur.getHeildFjoldi() + "");
            fxHeildVirdi.setText(floskur.getHeildVirdi() + "");
        });
        onHreinsa(actionEvent);
    }

    public void onStafur(KeyEvent keyEvent) {
        TextField textField = ((TextField) keyEvent.getSource());
        List<String> styleClasses = textField.getStyleClass();
        keyEydaRanga(keyEvent, styleClasses);
    }

    private static void keyEydaRanga(KeyEvent keyEvent, List<String> styleClasses) {
        if (styleClasses == null || styleClasses.isEmpty()) {
            return;
        }
        if (keyEvent.getCode() != KeyCode.ENTER) {
            eydaRanga(styleClasses);
        }
    }

    private static void eydaRanga(List<String> styleClasses) {
        styleClasses.remove(RANGT);
    }




    private void setSamtals() {
        try {
            int dosir = Integer.parseInt(fxDosir.getText());
            int floskurCount = Integer.parseInt(fxFloskur.getText());

            floskur.setDosir(dosir);
            floskur.setFloskur(floskurCount);

            fxISKDosir.setText(floskur.getISKDosir() + "");
            fxISKFloskur.setText(floskur.getISKFloskur() + "");
            fxSamtalsFjoldi.setText(floskur.getSamtalsFjoldi() + "");
            fxSamtalsVirdi.setText(floskur.getSamtalsVirdi() + "");

            fxHeildFjoldi.setText(floskur.getHeildFjoldi() + "");
            fxHeildVirdi.setText(floskur.getHeildVirdi() + "");

        } catch (NumberFormatException e) {
            // Gæti sett t.d. viðvörun eða ekkert gert
            fxSamtalsFjoldi.setText("0");
            fxSamtalsVirdi.setText("0");
        }
    }

    private void neikvaedurFjoldi(TextField f) {
        List<String> styleClasses = f.getStyleClass();
        if (Integer.parseInt(f.getText()) < 0) {
            styleClasses.add(RANGT);
        }
    }
}