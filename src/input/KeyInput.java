package input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class KeyInput extends GLFWKeyCallback {

    public static boolean[] keysPressed = new boolean[GLFW_KEY_LAST];
    public static boolean[] keysReleased = new boolean[GLFW_KEY_LAST];
    public static boolean[] keysRepeat = new boolean[GLFW_KEY_LAST];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keysPressed[key] = action != GLFW_RELEASE;
        keysReleased[key] = action == GLFW_RELEASE;
        keysRepeat[key] = action == GLFW_REPEAT;
    }

    public boolean keyPressed(int keycode) {
        boolean result = keysPressed[keycode];
        keysPressed[keycode] = false;
        return result;
    }

    public boolean keyReleased(int keycode) {
        boolean result = keysReleased[keycode];
        keysReleased[keycode] = false;
        return result;
    }

    public boolean keyDown(int keycode) {
        return keysPressed[keycode] && !keysReleased[keycode];
    }

    public boolean keyHeld(int keycode) {
        return keysRepeat[keycode];
    }

}
