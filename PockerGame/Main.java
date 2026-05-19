package org.home.pocker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private PokerGame game;


    private final HBox dealerBox = new HBox(15);
    private final HBox playerBox = new HBox(15);
    private final HBox communityBox = new HBox(15);

    private final Label status = new Label("Welcome To Casino Poker");
    private final Label potLabel = new Label("Pot: $0");
    private final Label chipsLabel = new Label("Chips: $1000");


    private int playerChips = 1000;
    private int phase = 0;

    private PokerCard p1, p2, d1, d2;

    @Override
    public void start(Stage stage) {

        Button startBtn = new Button("START ROUND");
        Button nextBtn = new Button("NEXT");
        Button betBtn = new Button("BET 100");
        Button callBtn = new Button("CALL");
        Button foldBtn = new Button("FOLD");

        startBtn.getStyleClass().add("game-button");
        nextBtn.getStyleClass().add("game-button");
        betBtn.getStyleClass().add("game-button");
        callBtn.getStyleClass().add("game-button");
        foldBtn.getStyleClass().add("game-button");

        startBtn.setOnAction(e -> startGame());
        nextBtn.setOnAction(e -> nextPhase());
        betBtn.setOnAction(e -> {
            if (playerChips >= 100) {
                playerChips -= 100;
                game.placeBet(100);
                updateUI();

                status.setText("Player Bet $100");
            }
        });

        callBtn.setOnAction(e -> {
            if (game == null) return;
            int amount = game.getCurrentBet();
            if (playerChips >= amount) {
                playerChips -= amount;
                game.call(amount);
                updateUI();

                status.setText("Player Called");
            }
        });

        foldBtn.setOnAction(e -> {
            status.setText("Player Folded");
            dealerBox.getChildren().clear();
            addCard(dealerBox, d1);
            addCard(dealerBox, d2);
        });

        Label title = new Label("CASINO POKER");
        title.getStyleClass().add("title");

        Label dealerTitle = new Label("DEALER");
        dealerTitle.getStyleClass().add("section-title");

        Label communityTitle = new Label("TABLE");
        communityTitle.getStyleClass().add("section-title");

        Label playerTitle = new Label("PLAYER");
        playerTitle.getStyleClass().add("section-title");

        HBox dealerSection = new HBox(10, dealerTitle, dealerBox);
        HBox communitySection = new HBox(10, communityTitle, communityBox);
        VBox playerSection = new VBox(10, playerTitle, playerBox);

        dealerSection.setAlignment(Pos.CENTER);
        communitySection.setAlignment(Pos.CENTER);
        playerSection.setAlignment(Pos.CENTER);

        VBox infoBox = new VBox(
                8,
                potLabel,
                chipsLabel,
                status
        );

        infoBox.setAlignment(Pos.CENTER);

        HBox controls = new HBox(
                15,
                startBtn,
                nextBtn,
                betBtn,
                callBtn,
                foldBtn
        );

        controls.setAlignment(Pos.CENTER);

        VBox table = new VBox(
                35,
                title,
                dealerSection,
                communitySection,
                playerSection,
                infoBox,
                controls
        );

        table.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();

        root.setCenter(table);

        BorderPane.setMargin(table, new Insets(20));

        Scene scene = new Scene(root, 1100, 750);
        scene.getStylesheets().add(
                getClass()
                        .getResource("/styles.css")
                        .toExternalForm()
        );

        stage.setTitle("Poker Game");
        stage.setScene(scene);
        stage.show();
    }

    private void startGame() {
        game = new PokerGame();
        phase = 0;

        playerBox.getChildren().clear();
        dealerBox.getChildren().clear();
        communityBox.getChildren().clear();

        p1 = game.deal();
        p2 = game.deal();
        d1 = game.deal();
        d2 = game.deal();

        addCard(playerBox, p1);
        addCard(playerBox, p2);

        Label hidden = new Label("🂠 🂠");
        hidden.getStyleClass().add("hidden-card");
        dealerBox.getChildren().add(hidden);
        status.setText("Pre-Flop");
        updateUI();
    }

    private void nextPhase() {
        if (game == null) return;
        phase++;
        switch (phase) {
            case 1 -> {
                game.flop();showCommunity();status.setText("FLOP");
            }

            case 2 -> {
                game.turn();showCommunity();status.setText("TURN");
            }
            case 3 -> {
                game.river();showCommunity();status.setText("RIVER");
            }

            case 4 -> {
                revealDealer();
                List<PokerCard> player = new ArrayList<>();
                player.add(p1);
                player.add(p2);
                player.addAll(game.community());
                List<PokerCard> dealer = new ArrayList<>();

                dealer.add(d1);
                dealer.add(d2);
                dealer.addAll(game.community());

                status.setText(game.winner(player, dealer));
            }
        }
    }

    private void showCommunity() {

        communityBox.getChildren().clear();

        for (PokerCard c : game.community()) {

            addCard(communityBox, c);
        }
    }

    private void revealDealer() {

        dealerBox.getChildren().clear();

        addCard(dealerBox, d1);
        addCard(dealerBox, d2);
    }

    private void addCard(HBox box, PokerCard card) {

        Label label = new Label(card.toString());

        label.getStyleClass().add("card");

        // RED SUITS

        if (card.toString().contains("♥") ||
                card.toString().contains("♦")) {

            label.setStyle("""
                    -fx-text-fill: red;
                    """);
        }

        box.getChildren().add(label);
    }

    private void updateUI() {

        if (game == null) return;

        potLabel.setText(
                "Pot: $" + game.getPot()
        );

        chipsLabel.setText(
                "Chips: $" + playerChips
        );
    }

    public static void main(String[] args) {
        launch();
    }
}
