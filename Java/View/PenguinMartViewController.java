package View;

import Core.CustomCellFactory.ProductListView;
import Core.ViewController;
import Core.ViewHandler;
import Core.ViewModelFactory;
import Shared.TransferObject.Product;
import Shared.Util.MyDate;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class PenguinMartViewController implements ViewController {

    private PenguinMartViewModel viewModel;
    private ViewHandler vh;

    private ProductListView plv;

    @FXML
    private TableView<Product> presentedProducts;
    @FXML
    TableColumn<Product, String> name;
    @FXML
    TableColumn<Product, Integer> price;
    @FXML
    TableColumn<Product, Integer> quantity;
    @FXML
    TableColumn<Product, MyDate> expirationDate;

    @FXML
    TableColumn<Product, String> barcode;

    @FXML
    TableColumn<Product, String> unitType;
    @FXML
    TableColumn<Product, String> category;

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
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        expirationDate.setCellValueFactory(new PropertyValueFactory<>("ExpirationDate"));
        barcode.setCellValueFactory(new PropertyValueFactory<>("Barcode"));
        category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        unitType.setCellValueFactory(new PropertyValueFactory<>("UnitType"));

       presentedProducts.setItems(viewModel.getProductList());
       viewModel.requestAllProducts();

    }


    @FXML
    public void onPressed_moveToBasket() throws IOException {
        if(presentedProducts.getSelectionModel().getSelectedItem() != null){
            Product p = presentedProducts.getSelectionModel().getSelectedItem();
            viewModel.moveToBasket(p);
        }
    }





    @FXML
    public void onPressed_getAllProducts(){
        viewModel.allProductsToStackPane();
    }
    @FXML
    public void onPressed_updateAllProducts()
    {
        viewModel.requestAllProducts();
    }
}
