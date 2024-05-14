import Core.ClientFactory;
import Core.ModelFactory;
import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;


public class ShopSystemApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ClientFactory cf = new ClientFactory();
        ModelFactory mf = new ModelFactory(cf);
        ViewModelFactory vmf = new ViewModelFactory(mf);
        ViewHandler vh = new ViewHandler(vmf);
        vh.start();
    }
}
