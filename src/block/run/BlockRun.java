/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package block.run;

import static com.sun.webkit.graphics.WCImage.getImage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * @author PC
 */
public class BlockRun extends Application {

    public static final int SCENE_WIDTH = 900;
    public static final int SCENE_HEIGHT = 750;
    public double StartX = 130;
    public double StartY = 586;
    public double deltaY;
    public double deltaX;
    public int scoreNumber = 1;
    public int i = 0;
    public double x = 1.0;
    public ImageView block;
    char a;
    Random r = new Random();

    Rectangle support = new Rectangle(SCENE_WIDTH, 10);
    int character;
    int secondsPassed;

    ImageView background = new ImageView("img/background.jpg");
    ImageView background2 = new ImageView("img/background_play.png");
    ImageView fire = new ImageView("img/char1.png");
    ImageView sea = new ImageView("img/char2.png");
    ImageView hulk = new ImageView("img/char3.png");
    ImageView spike = new ImageView("img/spike.png");

    Label score = new Label();
    Group root = new Group();

    String soundtrack = "src/music/soundtrack.mp3";
    Media hit = new Media(new File(soundtrack).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(hit);

    public Group getVisuals() {
        return root;
    }

    Timeline jumpUp;
    Timeline spikeAnimation;
    Scene scene;

    @Override
    public void start(Stage primaryStage) {
        Button fireButton = new Button();
        fireButton.setText("Fire");
        fireButton.setLayoutX(180);
        fireButton.setLayoutY(520);
        fireButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                character = 1;
                gameStart();
            }
        });

        Button seaButton = new Button();
        seaButton.setText("Sea");
        seaButton.setLayoutX(435);
        seaButton.setLayoutY(520);
        seaButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                character = 2;
                gameStart();
            }
        });

        Button hulkButton = new Button();
        hulkButton.setText("Hulk");
        hulkButton.setLayoutX(685);
        hulkButton.setLayoutY(520);
        hulkButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                character = 3;
                gameStart();
            }
        }
        );

        root.getChildren().add(background);
        root.getChildren().add(fireButton);
        root.getChildren().add(seaButton);
        root.getChildren().add(hulkButton);

        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setTitle("Block Run");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                saveState();
            }
        });
    }

    public void gameStart() {
        root.getChildren().removeAll();
        System.out.println("Character chosen: " + character);
        support.setLayoutY(650);
        spike.setLayoutX(800);
        spike.setLayoutY(588);

        mediaPlayer.play();

        Button pauseButton = new Button();

        pauseButton.setOnAction((EventHandler) new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                jumpUp.pause();
                spikeAnimation.pause();
                mediaPlayer.stop();

            }
        });

        pauseButton.setLayoutX(10);
        pauseButton.setLayoutY(10);
        pauseButton.setText("||");
        score.setLayoutX(50);
        score.setLayoutY(10);
        score.setFont(new Font(20));
        score.setTextFill(Color.WHITE);
        root.getChildren().add(background2);
        root.getChildren().add(pauseButton);
        root.getChildren().add(support);
        root.getChildren().add(spike);
        root.getChildren().add(score);

        if (character == 1) {
            fire.getImage();
            root.getChildren().add(fire);
            fire.setLayoutX(StartX);
            fire.setLayoutY(StartY);
            block = fire;
        }

        if (character == 2) {
            sea.getImage();
            root.getChildren().add(sea);
            sea.setLayoutX(StartX);
            sea.setLayoutY(StartY);
            block = sea;
        }

        if (character == 3) {
            hulk.getImage();
            root.getChildren().add(hulk);
            hulk.setLayoutX(StartX);
            hulk.setLayoutY(StartY);
            block = hulk;
        }

        jumpUp = new Timeline(new KeyFrame(Duration.millis(4), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deltaY = StartY - (StartY + block.getY());

                if (deltaY <= 150 && i == 0) {
                    block.setY(block.getY() - 2);
                    if (deltaY == 150) {
                        i = 1;

                    }

                } else {
                    block.setY(block.getY() + 1);
                    if (deltaY == 0) {
                        i = 0;
                        jumpUp.stop();
                    }
                }
            }
        }));

        spikeAnimation = new Timeline(new KeyFrame(Duration.millis(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (spike.getLayoutX() < SCENE_WIDTH && spike.getLayoutX() > 0) {
                    spike.setLayoutX(spike.getLayoutX() - x);
                    System.out.println(x);
                    score.setText("Score: " + scoreNumber);

                    if (spike.getBoundsInParent().intersects(block.getBoundsInParent())) {
                        hasCollided();
                    }

                    if (spike.getLayoutX() <= 0) {
                        spike.setLayoutX(SCENE_WIDTH - 1);
                        x = r.nextInt(3) + 1;
                        scoreNumber++;
                    }

                }

            }

        }));

        //make the code run indefinitely.
        jumpUp.setCycleCount(Timeline.INDEFINITE);
        spikeAnimation.setCycleCount(Timeline.INDEFINITE);

        scene.setOnKeyPressed((EventHandler) new EventHandler<KeyEvent>() {

            public void handle(final KeyEvent event) {
                if (event.getCode() == KeyCode.UP) {

                    jumpUp.play();
                    spikeAnimation.play();

                }
            }
        });

    }

    private void saveState() {
        try {
            FileWriter w = new FileWriter(new File("score.txt"));
            char mychar = Character.forDigit(scoreNumber, 10);
            System.out.println("Score: " + mychar);
            w.append("Score: " + mychar + "\n");
            w.close();

        } catch (IOException ex) {
            System.err.println("Error writing to file");
        }
    }

    public void hasCollided() {
        saveState();
        spikeAnimation.stop();
        jumpUp.stop();
        x = 1;
        mediaPlayer.stop();
        scoreNumber = 0;

        Stage secondaryStage = new Stage();
        GridPane secondaryRoot = new GridPane(); //table structure

        //center the elements within their cells.
        secondaryRoot.setAlignment(Pos.CENTER);
        secondaryRoot.setHgap(5); //spacing
        secondaryRoot.setVgap(5);

        Label messageLabel = new Label();
        messageLabel.setFont(Font.font(20));

        Label promptLabel = new Label("Would you like to play again? ");

        Button yesButton = new Button("Yes");
        yesButton.setTranslateX(30); //manual spacing override.
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                secondaryStage.close(); //close the popup
                gameStart();
            }
        });

        Button noButton = new Button("No");
        noButton.setTranslateX(45);
        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        secondaryRoot.add(messageLabel, 0, 0, 2, 1);
        secondaryRoot.add(promptLabel, 0, 1, 2, 1);
        secondaryRoot.addRow(2, yesButton, noButton);

        Scene secondaryScene = new Scene(secondaryRoot, 200, 100);
        secondaryStage.setScene(secondaryScene);
        secondaryStage.show();

        //if the user closes the popup.
        secondaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);

    }

}
