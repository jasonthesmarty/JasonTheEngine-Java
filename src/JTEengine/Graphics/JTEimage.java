package JTEengine.Graphics;

import JTEengine.Shaders.JTEshaders;
import JTEengine.Standard.JTEstandard;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class JTEimage {

    private int[] width, height, channels;
    private int VAO, VBO, IBO, CBO, IMGBO;
    float[] vertices, texture, colors;
    int[] indices;
    JTEshaders shader;
    String filename;
    JTEstandard std = new JTEstandard();
    String newName;
    public JTEimage(String filename) {
        ArrayList<String> filenameList = new ArrayList<>(Arrays.asList(filename.split("")));

        int lastSlashPlace = 0, periodPlace = 0;

        for (int i = 0; i < filenameList.size(); i ++) {
            if (filenameList.get(i).equals("\\")) {
                lastSlashPlace = i;
            }
            if (filenameList.get(i).equals(".")) {
                    periodPlace = i;
            }
        }

        StringBuilder name = new StringBuilder();
        for (int j = lastSlashPlace+1; j < periodPlace; j++) {
            name.append(filenameList.get(j));
        }

        newName = name.toString();

        File assetsTemp = new File("..\\JasonTheEngine\\src\\assets\\runtimeTemp");
        File[] assetsTempList = assetsTemp.listFiles();
        assert assetsTempList != null;
        int assetsTempLength = assetsTempList.length;

        File input = new File(filename);
        File output = new File("..\\JasonTheEngine\\src\\assets\\runtimeTemp\\" + newName + "_temp.png");

        BufferedImage imageIn = null;

        try {
            imageIn = ImageIO.read(input);
        } catch (IOException io) {
            io.printStackTrace();
        }

        assert imageIn != null;
        BufferedImage imageOut = new BufferedImage(imageIn.getWidth(), imageIn.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        imageOut.getGraphics().drawImage(imageIn, 0, 0, null);
        try {
            ImageIO.write(imageOut, "png", output);
        } catch (IOException io) {
            io.printStackTrace();
        }
        this.filename = "..\\JasonTheEngine\\src\\assets\\runtimeTemp\\" + newName + "_temp.png";
    }

    public void render(JTEshaders shader) {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer imageData = STBImage.stbi_load(filename, width, height, channels, 0);
        STBImage.stbi_set_flip_vertically_on_load(true);

        int textureID = GL30.glGenTextures();
        GL30.glActiveTexture(GL13.GL_TEXTURE0);
        GL30.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL30.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL30.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        GL30.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL30.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        GL30.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width.get(0), height.get(0), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        assert imageData != null;
        STBImage.stbi_image_free(imageData);

        this.vertices = new float[] {-0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f, -0.5f, 0.5f, 0.0f};
        this.indices = new int[] {0, 1, 3, 3, 1, 2};
        this.colors = new float[] {1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
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

        CBO = GL30.glGenBuffers();
        GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, CBO);
        GL30.glBufferData(GL15.GL_ARRAY_BUFFER, colors, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);

        IMGBO = GL30.glGenBuffers();
        GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, IMGBO);
        GL30.glBufferData(GL15.GL_ARRAY_BUFFER, texture, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 0, 0);

        GL30.glBindVertexArray(VAO);
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);

        GL30.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL30.glEnableVertexAttribArray(2);

        GL30.glDrawElements(GL11.GL_TRIANGLES, this.indices.length, GL11.GL_UNSIGNED_INT, 0);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void terminate() {
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(VAO);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);

        File remove = new File("..\\JasonTheEngine\\src\\assets\\runtimeTemp\\" + newName + "_temp.png");
        remove.delete();

        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
        GL30.glDeleteBuffers(VBO);
        GL30.glDeleteBuffers(IBO);
        GL30.glDeleteBuffers(CBO);
        GL30.glDeleteBuffers(IMGBO);
    }
}
