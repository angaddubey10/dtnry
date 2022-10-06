package com.example.dictonary;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelloApplication extends Application {
    int height = 400;
    int width = 400;
    private Group tileGroup = new Group();

    private HashMap<String, String> dictionary = new HashMap<>();
    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(width, height);
        root.getChildren().addAll(tileGroup);

        TextField wordText = new TextField("word");
        wordText.setTranslateX(10);
        wordText.setTranslateY(100);
        wordText.setId("word");

        Label meaningLabel = new Label(); //new Label("This is the skdjfdskf dsfe dksfdi dkdnfien sdkfdsfn ie kdsf ekddddddddddddddddddddddddddddddddddddddddddddddddddddddddddsfds meanig");
        meaningLabel.setTranslateX(10);
        meaningLabel.setTranslateY(130);
        meaningLabel.setWrapText(true);
        meaningLabel.setMaxWidth(200);

        Button searchButton = new Button("Search");
        searchButton.setTranslateX(200);
        searchButton.setTranslateY(100);


        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String key = wordText.getText();
                if(key.isBlank()){
                    meaningLabel.setText("Please enter a word");
                }
                else if(dictionary.containsKey(key)){
                    meaningLabel.setText(dictionary.get(key));
                }
                else {
                    meaningLabel.setText("Given word not found");
                }
            }
        });

        Label messageLabel = new Label();
        messageLabel.setTranslateX(10);
        messageLabel.setTranslateY(170);
        messageLabel.setTextFill(Color.RED);

        TextField newWordText = new TextField();
        newWordText.setTranslateX(10);
        newWordText.setTranslateY(200);
        newWordText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                messageLabel.setText("");
            }
        });

        TextField newMeaningText = new TextField();
        newMeaningText.setTranslateX(10);
        newMeaningText.setTranslateY(230);
        newMeaningText.setPrefHeight(30);
        newMeaningText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                messageLabel.setText("");
            }
        });


        Button addButton = new Button("Add");
        addButton.setTranslateX(200);
        addButton.setTranslateY(200);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String word = newWordText.getText();
                String meaning = newMeaningText.getText();
                if(word.isBlank()){
                    messageLabel.setText("Please enter a word");
                }
                else if(meaning.isBlank()){
                    messageLabel.setText("Please enter meaning");
                }
                else{
                    dictionary.put(word,meaning);
                    messageLabel.setText("Added to Dictionary Successfully");
                }
            }
        });






        dictionary.put("world", "collection of countris");
        dictionary.put("maha", "name sued to say more");

        tileGroup.getChildren().addAll( searchButton, wordText, meaningLabel,
                newWordText, newMeaningText, addButton, messageLabel);//, randResult,player2, player1, button1Player, button2Player, gameButton);
        return root;

    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("My Dictionary!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}