package Core;

import Core.ModelFactory;
import View.PenguinMartViewModel;

public class ViewModelFactory {
    private final ModelFactory mf;
    private PenguinMartViewModel penguinMartViewModel;

    public ViewModelFactory(ModelFactory mf) {
        this.mf = mf;
    }

    public PenguinMartViewModel getPenguinMartVM(){
        if (penguinMartViewModel == null){
            penguinMartViewModel = new PenguinMartViewModel(mf.getShopSystemModel());
        }
        return penguinMartViewModel;
    }

    //TODO: error might be in mf.getPenguinMartModel

}
