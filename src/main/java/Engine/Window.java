package Engine;

import Engine.Screens.TitleScreen;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private final int height, width;
    private final String title;

    private static Window w = null;

    private long glfwWindow;

    private Screen s = null;

    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Window";
        s = new TitleScreen();
    }

    public static Window getWindow(){
        if(Window.w == null){
            Window.w = new Window();
        }
        return Window.w;
    }

    //public static void changeScreen(int newScreen){

    //}


    public void run(){
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();



        loop();

        glfwFreeCallbacks(this.glfwWindow);
        glfwDestroyWindow(this.glfwWindow);


        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        this.glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if (this.glfwWindow == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetCursorPosCallback(this.glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(this.glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(this.glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(this.glfwWindow, KeyListener::keyCallback);

        glfwMakeContextCurrent(this.glfwWindow);
        glfwSwapInterval(1);
        glfwShowWindow(this.glfwWindow);

    }

    public void loop(){
        GL.createCapabilities();

        s.init();

        float begin = Time.get();
        float dT = 0;
        float end;

        while(!glfwWindowShouldClose(this.glfwWindow)){
            glfwPollEvents();

            glClearColor(1.0f,1.0f,1.0f,1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)){
                System.out.println("im in space " + dT);
            }

            s.update(dT);

            glfwSwapBuffers(this.glfwWindow);

            end = Time.get();
            dT = end - begin;
            begin = end;
        }
    }
}
