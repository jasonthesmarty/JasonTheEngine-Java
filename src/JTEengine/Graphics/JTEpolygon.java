package JTEengine.Graphics;

import org.lwjgl.opengl.*;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class JTEpolygon {

    private int VAO, VBO, IBO, CBO;
    float[] vertices;
    int[] indices;

    public JTEpolygon(float[] vertices, int[] indices, float[] colors) {
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
