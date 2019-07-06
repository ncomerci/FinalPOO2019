package game.frontend;

import game.backend.CandyGame;
import game.backend.level.Level1;
import game.backend.level.Level2;
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

public class MainMenu {

    private static Stage current_Stage;
    private AudioClip mediaPlayer;

    public MainMenu(Stage primaryStage) {
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

        Button button1 = new Button("Level 1");
        Button button2 = new Button("Level 2");
        Button button3 = new Button("Level 3");

        button1.setFont(new Font("Serif Bold Italic", 40));
        button2.setFont(new Font("Serif Bold Italic", 40));
        button3.setFont(new Font("Serif Bold Italic", 40));

        button1.setMinSize(200, 100);
        button2.setMinSize(200, 100);
        button3.setMinSize(200, 100);

        root.getChildren().addAll(button1, button2, button3);

        Scene mainMenuScene = new Scene(root, 800, 600);

        button1.setOnMouseClicked(e -> launchLevel(primaryStage, Level1.class));

        button2.setOnMouseClicked(e -> launchLevel(primaryStage, Level2.class));

        button3.setOnMouseClicked(e -> launchLevel(primaryStage, Level3.class));

        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    private void launchLevel(Stage primaryStage, Class<?> level) {
        mediaPlayer.stop();
        CandyGame game = new CandyGame(level);
        CandyFrame frame = new CandyFrame(game);
        Scene scene = new Scene(frame);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage getCurrent_Stage() {
        return current_Stage;
    }
}
