package input;

import org.lwjgl.glfw.GLFWScrollCallback;

public class MouseScroll extends GLFWScrollCallback {

    private static float XScroll;
    private static float YScroll;

    @Override
    public void invoke(long window, double xoffset, double yoffset) {
        XScroll = (float) xoffset;
        YScroll = (float) yoffset;
    }

    public float getXScroll() {
        float result = XScroll;
        XScroll = 0.0f;
        return result;
    }

    public float getYScroll() {
        float result = YScroll;
        YScroll = 0.0f;
        return result;
    }
}
