package org.plain.old.retro.shooter;

import org.plain.old.retro.shooter.engine.Space2d;
import org.plain.old.retro.shooter.engine.clock.Clock;
import org.plain.old.retro.shooter.engine.clock.LowIntensiveClock;
import org.plain.old.retro.shooter.engine.graphics.Camera;
import org.plain.old.retro.shooter.engine.graphics.Screen;
import org.plain.old.retro.shooter.engine.graphics.Sprite;
import org.plain.old.retro.shooter.engine.listener.InputController;
import org.plain.old.retro.shooter.engine.physics.BulletHitScanner;
import org.plain.old.retro.shooter.engine.unit.Enemy;
import org.plain.old.retro.shooter.engine.unit.RegisterEntity;
import org.plain.old.retro.shooter.engine.unit.Unit;
import org.plain.old.retro.shooter.engine.unit.equipment.bullet.Bullet;
import org.plain.old.retro.shooter.engine.unit.equipment.weapon.BoomStick;
import org.plain.old.retro.shooter.multi.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * The type Game.
 * <p>
 * This is a main scene class, which is runnable and
 * will manage all scene logics (such as fps, screen update && e.t.c.)
 * <p>
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 19.06.2020 08:58
 */
public class Scene extends JFrame {

    /**
     * The constant SCENE_WIDTH.
     */
    public static final int SCENE_WIDTH = 1024;//2560;

    /**
     * The constant SCENE_HEIGHT.
     */
    public static final int SCENE_HEIGHT = 600;//1440;

    /**
     * The Scene temp.
     */
    private Clock sceneTemp;

    /**
     * The Render temp.
     */
    private Clock renderTemp;

    /**
     * The Client temp.
     */
    private Clock clientTemp;

    /**
     * The Main player.
     */
    private Camera mainPlayer;

    /**
     * The Image.
     */
    private BufferedImage image;

    /**
     * The Pixels.
     */
    public int[] pixels;

    /**
     * The Screen.
     */
    public Screen screen;

    /**
     * The Stick.
     */
    private BoomStick stick;

    /**
     * The Enemies.
     */
    private ConcurrentSkipListSet<Enemy> enemies;

    /**
     * The Temp bullets.
     */
    private ConcurrentSkipListSet<Bullet> tempBullets = new ConcurrentSkipListSet<>();

    /**
     * The Bullets.
     */
    private ConcurrentSkipListSet<Bullet> bullets = new ConcurrentSkipListSet<>();

    /**
     * The Other units.
     */
    private ConcurrentSkipListMap<UUID, Unit> otherUnits = new ConcurrentSkipListMap<>();

    /**
     * The Client.
     */
    private Client client;

    //TODO: Refactor It when main entities will be completed - it's just for tests

