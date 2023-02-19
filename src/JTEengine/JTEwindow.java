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
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public boolean close() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void terminate() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    private float[] FloatToFloatColor(float red, float green, float blue, float alpha) {
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

    public void changeColor(float red, float green, float blue, float alpha) {
        float[] colors = FloatToFloatColor(red, green, blue, alpha);
        GL11.glClearColor(colors[0], colors[1], colors[2], colors[3]);
    }

    public void changeColor(float red, float green, float blue) {
        float[] colors = FloatToFloatColor(red, green, blue, 255);
        GL11.glClearColor(colors[0], colors[1], colors[2], colors[3]);
    }

    public void changeColor(float[] color) {
        float[] colors = FloatToFloatColor(color[0], color[1], color[2], color[3]);
        GL11.glClearColor(colors[0], colors[1], colors[2], colors[3]);
    }
}
