package render;

import engine.Time;
import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import static org.lwjgl.glfw.GLFW.*;

public class Window {

    private static long window;

    private static GLFWVidMode vidmode;
    private static boolean mouseState;

    private static long lastTime;
    private static float delta;

    public static void createWindow(int width, int height, String title, boolean vsync) {
        initialize();
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        glfwMakeContextCurrent(window);

        if(vsync)
            glfwSwapInterval(1);
        else if (!vsync)
            glfwSwapInterval(0);

        glfwShowWindow(window);

    }

    public static void update() {
        glfwSwapBuffers(window);
        glfwPollEvents();

    }

    private static void initialize() {
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    }

    public static boolean shouldClose() {
        return !glfwWindowShouldClose(window);
    }

    public static void mouseState(boolean lock) {
        mouseState = lock;
        glfwSetInputMode(window, GLFW_CURSOR, lock ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
    }

    public static boolean getMouseState() {
        return mouseState;
    }

    public static long getWindow() {
        return window;
    }

    public static int getWidth() {
        return vidmode.width();
    }

    public static int getHeight() {
        return vidmode.height();
    }

}
