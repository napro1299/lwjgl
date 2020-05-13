package engine;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {

    private static double last;
    private static float delta;
    private static double currentTime;

    public static void start() {
        last = getCurrentTime();
        update();
    }

    private static void update() {
        currentTime = getCurrentTime();
        delta = (float) (currentTime - last);
        last = currentTime;
//        System.out.println(getCurrentTimeMillis());
    }

    public static double getCurrentTime() {
        return glfwGetTime();
    }

    public static float getDeltaTime() {
        return delta;
    }

    public static double getCurrentTimeMillis() {
        return glfwGetTime() * 1000.0;
    }

}





