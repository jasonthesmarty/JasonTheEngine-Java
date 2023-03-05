package JTEengine.Graphics;

import JTEengine.Shaders.JTEshaders;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class JTEimage {

    private int[] width, height, channels;
    private int VAO, VBO, IBO, IMGBO;
    private int textureID;
    float[] vertices, texture, colors;
    int[] indices;
    JTEshaders shader;
    String filename;

    public JTEimage(String filename) {
        this.filename = filename;
    }

    public void render() {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer imageData = STBImage.stbi_load(filename, width, height, channels, 0);
        STBImage.stbi_set_flip_vertically_on_load(true);

        textureID = GL30.glGenTextures();
        GL30.glActiveTexture(GL13.GL_TEXTURE0);
        GL30.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL30.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL30.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        GL30.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL30.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);


        GL30.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width.get(0), height.get(0), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        STBImage.stbi_image_free(imageData);

        this.vertices = new float[] {-0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f, -0.5f, 0.5f, 0.0f};
        this.indices = new int[] {0, 1, 3, 3, 1, 2};
        this.colors = new float[] {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        this.texture = new float[] {0, 0, 1, 0, 1, 1, 0, 1};

        VAO = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(VAO);

        VBO = GL30.glGenBuffers();
        GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
        GL30.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

        IBO = GL30.glGenBuffers();
        GL30.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, IBO);
        GL30.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        IMGBO = GL30.glGenBuffers();
        GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, IMGBO);
        GL30.glBufferData(GL15.GL_ARRAY_BUFFER, texture, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 0, 0);

        GL30.glBindVertexArray(VAO);
        GL30.glEnableVertexAttribArray(0);

        GL30.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL30.glEnableVertexAttribArray(2);

        GL30.glDrawElements(GL11.GL_TRIANGLES, this.indices.length, GL11.GL_UNSIGNED_INT, 0);
    }

    public void terminate() {
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(VAO);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glDeleteBuffers(VBO);
        GL30.glDeleteBuffers(IBO);
    }
}
