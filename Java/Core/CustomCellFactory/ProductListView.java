package Core.CustomCellFactory;

import Model.ShoppingCart;
import Shared.TransferObject.Product;
import Shared.Util.MyDate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

public class ProductListView extends Application {
    //private Product product;
    @Override
    public void start(Stage primaryStage) throws Exception {
        ShoppingCart sc = new ShoppingCart();
        ArrayList<Product> cart = new ArrayList<>();

        cart.add(new Product("Brød", "1", 123, "Wholefood", MyDate.fromString("01/01/2020"), MyDate.fromString("02/08/2020"), 234565, 3.95,7.25,5.55,"stk"));
        cart.add(new Product("Mælk", "2", 234, "Grocery", MyDate.fromString("10/01/2020"), MyDate.fromString("23/02/2020"), 345064,7.95, 8.65, 2.25, "liter"));
        cart.add(new Product("Æg", "3", 132, "Grocery", MyDate.fromString("11/01/2020"), MyDate.fromString("27/01/2020"), 45063,8.95, 50, 5, "Kg"));
        cart.add(new Product("Bacon og Æg", "4", 754, "Breakfast", MyDate.fromString("11/01/2020"), MyDate.fromString("27/01/2020"), 12345,10.95, 40, 2, "Kg"));

        final ListView<Product> listView = new ListView<Product>(sc.getObservableList());
        listView.setCellFactory(new Callback<ListView<Product>, ListCell<Product>>() {
            @Override
            public ListCell<Product> call(ListView<Product> listView) {
                return new ProductListCell();
            }
        });

        //Make button to Add
        Button button2 = new Button("Add Bread");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                sc.reserveProduct(cart.get(0));
            }
        });

        //Make button to edit
        Button button1 = new Button("Edit Bread");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Product p1 = sc.getObservableList().get(0);
                p1.setName("Rugbrod");
                sc.getObservableList().set(0,p1);
            }
        });


        StackPane root = new StackPane();

        root.getChildren().add(listView);
//        root.getChildren().add(button2);
        root.getChildren().add(button1);

        primaryStage.setScene(new Scene(root, 200, 250));
        primaryStage.show();

        for(Product item : cart){
            sc.getObservableList().add(item);
        }
    }
}
