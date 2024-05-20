package Core.CustomCellFactory;

import Shared.TransferObject.Product;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProductListCell {
    private HBox content;
    private Image productImage;
    private Text description;

    private Text ID;
    private Text name;
    private Text price;
    private Text quantity;
    private Text expirationDate;

    private Text unitType;
    private Text totalPrice;

    public ProductListCell(Product product) {
        name = new Text();
        price = new Text();
        expirationDate = new Text();
        ID = new Text();
        VBox vBox = new VBox(name, price, expirationDate);
        content = new HBox(new Label("[Graphic]"), vBox);
        content.setSpacing(20);
        System.out.println("Constructor ProductListCell");
        ID.setText(product.getID());

        name.setText(product.getName());
        price.setText(String.format("%1$,.2f", product.getPrice()));
        expirationDate.setText(product.getExpirationDate().toString());
    }

    public HBox getElement()
    {
        return content;
    }

    public String getID(){
        return ID.getText();
    }
    public void updateItem(Product item, boolean empty) {
        if (item != null && !empty) { // <== test for null item and empty parameter
            name.setText(item.getName());
            price.setText(String.format("%1$,.2f", item.getPrice()));
            expirationDate.setText(item.getExpirationDate().toString());
            System.out.println("updatedItem activated");
        }

    }

}

