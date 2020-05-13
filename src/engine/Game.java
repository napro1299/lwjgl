package engine;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
import input.Callback;
import input.Input;
import input.MousePos;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import render.*;
import terrain.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import textures.TexturedModel;
import util.MousePicker;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Game {

    Loader loader;
    List<Entity> entities;
    Camera camera;
    Light light;
    MasterRenderer renderer;
    Terrain terrain, terrain2;
    TexturedModel staticModel, grass, fern;
    TerrainTexture backgroundTexture, rTexture, gTexture, bTexture, blendMap;
    TerrainTexturePack texturePack;
    Player player;
    TexturedModel stanfordBunny;
    ModelTexture fernTextureAtlas;
    TexturedModel lamp;

    MousePicker picker;

    GuiTexture gui;
    GuiTexture gui2;
    GuiRenderer guiRenderer;
    List<GuiTexture> guis;
    List<Light> lights;

    public Game() {
        init();
        run();

        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(Window.getWindow());
        glfwDestroyWindow(Window.getWindow());

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        Window.createWindow(1280, 720, "LWJGL", false);
        GL.createCapabilities();

        Callback.setCallBacks();
        Input.setInputCallbacks();

        loader = new Loader();

        renderer = new MasterRenderer(loader);

        // TERRAIN TEXTURE STUFF //

        backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        bTexture = new TerrainTexture(loader.loadTexture("path"));

        texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        // ********************* //

        stanfordBunny = new TexturedModel(OBJLoader.loadObjModel("person", loader), new ModelTexture(loader.loadTexture("white")));
        lamp = new TexturedModel(OBJLoader.loadObjModel("lamp", loader), new ModelTexture(loader.loadTexture("lamp")));

        fernTextureAtlas = new ModelTexture(loader.loadTexture("atlas"));
        fernTextureAtlas.setNumberOfRows(2);

        fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);

        staticModel = new TexturedModel(OBJLoader.loadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));
        grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        fern.getTexture().setHasTransparency(true);

        terrain = new Terrain(0, -1, loader, texturePack, blendMap, "hm");

        entities = new ArrayList<>();
        Random random = new Random(676452);
        for (int i = 0; i < 500; i++) {
            if (i % 2 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.9f));
            }
            if (i % 5 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(grass, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, random.nextFloat() * 0.1f + 0.6f));

                x = random.nextFloat() * 800 - 400;
                z = random.nextFloat() * -600;
                y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(staticModel, new Vector3f(x, y, z), 0, 0, 0, random.nextFloat() * 1 + 4));
            }
        }

        light = new Light(new Vector3f(2000, 2000, 2000), new Vector3f(0.4f, 0.4f, 0.4f));
        lights = new ArrayList<>();
        lights.add(light);
        lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2,  2), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));

        entities.add(new Entity(lamp, new Vector3f(185, -4.7f, -293), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(370, 4.72f, -300), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1));


        player = new Player(stanfordBunny, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        camera = new Camera(player);

        guis = new ArrayList<>();
        gui = new GuiTexture(loader.loadTexture("thinmatrix"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
        gui2 = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(0.5f, 0.7f), new Vector2f(0.25f, 0.25f));
        guis.add(gui);
        guis.add(gui2);

        guiRenderer = new GuiRenderer(loader);

        picker = new MousePicker(camera, renderer.getProjectionMatrix());

    }

    private void run() {

        while (Window.shouldClose()) {
            player.move(terrain);
            camera.move();
            picker.update();
            System.out.println(picker.getCurrentRay());

            renderer.processEntity(player);
//            System.out.println(Time.getDeltaTime());
            renderer.processTerrain(terrain);
            for (Entity entity : entities) {
                renderer.processEntity(entity);
            }

            renderer.render(lights, camera);
            guiRenderer.render(guis);

            Time.start();
            Window.update();
        }
    }

    public static void main(String[] args) {
        new Game();
    }

}
