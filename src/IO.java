import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.max;

public class IO {
    private final String[] escCodes;
    private final String resetCode;
    private final int[] hexCodes;
    public IO() {
        escCodes = new String[26];
        escCodes[0] = "\033[38;5;1m";
        escCodes[1] = "\033[38;5;27m";
        escCodes[2] = "\033[38;5;93m";
        escCodes[3] = "\033[38;5;4m";
        escCodes[4] = "\033[38;5;5m";
        escCodes[5] = "\033[38;5;6m";
        escCodes[6] = "\033[38;5;7m";
        escCodes[7] = "\033[38;5;8m";
        escCodes[8] = "\033[38;5;9m";
        escCodes[9] = "\033[38;5;10m";
        escCodes[10] = "\033[38;5;11m";
        escCodes[11] = "\033[38;5;12m";
        escCodes[12] = "\033[38;5;13m";
        escCodes[13] = "\033[38;5;14m";
        escCodes[14] = "\033[38;5;21m";
        escCodes[15] = "\033[38;5;196m";
        escCodes[16] = "\033[38;5;213m";
        escCodes[17] = "\033[38;5;3m";
        escCodes[18] = "\033[38;5;220m";
        escCodes[19] = "\033[38;5;202m";
        escCodes[20] = "\033[38;5;117m";
        escCodes[21] = "\033[38;5;100m";
        escCodes[22] = "\033[38;5;34m";
        escCodes[23] = "\033[38;5;225m";
        escCodes[24] = "\033[38;5;111m";
        escCodes[25] = "\033[38;5;243m";
        resetCode = "\033[0m";
        hexCodes = new int[26];
        hexCodes[0] = 0x800000;
        hexCodes[1] = 0x005fff;
        hexCodes[2] = 0x8700ff;
        hexCodes[3] = 0x000080;
        hexCodes[4] = 0x800080;
        hexCodes[5] = 0x008080;
        hexCodes[6] = 0xc0c0c0;
        hexCodes[7] = 0x808080;
        hexCodes[8] = 0xff0000;
        hexCodes[9] = 0x00ff00;
        hexCodes[10] = 0xffff00;
        hexCodes[11] = 0x0000ff;
        hexCodes[12] = 0xff00ff;
        hexCodes[13] = 0x00ffff;
        hexCodes[14] = 0x0000ff;
        hexCodes[15] = 0xff8080;
        hexCodes[16] = 0xff87ff;
        hexCodes[17] = 0x808000;
        hexCodes[18] = 0xffd700;
        hexCodes[19] = 0xff5f00;
        hexCodes[20] = 0x87d7ff;
        hexCodes[21] = 0x878700;
        hexCodes[22] = 0x00af00;
        hexCodes[23] = 0xffd7ff;
        hexCodes[24] = 0x87afff;
        hexCodes[25] = 0x767676;
    }
    public static Game readConfigFile(File file) {
        try {
            Scanner sc = new Scanner(file);
            int n, m, p;
            if (sc.hasNextInt()) {
                n = sc.nextInt();
            } else {
                throw new IOException("Invalid config file!");
            }
            if (sc.hasNextInt()) {
                m = sc.nextInt();
            } else {
                throw new IOException("Invalid config file!");
            }
            if (sc.hasNextInt()) {
                p = sc.nextInt();
            } else {
                throw new IOException("Invalid config file!");
            }
            sc.nextLine();
            if (!Objects.equals(sc.nextLine(), "DEFAULT")) {
                throw new IOException("Invalid config file!");
            }
            Piece[] pieces = new Piece[p];
            ArrayList<String> lastPiece = new ArrayList<>();
            String temp = sc.nextLine();
            Map<Integer, Boolean> occurred = new HashMap<>();
            int i;
            for (i = 0; i < p && sc.hasNextLine(); i++) {
                ArrayList<String> pieceString = new ArrayList<>();
                pieceString.add(temp);
                String line = sc.nextLine();
                int currentPiece = Piece.lineCheck(temp);
                if (occurred.get(currentPiece) == null) {
                    occurred.put(currentPiece, true);
                } else {
                    return new Game();
                }
                while (Piece.lineCheck(line) == currentPiece && sc.hasNextLine()) {
                    pieceString.add(line);
                    line = sc.nextLine();
                }
                temp = line;
                if (!sc.hasNextLine()) {
                    if (Piece.lineCheck(line) == currentPiece) {
                        pieceString.add(line);
                    } else {
                        lastPiece.add(line);
                    }
                }
                pieces[i] = new Piece(pieceString);
            }
            if (!lastPiece.isEmpty() && pieces[p-1] == null) {
                pieces[p - 1] = new Piece(lastPiece);
            } else if (i < p || (!lastPiece.isEmpty() && pieces[p-1] != null)) {
                return new Game();
            }
            return new Game(pieces, new Board(n, m));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Config file invalid!");
        }
        return new Game();
    }

    public void printColor(char a, int colorCode) {
        System.out.print(escCodes[colorCode] + a + resetCode);
    }

    public void saveText(Board board, String fileName) {
        try (FileWriter f = new FileWriter("result/" + fileName)) {
            for (int i = 0; i < board.getRow(); i++) {
                for (int j = 0; j < board.getCol(); j++) {
                    f.write((char) ('A' + (board.matrix[i][j] - 1)));
                }
                if (i == board.getRow() - 1) break;
                f.write('\n');
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public BufferedImage generateImage(Board board) {
        int r = board.getRow(), c = board.getCol();
        int size = 400 / max(r, c);
        int height = size * r - 1, width = size * c - 1;

        // Create buffered image object
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < r; i++) {
            for (int n = 0; n < size-1; n++) {
                for (int j = 0; j < c; j++) {
                    for (int m = 0; m < size-1; m++) {
                        img.setRGB(j*size+m, i*size+n, hexCodes[board.matrix[i][j] - 1]);
                    }
                }
            }
        }
        return img;
    }

    public void imageSave(BufferedImage img, String fileName) {
        File f;
        // write image
        try {
            f = new File("result/" + fileName);
            ImageIO.write(img, "png", f);
            System.out.println("Result successfully saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}