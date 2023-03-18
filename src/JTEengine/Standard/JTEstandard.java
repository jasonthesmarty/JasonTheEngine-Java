package JTEengine.Standard;

import JTEengine.Window.JTEwindow;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class JTEstandard {

    private double seconds;
    private float frames;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");


    public JTEstandard() {}

    public String fileContents(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            StringBuilder contents = new StringBuilder();
            while (scanner.hasNextLine()) {
                contents.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            return contents.toString();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return null;
        }
    }

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

    public float[] RGBAtoNormalized(float red, float green, float blue, float alpha) {
        float[] colors = {red, green, blue, alpha};

        // Red
        if (colors[0] > 255) {
            throw new IllegalArgumentException("RGB value: Red too high ( red > 255 ).");
        }
        else if (colors[0] < 0) {
            throw new IllegalArgumentException("RGB value: Red too high ( red < 0 ).");
        }
        else {
            colors[0] = colors[0]/255;
        }

        // Green
        if (colors[1] > 255) {
            throw new IllegalArgumentException("RGB value: Green too high ( green > 255 ).");
        }
        else if (colors[1] < 0) {
            throw new IllegalArgumentException("RGB value: Green too high ( green < 0 ).");
        }
        else {
            colors[1] = colors[1]/255;
        }

        // Blue
        if (colors[2] > 255) {
            throw new IllegalArgumentException("RGB value: Blue too high ( blue > 255 ).");
        }
        else if (colors[2] < 0) {
            throw new IllegalArgumentException("RGB value: Blue too high ( blue < 0 ).");
        }
        else {
            colors[2] = colors[2]/255;
        }

        // Alpha
        if (colors[3] > 255) {
            throw new IllegalArgumentException("Alpha value: Alpha too high ( alpha > 255 ).");
        }
        else if (colors[3] < 0) {
            throw new IllegalArgumentException("Alpha value: Alpha too high ( alpha < 0 ).");
        }
        else {
            colors[3] = colors[3]/255;
        }

        return colors;
    }

    public float[] PixelsToNormalizedCoords(float x, float y, JTEwindow window) {
        if (x > window.getWidth() || x < 0) {
            argumentException("Illegal x value");
        }
        if (y > window.getWidth() || y < 0) {
            argumentException("Illegal y value");
        }

        float widthHalved = window.getWidth()/2f;
        float heightHalved = window.getHeight()/2f;

        float X = ((1 - (x / widthHalved)) * -1);
        float Y = (1 - (y / heightHalved));

        return new float[] {X, Y};
    }

    public float[] PixelsToNormalizedCoordsQuad(float x, float y, float width, float height, JTEwindow window) {
        float widthHalved = window.getWidth()/2f;
        float heightHalved = window.getHeight()/2f;

        float X = ((1 - (x / widthHalved)) * -1);
        float Y = (1 - (y / heightHalved));

        float Width = (width/widthHalved);
        float Height = (height/heightHalved);

        return new float[] {
                X, Y, 0.0f,
                X + Width, Y, 0.0f,
                X + Width, Y - Height, 0.0f,
                X, Y - Height, 0.0f
        };
    }

    public String findFilenameFromPath(String path) {
        ArrayList<String> filenameList = new ArrayList<>(Arrays.asList(path.split("")));

        int lastSlashPlace = 0, periodPlace = 0;

        for (int i = 0; i < filenameList.size(); i ++) {
            if (filenameList.get(i).equals("\\")) {
                lastSlashPlace = i;
            }
            if (filenameList.get(i).equals(".")) {
                periodPlace = i;
            }
        }

        StringBuilder name = new StringBuilder();
        for (int j = lastSlashPlace+1; j < periodPlace; j++) {
            name.append(filenameList.get(j));
        }

        return name.toString();
    }

    // Print

    public void print() {
        System.out.println();
    }

    public void print(int number) {
        System.out.println(number);
    }

    public void print(int[] numbers) {
        System.out.println(Arrays.toString(numbers));
    }

    public void print(short shorter) {
        System.out.println(shorter);
    }

    public void print(short[] shorter) {
        System.out.println(Arrays.toString(shorter));
    }

    public void print(long number) {
        System.out.println(number);
    }

    public void print(long[] numbers) {
        System.out.println(Arrays.toString(numbers));
    }

    public void print(float number) {
        System.out.println(number);
    }

    public void print(float[] numbers) {
        System.out.println(Arrays.toString(numbers));
    }

    public void print(double number) {
        System.out.println(number);
    }

    public void print(double[] numbers) {
        System.out.println(Arrays.toString(numbers));
    }

    public void print(char character) {
        System.out.println(character);
    }

    public void print(char[] characters) {
        System.out.println(characters);
    }

    public void print(byte bytes) {
        System.out.println(bytes);
    }

    public void print(byte[] bytes) {
        System.out.println(Arrays.toString(bytes));
    }

    public void print(boolean bool) {
        System.out.println(bool);
    }

    public void print(boolean[] bools) {
        System.out.println(Arrays.toString(bools));
    }

    public void print(Object object) {
        System.out.println(object);
    }

    public void print(Object[] objects) {
        System.out.println(Arrays.toString(objects));
    }

    // Exceptions

    public void argumentException(String string) {
        throw new IllegalArgumentException(string);
    }

    public void runtimeException(String string) {
        throw new RuntimeException(string);
    }

    // Changers - String

    public String string(char chars) {
        return Character.toString(chars);
    }

    public String string(char[] chars) {
        return Arrays.toString(chars);
    }

    public String string(int integer) {
        return Integer.toString(integer);
    }

    public String string(int[] integers) {
        return Arrays.toString(integers);
    }

    public String string(short shorter) {
        return Short.toString(shorter);
    }

    public String string(short[] shorter) {
        return Arrays.toString(shorter);
    }

    public String string(long longer) {
        return Long.toString(longer);
    }

    public String string(long[] longers) {
        return Arrays.toString(longers);
    }

    public String string(float floater) {
        return Float.toString(floater);
    }

    public String string(float[] floaters) {
        return Arrays.toString(floaters);
    }

    public String string(double doubler) {
        return Double.toString(doubler);
    }

    public String string(double[] doublers) {
        return Arrays.toString(doublers);
    }

    public String string(boolean bool) {
        return Boolean.toString(bool);
    }

    public String string(boolean[] bools) {
        return Arrays.toString(bools);
    }

    public String string(byte bytes) {
        return Byte.toString(bytes);
    }

    public String string(byte[] bytes) {
        return Arrays.toString(bytes);
    }

    public String string(Object object) {
        return Objects.toString(object);
    }

    public String string(Object[] objects) {
        return Arrays.toString(objects);
    }


}

