package Engine.Screens;

import Engine.Screen;
import Engine.Shader;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class TitleScreen extends Screen{
    private Shader shade;
    private float[] vertexArray = {
            0.5f, -0.5f, 0.0f,        1.0f, 0.0f, 0.0f, 0.0f,
            -0.5f, 0.5f, 0.0f,        1.0f, 100.0f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.0f,         1.0f, 1.0f, -100.0f, 1.0f,
            -0.5f, -0.5f, 0.0f,       1.0f, 1.0f, 1.0f, 0.0f,
    };
    private int[] elementArray = {
        2,1,0,
        0,1,3,
    };
    private int vaoId, vboId,eboId;

    public TitleScreen() {

    }

    @Override
    public void init(){

        this.shade = new Shader("C:\\Users\\King\\glitter\\src\\main\\java\\Engine\\Assets\\Shaders\\default.glsl");
        shade.compile();

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        int positionsSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void update(float time){
        this.shade.use();

        this.shade.uploadFloat("uTime", time);


        glBindVertexArray(vaoId);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);


        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        this.shade.detach();
    }

}
