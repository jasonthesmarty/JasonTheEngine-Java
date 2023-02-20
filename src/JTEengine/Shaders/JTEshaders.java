package JTEengine.Shaders;

import JTEengine.Standard.JTEstandard;
import org.lwjgl.opengl.GL20;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class JTEshaders {

    private final String verticalShaderSource, fragmentShaderSource;
    private int vertexShader, fragmentShader, shaderProgram;

    JTEstandard std = new JTEstandard();

    public JTEshaders() {
        this.verticalShaderSource = std.fileContents("..\\JasonTheEngine\\src\\JTEengine\\Shaders\\vertical.glsl");
        this.fragmentShaderSource = std.fileContents("..\\JasonTheEngine\\src\\JTEengine\\Shaders\\fragment.glsl");
    }

    public void createShaders() {
        vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, this.verticalShaderSource);
        GL20.glCompileShader(vertexShader);

        if (vertexShader == 0) {throw new IllegalStateException("Vertex Shader not created");}

        fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, this.fragmentShaderSource);
        GL20.glCompileShader(fragmentShader);

        if (fragmentShader == 0) {throw new IllegalStateException("Fragment Shader not created");}

        shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(vertexShader, shaderProgram);
        GL20.glAttachShader(fragmentShader, shaderProgram);
        GL20.glCompileShader(shaderProgram);

        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
    }

    public void activate() {
        GL20.glUseProgram(shaderProgram);
    }

    public void deactivate() {
        GL20.glUseProgram(0);
    }

    public void terminate() {
        GL20.glDeleteShader(shaderProgram);
    }
}
