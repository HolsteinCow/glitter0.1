package Engine;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener {
    private static KeyListener key;
    private boolean[] keyPressed = new boolean[350];

    private KeyListener(){}

    public static KeyListener get(){
        if (key == null){
            KeyListener.key = new KeyListener();
        }
        return KeyListener.key;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods){
        if(key < get().keyPressed.length) {
            if (action == GLFW_PRESS) {
                get().keyPressed[key] = true;
            } else if (action == GLFW_RELEASE) {
                get().keyPressed[key] = false;
            }
        }
    }

    public static boolean isKeyPressed(int key){
        if(key < get().keyPressed.length)
            return get().keyPressed[key];
        throw new IllegalArgumentException("invalid input");
    }
}
