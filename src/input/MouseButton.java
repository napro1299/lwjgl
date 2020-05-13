package input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.*;

public class MouseButton extends GLFWMouseButtonCallback {

    private static boolean[] buttonsReleased = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private static boolean[] buttonsPressed = new boolean[GLFW_MOUSE_BUTTON_LAST];

    @Override
    public void invoke(long window, int button, int action, int mods) {
        buttonsPressed[button] = action != GLFW_RELEASE;
        buttonsReleased[button] = action != GLFW_PRESS;
    }

    public boolean mouseButtonPressed(int button) {
//        boolean result = buttonsPressed[button];
//        System.out.println("PRESSED" + result);
//        buttonsPressed[button] = false;
        return buttonsPressed[button];
    }

    public boolean mouseButtonReleased(int button) {
//        boolean result = buttonsReleased[button];
//        System.out.println("RELEASED" + result);
//        buttonsReleased[button] = false;
        return buttonsReleased[button];
    }

    public boolean mouseButtonDragged(int button) {
        //System.out.println("DRAGGED: " + (buttonsPressed[button] && !buttonsReleased[button]));
        return buttonsPressed[button] && !buttonsReleased[button];
    }

}
