package JTEengine.Standard;

import JTEengine.Window.JTEwindow;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class JTEstandard {

    private double seconds;

    @SuppressWarnings({"unused", "SpellCheckingInspection"})
    public JTEstandard() {}

    public String fileContents(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String contents = "";
            while (scanner.hasNextLine()) {
                contents = contents + scanner.nextLine() + "\n";
            }
            scanner.close();
            return contents.toString();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return null;
        }
    }

    float frames;
    final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public void debugMenu(JTEwindow window, float secondsBetweenPrint) {
        long totalMem = Runtime.getRuntime().totalMemory();
        long maxMem = Runtime.getRuntime().maxMemory();
        long freeMem = Runtime.getRuntime().freeMemory();

        float totalMemMB = (float)totalMem/1_000_000f;
        float maxMemMB = (float)maxMem/1_000_000f;
        float freeMemMB = (float)freeMem/1_000_000f;

        frames++;
        if (GLFW.glfwGetTime() > seconds + secondsBetweenPrint) {
            seconds = GLFW.glfwGetTime();
            System.out.println("========== Debug Menu ==========");
            System.out.println();

            System.out.println("=========== Window ===========");
            System.out.println("FPS: " + (Math.floor(frames)/secondsBetweenPrint));
            seconds = GLFW.glfwGetTime();
            frames = 0;
            System.out.println();

            System.out.println("=========== Memory ===========");
            System.out.println("Total Memory: " + decimalFormat.format(totalMemMB) + "MB");
            System.out.println("Maximum Memory: " + decimalFormat.format(maxMemMB) + "MB");
            System.out.println("Free Memory: " + decimalFormat.format(freeMemMB) + "MB");
            System.out.println("Memory being used: " + decimalFormat.format((totalMemMB-freeMemMB)) + "MB");
            System.out.println("================================");
        }
    }
}
