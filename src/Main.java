import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) { // Default run, GUI
            GUI.show();
        }
        else if (args.length == 1 && Objects.equals(args[0], "CLI")){ // CLI version, accessed through
            Scanner consoleInput = new Scanner(System.in);
            while (true) {
                System.out.print("Enter the Path (type EXIT to exit) : ");
                // Reading File name
                String path = consoleInput.nextLine();
                if (Objects.equals(path, "EXIT")) {
                    break;
                }
                path = "test/" + path;
                File file = new File(path);
                Game game = IO.readConfigFile(file);
                if (game.invalid) {
                    continue;
                }
                game.solve();
                System.out.println("\nIterations: " + game.iterations);
                System.out.println("Runtime: " + game.runtime + " ms");
                if (game.solved) {
                    System.out.println("Solved!");
                    game.board.printBoard();
                    while (true) {
                        System.out.print("Save the results? (y/n) ");
                        String input = consoleInput.nextLine();
                        if (Objects.equals(input, "y")) {
                            System.out.println("1. Image");
                            System.out.println("2. Text\n>> ");
                            if (consoleInput.nextInt() == 1) {
                                String dest;
                                do {
                                    System.out.print("File name: ");
                                    dest = consoleInput.nextLine();
                                } while (dest.isEmpty());
                                IO helper = new IO();
                                helper.imageSave(helper.generateImage(game.board), dest);
                                break;
                            } else if (consoleInput.nextInt() == 2) {
                                String dest;
                                do {
                                    System.out.print("File name: ");
                                    dest = consoleInput.nextLine();
                                } while (dest.isEmpty());
                                IO helper = new IO();
                                helper.saveText(game.board, dest);
                                break;
                            }
                        } else if (Objects.equals(input, "n")) {
                            System.out.println("Thanks for playing!");
                            break;
                        }
                    }
                } else {
                    System.out.println("Unsolvable.");
                }
            }
            consoleInput.close();
        }
    }
}