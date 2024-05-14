package Core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewHandler {

    private Scene PenguinMartClientScene;
    private Stage stage;
    private ViewModelFactory vmf;
    public ViewHandler(ViewModelFactory vmf) {
        this.vmf = vmf;
    }

    public void start() {
        stage = new Stage();
        openPenguinMartView();
    }

    public void openPenguinMartView(){
        if (PenguinMartClientScene == null) {
            try {
                Parent root = loadFXML("../View/FXML/ShopSystemClientView.fxml");
                stage.setTitle("PenguinMart");
                PenguinMartClientScene = new Scene(root);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stage.setScene(PenguinMartClientScene);
        stage.show();
    }

    private Parent loadFXML(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/FXML/ShopSystemClientView.fxml"));
        Parent root = loader.load();

        ViewController ctrl = loader.getController();
        ctrl.init(this, vmf);
        return root;
    }

    public void openLog(){

    }
}
