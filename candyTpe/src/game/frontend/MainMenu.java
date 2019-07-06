package game.frontend;

import game.backend.CandyGame;
import game.backend.level.Level1;
import game.backend.level.Level2;
import game.backend.level.Level2Aux;
import game.backend.level.Level3;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MainMenu {

    private static Stage current_Stage;
    private AudioClip mediaPlayer;

    MainMenu(Stage primaryStage) {
        current_Stage = primaryStage;

        String musicFile = "candyTpe/resources/audio/Candy Crush Intro.mp3";
        Media sound = new Media(Paths.get(musicFile).toUri().toString());
        mediaPlayer = new AudioClip(sound.getSource());
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        FlowPane root = new FlowPane(Orientation.VERTICAL, 100, 100);
        root.setAlignment(Pos.CENTER);

        Image image = new Image("images/candyBackground.png");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root.setBackground(new Background(backgroundImage));

        Button[] buttons_array = {new Button("Level 1"), new Button("Level 2"), new Button("Level 3"), new Button("\t  Level 2\n(Alternative version)")};
        List<Button> buttons = new ArrayList<>(Arrays.asList(buttons_array));

        for(Button button: buttons) {
            button.setFont(new Font("Serif Bold Italic", 40));
            button.setMinSize(200, 100);
        }

        root.getChildren().addAll(buttons_array);

        Scene mainMenuScene = new Scene(root, 800, 600);

        buttons_array[0].setOnMouseClicked(e -> launchLevel(primaryStage, Level1.class));

        buttons_array[1].setOnMouseClicked(e -> launchLevel(primaryStage, Level2.class));

        buttons_array[2].setOnMouseClicked(e -> launchLevel(primaryStage, Level3.class));

        buttons_array[3].setOnMouseClicked(e -> launchLevel(primaryStage, Level2Aux.class));


        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    private void launchLevel(Stage primaryStage, Class<?> level) {
        CandyGame game = new CandyGame(level);
        CandyFrame frame = new CandyFrame(game);
        Scene scene = new Scene(frame);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        mediaPlayer.stop();
    }

    static Stage getCurrent_Stage() {
        return current_Stage;
    }
}
