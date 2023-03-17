package JTEengine.Standard;

import JTEengine.Variables.JTEvariables;
import JTEengine.Window.JTEwindow;
import org.lwjgl.glfw.GLFW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.Arrays;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class JTEdebug {

    private final JFrame frame;
    private final JLabel secondsElapsed, FPS;
    private final JLabel winSize;
    private final JLabel totalMem, maxMem, freeMem, usedMem;
    private final JLabel versionM, versionD;
    private final JButton GC;
    private double seconds = 0;
    private final double startTime = GLFW.glfwGetTime();
    private final JTEwindow window;
    private int frames;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public JTEdebug(JTEwindow window) {
        this.window = window;

        frame = new JFrame("Debug Menu");
        secondsElapsed = new JLabel(String.valueOf(0));
        FPS = new JLabel(String.valueOf(0));
        GC = new JButton("Garbage Collector");

        totalMem = new JLabel("0");
        maxMem = new JLabel("0");
        freeMem = new JLabel("0");
        usedMem = new JLabel("0");

        winSize = new JLabel("0");

        versionM = new JLabel(JTEvariables.VERSION_M);
        versionD = new JLabel(JTEvariables.VERSION_D);

        Font stringFont = new Font( "Arial", Font.BOLD, 20 );

        secondsElapsed.setFont(stringFont);
        FPS.setFont(stringFont);
        totalMem.setFont(stringFont);
        maxMem.setFont(stringFont);
        freeMem.setFont(stringFont);
        usedMem.setFont(stringFont);
        winSize.setFont(stringFont);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);

        frame.add(secondsElapsed);
        frame.add(FPS);

        frame.add(totalMem);
        frame.add(maxMem);
        frame.add(freeMem);
        frame.add(usedMem);

        frame.add(winSize);

        frame.add(versionM);
        frame.add(versionD);

        frame.add(GC);

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
            float usedMemMB = totalMemMB-freeMemMB;

            String totalMemStr = "Total Memory: " + decimalFormat.format(totalMemMB) + "MB";
            String maxMemStr = "Max Memory: " + decimalFormat.format(maxMemMB) + "MB";
            String freeMemStr = "Free Memory: " + decimalFormat.format(freeMemMB) + "MB";
            String freeMemPercentage = decimalFormat.format((freeMemMB/totalMemMB)*100);
            String usedMemStr = "Used Memory: " + decimalFormat.format(usedMemMB) + "MB";
            String usedMemPercentage = decimalFormat.format((usedMemMB/totalMemMB)*100);

            versionM.setBounds(625, 25, 800, 20);
            versionD.setBounds(625, 45, 800, 20);

            FPS.setText(Fps);
            FPS.setBounds(25, 60, 800, 20);

            totalMem.setText(totalMemStr);
            totalMem.setBounds(25, 110, 800, 20);

            maxMem.setText(maxMemStr);
            maxMem.setBounds(25, 145, 800, 20);

            freeMem.setText(freeMemStr + " || " + freeMemPercentage + "% free");
            freeMem.setBounds(25, 180, 800, 20);

            usedMem.setText(usedMemStr + " || " + usedMemPercentage + "% used");
            usedMem.setBounds(25, 215, 800, 20);

            winSize.setText("Screen size: " + Arrays.toString(window.getWindowDimensions()));
            winSize.setBounds(25, 280, 800, 20);

            GC.setBounds(550,520,200,30);
            GC.addActionListener(e -> System.gc());

            secondsElapsed.setText(timeElapsed);
            secondsElapsed.setBounds(25, 25, 800, 20);
            frame.add(secondsElapsed);
        }
    }

    public void terminate() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
