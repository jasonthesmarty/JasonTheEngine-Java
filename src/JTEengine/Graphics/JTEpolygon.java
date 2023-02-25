package JTEengine.Graphics;

import JTEengine.Window.JTEwindow;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class JTEpolygon {

    private final JTEshapeBuffer shapeBuffer;

    public JTEpolygon(float[] vertices, int[] indices, float[] colors) {
        shapeBuffer = new JTEshapeBuffer(vertices, indices, colors);
    }

    public JTEpolygon(JTEwindow window, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
        int[] dimensions = window.getWindowDimensions();
        float[] vertices = coordinatesToPixels(x, y, width, height, dimensions[0], dimensions[1]);
        int[] indices = new int[]{0, 1, 2, 2, 0, 3};
        float[] Colors = FloatToFloatColor((float)red, (float)green, (float)blue, (float)alpha);
        float[] colors = {
                Colors[0], Colors[1], Colors[2],
                Colors[0], Colors[1], Colors[2],
                Colors[0], Colors[1], Colors[2],
                Colors[0], Colors[1], Colors[2]
        };

        shapeBuffer = new JTEshapeBuffer(vertices, indices, colors);
    }

    public JTEpolygon(JTEwindow window, int x, int y, int width, int height, int[] color) {
        int[] dimensions = window.getWindowDimensions();
        float[] vertices = coordinatesToPixels(x, y, width, height, dimensions[0], dimensions[1]);
        int[] indices = new int[]{0, 1, 2, 2, 0, 3};
        float[] Colors = FloatToFloatColor((float)color[0], (float)color[1], (float)color[2], (float)color[3]);
        float[] colors = {
                Colors[0], Colors[1], Colors[2],
                Colors[0], Colors[1], Colors[2],
                Colors[0], Colors[1], Colors[2],
                Colors[0], Colors[1], Colors[2]
        };

        shapeBuffer = new JTEshapeBuffer(vertices, indices, colors);
    }

    public JTEpolygon(JTEwindow window, int x, int y, int width, int height, int[] color1, int[] color2, int[] color3, int[] color4) {
        int[] dimensions = window.getWindowDimensions();
        float[] vertices = coordinatesToPixels(x, y, width, height, dimensions[0], dimensions[1]);
        int[] indices = new int[]{0, 1, 2, 2, 0, 3};
        float[] Colors1 = FloatToFloatColor((float)color1[0], (float)color1[1], (float)color1[2], (float)color1[3]);
        float[] Colors2 = FloatToFloatColor((float)color2[0], (float)color2[1], (float)color2[2], (float)color2[3]);
        float[] Colors3 = FloatToFloatColor((float)color3[0], (float)color3[1], (float)color3[2], (float)color3[3]);
        float[] Colors4 = FloatToFloatColor((float)color4[0], (float)color4[1], (float)color4[2], (float)color4[3]);

        float[] Colors = {
                Colors1[0], Colors1[1], Colors2[2],
                Colors2[0], Colors2[1], Colors2[2],
                Colors3[0], Colors3[1], Colors3[2],
                Colors4[0], Colors4[1], Colors4[2]
        };

        shapeBuffer = new JTEshapeBuffer(vertices, indices, Colors);
    }

    public void render() {
        shapeBuffer.render();
    }

    public void terminate() {
        shapeBuffer.terminate();
    }

    private float[] coordinatesToPixels(int x, int y, int width, int height, int windowWidth, int windowHeight) {
        double widthHalved = (double)windowWidth/2;
        double heightHalved = (double)windowHeight/2;

        float X = (float)((1 - ((double)x / widthHalved)) * -1);
        float Y = (float) ((1 - ((double)y / heightHalved)) * -1);

        float Width = (float)((double)width/widthHalved);
        float Height = (float)((double)height/heightHalved);

        return new float[] {
                X, Y, 0.0f,
                X + Width, Y, 0.0f,
                X + Width, Y + Height, 0.0f,
                X, Y + Width, 0.0f
        };
    }

    private float[] FloatToFloatColor(float red, float green, float blue, float alpha) {
        float[] colors = {red, green, blue, alpha};

        // Red
        if (colors[0] > 255) {
            throw new IllegalArgumentException("RGB value: Red too high ( red > 255 ).");
        }
        else if (colors[0] < 0) {
            throw new IllegalArgumentException("RGB value: Red too high ( red < 0 ).");
        }
        else {
            colors[0] = colors[0]/255;
        }

        // Green
        if (colors[1] > 255) {
            throw new IllegalArgumentException("RGB value: Green too high ( green > 255 ).");
        }
        else if (colors[1] < 0) {
            throw new IllegalArgumentException("RGB value: Green too high ( green < 0 ).");
        }
        else {
            colors[1] = colors[1]/255;
        }

        // Blue
        if (colors[2] > 255) {
            throw new IllegalArgumentException("RGB value: Blue too high ( blue > 255 ).");
        }
        else if (colors[2] < 0) {
            throw new IllegalArgumentException("RGB value: Blue too high ( blue < 0 ).");
        }
        else {
            colors[2] = colors[2]/255;
        }

        // Alpha
        if (colors[3] > 255) {
            throw new IllegalArgumentException("Alpha value: Alpha too high ( alpha > 255 ).");
        }
        else if (colors[3] < 0) {
            throw new IllegalArgumentException("Alpha value: Alpha too high ( alpha < 0 ).");
        }
        else {
            colors[3] = colors[3]/255;
        }

        return colors;
    }
}

@SuppressWarnings({"SpellCheckingInspection", "unused"})
class JTEshapeBuffer {

    private final int VAO, VBO, IBO, CBO;
    float[] vertices;
    int[] indices;

    public JTEshapeBuffer(float[] vertices, int[] indices, float[] colors) {
        this.vertices = vertices;
        this.indices = indices;

        VAO = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(VAO);

        VBO = GL30.glGenBuffers();
        GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
        GL30.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

        IBO = GL30.glGenBuffers();
        GL30.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, IBO);
        GL30.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        CBO = GL30.glGenBuffers();
        GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, CBO);
        GL30.glBufferData(GL15.GL_ARRAY_BUFFER, colors, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
    }

    public void render() {
        GL30.glBindVertexArray(VAO);
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);

        GL30.glDrawElements(GL11.GL_TRIANGLES, this.indices.length, GL11.GL_UNSIGNED_INT, 0);
    }

    public void terminate() {
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(VAO);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glDeleteBuffers(VBO);
        GL30.glDeleteBuffers(IBO);
        GL30.glDeleteBuffers(CBO);
    }
}