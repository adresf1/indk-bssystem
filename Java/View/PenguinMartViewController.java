package View;

import Core.CustomCellFactory.ProductListCell;
import Core.CustomCellFactory.ProductListView;
import Core.ViewController;
import Core.ViewHandler;
import Core.ViewModelFactory;
import Shared.TransferObject.Product;
import Shared.Util.MyDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.util.Scanner;

public class PenguinMartViewController implements ViewController {

    private PenguinMartViewModel viewModel;
    private ViewHandler vh;

    //private ProductListView plv;

    @FXML
    private StackPane shoppingCartFX;
    @FXML
    private TextField textinput;

    @FXML
    private TextField UsernameTextfield;

    @Override
    public void init(ViewHandler vh, ViewModelFactory vmf) {
        this.vh = vh;
        this.viewModel=vmf.getPenguinMartVM();


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

        StackPane root = shoppingCartFX;
        root.getChildren().add(listView);


//        textinput.textProperty().bindBidirectional(viewModel.messageProperty());
//        UsernameTextfield.textProperty().bindBidirectional(viewModel.usernameProperty());

        //This is a lazy implementation of setting a username
        /*
        System.out.println("Please enter your username: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        UsernameTextfield.setText(input);
        UsernameTextfield.setDisable(true);
         */
    }

    @FXML
    private void OnSubmitPressed(){
        System.out.println("onSubmit Has been pressed");
        //viewModel.sendMessage();
    }
}
