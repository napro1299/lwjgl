package input;

public class Input {

    // Input callbacks
    private static KeyInput keyInput;
    private static MouseButton mouseButton;
    private static MousePos mousePos;
    private static MouseScroll mouseScroll;

    private Input() {

    }

    public static void setInputCallbacks() {
        keyInput = new KeyInput();
        mouseButton = new MouseButton();
        mousePos = new MousePos();
        mouseScroll = new MouseScroll();
    }

    /* Static getter methods */

    public static boolean isKeyPressed(int keycode) {
        return keyInput.keyPressed(keycode);
    }

    public static boolean isKeyReleased(int keycode) {
        return keyInput.keyReleased(keycode);
    }

    public static boolean isKeyDown(int keycode) {
        return keyInput.keyDown(keycode);
    }

    public static boolean isKeyHeld(int keycode) {
        return keyInput.keyHeld(keycode);
    }

    public static boolean isMouseButtonPressed(int button) {
        return mouseButton.mouseButtonPressed(button);
    }

    public static boolean isMouseButtonReleased(int button) {
        return mouseButton.mouseButtonReleased(button);
    }

    public static boolean isMouseButtonDragged(int button) {
        return mouseButton.mouseButtonDragged(button);
    }

    public static double getMouseX() {
        return mousePos.getMouseX();
    }

    public static double getMouseY() {
        return mousePos.getMouseY();
    }

    public static double getMouseDX() {
        return mousePos.getMouseDX();
    }

    public static double getMouseDY() {
        return mousePos.getMouseDY();
    }

    public static float getMouseXScroll() {
        return mouseScroll.getXScroll();
    }

    public static float getMouseYScroll() {
        return mouseScroll.getYScroll();
    }

}
