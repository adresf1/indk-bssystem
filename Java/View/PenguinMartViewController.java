package View;

import Core.CustomCellFactory.ProductListView;
import Core.ViewController;
import Core.ViewHandler;
import Core.ViewModelFactory;
import Shared.TransferObject.Product;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

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

    @FXML
    public void onPressed_getAllProducts(){
        viewModel.allProductsToStackPane();
    }
}
