package JTEengine.Window;

import JTEengine.Standard.JTEstandard;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class JTEwindow {
    private int width, height;
    private String title;
    private long window;
    private int frames;
    private double seconds;

    JTEinput input = new JTEinput();
    JTEstandard std = new JTEstandard();

    // Constructor

    public JTEwindow(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    // Methods

    public void create() {
        GLFW.glfwInit();

        if (!GLFW.glfwInit()) {
            throw new RuntimeException("GLFW not initialize.");
        }

        window = GLFW.glfwCreateWindow(this.width, this.height, this.title, 0, 0);

        GLFW.glfwSetKeyCallback(window, input.keyboardCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.mouseButtonCallback());
        GLFW.glfwSetCursorPosCallback(window, input.mousePositionCallback());

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
        GL11.glViewport(0, 0, this.width, this.height);
    }

    public void updateGLViewport() {
        int[] dimensions = getWindowDimensions();
        GL11.glViewport(0, 0, dimensions[0], dimensions[1]);
    }

    public void clearColorGL() {
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

    public void changeColor(float red, float green, float blue, float alpha) {
        float[] colors = std.RGBAtoNormalized(red, green, blue, alpha);
        GL11.glClearColor(colors[0], colors[1], colors[2], colors[3]);
    }

    public void changeColor(float red, float green, float blue) {
        float[] colors = std.RGBAtoNormalized(red, green, blue, 255);
        GL11.glClearColor(colors[0], colors[1], colors[2], colors[3]);
    }

    public void changeColor(float[] color) {
        float[] colors = std.RGBAtoNormalized(color[0], color[1], color[2], color[3]);
        GL11.glClearColor(colors[0], colors[1], colors[2], colors[3]);
    }

    public void FPS() {
        frames++;
        if (GLFW.glfwGetTime() > seconds + 1) {
            System.out.println(frames);
            seconds = GLFW.glfwGetTime();
            frames = 0;
        }
    }

    // Setters

    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    // Getters

    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public String getTitle() {
        return this.title;
    }
    public int[] getWindowDimensions() {
        int[] width = new int[1];
        int[] height = new int[1];
        GLFW.glfwGetWindowSize(window, width, height);
        return new int[]{width[0], height[0]};
    }
    public float[] getMousePosition() {
        int[] dimensions = getWindowDimensions();
        float widthRatio = (float)dimensions[0]/this.width;
        float heightRatio = (float)dimensions[1]/this.height;

        double[] mousePos = input.getMousePosition();

        return new float[] {((float)mousePos[0]/widthRatio), ((float)mousePos[1]/heightRatio)};
    }

}
