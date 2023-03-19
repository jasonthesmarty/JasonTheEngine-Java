package JTEengine.Graphics;

import JTEengine.Shaders.JTEshaders;
import JTEengine.Standard.JTEstandard;
import JTEengine.Window.JTEwindow;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class JTEtext {

    private String fontFile;
    private JTEstandard std = new JTEstandard();
    private int width, height, lineHeight;
    private Map<Integer, JTEfontInfo> charMap = new HashMap<>();
    private BufferedImage fontImage;
    int VAO, VBO, IBO, CBO, IMGBO;
    float[] vertices, colors, textureCoords;
    int[] indices;
    JTEimage image;
    JTEshaders shader;

    public JTEtext(String fontFile, int fontSize, JTEshaders shader, JTEwindow window) {
        this.fontFile = fontFile;
        this.shader = shader;

        fontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Font font = new Font(fontFile, Font.PLAIN, fontSize);

        Graphics2D fontGraphics = fontImage.createGraphics();
        fontGraphics.setFont(font);
        FontMetrics fontMetrics = fontGraphics.getFontMetrics();

        int imageWidth = (int)(Math.sqrt(font.getNumGlyphs()) * fontSize + 1);
        width = 0;
        height = fontMetrics.getHeight();
        lineHeight = fontMetrics.getHeight();
        int x = 0;
        int y = (int)(fontMetrics.getHeight() * 1.4f);

        for (int i = 0; i < font.getNumGlyphs(); i++) {
            if (font.canDisplay(i)) {
                JTEfontInfo fontInfo = new JTEfontInfo(x, y, fontMetrics.charWidth(i), fontMetrics.getHeight());
                charMap.put(i, fontInfo);
                width = Math.max(x + fontMetrics.charWidth(i), width);
                x += fontInfo.width;

                if (x > imageWidth) {
                    x = 0;
                    y += fontMetrics.getHeight() * 1.4f;
                    height += fontMetrics.getHeight() * 1.4f;
                }
            }
        }

        height += fontMetrics.getHeight() * 1.4f;
        fontGraphics.dispose();

        fontImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fontGraphics = fontImage.createGraphics();
        fontGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        fontGraphics.setFont(font);
        fontGraphics.setColor(Color.WHITE);
        for (int i=0; i < font.getNumGlyphs(); i++) {
            if (font.canDisplay(i)) {
                JTEfontInfo info = charMap.get(i);
                info.calculateTextureCoordinates(width, height);
                fontGraphics.drawString("" + (char)i, info.x, info.y);
            }
        }

        fontGraphics.dispose();
        
        File output = new File("..\\JasonTheEngine\\src\\assets\\name.png");
        assert fontImage != null;
        BufferedImage imageOut = new BufferedImage(fontImage.getWidth(), fontImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        imageOut.getGraphics().drawImage(fontImage, 0, 0, null);
        try {
            ImageIO.write(imageOut, "png", output);
        } catch (IOException io) {
            io.printStackTrace();
        }

        image = new JTEimage("..\\JasonTheEngine\\src\\assets\\name.png", 100, 100, window);

    }

    public void render() {
        image.render(shader);
    }
}

@SuppressWarnings({"unused", "SpellCheckingInspection"})
class JTEfontInfo {

    int x, y, width, height;
    public Vector2f[] textureCoordinates = new Vector2f[4];

    public JTEfontInfo(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void calculateTextureCoordinates(int fontWidth, int fontHeight) {
        float x0 = (float)x / (float)fontWidth;
        float x1 = (float)(x + width) / (float)fontWidth;
        float y0 = (float)(y - height) / (float)fontHeight;
        float y1 = (float)(y) / (float)fontHeight;

        textureCoordinates[0] = new Vector2f(x0, y1);
        textureCoordinates[1] = new Vector2f(x1, y0);
    }
}