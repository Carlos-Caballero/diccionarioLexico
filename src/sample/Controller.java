package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller implements Initializable {
    Diccionario dicc = new Diccionario();

    @FXML
    private TextField txtEntrada;

    @FXML
    void existe(ActionEvent event) {
        String entrada = txtEntrada.getText();
        System.out.println(dicc.existe(entrada));
    }

    @FXML
    void getDescription(ActionEvent event) {
        System.out.println(dicc.getDescriptionOf(txtEntrada.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void getDescriptions(ActionEvent event) {
        System.out.println(dicc.getDescriptions());
    }

    @FXML
    void getStaticKeys(ActionEvent event) {
        System.out.println(dicc.getStaticKeys());
    }
}
