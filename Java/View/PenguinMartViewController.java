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
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.util.Scanner;

public class PenguinMartViewController implements ViewController {

    private PenguinMartViewModel viewModel;
    private ViewHandler vh;

    private ProductListView plv;

    @FXML
    private TableView<Product> presentedProducts;

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
        this.plv= new ProductListView(viewModel.getShoppingCart(),shoppingCartFX);
    }

    @FXML
    public void onPressed_moveToBasket(){
        if(presentedProducts.getSelectionModel().getSelectedItem() != null){
            Product p = presentedProducts.getSelectionModel().getSelectedItem();
            viewModel.moveToBasket(p);
        }
    }
}
