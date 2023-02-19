package JTEengine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class JTEwindow {
    private int width, height;
    private String title;
    private long window;

    public JTEwindow(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() {
        GLFW.glfwInit();

        if (!GLFW.glfwInit()) {
            throw new RuntimeException("GLFW not initialize.");
        }

        window = GLFW.glfwCreateWindow(this.width, this.height, this.title, 0, 0);

        if (window == 0) {
            throw new RuntimeException("Window was not created.");
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);
    }

    public void update() {
        GLFW.glfwSwapBuffers(window);
        GLFW.glfwPollEvents();
    }

    public void startOpenGL() {
        GL.createCapabilities();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public boolean close() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void terminate() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}
