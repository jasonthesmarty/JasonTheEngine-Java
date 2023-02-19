import JTEengine.JTEwindow;

public class Main {
    public static void main(String[] args) {
        JTEwindow window = new JTEwindow(800, 600, "Java Application");
        window.create();

        while(!window.close()) {
            window.startOpenGL();
            window.update();
        }
        window.terminate();

    }
}