import JTEengine.Shaders.JTEshaders;
import JTEengine.Standard.JTEstandard;
import JTEengine.Window.JTEinput;
import JTEengine.Window.JTEwindow;

public class Main {
    public static void main(String[] args) {
        JTEwindow window = new JTEwindow(800, 600, "Java Application");
        window.create();

        JTEinput input = new JTEinput();

        JTEshaders shaders = new JTEshaders();

        while(!window.close()) {
            window.startOpenGL();

            window.changeColor(100, 0, 0, 255);

            if (input.keyDown(256)) {
                break;
            }

            window.update();
        }
        window.terminate();
    }
}