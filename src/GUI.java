import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends Application {
    public static void show() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("IQ Puzzler Pro Puzzle-Solver");
        Button btn = new Button();
        btn.setText("Select Input Config File");
        btn.setOnAction(ae -> {
            final FileChooser fileChooser = new FileChooser();
            File inputPath = new File("./test/");
            fileChooser.setInitialDirectory(inputPath);
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                Game game = IO.readConfigFile(file);
                if (game.invalid) {
                    Alert invalid = new Alert(Alert.AlertType.ERROR, "Invalid Config File!", ButtonType.CLOSE);
                    invalid.show();
                } else {
                    game.solve();
                    IO imageMaker = new IO();
                    if (game.solved) {
                        Stage popup = new Stage();
                        VBox vb = new VBox(10);
                        Label label = new Label("Solved!");
                        label.setFont(new Font(24));
                        vb.getChildren().add(label);
                        label = new Label("Final Board Result:");
                        label.setFont(new Font(18));
                        vb.getChildren().add(label);
                        BufferedImage boardImage = imageMaker.generateImage(game.board);
                        vb.getChildren().add(new ImageView(SwingFXUtils.toFXImage(boardImage, null)));
                        String stats = "Runtime: " + game.runtime + "ms" + "    Iterations: " + game.iterations;
                        vb.getChildren().add(new Label(stats));
                        label = new Label("File Name: ");
                        TextField fileName = new TextField();
                        fileName.setMaxWidth(100);
                        vb.getChildren().add(label);
                        vb.getChildren().add(fileName);
                        Button saveImage = new Button();
                        saveImage.setText("Save as Image");
                        saveImage.setOnAction(event -> {
                            imageMaker.imageSave(boardImage, fileName.getText());
                            popup.close();
                        });
                        vb.getChildren().add(saveImage);
                        Button saveText = new Button();
                        saveText.setText("Save as Text");
                        saveText.setOnAction(event-> {
                            try {
                                imageMaker.saveText(game.board, fileName.getText());
                            } catch (IOException e) {
                                Alert err = new Alert(Alert.AlertType.ERROR, "File writing error!", ButtonType.CLOSE);
                                err.show();
                            }
                            popup.close();
                        });
                        vb.getChildren().add(saveText);
                        vb.setAlignment(Pos.CENTER);
                        popup.setScene(new Scene(vb, 920, 660));
                        popup.initModality(Modality.APPLICATION_MODAL);
                        popup.setTitle("Results");
                        popup.show();
                    } else {
                        Alert unsolved = new Alert(Alert.AlertType.INFORMATION, "Unsolvable!\nRuntime: " + game.runtime + "ms, Iterations: " + game.iterations, ButtonType.CLOSE);
                        unsolved.show();
                    }
                }
            }
        });

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        Label label = new Label("Tucil Stima 1 - 13523083");
        label.setFont(new Font(24));
        root.getChildren().add(label);
        label = new Label("Welcome to the Playground!");
        label.setFont(new Font(30));
        File f = new File("image/ekko.jpg");
        Image image = new Image(f.toURI().toString());
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        root.getChildren().add(label);
        root.getChildren().add(iv1);
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}