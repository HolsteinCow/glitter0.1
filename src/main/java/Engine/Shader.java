package Engine;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;


public class Shader {

    private int shaderPID;

    private String vertexSource;
    private String fragmentSource;
    private final String filepath;

    public Shader(String filepath) {
        this.filepath = filepath;
        //rework this
        try {
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\r\n", index);

            String firstPattern = source.substring(index, eol).trim();

            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\r\n", index);

            String secondPattern = source.substring(index, eol).trim();

            if(firstPattern.equals("vertex")){
                vertexSource = splitString[1];
            }
            else if(firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            }
            else {
                throw new IOException("Unexpected token: " + firstPattern);
            }

            if(secondPattern.equals("vertex")){
                vertexSource = splitString[2];
            }
            else if(secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];
            }
            else {
                throw new IOException("Unexpected token: " + secondPattern);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
            assert false : "Error: Couldn't open file at: " + filepath;
        }

        System.out.println(vertexSource);
        System.out.println(fragmentSource);
    }

    public void compile() {
        int vertexId, fragmentId;

        vertexId = glCreateShader(GL_VERTEX_SHADER);

        glShaderSource(vertexId, vertexSource);
        glCompileShader(vertexId);

        int status = glGetShaderi(vertexId, GL_COMPILE_STATUS);
        if(status == GL_FALSE) {
            int len = glGetShaderi(vertexId, GL_INFO_LOG_LENGTH);
            System.err.println(filepath + " vertex shader compilation error");
            System.err.println(glGetShaderInfoLog(vertexId, len));
            assert false : "";
        }

        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(fragmentId, fragmentSource);
        glCompileShader(fragmentId);

        status = glGetShaderi(fragmentId, GL_COMPILE_STATUS);
        if(status == GL_FALSE) {
            int len = glGetShaderi(fragmentId, GL_INFO_LOG_LENGTH);
            System.err.println(filepath + " vertex shader compilation error");
            System.err.println(glGetShaderInfoLog(fragmentId, len));
            assert false : "";
        }

        shaderPID = glCreateProgram();
        glAttachShader(shaderPID,vertexId);
        glAttachShader(shaderPID,fragmentId);
        glLinkProgram(shaderPID);

        status = glGetProgrami(shaderPID, GL_LINK_STATUS);
        if(status == GL_FALSE) {
            int len = glGetShaderi(shaderPID, GL_INFO_LOG_LENGTH);
            System.err.println(filepath + " shader linking error");
            System.err.println(glGetShaderInfoLog(shaderPID, len));
            assert false : "";
        }
    }

    public void use() {
        glUseProgram(shaderPID);
    }

    public void detach() {
        glUseProgram(0);
    }

    public void upload(String name, Matrix4f mat4){
        int varLocation = glGetUniformLocation(shaderPID, name);
        FloatBuffer mBuff = BufferUtils.createFloatBuffer(16);
        mat4.get(mBuff);
        glUniformMatrix4fv(varLocation, false, mBuff);
    }
}
