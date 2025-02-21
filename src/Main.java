import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // read txt file
        Scanner consoleInput = new Scanner(System.in);

        System.out.print("Enter the Path : ");

        // Reading File name
        String path = "test/" + consoleInput.nextLine();

        // pass the path to the file as a parameter
        File file = new File(path);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                int n, m, p;
                if (sc.hasNextInt()) {
                    n = sc.nextInt();
//                    System.out.println("n: " + n);
                } else {
                    break;
                }
                if (sc.hasNextInt()) {
                    m = sc.nextInt();
//                    System.out.println("m: " + m);
                } else {
                    break;
                }
                if (sc.hasNextInt()) {
                    p = sc.nextInt();
//                    System.out.println("p: " + p);
                } else {
                    break;
                }
                sc.nextLine();
                sc.nextLine();
                Piece[] pieces = new Piece[p];
                ArrayList<String> lastPiece = new ArrayList<>();
                String temp = sc.nextLine();
                for (int i = 0; i < p && sc.hasNextLine(); i++) {
                    ArrayList<String> pieceString = new ArrayList<>();
                    pieceString.add(temp);
                    String line = sc.nextLine();
                    while (Piece.lineCheck(line) == i + 1 && sc.hasNextLine()) {
                        pieceString.add(line);
                        line = sc.nextLine();
                    }
                    temp = line;
                    if (!sc.hasNextLine()) {
                        if (Piece.lineCheck(line) == i + 1) {
                            pieceString.add(line);
                        } else {
                            lastPiece.add(line);
                        }
                    }
                    pieces[i] = new Piece(pieceString, i + 1);
//                    pieces[i].printPiece();
                }
                if (!lastPiece.isEmpty()) {
                    pieces[p - 1] = new Piece(lastPiece, p);
//                    pieces[p - 1].printPiece();
                }
                Solution solution = new Solution(pieces, new Board(n, m));
                System.out.println("\nIterations: " + solution.iterations);
                System.out.println("Runtime: " + solution.runtime + " ms");
                if (solution.solved) {
                    System.out.println("Solved!");
                    solution.board.printBoard();
                    while (true) {
                        System.out.print("Save the results? (y/n) ");
                        String input = consoleInput.nextLine();
                        if (Objects.equals(input, "y")) {
                            String dest;
                            do {
                                System.out.print("File name: ");
                                dest = consoleInput.nextLine();
                            } while (dest.isEmpty());
                            IO helper = new IO();
                            helper.imageSave(solution.board, dest);
                            System.out.println("Image successfully saved to " + dest);
                            break;
                        } else if (Objects.equals(input, "n")) {
                            System.out.println("Thanks for playing!");
                            break;
                        }
                    }
                } else {
                    System.out.println("Not solved.");
                }
            }
            sc.close();
            consoleInput.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}