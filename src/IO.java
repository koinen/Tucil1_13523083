import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    public void printColor(char a, int colorCode) {
        System.out.print(escCodes[colorCode] + a + resetCode);
    }
    public void imageSave(Board board, String fileName) {
        int r = board.getRow(), c = board.getCol();
        int width = 40 * r, height = 40 * c;

        // Create buffered image object
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        File f;

        for (int i = 0; i < r; i++) {
            for (int n = 0; n < 39; n++) {
                for (int j = 0; j < c; j++) {
                    for (int m = 0; m < 39; m++) {
                        img.setRGB(j*40+m, i*40+n, hexCodes[board.matrix[i][j] - 1]);
                    }
                }
            }
        }
        // write image
        try {
            f = new File("result/" + fileName);
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}