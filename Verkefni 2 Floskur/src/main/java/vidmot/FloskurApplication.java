
/******************************************************************************
 *  Nafn    : Stefán Steinar Guðlaugsson
 *  T-póstur: ssg49@hi.is
 *  Lýsing  : Application klasi sem les inn notendaviðmótslýsingu úr .fxml og ræsir gluggann
 *
 *
 *****************************************************************************/

package vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class FloskurApplication extends Application {
    /**
     * les inn .fxml skrá og fær rót viðmótstrésins, tengir í senu og birtir í glugga stage
     * @param stage glugginn sem birta á senuna í
     * @throws IOException Exception ef .fxml skráin finnst ekki
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FloskurApplication.
                class.getResource("floskur-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),
                625, 375, Color.LIGHTBLUE);
        stage.setTitle("Flöskumóttaka");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Aðalforrit sem ræsir application
     * @param args ónotað
     */
    public static void main(String[] args) {
        launch();
    }
}
