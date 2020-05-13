package input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;

public class  MousePos extends GLFWCursorPosCallback {

    private static double mouseX;
    private static double mouseY;
    private static double mouseDY;
    private static double mouseDX;

    @Override
    public void invoke(long window, double xpos, double ypos) {
        mouseDX = xpos - mouseX;
        mouseDY = ypos - mouseY;
        mouseX = xpos;
        mouseY = ypos;
    }

    public double getMouseDX() {
        double result = mouseDX;
        mouseDX = 0;
        return result;
    }

    public double getMouseDY() {
        double result = mouseDY;
        mouseDY = 0;
        return result;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

}
