package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Main extends Application {

    int tries = 0;

    int highscore = 999;

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            File myObj = new File("highscore.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
                if(myObj.length() != 0){
                    highscore = Integer.parseInt(Files.readString(Paths.get("highscore.txt")));
                }

            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Random random = new Random();
        Button button = new Button();
        button.setText("Check");
        button.setLayoutY(50);
        Group root = new Group(button);



        int numberToGuess = random.nextInt(100)+1;
        TextField guessedNumber = new TextField();
        guessedNumber.setLayoutY(20);
        root.getChildren().add(guessedNumber);

        Label l  = new Label("Number: ");
        //Label l2 = new Label("Number to guess: " + numberToGuess);
        Label l3 = new Label("Highscore " + highscore);

        //l2.setLayoutY(80);
        l3.setLayoutX(200);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tries++;
                if (numberToGuess == Integer.parseInt(guessedNumber.getText())){
                    l.setText("Congratulations");
                    if (tries < highscore){
                        highscore = tries;
                        l3.setText("Highscore " + highscore);
                        try {
                            FileWriter myWriter = new FileWriter("highscore.txt");
                            String hiscore = String.valueOf(highscore);
                            myWriter.write(hiscore);
                            myWriter.close();
                            System.out.println("Successfully wrote to the file.");
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }
                    }

                }
                else if (numberToGuess != Integer.parseInt(guessedNumber.getText())){
                    if(Integer.parseInt(guessedNumber.getText()) > numberToGuess){
                        l.setText("Your number is too big!");
                    }
                    if(Integer.parseInt(guessedNumber.getText()) < numberToGuess){
                        l.setText("Your number is too small!");
                    }
                }


            }
        };

        button.setOnAction(event);

        root.getChildren().add(l);
        //root.getChildren().add(l2);
        root.getChildren().add(l3);


        Scene scene = new Scene(root, 300, 275);
        primaryStage.setTitle("Guess number!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
