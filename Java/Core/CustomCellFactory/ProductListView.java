package Core.CustomCellFactory;

import Shared.TransferObject.Product;
import Shared.Util.MyDate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ProductListView extends Application {
    //private Product product;
    @Override
    public void start(Stage primaryStage) throws Exception {
        ObservableList<Product> cart = FXCollections.observableArrayList();
        ObservableList<Product> displayProducts = FXCollections.observableArrayList();


        cart.addAll(new Product("Brød", "1", 123, "Wholefood", MyDate.fromString("01/01/2020"), MyDate.fromString("02/08/2020"), 234565, 3.95,7.25,5.55,"stk"),
                    new Product("Mælk", "2", 234, "Grocery", MyDate.fromString("10/01/2020"), MyDate.fromString("23/02/2020"), 345064,7.95, 8.65, 2.25, "liter"),
                    new Product("Æg", "3", 132, "Grocery", MyDate.fromString("11/01/2020"), MyDate.fromString("27/01/2020"), 45063,8.95, 50, 5, "Kg"),
                new Product("Bacon og Æg", "4", 754, "Breakfast", MyDate.fromString("11/01/2020"), MyDate.fromString("27/01/2020"), 12345,10.95, 40, 2, "Kg")
        );

        final ListView<Product> listView = new ListView<Product>(cart);
        listView.setCellFactory(new Callback<ListView<Product>, ListCell<Product>>() {
            @Override
            public ListCell<Product> call(ListView<Product> listView) {
                return new ProductListCell();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(listView);
        primaryStage.setScene(new Scene(root, 200, 250));
        primaryStage.show();
    }
}
