package Core.CustomCellFactory;

import Shared.TransferObject.Product;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProductListCell extends ListCell<Product> {
        private HBox content;
        private Image productImage;
        private Text description;
        private Text name;
        private Text price;
        private Text quantity;
        private Text expirationDate;

        private Text unitType;
        private Text totalPrice;


        public ProductListCell() {
            super();
            name = new Text();
            price = new Text();
            expirationDate = new Text();
            VBox vBox = new VBox(name, price, expirationDate);
            content = new HBox(new Label("[Graphic]"), vBox);
            content.setSpacing(20);
        }
    @Override
    protected void updateItem(Product item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) { // <== test for null item and empty parameter
            name.setText(item.getName());
            price.setText(String.format("%1$,.2f", item.getPrice()));
            expirationDate.setText(item.getExpirationDate().toString());
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}

