import JTEengine.Graphics.JTEimage;
import JTEengine.Graphics.JTEpolygon;
import JTEengine.Shaders.JTEshaders;
import JTEengine.Standard.JTEdebug;
import JTEengine.Standard.JTEstandard;
import JTEengine.Window.JTEinput;
import JTEengine.Window.JTEwindow;

import java.util.Arrays;

import static JTEengine.Variables.JTEvariables.*;

public class Main {

    public static void main(String[] args) {
        JTEwindow window = new JTEwindow(1600, 900, "Java Application");
        window.create();
        window.startOpenGL();

        JTEinput input = new JTEinput();

        JTEshaders shaders = new JTEshaders();
        shaders.createShaders();

        JTEpolygon poly = new JTEpolygon(window, 100, 100, 200, 200, COLOR_WHITE_RGBA, COLOR_BLACK_RGBA, COLOR_WHITE_RGBA, COLOR_BLACK_RGBA);

        JTEimage image = new JTEimage("..\\JasonTheEngine\\src\\assets\\testcard.png");
        JTEimage image2 = new JTEimage("..\\JasonTheEngine\\src\\assets\\cat-300572__340.jpg");

        JTEstandard std = new JTEstandard();

        JTEdebug debug = new JTEdebug(window);

        while(!window.close()) {
            window.clearColorGL();
            window.updateGLViewport();
            shaders.startShaders();

            window.changeColor(25, 125, 250, 255);

            poly.render(shaders);

            float[] windowPos = window.getMousePosition();

            poly.setX((int)windowPos[0]);
            poly.setY((int)windowPos[1]);

            debug.render();

            std.debugMenu(window, 1f);

            image.render(shaders);

            if (input.keyDown(256)) {
                break;
            }

            shaders.stopShaders();
            window.update();


        }
        image.terminate();
        image2.terminate();

        poly.terminate();

        debug.terminate();
        shaders.terminate();
        input.terminate();
        window.terminate();
    }
}