package com.example.dictonary;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class HelloApplication extends Application {
    int height = 400;
    int width = 400;
    private Group tileGroup = new Group();



    int xLine = 20;
    int yLine1 = 20;
    int yLine2 = 50;
    int yLine3 = 110;

    DictionaryUsingHashMap hashDictionary = new DictionaryUsingHashMap();

    ListView<String> suggestedWordList;

    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(width, height);
        root.getChildren().addAll(tileGroup);

        TextField wordText = new TextField("word");
        wordText.setTranslateX(xLine);
        wordText.setTranslateY(yLine1);
        wordText.setId("word");
        wordText.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String prefix = wordText.getText();
                if(prefix.length()>=3){
                    suggestedWordList.getItems().clear();
                    ArrayList<String> suggestions = hashDictionary.getSuggestions(prefix);
                    if(suggestions.size()>0){
                        suggestedWordList.getItems().addAll(suggestions);
                    }
                }
                else {
                    suggestedWordList.getItems().clear();
                }
            }
        });

        Button searchButton = new Button("Search");
        searchButton.setTranslateX(xLine+180);
        searchButton.setTranslateY(yLine1);
        searchButton.setMinWidth(150);

        suggestedWordList = new ListView();
        suggestedWordList.setTranslateX(xLine);
        suggestedWordList.setTranslateY(yLine2);
        suggestedWordList.setMaxSize(330, 50);
        suggestedWordList.setMinSize(330,50);
        String[] suggestedList = {"Angad", "Ankit", "Abdsdfe", "kdfdfie ", "sdfdfewf"};
        suggestedWordList.getItems().addAll(suggestedList);
        suggestedWordList.setOrientation(Orientation.HORIZONTAL);

        Label meaningLabel = new Label();

        suggestedWordList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String selectedWord = suggestedWordList.getSelectionModel().getSelectedItem();
                wordText.setText(selectedWord);
                meaningLabel.setText(hashDictionary.findMeaning(selectedWord));
            }
        });

        Border b = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
         //new Label("This is the skdjfdskf dsfe dksfdi dkdnfien sdkfdsfn ie kdsf ekddddddddddddddddddddddddddddddddddddddddddddddddddddddddddsfds meanig");
        meaningLabel.setTranslateX(xLine);
        meaningLabel.setTranslateY(yLine3);
        meaningLabel.setWrapText(true);
        meaningLabel.setBorder(b);
        meaningLabel.setMinSize(330,100);
        meaningLabel.setMaxSize(330,100);

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String key = wordText.getText();
                if(key.isBlank()){
                    meaningLabel.setText("Please enter a word");
                }
                else  {
                    meaningLabel.setText(hashDictionary.findMeaning(key));
                }
//                else if(dictionary.containsKey(key)){
//                    meaningLabel.setText(dictionary.get(key));
//                }
//                else {
//                    meaningLabel.setText("Given word not found");
//                }
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
                    //dictionary.put(word,meaning);
                    messageLabel.setText("Added to Dictionary Successfully");
                }
            }
        });


        hashDictionary.addWord("world", "collection of countris");
        hashDictionary.addWord("maha", "name sued to say more");

        tileGroup.getChildren().addAll( searchButton, wordText, meaningLabel, suggestedWordList);
               // newWordText, newMeaningText, addButton, messageLabel);
//, randResult,player2, player1, button1Player, button2Player, gameButton);
        return root;

    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("My Dictionary!");
        stage.setScene(scene);
        stage.show();
    }

    public static void demoDBConnection(){
        final String DB_URL = "jdbc:mysql://localhost:3306/dictdb";
        final String USER = "root";
        final String PASS = "angad123";

        System.out.println("Connecting to database");
        String newId = "select * from dict_words";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(newId);
        ) {
            while (rs.next()) {
                //Display values

                System.out.println(rs.getInt("id") + rs.getString("word") + rs.getString("meaning")); //rs.getInt("rollno");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        demoDBConnection();
        launch();
    }
}