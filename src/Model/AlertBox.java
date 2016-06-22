package Model;

import javafx.beans.NamedArg;
import javafx.scene.control.Alert;

/**
 * Created by Nadin on 02/12/15.
 */
public class AlertBox extends Alert {

    public AlertBox(String message, String title, Alert alert) {
        super(alert.getAlertType());
        this.setTitle(title);
        this.setHeaderText(message);
        this.showAndWait();
    }

}
