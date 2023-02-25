import JTEengine.Graphics.JTEpolygon;
import JTEengine.Shaders.JTEshaders;
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

        float[] vertices = {
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
        };
        int[] indices = {
                0, 1, 2
        };
        float[] colors = {
                0.05f, 1.0f, 0.5f,
                1.0f, 0.0f, 1.0f,
                0.0f, 0.75f, 1.0f
        };

        JTEpolygon poly = new JTEpolygon(window, 10, 10, 100, 100, COLOR_WHITE_RGBA, COLOR_WHITE_RGBA, COLOR_BLACK_RGBA, COLOR_BLACK_RGBA);

        JTEstandard std = new JTEstandard();

        boolean print = true;

        while(!window.close()) {
            window.clearColorGL();
            window.updateGLViewport();
            shaders.startShaders();

            window.changeColor(25, 125, 250, 255);

            float[] mousePos = window.getMousePosition();

            poly.setX((int)mousePos[0]);
            poly.setY((int)mousePos[1]);

            System.out.println(Arrays.toString(window.getMousePosition()));

            if (input.keyDown(48)) {
                poly.setColor1(COLOR_BLACK_RGBA);
                poly.setColor2(COLOR_BLACK_RGBA);
                poly.setColor3(COLOR_WHITE_RGBA);
                poly.setColor4(COLOR_WHITE_RGBA);
            }
            else {
                poly.setColor1(COLOR_WHITE_RGBA);
                poly.setColor2(COLOR_WHITE_RGBA);
                poly.setColor3(COLOR_BLACK_RGBA);
                poly.setColor4(COLOR_BLACK_RGBA);
            }

            /*
            if (print) {
                shaders.getShaderStatus();
                shaders.getSources();
                print = false;
            }
             */

            poly.render();

            if (input.keyDown(256)) {
                break;
            }

            shaders.stopShaders();
            window.update();
        }
        poly.terminate();

        shaders.terminate();
        input.terminate();
        window.terminate();
    }
}