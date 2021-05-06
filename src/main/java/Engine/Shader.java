package Engine;
import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;


public class Shader {

    private int shaderPID;
    private boolean beingUsed = false;

    private String vertexSource;
    public String fragmentSource;
    private final String filepath;

    public Shader(String filepath) {
        this.filepath = filepath;
        //rework this
        try {
            String source = new String(Files.readAllBytes(Paths.get(filepath)));
           /* BufferedReader in = new BufferedReader(new FileReader("../Engine/Assets/Shaders/default.glsl"));
            StringBuilder sb = new StringBuilder();
            String input = null;
            while ((input = in.readLine()) != null) {
                    sb.append(input);
            }

            String source = sb.toString();*/

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

        System.out.println(vertexId + " : " + vertexSource);
        glShaderSource(vertexId, vertexSource);
        glCompileShader(vertexId);

        int status = glGetShaderi(vertexId, GL_COMPILE_STATUS);
        if(status == GL_FALSE) {
            int len = glGetShaderi(vertexId, GL_INFO_LOG_LENGTH);
            System.out.println(filepath + " vertex shader compilation error");
            System.out.println(glGetShaderInfoLog(vertexId, len));
            //assert false : "";
        }

        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);

        System.out.println(fragmentId + " : " + fragmentSource);
        glShaderSource(fragmentId, fragmentSource);
        glCompileShader(fragmentId);

        status = glGetShaderi(fragmentId, GL_COMPILE_STATUS);
        if(status == GL_FALSE) {
            int len = glGetShaderi(fragmentId, GL_INFO_LOG_LENGTH);
            System.out.println(filepath + " fragment compilation error");
            System.out.println(glGetShaderInfoLog(fragmentId, len));
            //assert false : "";
        }

        shaderPID = glCreateProgram();
        glAttachShader(shaderPID,vertexId);
        glAttachShader(shaderPID,fragmentId);
        glLinkProgram(shaderPID);

        status = glGetProgrami(shaderPID, GL_LINK_STATUS);
        if(status == GL_FALSE) {
            int len = glGetShaderi(shaderPID, GL_INFO_LOG_LENGTH);
            System.out.println(filepath + " shader linking error");
            System.out.println(glGetShaderInfoLog(shaderPID, len));
            //assert false : "";
        }
    }

    public void use() {
        if (!beingUsed) {
            glUseProgram(shaderPID);
            beingUsed = true;
        }
    }

    public void detach() {
        glUseProgram(0);
        beingUsed = false;
    }

    public void uploadMat4f(String name, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(shaderPID, name);
        use();
        FloatBuffer mBuff = BufferUtils.createFloatBuffer(16);
        mat4.get(mBuff);
        glUniformMatrix4fv(varLocation, false, mBuff);
    }

    public void uploadMat3f(String name, Matrix3f mat3) {
        int varLocation = glGetUniformLocation(shaderPID, name);
        use();
        FloatBuffer mBuff = BufferUtils.createFloatBuffer(9);
        mat3.get(mBuff);
        glUniformMatrix3fv(varLocation, false, mBuff);
    }

    public void uploadVect4f(String name, Vector4f vec4) {
        int varLocation = glGetUniformLocation(shaderPID, name);
        use();
        glUniform4f(varLocation, vec4.x, vec4.y, vec4.z, vec4.w);
    }

    public void uploadFloat(String name, float val) {
        int varLocation = glGetUniformLocation(shaderPID, name);
        use();
        glUniform1f(varLocation, val);
    }

    public void uploadInt(String name, int val) {
        int varLocation = glGetUniformLocation(shaderPID, name);
        use();
        glUniform1i(varLocation, val);
    }

    public void uploadVect3f(String name, Vector3f vec3) {
        int varLocation = glGetUniformLocation(shaderPID, name);
        use();
        glUniform3f(varLocation, vec3.x, vec3.y, vec3.z);
    }

    public void uploadVect2f(String name, Vector2f vec2) {
        int varLocation = glGetUniformLocation(shaderPID, name);
        use();
        glUniform2f(varLocation, vec2.x, vec2.y);
    }

}
