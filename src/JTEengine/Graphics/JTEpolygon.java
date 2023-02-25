package JTEengine.Graphics;

import JTEengine.Window.JTEwindow;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class JTEpolygon {

    private float [] vertices, colors;
    private int[] indices;
    private int x, y, width, height, type, red, green, blue, alpha;
    private int[] color;
    private int[] color1, color2, color3, color4;
    private JTEshapeBuffer shapeBuffer;
    private JTEwindow window;

    public JTEpolygon(float[] vertices, int[] indices, float[] colors) {
        this.vertices = vertices;
        this.indices = indices;
        this.colors = colors;
        this.type = 1;
    }

    public JTEpolygon(JTEwindow window, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.window = window;
        this.type = 2;
    }

    public JTEpolygon(JTEwindow window, int x, int y, int width, int height, int[] color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.window = window;
        this.type = 3;
    }

    public JTEpolygon(JTEwindow window, int x, int y, int width, int height, int[] color1, int[] color2, int[] color3, int[] color4) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
        this.window = window;
        this.type = 4;
    }

    public void render() {
        if (this.type == 1) {
            shapeBuffer = new JTEshapeBuffer(this.vertices, this.indices, this.colors);
            shapeBuffer.render();
        }
        else if (this.type == 2) {
            int[] dimensions = this.window.getWindowDimensions();
            this.vertices = coordinatesToPixels(this.x, this.y, this.width, this.height, dimensions[0], dimensions[1]);
            this.indices = new int[]{0, 1, 2, 2, 0, 3};
            float[] Colors = FloatToFloatColor((float)this.red, (float)this.green, (float)this.blue, (float)this.alpha);
            this.colors = new float[]{
                    Colors[0], Colors[1], Colors[2],
                    Colors[0], Colors[1], Colors[2],
                    Colors[0], Colors[1], Colors[2],
                    Colors[0], Colors[1], Colors[2]
            };

            shapeBuffer = new JTEshapeBuffer(this.vertices, this.indices, this.colors);
            shapeBuffer.render();
        }
        else if (this.type == 3) {
            int[] dimensions = this.window.getWindowDimensions();
            this.vertices = coordinatesToPixels(this.x, this.y, this.width, this.height, dimensions[0], dimensions[1]);
            this.indices = new int[]{0, 1, 2, 2, 0, 3};
            float[] Colors = FloatToFloatColor((float)this.color[0], (float)this.color[1], (float)this.color[2], (float)this.color[3]);
            this.colors = new float[]{
                    Colors[0], Colors[1], Colors[2],
                    Colors[0], Colors[1], Colors[2],
                    Colors[0], Colors[1], Colors[2],
                    Colors[0], Colors[1], Colors[2]
            };

            shapeBuffer = new JTEshapeBuffer(this.vertices, this.indices, this.colors);
            shapeBuffer.render();
        }
        else if (this.type == 4) {
            int[] dimensions = this.window.getWindowDimensions();
            this.vertices = coordinatesToPixels(this.x, this.y, this.width, this.height, dimensions[0], dimensions[1]);
            this.indices = new int[]{0, 1, 3, 3, 1, 2};
            float[] Colors1 = FloatToFloatColor((float)this.color1[0], (float)this.color1[1], (float)this.color1[2], (float)this.color1[3]);
            float[] Colors2 = FloatToFloatColor((float)this.color2[0], (float)this.color2[1], (float)this.color2[2], (float)this.color2[3]);
            float[] Colors3 = FloatToFloatColor((float)this.color3[0], (float)this.color3[1], (float)this.color3[2], (float)this.color3[3]);
            float[] Colors4 = FloatToFloatColor((float)this.color4[0], (float)this.color4[1], (float)this.color4[2], (float)this.color4[3]);

            this.colors = new float[]{
                    Colors1[0], Colors1[1], Colors2[2],
                    Colors2[0], Colors2[1], Colors2[2],
                    Colors3[0], Colors3[1], Colors3[2],
                    Colors4[0], Colors4[1], Colors4[2]
            };

            shapeBuffer = new JTEshapeBuffer(this.vertices, this.indices, this.colors);
            shapeBuffer.render();
        }
    }

    public void terminate() {
        shapeBuffer.terminate();
    }

    // Setters:

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setRed(int red) {
        if (this.type == 2) {
            this.red = red;
        }
        else {
            System.err.println("This constructor does not support the value red.");
        }
    }
    public void setGreen(int green) {
        if (this.type == 2) {
            this.green = green;
        }
        else {
            System.err.println("This constructor does not support the value green.");
        }
    }
    public void setBlue(int blue) {
        if (this.type == 2) {
            this.blue = blue;
        }
        else {
            System.err.println("This constructor does not support the value blue.");
        }
    }
    public void setColor(int[] color) {
        if (this.type == 3) {
            this.color = color;
        }
        else {
            System.err.println("This constructor does not support color in an array format.");
        }
    }
    public void setColor1(int[] color1) {
        if (this.type == 4) {
            this.color1 = color1;
        }
        else {
            System.err.println("This constructor does not support color in an quad array format.");
        }
    }
    public void setColor2(int[] color2) {
        if (this.type == 4) {
            this.color2 = color2;
        }
        else {
            System.err.println("This constructor does not support color in an quad array format.");
        }
    }
    public void setColor3(int[] color3) {
        if (this.type == 4) {
            this.color3 = color3;
        }
        else {
            System.err.println("This constructor does not support color in an quad array format.");
        }
    }
    public void setColor4(int[] color4) {
        if (this.type == 4) {
            this.color4 = color4;
        }
        else {
            System.err.println("This constructor does not support color in an quad array format.");
        }
    }

    // Getters:

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }


    private float[] coordinatesToPixels(int x, int y, int width, int height, int windowWidth, int windowHeight) {
        double widthHalved = (double)windowWidth/2;
        double heightHalved = (double)windowHeight/2;

        float X = (float)((1 - ((double)x / widthHalved)) * -1);
        float Y = (float)(1 - ((double)y / heightHalved));

        float Width = (float)((double)width/widthHalved);
        float Height = (float)((double)height/heightHalved);

        return new float[] {
                X, Y, 0.0f,
                X + Width, Y, 0.0f,
                X + Width, Y - Height, 0.0f,
                X, Y - Height, 0.0f
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