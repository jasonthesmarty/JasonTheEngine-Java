package JTEengine.Standard;

import JTEengine.Window.JTEwindow;
import org.lwjgl.glfw.GLFW;

import javax.swing.*;
import java.awt.event.WindowEvent;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class JTEdebug {

    private final JTEwindow window;
    private JFrame frame;
    private boolean havent = true;
    private int frames = 0;
    private int i = 0;
    private double seconds = 0;
    private JLabel label;

    public JTEdebug(JTEwindow window) {
        this.window = window;

        frame = new JFrame("Debug Menu");
        label = new JLabel(String.valueOf(i));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.add(label);
        frame.setVisible(true);
        frame.setLayout(null);
    }

    public void render() {
        if (GLFW.glfwGetTime() > seconds + 1) {
            seconds = GLFW.glfwGetTime();

            String seconds = "Seconds Elapsed: " + i;

            label.setText(seconds); i++;
            label.setBounds(25, 25, 800, 20);
            frame.add(label);
        }
    }

    public void terminate() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
