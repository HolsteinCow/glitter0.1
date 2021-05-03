package Engine;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {
    private static MouseListener mouse;

    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;

    private boolean[] mouseButtonsPressed = new boolean[5];
    private boolean dragging;

    private MouseListener(){
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    public static MouseListener get(){
        if (mouse == null){
            MouseListener.mouse = new MouseListener();
        }
        return MouseListener.mouse;
    }

    public static void mousePosCallback(long window, double _xPos, double _yPos){
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = _xPos;
        get().yPos = _yPos;
        get().dragging =
                get().mouseButtonsPressed[0] ||
                get().mouseButtonsPressed[1] ||
                get().mouseButtonsPressed[2] ||
                get().mouseButtonsPressed[3] ||
                get().mouseButtonsPressed[4];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods){
        if (action == GLFW_PRESS){
            if(button < get().mouseButtonsPressed.length)
                get().mouseButtonsPressed[button] = true;
        }
        else if (action == GLFW_RELEASE){
            if(button < get().mouseButtonsPressed.length) {
                get().mouseButtonsPressed[button] = false;
                get().dragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double _scrollX, double _scrollY){
        get().scrollX = _scrollX;
        get().scrollY = _scrollY;
    }

    public static void endFrame(){
        get().scrollX = 0.0;
        get().scrollY = 0.0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX(){
        return (float)get().xPos;
    }

    public static float getY(){
        return (float)get().yPos;
    }

    public static float dX(){
        return (float)(get().lastX - get().xPos);
    }

    public static float dY(){
        return (float)(get().lastY - get().lastX);
    }

    public static float getScrollX(){
        return (float)get().scrollX;
    }

    public static float getScrollY(){
        return (float)get().scrollY;
    }

    public static boolean getDragging(){
        return get().dragging;
    }

    public static boolean mousePressed(int button){
        if (button < get().mouseButtonsPressed.length){
            return get().mouseButtonsPressed[button];
        }
        else{
            return false;
        }
    }
}
