package JTEengine.Window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class JTEinput {

    private final GLFWKeyCallback keyboard;
    private final GLFWMouseButtonCallback mouse;
    private final GLFWCursorPosCallback position;

    private final static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private final static boolean[] buttons = new boolean[GLFW.GLFW_KEY_LAST];
    private static double mouseXpos, mouseYpos;

    // Constructor

    public JTEinput() {
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = action != GLFW.GLFW_RELEASE;
            }
        };

        mouse = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = action != GLFW.GLFW_RELEASE;
            }
        };

        position = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseXpos = xpos;
                mouseYpos = ypos;
            }
        };
    }

    // Callback functions getters

    public GLFWKeyCallback keyboardCallback() {
        return keyboard;
    }

    public GLFWMouseButtonCallback mouseButtonCallback() {
        return mouse;
    }

    public GLFWCursorPosCallback mousePositionCallback() {
        return position;
    }

    // Mouse position Getters

    public double getMousePositionX() {
        return mouseXpos;
    }

    public double getMousePositionY() {
        return mouseYpos;
    }

    public double[] getMousePosition() {
        return new double[] {mouseXpos, mouseYpos};
    }

    // Killing input

    public void terminate() {
        keyboard.free();
        mouse.free();
        position.free();
    }

    // Checking functions for keys/buttons

    public boolean keyDown(int key) {
        return keys[key];
    }

    public boolean mouseDown(int button) {
        return buttons[button];
    }
}
