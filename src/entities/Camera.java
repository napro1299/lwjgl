package entities;

import input.Input;
import input.KeyInput;
import org.joml.Vector3f;
import render.Window;
import util.Maths;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    public static final float SENSITIVITY = 0.1f;

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch = 20;
    private float yaw = 0;
    private float roll;

    private Player player;

    public Camera(Player player) {
        this.player = player;
        Window.mouseState(true);
    }

    public void move3() {
        calculateZoom3();
        calculatePitch3();
        calculateAngleAroundPlayer3();
        float horizontalDistance = calculateHorizontalDistance3();
        float verticalDistance = calculateVerticalDistance3();
        calculateCameraPosition3(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotX() + angleAroundPlayer);
    }

    public void move() {
        this.position = new Vector3f(player.getPosition().x, player.getPosition().y + 10.5f, player.getPosition().z);
        this.yaw += Input.getMouseDX() * SENSITIVITY;
        this.pitch += Input.getMouseDY() * SENSITIVITY;
        this.pitch = Maths.clamp(this.pitch, -90.0f, 90.0f);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    private void calculateCameraPosition3(float horizDistance, float verticDistance) {
        float theta = player.getRotZ() + angleAroundPlayer;
        float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (verticDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().y - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + verticDistance;
    }

    private float calculateHorizontalDistance3() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance3() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom3() {
        float zoomLevel =  Input.getMouseYScroll() * 2.0f;
        distanceFromPlayer -= zoomLevel;
    }

    private void calculatePitch3() {
        if (Input.isMouseButtonDragged(GLFW_MOUSE_BUTTON_2)) {
            float pitchChange = (float) Input.getMouseDY() * 0.1f;
            pitch -= pitchChange;
        }
    }

    private void calculateAngleAroundPlayer3() {
        if (Input.isMouseButtonDragged(GLFW_MOUSE_BUTTON_1)) {
            float angleChange = (float) Input.getMouseDX() * 0.3f;
            angleAroundPlayer -= angleChange;
        }
    }

}
