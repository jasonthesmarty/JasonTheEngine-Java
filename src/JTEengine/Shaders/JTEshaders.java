package JTEengine.Shaders;

import JTEengine.Standard.JTEstandard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class JTEshaders {

    private String vertexShaderCode, fragmentShaderCode;
    private int vertexShader, fragmentShader, shaderProgram;

    JTEstandard std = new JTEstandard();

    public JTEshaders() {
        this.vertexShaderCode = std.fileContents("..\\JasonTheEngine\\src\\JTEengine\\Shaders\\vertex.vert");
        this.fragmentShaderCode = std.fileContents("..\\JasonTheEngine\\src\\JTEengine\\Shaders\\fragment.frag");
    }

    public void createShaders() {
        vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, vertexShaderCode);
        GL20.glCompileShader(vertexShader);

        if (GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Vertex Shader compilation failed");
        }

        fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, fragmentShaderCode);
        GL20.glCompileShader(fragmentShader);

        if (GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Fragment Shader compilation failed");
        }

        shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        GL20.glLinkProgram(shaderProgram);

        if (GL20.glGetProgrami(shaderProgram, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.err.println("Shader program link failed");
        }

        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
    }

    public void startShaders() {
        GL20.glUseProgram(shaderProgram);
    }

    public void stopShaders() {
        GL20.glUseProgram(0);
    }

    public void terminate() {
        GL20.glDeleteProgram(shaderProgram);
    }

    public void getShaderStatus() {
        System.out.println(GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS));
        System.out.println(GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS));

        int[] shaders = new int[1];
        int[] count = new int[1];

        GL20.glGetAttachedShaders(shaderProgram, count, shaders);

        System.out.println(shaders[0]);
        System.out.println(count[0]);
    }

    public void getSources() {
        System.out.println(this.vertexShaderCode);
        System.out.println(this.fragmentShaderCode);
    }
}
