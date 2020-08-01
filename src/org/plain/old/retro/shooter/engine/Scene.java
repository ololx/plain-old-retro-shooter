package org.plain.old.retro.shooter.engine;

import org.plain.old.retro.shooter.engine.clock.RateTimer;
import org.plain.old.retro.shooter.engine.graphics.Sprite;
import org.plain.old.retro.shooter.unit.Enemy;
import org.plain.old.retro.shooter.unit.equipment.bullet.Bullet;
import org.plain.old.retro.shooter.unit.equipment.weapon.BoomStick;
import org.plain.old.retro.shooter.engine.listener.KeyboardController;
import org.plain.old.retro.shooter.engine.graphics.Screen;
import org.plain.old.retro.shooter.engine.physics.BulletHitScanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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

    public static final int SCENE_WIDTH = 800;

    public static final int SCENE_HEIGHT = 600;

    private RateTimer sceneTemp;

    private RateTimer renderTemp;

    private Camera mainPlayer;

    private BufferedImage image;
    public int[] pixels;
    public Screen screen;

    private BoomStick stick;

    private Vector<Enemy> enemies;

    private Vector<Bullet> bullets = new Vector<>();

    //TODO: Refactor It when main entities will be completed - it's just for tests
    /**
     * Instantiates a new Game.
     */
    public Scene() {
        Space2d map = new Space2d(
                new int[][]{
                        {1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                        {1,0,0,0,3,0,0,4,0,0,2,1,1,0,2},
                        {1,0,2,2,3,0,0,4,0,0,3,0,0,0,2},
                        {1,0,2,0,3,0,4,1,0,0,2,4,3,0,2},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                        {1,0,1,0,0,0,0,0,0,0,0,0,0,0,2},
                        {1,0,1,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,1,0,0,0,0,2,0,0,0,0,0,0,4},
                        {1,0,1,0,0,0,0,2,0,0,0,0,0,0,4},
                        {1,0,1,0,0,0,0,3,0,0,0,0,0,0,4},
                        {1,0,1,0,0,0,0,1,0,0,0,0,0,0,4},
                        {1,0,1,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,1,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,1,1,2,3,1,4,1,3,1,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
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
            put(KeyEvent.VK_SPACE, "SHOT");
            put(KeyEvent.VK_R, "RELOAD");
        }});
        this.enemies = new Vector<>(){{
            add(new Enemy(7.5, 7.5, new Sprite("src/resources/enemy-1.png")));
            add(new Enemy(25.5, 3.5, new Sprite("src/resources/enemy-1.png")));
            add(new Enemy(21.5, 7.5, new Sprite("src/resources/enemy-1.png")));
            add(new Enemy(22.5, 6.5, new Sprite("src/resources/enemy-2.png")));
            add(new Enemy(23.5, 7.5, new Sprite("src/resources/enemy-2.png")));
            add(new Enemy(20.5, 12.5, new Sprite("src/resources/enemy-2.png")));
            add(new Enemy(5.5, 10.5, new Sprite("src/resources/enemy-3.png")));
            add(new Enemy(14.5, 19.5, new Sprite("src/resources/enemy-3.png")));
            add(new Enemy(12.5, 10.5, new Sprite("src/resources/enemy-3.png")));
        }};
        mainPlayer = new Camera(1, 2, 1, 0, 0, 0);
        image = new BufferedImage(SCENE_WIDTH, SCENE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        screen = new Screen(
                map.getSpace(),
                SCENE_WIDTH,
                SCENE_HEIGHT,
                new ArrayList<>(){{
                    add(new Sprite("src/resources/room/wall-1.png"));
                    add(new Sprite("src/resources/room/wall-2.png"));
                    add(new Sprite("src/resources/room/wall-3.png"));
                    add(new Sprite("src/resources/room/wall-4.png"));
                    add(new Sprite("src/resources/room/ceiling.png"));
                    add(new Sprite("src/resources/room/floor.png"));
                }}
        );
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
                                mainPlayer.moveForward(map.getSpace());
                            }

                            if (state.getKey().equals("MV_BWD")) {
                                mainPlayer.moveBackward(map.getSpace());
                            }

                            if (state.getKey().equals("MV_LFT")) {
                                mainPlayer.moveLeft();
                            }

                            if (state.getKey().equals("MV_RHT")) {
                                mainPlayer.moveRight();
                            }

                            if (state.getKey().equals("TN_LFT")) {
                                mainPlayer.turnLeft(map.getSpace());
                            }

                            if (state.getKey().equals("TN_RGT")) {
                                mainPlayer.turnRight(map.getSpace());
                            }

                            if (state.getKey().equals("SHOT")) {
                                this.bullets.addAll(stick.shoot(mainPlayer.position, mainPlayer.direction));
                            }

                            if (state.getKey().equals("RELOAD")) {
                                this.stick.reload();
                            }
                        }
                    }
                },
                () -> {
                    this.stick.update();

                    for (int i = 0; i < this.bullets.size(); i++) {
                        Bullet bullet = this.bullets.get(i);
                        if (bullet.isHited) this.bullets.remove(i);
                    }

                    for (int j = 0; j < this.enemies.size(); j++) {
                        Enemy enemy = this.enemies.get(j);
                        if (!enemy.isAlive) this.enemies.remove(j);
                    }

                    BulletHitScanner.scan(this.bullets, this.enemies, sceneTemp.getHerz(), map);
                }
        );
        renderTemp = new RateTimer(
                90,
                () -> screen.render(
                        pixels,
                        mainPlayer.position,
                        mainPlayer.direction,
                        mainPlayer.plain,
                        this.stick,
                        this.enemies,
                        this.bullets
                ),
                () -> this.render(map, String.format(
                        "UPS: %s \n FPS: %s",
                        sceneTemp.getHerz(),
                        renderTemp.getHerz())
                )
        );
    }

    /**
     * Init.
     */
    public void init() {
        requestFocus();
        this.stick = new BoomStick(
                new ArrayList<>(){{
                    add(new Sprite("src/resources/equip/boomstick-1.png", 3, 3));
                    add(new Sprite("src/resources/equip/boomstick-2.png", 3, 3));
                    add(new Sprite("src/resources/equip/boomstick-3.png", 3, 3));
                    add(new Sprite("src/resources/equip/boomstick-4.png", 3, 3));
                    add(new Sprite("src/resources/equip/boomstick-5.png", 3, 3));
                    add(new Sprite("src/resources/equip/boomstick-6.png", 3, 3));
                }},
                new ArrayList<>(){{
                    add(1);
                    add(2);
                }},
                new ArrayList<>(){{
                    add(3);
                    add(4);
                    add(5);
                }},
                0
                );
        this.renderTemp.start();
        this.sceneTemp.start();
    }

    public void render(Space2d map, String rateInfo) {
        int height = this.getSize().height;
        int width = this.getSize().width;

        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, width, height,null);
        g.setColor(Color.GREEN);
        g.setFont(g.getFont().deriveFont(50f));
        g.drawString(rateInfo, 25, 75);
        bs.show();
    }
}
