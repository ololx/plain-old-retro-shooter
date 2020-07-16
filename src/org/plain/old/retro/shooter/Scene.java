package org.plain.old.retro.shooter;

import org.plain.old.retro.shooter.clock.RateTimer;
import org.plain.old.retro.shooter.listener.KeyboardController;
import org.plain.old.retro.shooter.monitor.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Game.
 * This is a main scene class, which is runnable and
 * will manage all scene logics (such as fps, screen update && e.t.c.)
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 19.06.2020 08:58 <p>
 */
public class Scene extends JFrame {

    public static final int SCENE_WIDTH = 1280;
    public static final int SCENE_HEIGHT = 720;

    public static final int BLOCKSIZE = 50;

    public static final int SC_BLOCKSIZE = 300;

    private RateTimer sceneTemp;

    private RateTimer renderTemp;

    private Camera mainP;

    private BufferedImage image;
    public int[] pixels;
    public Screen screen;

    //TODO: Refactor It when main entities will be completed - it's just for tests
    /**
     * Instantiates a new Game.
     */
    public Scene() {
        Space2d map = new Space2d(
                new int[][]{
                        {1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
                        {1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
                        {1,0,3,3,3,0,3,0,0,0,0,0,0,0,2},
                        {1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
                        {1,0,3,0,0,0,3,0,2,2,2,0,2,2,2},
                        {1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
                        {1,0,3,3,0,3,3,0,2,0,0,0,0,0,2},
                        {1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
                        {1,1,1,1,1,1,1,1,4,4,4,0,4,4,4},
                        {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
                        {1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}
                }
        );
        KeyboardController controller = new KeyboardController(new HashMap<Integer, String>(){{
            put(KeyEvent.VK_W, "MV_FWD");
            put(KeyEvent.VK_S, "MV_BWD");
            put(KeyEvent.VK_A, "TN_LFT");
            put(KeyEvent.VK_D, "TN_RGT");
            put(KeyEvent.VK_LEFT, "MV_LFT");
            put(KeyEvent.VK_RIGHT, "MV_RHT");
        }});
        mainP = new Camera(1, 5, 1, 0, 0, -.66);
        image = new BufferedImage(SCENE_WIDTH, SCENE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        screen = new Screen(map.getSpace(), SCENE_WIDTH, SCENE_HEIGHT);
        this.setSize(SCENE_WIDTH, SCENE_HEIGHT);
        addKeyListener(controller);
        setSize(image.getWidth(), image.getHeight());
        setResizable(true);
        setTitle("The Plain Old Retro Shooter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);
        this.sceneTemp = new RateTimer(
                30,
                () -> {
                    for (Map.Entry<String, Boolean> state : controller.getState().entrySet()) {

                        if (state.getValue()) {
                            if (state.getKey().equals("MV_FWD")) {
                                mainP.moveForward(map.getSpace());
                            }

                            if (state.getKey().equals("MV_BWD")) {
                                mainP.moveBackward(map.getSpace());
                            }

                            if (state.getKey().equals("MV_LFT")) {
                                mainP.moveLeft();
                            }

                            if (state.getKey().equals("MV_RHT")) {
                                mainP.moveRight();
                            }

                            if (state.getKey().equals("TN_LFT")) {
                                mainP.turnLeft(map.getSpace());
                            }

                            if (state.getKey().equals("TN_RGT")) {
                                mainP.turnRight(map.getSpace());
                            }
                        }
                    }
                },
                () -> System.out.printf(
                        "UPS: %s; FPS: %s; KEYS: %s PLAYER %s\r",
                        sceneTemp.getHerz(),
                        renderTemp.getHerz(),
                        controller.getState(),
                        mainP.toString()
                )
        );
        renderTemp = new RateTimer(
                120,
                () -> screen.render(pixels, mainP.position, mainP.direction, mainP.plain),
                () -> this.render(map)
        );
    }

    /**
     * Init.
     */
    public void init() {
        requestFocus();
        this.sceneTemp.start();
        this.renderTemp.start();
    }

    public void render(Space2d map) {
        int height = this.getSize().height;
        int width = this.getSize().width;
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, width, height,null);
        bs.show();
    }
}
