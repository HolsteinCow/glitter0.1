package Render;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private final int height, width;
    private final String title;

    private static Window w = null;

    private long glfwWindow;

    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Window";
    }

    public static Window getWindow(){
        if(Window.w == null){
            Window.w = new Window();
        }
        return Window.w;
    }

    public void run(){
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        this.glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if ( this.glfwWindow == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(this.glfwWindow);
        glfwSwapInterval(1);
        glfwShowWindow(this.glfwWindow);
    }

    public void loop(){
        GL.createCapabilities();

        while(!glfwWindowShouldClose(this.glfwWindow)){
            glfwPollEvents();
            glClearColor(0.0f,0.0f,0.0f,1.0f);
            glClear(GL_COLOR_BUFFER_BIT);
            glfwSwapBuffers(this.glfwWindow);
        }
    }
}
