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

        JTEpolygon poly = new JTEpolygon(window, 10, 10, 100, 100, COLOR_WHITE_RGBA, COLOR_WHITE_RGBA, COLOR_BLACK_RGBA, COLOR_BLACK_RGBA);
        JTEpolygon polyHighlight = new JTEpolygon(window, 0, 0, 120, 120, COLOR_YELLOW_RGBA);

        JTEpolygon ground = new JTEpolygon(window, 0, 700, 1600, 200, COLOR_GRAY_RGBA, COLOR_GRAY_RGBA, COLOR_WHITE_RGBA, COLOR_WHITE_RGBA);

        JTEstandard std = new JTEstandard();

        boolean print = true;
        boolean clicking = false;


        int falling = 0;

        while(!window.close()) {
            window.clearColorGL();
            window.updateGLViewport();
            shaders.startShaders();

            window.changeColor(25, 125, 250, 255);

            float[] mousePos = window.getMousePosition();

            boolean hitboxX = !(mousePos[0] < poly.getX() || mousePos[0] > (poly.getX()+poly.getWidth()));
            boolean hitboxY = !(mousePos[1] < poly.getY() || mousePos[1] > (poly.getY()+poly.getHeight()));

            if (poly.getY() < 600 && clicking) {
                if (poly.getY() + falling > 600) {
                    int last = 600 - (int)poly.getY();
                    poly.setY((int)(poly.getY()+last));
                    falling = 0;
                }
                else {
                    poly.setY((int)(poly.getY()+falling));
                    falling += 2;
                }
            }
            if (input.mouseDown(0)) {
                if (hitboxX && hitboxY) {
                    falling = 0;
                    System.out.println(true);
                    polyHighlight.setX((int) poly.getX() - 10);
                    polyHighlight.setY((int) poly.getY() - 10);
                    polyHighlight.render();
                    poly.setX((int) mousePos[0] - (int) poly.getWidth() / 2);
                    poly.setY((int) mousePos[1] - (int) poly.getHeight() / 2);
                    clicking = true;
                }
            }


            //System.out.println(Arrays.toString(window.getMousePosition()));

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
            ground.render();

            if (input.keyDown(256)) {
                break;
            }

            shaders.stopShaders();
            window.update();
        }
        ground.terminate();
        poly.terminate();

        shaders.terminate();
        input.terminate();
        window.terminate();
    }
}