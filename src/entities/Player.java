package entities;

import engine.Time;
import input.Input;
import input.KeyInput;
import org.joml.Vector3f;
import render.Window;
import terrain.Terrain;
import textures.TexturedModel;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private static final float TERRAIN_HEIGHT = 0;

    private static float delta = 0.001f;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean inAir = false;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(Terrain terrain) {
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed * delta, 0);
        float distance = currentSpeed * delta;
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        upwardsSpeed += GRAVITY * delta;
        super.increasePosition(0, upwardsSpeed * delta, 0);
        if (super.getPosition().y < terrainHeight) {
            upwardsSpeed = 0;
            inAir = false;
            super.getPosition().y = terrainHeight;
        }
    }

    private void jump() {
        if(!inAir) {
            this.upwardsSpeed = JUMP_POWER;
            inAir = true;
        }
    }

    private void checkInputs() {
        if(Input.isKeyDown(GLFW_KEY_W)) {
            this.currentSpeed = RUN_SPEED;
        } else if (Input.isKeyDown(GLFW_KEY_S)) {
            this.currentSpeed = -RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (Input.isKeyDown(GLFW_KEY_D)) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else if (Input.isKeyDown(GLFW_KEY_A)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        if (Input.isKeyDown(GLFW_KEY_SPACE)) {
            jump();
        }
    }

}
