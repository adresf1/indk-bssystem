package View;

import Core.ViewController;
import Core.ViewHandler;
import Core.ViewModelFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.Scanner;

public class PenguinMartViewController implements ViewController {
    private PenguinMartViewModel viewModel;
    private ViewHandler vh;


    @FXML
    private TextField textinput;

    @FXML
    private TextField UsernameTextfield;

    @Override
    public void init(ViewHandler vh, ViewModelFactory vmf) {
        this.vh = vh;
        this.viewModel=vmf.getPenguinMartVM();
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
