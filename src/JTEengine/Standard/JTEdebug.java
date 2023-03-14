package JTEengine.Standard;

import JTEengine.Window.JTEwindow;
import org.lwjgl.glfw.GLFW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class JTEdebug {

    private final JFrame frame;
    private final JLabel secondsElapsed, FPS;
    private final JLabel totalMem, maxMem, freeMem, usedMem;
    private double seconds = 0;
    private final double startTime = GLFW.glfwGetTime();
    private JTEwindow window;
    private int frames;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public JTEdebug(JTEwindow window) {
        this.window = window;

        frame = new JFrame("Debug Menu");
        secondsElapsed = new JLabel(String.valueOf(0));
        FPS = new JLabel(String.valueOf(0));

        totalMem = new JLabel("0");
        maxMem = new JLabel("0");
        freeMem = new JLabel("0");
        usedMem = new JLabel("0");

        Font stringFont = new Font( "Arial", Font.BOLD, 20 );

        secondsElapsed.setFont(stringFont);
        FPS.setFont(stringFont);
        totalMem.setFont(stringFont);
        maxMem.setFont(stringFont);
        freeMem.setFont(stringFont);
        usedMem.setFont(stringFont);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);

        frame.add(secondsElapsed);
        frame.add(FPS);

        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLayout(null);
    }

    public void render() {
        frames++;
        if (GLFW.glfwGetTime() > seconds + 1) {
            seconds = GLFW.glfwGetTime();
            long elapsed = (long)seconds - (long)startTime;

            String timeElapsed = "Seconds Elapsed: " + elapsed;
            String Fps = "FPS: " + frames;
            frames = 0;

            long totalMemLong = Runtime.getRuntime().totalMemory();
            long maxMemLong = Runtime.getRuntime().maxMemory();
            long freeMemLong = Runtime.getRuntime().freeMemory();

            float totalMemMB = (float)totalMemLong/1_000_000f;
            float maxMemMB = (float)maxMemLong/1_000_000f;
            float freeMemMB = (float)freeMemLong/1_000_000f;

            String totalMemStr = "Total Memory: " + decimalFormat.format(totalMemMB) + "MB";
            String maxMemStr = "Max Memory: " + decimalFormat.format(maxMemMB) + "MB";
            String freeMemStr = "Free Memory: " + decimalFormat.format(freeMemMB) + "MB";
            String usedMemStr = "Used Memory: " + decimalFormat.format(totalMemMB-freeMemMB) + "MB";

            secondsElapsed.setText(timeElapsed);
            secondsElapsed.setBounds(25, 25, 800, 20);
            frame.add(secondsElapsed);

            FPS.setText(Fps);
            FPS.setBounds(25, 60, 800, 20);
            frame.add(FPS);

            totalMem.setText(totalMemStr);
            totalMem.setBounds(25, 110, 800, 20);
            frame.add(totalMem);

            maxMem.setText(maxMemStr);
            maxMem.setBounds(25, 145, 800, 20);
            frame.add(maxMem);

            freeMem.setText(freeMemStr);
            freeMem.setBounds(25, 180, 800, 20);
            frame.add(freeMem);

            usedMem.setText(usedMemStr);
            usedMem.setBounds(25, 215, 800, 20);
            frame.add(usedMem);
        }
    }

    public void terminate() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
