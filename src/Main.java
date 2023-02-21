import JTEengine.Standard.JTEstandard;
import JTEengine.Window.JTEinput;
import JTEengine.Window.JTEwindow;

public class Main {
    public static void main(String[] args) {
        JTEwindow window = new JTEwindow(1600, 900, "Java Application");
        window.create();
        window.startOpenGL();

        JTEinput input = new JTEinput();

        JTEstandard std = new JTEstandard();

        boolean print = true;

        while(!window.close()) {
            window.clearColorGL();

            window.changeColor(25, 125, 250, 255);

            if (print) {
                print = false;
            }

            if (input.keyDown(256)) {
                break;
            }

            window.update();
        }
        window.terminate();
    }
}