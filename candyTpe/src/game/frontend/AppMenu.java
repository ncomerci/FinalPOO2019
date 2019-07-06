package game.frontend;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

class AppMenu extends MenuBar {

    AppMenu() {
        Menu file = new Menu("Archivo");

        MenuItem backMenuItem = new MenuItem("Volver al menú principal");
        backMenuItem.setOnAction(e -> {
            MainMenu.getCurrent_Stage().hide();
            new MainMenu(new Stage());
        });
        file.getItems().add(backMenuItem);

        MenuItem exitMenuItem = new MenuItem("Salir");
        exitMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Salir");
            alert.setHeaderText("Salir de la aplicación");
            alert.setContentText("¿Está seguro que desea salir de la aplicación?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    Platform.exit();
                }
            }
        });
        file.getItems().add(exitMenuItem);

        Menu help = new Menu("Ayuda");
        MenuItem aboutMenuItem = new MenuItem("Acerca De");
        aboutMenuItem.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acerca De");
            alert.setHeaderText("Candy TPE");
            alert.setContentText("Cátedra POO 2018.\n" +
                    "Implementación Original: Laura Zabaleta (POO 2013)." +
                    "\n============================\n" +
                    "Modificaciones:\nTamara Puig\nNicolás Comerci\nLuciano Boccardi\n(POO 2019)");
            alert.showAndWait();
        });
        help.getItems().add(aboutMenuItem);
        getMenus().addAll(file, help);
    }

}
