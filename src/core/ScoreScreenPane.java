package core;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Parastoo , Date 03/05/2026
 *         ScoreScreenPane: Displays player scores in three separate level
 *         columns.
 *         Each column shows the top 10 players for that level.
 *         Sorted by: passed first, then score descending, then time ascending.
 *         Bottom section shows the last game played.
 */
public class ScoreScreenPane extends VBox {

    private Button backButton;

    public ScoreScreenPane(Runnable onBack) {
        super(15);
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(20));
        setPrefSize(1024, 768);
        setStyle("-fx-background-color: #f5f0e8;");

        // Title
        Label titleLabel = new Label("Score Screen");
        titleLabel.setFont(Font.font("SansSerif", FontWeight.BOLD, 32));

        // Three columns side by side
        HBox columnsBox = new HBox(20);
        columnsBox.setAlignment(Pos.TOP_CENTER);
        columnsBox.setPadding(new Insets(10));
        columnsBox.getChildren().addAll(
                buildLevelColumn(1),
                buildLevelColumn(2),
                buildLevelColumn(3));

        ScrollPane scrollPane = new ScrollPane(columnsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(450);
        scrollPane.setStyle("-fx-background: #f5f0e8;");

        // Last game section
        Label lastGameLabel = new Label("Your Last Game:");
        lastGameLabel.setFont(Font.font("SansSerif", FontWeight.BOLD, 18));

        HBox lastGameBox = buildLastGameRow();

        // Back button
        backButton = new Button("Back to Menu");
        backButton.setPrefWidth(200);
        backButton.setOnAction(e -> onBack.run());

        getChildren().addAll(titleLabel, scrollPane, lastGameLabel, lastGameBox, backButton);
    }

    /**
     * @author Parastoo , Date 03/05/2026
     *         Builds a leaderboard column for a specific level.
     */
    private VBox buildLevelColumn(int level) {
        VBox column = new VBox(6);
        column.setAlignment(Pos.TOP_CENTER);
        column.setPrefWidth(355);
        column.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 12;");

        /**
         * @author Parastoo , Date 08/05/2026
         * Renamed Level to Difficulty in column title.
         */
        // Column title
        Label colTitle = new Label("Difficulty " + level);
        // Parastoo: code change ends here.
        colTitle.setFont(Font.font("SansSerif", FontWeight.BOLD, 20));
        colTitle.setStyle("-fx-text-fill: #333333;");
        column.getChildren().add(colTitle);

        // Header row
        String[] headers = { "Name", "Score", "Passed", "Time", "Seed" };
        int[] widths = { 65, 50, 55, 50, 120 };

        HBox headerRow = new HBox();
        headerRow.setAlignment(Pos.CENTER);
        for (int i = 0; i < headers.length; i++) {
            Label h = new Label(headers[i]);
            h.setFont(Font.font("SansSerif", FontWeight.BOLD, 13));
            h.setMinWidth(widths[i]);
            h.setPrefWidth(widths[i]);
            h.setAlignment(Pos.CENTER);
            h.setStyle(
                    "-fx-background-color: #d0d0d0;" +
                            "-fx-padding: 5;" +
                            "-fx-alignment: center;");
            headerRow.getChildren().add(h);
        }
        column.getChildren().add(headerRow);

        // Get and sort users for this level
        List<UserAccount> users = UserManager.getAllUsers().stream()
                .filter(u -> u.getLevel() == level)
                .sorted(Comparator
                        .comparing(UserAccount::isPassed).reversed()
                        .thenComparing(Comparator.comparingInt(UserAccount::getScore).reversed())
                        .thenComparingLong(UserAccount::getTimeTaken))
                .limit(10)
                .collect(Collectors.toList());

        if (users.isEmpty()) {
            Label noData = new Label("No players yet");
            noData.setFont(Font.font("SansSerif", 13));
            noData.setStyle("-fx-text-fill: #888888;");
            column.getChildren().add(noData);
        } else {
            for (int row = 0; row < users.size(); row++) {
                UserAccount u = users.get(row);
                String rowColor = row % 2 == 0 ? "#f9f9f9" : "#ffffff";

                String[] values = {
                        u.getName(),
                        String.valueOf(u.getScore()),
                        u.isPassed() ? "Yes" : "No",
                        formatTime(u.getTimeTaken()),
                        String.valueOf(u.getSeed())
                };

                HBox dataRow = new HBox();
                dataRow.setAlignment(Pos.CENTER);
                for (int col = 0; col < values.length; col++) {
                    Label cell = new Label(values[col]);
                    cell.setFont(Font.font("SansSerif", 13));
                    cell.setMinWidth(widths[col]);
                    cell.setPrefWidth(widths[col]);
                    cell.setAlignment(Pos.CENTER);
                    cell.setStyle(
                            "-fx-background-color: " + rowColor + ";" +
                                    "-fx-padding: 5;" +
                                    "-fx-alignment: center;");
                    dataRow.getChildren().add(cell);
                }
                column.getChildren().add(dataRow);
            }
        }

        return column;
    }

    /**
     * @author Parastoo , Date 03/05/2026
     *         Builds the last game row showing the most recent game played.
     */
    private HBox buildLastGameRow() {
        HBox box = new HBox(30);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15));
        box.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #aaaaaa;" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;");

        List<UserAccount> users = UserManager.getAllUsers();
        if (users.isEmpty()) {
            Label noData = new Label("No games played yet.");
            noData.setFont(Font.font("SansSerif", 14));
            box.getChildren().add(noData);
            return box;
        }

        UserAccount last = users.get(users.size() - 1);
        /**
         * @author Parastoo , Date 08/05/2026
         * Renamed Level to Difficulty in last game summary.
         */
        String[] labels = { "Name:", "Difficulty:", "Score:", "Passed:", "Time:", "Seed:" };
        String[] values = {
                last.getName(),
                last.getLevel() == 0 ? "-" : "Difficulty " + last.getLevel(),
                String.valueOf(last.getScore()),
                last.isPassed() ? "Yes" : "No",
                formatTime(last.getTimeTaken()),
                String.valueOf(last.getSeed())
        };
        // Parastoo: code change ends here.

        for (int i = 0; i < labels.length; i++) {
            VBox item = new VBox(4);
            item.setAlignment(Pos.CENTER);
            Label lbl = new Label(labels[i]);
            lbl.setFont(Font.font("SansSerif", FontWeight.BOLD, 14));
            Label val = new Label(values[i]);
            val.setFont(Font.font("SansSerif", 14));
            item.getChildren().addAll(lbl, val);
            box.getChildren().add(item);
        }

        return box;
    }

    public Button getBackButton() {
        return backButton;
    }

    /**
     * @author Parastoo , Date 03/05/2026
     *         Formats seconds into MM:SS format.
     */
    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
}
// Parastoo: code change ends here.