package View;

import Core.CustomCellFactory.ProductListCell;
import Core.ViewController;
import Core.ViewHandler;
import Core.ViewModelFactory;
import Shared.TransferObject.Product;
import Shared.Util.MyDate;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;

public class PenguinMartViewController implements ViewController {

    private PenguinMartViewModel viewModel;
    private ViewHandler vh;

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
    private TextField numberToBasket;

    @FXML
    private TextField UsernameTextfield;

    @FXML
    private HBox messageInfoBox;
    private VBox cartContainer;

    private String itemToRemove;

    @Override
    public void init(ViewHandler vh, ViewModelFactory vmf) {
        this.vh = vh;
        this.viewModel=vmf.getPenguinMartVM();
        //Setup cells for table view
        // this.plv= new ProductListView(viewModel.getShoppingCart(),shoppingCartFX);
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        expirationDate.setCellValueFactory(new PropertyValueFactory<>("ExpirationDate"));
        barcode.setCellValueFactory(new PropertyValueFactory<>("Barcode"));
        category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        unitType.setCellValueFactory(new PropertyValueFactory<>("UnitType"));
        numberToBasket.setPromptText("Antal");

        // Add listener to restrict input to numbers only
        numberToBasket.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberToBasket.setText(oldValue);
            }
        });

        /*//Stop the items in table view shifting around
        name.setSortType(TableColumn.SortType.ASCENDING);
        presentedProducts.getSortOrder().add(name);
        presentedProducts.sort();

        */
        //Bind observable list to Tabel view
        presentedProducts.setItems(viewModel.getProductList());
        //load all products into the observable list
        viewModel.requestAllProducts();

        //Setup eventlisteners from server
        viewModel.getSupport().addPropertyChangeListener("updateStackpaneItems",this::updateShoppingcart );
        viewModel.getSupport().addPropertyChangeListener("refreshTableView",this::refreshTableView );
        viewModel.getSupport().addPropertyChangeListener("allProductsBought_event", this::handleAllProductsBought);

        // Initialize VBox for stacking items

        cartContainer = new VBox();
        shoppingCartFX.getChildren().add(cartContainer);
    }

    private void refreshTableView(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() -> {
            presentedProducts.sort();
            System.out.println("Refreshing table");
        });
    }

    public void updateShoppingcart(PropertyChangeEvent propertyChangeEvent)
    {
        Platform.runLater(() -> {
            cartContainer.getChildren().clear(); // Clear existing children

            // Fetch products from the shopping cart
            for (Product product : viewModel.getShoppingCart())
            {
                ProductListCell cell = new ProductListCell(product);
                cell.getElement().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //Self implemented method of getSelectedItem from a stackpane
                        itemToRemove = cell.getID();
                        }
                });
                //Add elements to stackpane
                cartContainer.getChildren().add(cell.getElement());
            }
        });
    }

    @FXML
    public void onPressed_moveToBasket() throws IOException {
        if(presentedProducts.getSelectionModel().getSelectedItem() != null){
            Product p = presentedProducts.getSelectionModel().getSelectedItem();
            if ((numberToBasket.textProperty().toString()).isEmpty())
                p.setQuantity(1);

            else {
                p.setQuantity(Double.parseDouble(numberToBasket.getText()));
            }
            numberToBasket.clear();
            viewModel.moveToBasket(p);
        }
    }

    @FXML
    public void onPressed_checkOutBasket(ActionEvent actionEvent) throws IOException {
        double totalprice = 0, quantity = 0;
        for (Product product: viewModel.getShoppingCart())
        {
        totalprice += (product.getPrice()*product.getQuantity());
        quantity+=product.getQuantity();
        }
        System.out.println("Total price: " + totalprice);
        System.out.println("Total quantity: " + quantity);
        viewModel.requestBuyProducts();
    }

    private void addDynamicLabel(String message) {
        Platform.runLater(() -> {
            messageInfoBox.getChildren().clear();
            // Create a new Label
            Label label = new Label(message);
            // Add the label to the children of the HBox
            messageInfoBox.getChildren().add(label);
        });
    }


    public void onPressed_removeItem(ActionEvent actionEvent) throws IOException {
        for (Product product: viewModel.getShoppingCart()) {
            if (product.getID().equals(itemToRemove)){
                viewModel.requestRemoveProduct(product);
            }
        }
    }

    private void handleAllProductsBought(PropertyChangeEvent propertyChangeEvent) {
        double totalprice = 0;
        for (Product product: (ArrayList<Product>) propertyChangeEvent.getNewValue())
        {
            totalprice += (product.getPrice()*product.getQuantity());
        }
        addDynamicLabel("Du købte for: " + totalprice);
        viewModel.getShoppingCart().clear();
        viewModel.getSupport().firePropertyChange("updateStackpaneItems",null,null);
    }

}
