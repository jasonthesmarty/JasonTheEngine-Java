import JTEengine.Shaders.JTEshaders;
import JTEengine.Standard.JTEstandard;
import JTEengine.Window.JTEinput;
import JTEengine.Window.JTEwindow;

public class Main {
    public static void main(String[] args) {
        JTEwindow window = new JTEwindow(1600, 900, "Java Application");
        window.create();
        window.startOpenGL();

        JTEinput input = new JTEinput();

        JTEshaders shaders = new JTEshaders();
        shaders.createShaders();

        JTEstandard std = new JTEstandard();

        boolean print = true;

        while(!window.close()) {
            window.clearColorGL();
            shaders.startShaders();

            window.changeColor(25, 125, 250, 255);

            if (print) {
                //shaders.getShaderStatus();
                //shaders.getSources();
                print = false;
            }

            if (input.keyDown(256)) {
                break;
            }

            shaders.stopShaders();
            window.update();
        }
        shaders.terminate();
        window.terminate();
    }
}