    /**
     * Instantiates a new Game.
     *
     * @param client the client
     */
    public Scene(Client client) {
        this.client = client;
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

        this.enemies = new ConcurrentSkipListSet<>(){{
            add(new Enemy(7.5, 7.5, new Sprite("src/resources/enemy-1.png",1.3 , 1.3)));
            add(new Enemy(25.5, 3.5, new Sprite("src/resources/enemy-1.png",1.3 , 1.3)));
            add(new Enemy(21.5, 7.5, new Sprite("src/resources/enemy-1.png",1.3 , 1.3)));
            add(new Enemy(22.5, 6.5, new Sprite("src/resources/enemy-2.png",1.3 , 1.3)));
            add(new Enemy(23.5, 7.5, new Sprite("src/resources/enemy-2.png",1.3 , 1.3)));
            add(new Enemy(20.5, 12.5, new Sprite("src/resources/enemy-2.png",1.3 , 1.3)));
            add(new Enemy(5.5, 10.5, new Sprite("src/resources/enemy-3.png",1.3 , 1.3)));
            add(new Enemy(14.5, 19.5, new Sprite("src/resources/enemy-3.png",1.3 , 1.3)));
            add(new Enemy(12.5, 10.5, new Sprite("src/resources/enemy-3.png",1.3 , 1.3)));
        }};
        mainPlayer = new Camera(1.5, 2.5, 1, 0, SCENE_WIDTH, SCENE_HEIGHT, 60);
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
                }},
                mainPlayer
        );
        setSize(SCENE_WIDTH, SCENE_HEIGHT);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB),
                new Point(0, 0),
                "blank")
        );
        setSize(image.getWidth(), image.getHeight());
        setResizable(true);
        setTitle("The Plain Old Retro Shooter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);

        Point center = getLocationOnScreen();
        center.setLocation(
                center.x + (this.getWidth() >> 1),
                center.y + (this.getHeight() >> 1)
        );

        InputController controller = new InputController(new HashMap<Integer, String>(){{
            put(KeyEvent.VK_W, "MV_FWD");
            put(KeyEvent.VK_S, "MV_BWD");
            put(KeyEvent.VK_A, "TN_LFT");
            put(KeyEvent.VK_D, "TN_RGT");
            put(KeyEvent.VK_LEFT, "MV_LFT");
            put(MouseEvent.MOUSE_MOVED + 1, "MV_LFT2");
            put(MouseEvent.MOUSE_MOVED + 2, "MV_RHT2");
            put(KeyEvent.VK_RIGHT, "MV_RHT");
            put(KeyEvent.VK_UP, "MV_UP");
            put(KeyEvent.VK_DOWN, "MV_DOWN");
            put(KeyEvent.VK_SPACE, "SHOT");
            put(KeyEvent.VK_R, "RELOAD");
        }},
                center
        );

        addKeyListener(controller);
        addMouseListener(controller);
        addMouseMotionListener(controller);

        System.out.println(center);

        this.sceneTemp = new LowIntensiveClock(
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

                            if (state.getKey().equals("MV_LFT2")) {
                                mainPlayer.moveLeft();
                            }

                            if (state.getKey().equals("MV_RHT")) {
                                mainPlayer.moveRight();
                            }

                            if (state.getKey().equals("MV_RHT2")) {
                                mainPlayer.moveRight();
                            }

                            if (state.getKey().equals("MV_UP")) {
                                mainPlayer.up();
                            }

                            if (state.getKey().equals("MV_DOWN")) {
                                mainPlayer.down();
                            }

                            if (state.getKey().equals("TN_LFT")) {
                                mainPlayer.turnLeft(map.getSpace());
                            }

                            if (state.getKey().equals("TN_RGT")) {
                                mainPlayer.turnRight(map.getSpace());
                            }

                            if (state.getKey().equals("SHOT")) {
                                Vector<Bullet> bullets = stick.shoot(mainPlayer.getPosition(), mainPlayer.getDirection());
                                this.bullets.addAll(bullets);
                                this.tempBullets.addAll(bullets);
                            }

                            if (state.getKey().equals("RELOAD")) {
                                this.stick.reload();
                            }
                        }
                    }
                },
                () -> {
                    this.stick.update();

                    synchronized (bullets) {
                        for (Bullet bullet : bullets) {
                            if (!bullet.isExist()) this.bullets.remove(bullet);
                        }
                    }

                    synchronized (bullets) {
                        for (Enemy enemy : enemies) {
                            if (!enemy.isExist()) this.enemies.remove(enemy);
                        }
                    }

                    BulletHitScanner.scan(this.bullets, this.enemies, sceneTemp.getFrequency(), map);
                },
                () -> {
                    screen.rayCast(this.mainPlayer);
                }
        );
        renderTemp = new LowIntensiveClock(
                120,
                () -> screen.render(
                        pixels,
                        this.mainPlayer,
                        this.stick,
                        this.enemies,
                        this.bullets,
                        this.otherUnits.values()
                ),
                () -> this.render(
                        map, String.format(
                                "UPS: %s \n FPS: %s \n NETPS: %s",
                                sceneTemp.getFrequency(),
                                renderTemp.getFrequency(),
                                clientTemp.getFrequency()
                                )
                )
        );

        clientTemp = new LowIntensiveClock(
                sceneTemp.getFrequency(),
                () -> {

                    Set<Object> responseMessages = new HashSet<>();
                    this.client.connect();
                    Object requestMessage = mainPlayer;
                    Object responseMessage = this.client.sendMessage(requestMessage);
                    this.client.disconnect();
                    if (responseMessage != null) responseMessages.add(responseMessage);

                    for (Bullet bullet : tempBullets) {
                        this.client.connect();
                        responseMessage = this.client.sendMessage(bullet);
                        if (responseMessage != null) responseMessages.add(responseMessage);
                        this.client.disconnect();
                    }
                    tempBullets.clear();

                    for (Object unit : responseMessages) {
                        if (unit instanceof Bullet && !this.bullets.contains((unit))) {
                            this.bullets.add((Bullet) unit);
                        } else {
                            this.otherUnits.put(((RegisterEntity) unit).getUid(), (Unit) unit);
                        }
                    }

                }
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
        this.clientTemp.start();
    }

    /**
     * Render.
     *
     * @param map      the map
     * @param rateInfo the rate info
     */
    public void render(Space2d map, String rateInfo) {
        int height = this.getSize().height;
        int width = this.getSize().width;

        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, width, height,null);
        g.setColor(Color.GREEN);
        g.setFont(g.getFont().deriveFont(30f));
        g.drawString(rateInfo, 15, 45);
        bs.show();
    }
}
