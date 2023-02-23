import JTEengine.Graphics.JTEpolygon;
import JTEengine.Shaders.JTEshaders;
import JTEengine.Standard.JTEstandard;
import JTEengine.Window.JTEinput;
import JTEengine.Window.JTEwindow;

import java.util.ArrayList;
import java.util.Arrays;

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

        JTEpolygon triangle = new JTEpolygon(vertices, indices, colors);

        JTEstandard std = new JTEstandard();

        boolean print = true;

        while(!window.close()) {
            window.clearColorGL();
            shaders.startShaders();

            window.changeColor(25, 125, 250, 255);

            /*
            if (print) {
                shaders.getShaderStatus();
                shaders.getSources();
                print = false;
            }
             */

            int[] dimensions = window.getWindowDimensions();
            System.out.println(Arrays.toString(dimensions));

            triangle.render();

            if (input.keyDown(256)) {
                break;
            }

            shaders.stopShaders();
            window.update();
        }
        triangle.terminate();

        shaders.terminate();
        window.terminate();
    }
}