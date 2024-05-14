package Core;

import Model.IShopSystemManager;
import Model.ShopSystemManager;

public class ModelFactory {
    private final ClientFactory cf;
    private IShopSystemManager shopSystemModel;

    public ModelFactory(ClientFactory cf) {
        this.cf = cf;
        //TODO: why does this not need getInstance function - possible error?
    }

    public IShopSystemManager getShopSystemModel() {
        if(shopSystemModel == null)
            shopSystemModel = new ShopSystemManager(cf.getClient());
        return shopSystemModel;
    }

}
