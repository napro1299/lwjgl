package input;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import render.Window;

import static org.lwjgl.glfw.GLFW.*;

public class Callback {

    private static GLFWKeyCallback keyCallback;
    private static GLFWCursorPosCallback cursorPosCallback;
    private static GLFWMouseButtonCallback mouseButtonCallback;
    private static GLFWScrollCallback scrollCallback;

    public static void setCallBacks() {
        glfwSetKeyCallback(Window.getWindow(), keyCallback = new KeyInput());
        glfwSetCursorPosCallback(Window.getWindow(), cursorPosCallback = new MousePos());
        glfwSetMouseButtonCallback(Window.getWindow(), mouseButtonCallback = new MouseButton());
        glfwSetScrollCallback(Window.getWindow(), scrollCallback = new MouseScroll());
    }

    public static GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    public static GLFWCursorPosCallback getCursorPosCallback() {
        return cursorPosCallback;
    }

    public static GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButtonCallback;
    }

    public static GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }
}
