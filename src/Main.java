import JTEengine.JTEinput;
import JTEengine.JTEwindow;

public class Main {
    public static void main(String[] args) {
        JTEwindow window = new JTEwindow(800, 600, "Java Application");
        window.create();

        JTEinput input = new JTEinput();

        while(!window.close()) {
            window.startOpenGL();

            window.changeColor(100, 0, 0, 255);

            if (input.keyDown(256)) {
                break;
            }

            window.FPS();

            window.update();
        }
        window.terminate();
    }
}