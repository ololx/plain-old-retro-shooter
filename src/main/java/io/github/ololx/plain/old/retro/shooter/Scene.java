package io.github.ololx.plain.old.retro.shooter;

import io.github.ololx.plain.old.retro.shooter.engine.Space2d;
import io.github.ololx.plain.old.retro.shooter.engine.calculus.linear.Vector2d;
import io.github.ololx.plain.old.retro.shooter.engine.clock.Clock;
import io.github.ololx.plain.old.retro.shooter.engine.clock.Duration;
import io.github.ololx.plain.old.retro.shooter.engine.clock.LowIntensiveClock;
import io.github.ololx.plain.old.retro.shooter.engine.listener.*;
import io.github.ololx.plain.old.retro.shooter.engine.physics.BulletHitScanner;
import io.github.ololx.plain.old.retro.shooter.engine.unit.Enemy;
import io.github.ololx.plain.old.retro.shooter.engine.unit.RegisterEntity;
import io.github.ololx.plain.old.retro.shooter.engine.unit.Unit;
import io.github.ololx.plain.old.retro.shooter.engine.unit.equipment.bullet.Bullet;
import io.github.ololx.plain.old.retro.shooter.engine.unit.equipment.weapon.BoomStick;
import io.github.ololx.plain.old.retro.shooter.multi.Client;
import io.github.ololx.plain.old.retro.shooter.engine.graphics.Camera;
import io.github.ololx.plain.old.retro.shooter.engine.graphics.Screen;
import io.github.ololx.plain.old.retro.shooter.engine.graphics.Sprite;

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
    public static final int SCENE_WIDTH = 256;

    /**
     * The constant SCENE_HEIGHT.
     */
    public static final int SCENE_HEIGHT = 128;

    /**
     * The constant SCENE_WIDTH.
     */
    public static final int WIDTH = 1366;

    /**
     * The constant SCENE_HEIGHT.
     */
    public static final int HEIGHT = 768;

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
                        {1,0,7,7,7,0,0,4,0,0,3,0,0,0,2},
                        {1,0,6,0,6,0,4,1,0,0,2,4,3,0,2},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
                        {1,0,6,0,0,0,0,0,0,0,0,0,0,0,2},
                        {1,0,7,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,7,0,0,0,0,2,0,0,0,0,0,0,4},
                        {1,0,8,0,0,0,0,2,0,0,0,0,0,0,4},
                        {1,0,8,0,0,0,0,3,0,0,0,0,0,0,4},
                        {1,0,5,0,0,0,0,1,0,0,0,0,0,0,4},
                        {1,0,5,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,3,0,0,0,0,0,0,0,0,0,0,0,4},
                        {1,0,3,1,2,3,1,4,1,3,1,0,0,0,4},
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
                        {1,2,2,3,3,2,2,2,2,4,2,3,4,4,4}
                }
        );

        this.enemies = new ConcurrentSkipListSet<>(){{
            add(new Enemy(7.5, 7.5, new Sprite("enemy/18.png", SCENE_WIDTH, SCENE_HEIGHT, 1 , 1)));
            add(new Enemy(25.5, 3.5, new Sprite("enemy/16.png",SCENE_WIDTH, SCENE_HEIGHT, 0.8 , 0.8)));
            add(new Enemy(21.5, 7.5, new Sprite("enemy/3.png",SCENE_WIDTH, SCENE_HEIGHT, 0.8 , 0.8)));
            add(new Enemy(22.5, 6.9, new Sprite("enemy/4.png",SCENE_WIDTH, SCENE_HEIGHT, 0.8 , 0.8)));
            add(new Enemy(23.5, 7.5, new Sprite("enemy/5.png",SCENE_WIDTH, SCENE_HEIGHT, 0.8 , 0.8)));
            add(new Enemy(20.5, 12.5, new Sprite("enemy/6.png",SCENE_WIDTH, SCENE_HEIGHT, 0.8 , 0.8)));
            add(new Enemy(5.5, 10.5, new Sprite("enemy/7.png",SCENE_WIDTH, SCENE_HEIGHT, 0.8 , 0.8)));
            add(new Enemy(14.5, 19.5, new Sprite("enemy/8.png",SCENE_WIDTH, SCENE_HEIGHT, 0.8 , 0.8)));
            add(new Enemy(12.5, 10.5, new Sprite("enemy/9.png",SCENE_WIDTH, SCENE_HEIGHT, 0.8 , 0.8)));
        }};
        mainPlayer = new Camera(1.5, 2.5, 1, 0, SCENE_WIDTH, SCENE_HEIGHT, 60);
        image = new BufferedImage(SCENE_WIDTH, SCENE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        screen = new Screen(
                map.getSpace(),
                SCENE_WIDTH,
                SCENE_HEIGHT,
                new ArrayList<>(){{
                    add(new Sprite("textures/tile/16.png"));
                    add(new Sprite("textures/tile/12.png"));
                    add(new Sprite("textures/tile/13.png"));
                    add(new Sprite("textures/tile/3.png"));
                    add(new Sprite("textures/bricks/19.png"));
                    add(new Sprite("textures/bricks/20.png"));
                    add(new Sprite("textures/bricks/21.png"));
                    add(new Sprite("textures/wood/24.png"));
                    add(new Sprite("textures/wood/19.png"));
                    add(new Sprite("textures/tile/20.png"));
                    add(new Sprite("textures/tile/17.png"));
                }},
                mainPlayer
        );
        setSize(WIDTH, HEIGHT);
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB),
                new Point(0, 0),
                "blank")
        );
        //setSize(image.getWidth(), image.getHeight());
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
            put(KeyEvent.VK_ESCAPE, "ESC");
            put(KeyEvent.VK_RIGHT, "MV_RHT");
            put(KeyEvent.VK_UP, "MV_UP");
            put(KeyEvent.VK_DOWN, "MV_DOWN");
            put(KeyEvent.VK_SPACE, "SHOT");
            put(KeyEvent.VK_R, "RELOAD");
            put(KeyEvent.VK_M, "MAP");
        }},
                center
        );

        MouseController controllerMouseMove = new MouseController(new HashMap<Integer, ControlCommand>(){{
            put(MouseEvent.MOUSE_MOVED + 1, new RotateLeftCommand(mainPlayer));
            put(MouseEvent.MOUSE_MOVED + 2, new RotateRightCommand(mainPlayer));
        }},
                center
        );

        addKeyListener(controller);
        addMouseListener(controller);
        addMouseMotionListener(controllerMouseMove);

        this.sceneTemp = new LowIntensiveClock(
                25,
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

                            /*if (state.getKey().equals("MV_LFT2")) {
                                mainPlayer.moveLeft();
                            }*/

                            if (state.getKey().equals("MV_RHT")) {
                                mainPlayer.moveRight();
                            }

                            /*if (state.getKey().equals("MV_RHT2")) {
                                mainPlayer.moveRight();
                            }*/

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
                                Vector<Bullet> bullets = stick.shoot(
                                        mainPlayer.getPosition()
                                                .add(new Vector2d(0.25, 0.25)
                                                .multiply(mainPlayer.getDirection())),
                                        mainPlayer.getDirection()
                                );
                                this.bullets.addAll(bullets);
                                this.tempBullets.addAll(bullets);
                            }

                            if (state.getKey().equals("RELOAD")) {
                                this.stick.reload();
                            }

                            if (state.getKey().equals("ESC")) {
                                controllerMouseMove.swtch();
                            }

                            if (state.getKey().equals("MAP")) {
                                screen.switchShowMap();
                            }
                        }
                    }
                },
                () -> {
                    controllerMouseMove.execute();
                },
                () -> {
                    this.stick.update();

                    synchronized (bullets) {
                        for (Bullet bullet : bullets) {
                            if (!bullet.isExist()) this.bullets.remove(bullet);
                        }
                    }

                    synchronized (enemies) {
                        for (Enemy enemy : enemies) {
                            if (!enemy.isExist()) this.enemies.remove(enemy);
                            bullets.addAll(
                                    enemy.update(mainPlayer, map, new Vector<>())
                            );
                        }
                    }

                    BulletHitScanner.scan(this.bullets, this.enemies, sceneTemp.getFrequency(), map, this.mainPlayer);
                },
                () -> {
                    if (mainPlayer.health.get() <= 0) {
                        mainPlayer.health.reset();
                        mainPlayer.setPosition(1.5, 2.5);
                    }
                }
        );
        renderTemp = new LowIntensiveClock(
                60,
                () -> {
                    screen.rayCast(this.mainPlayer);
                },
                () -> screen.render(
                        pixels,
                        this.mainPlayer,
                        this.stick,
                        this.enemies,
                        this.bullets,
                        this.otherUnits.values()
                ),
                () -> {
                    String stats = "UPS: "
                            + sceneTemp.getFrequency()
                            + "\n FPS: "
                            + renderTemp.getFrequency();
                    if (clientTemp != null) {
                        stats += "\n NETPS: "
                                + clientTemp.getFrequency();
                    }

                    stats += "\n\n HEALTH: "
                            + mainPlayer.health.toString();

                    this.render(map, stats);
                }
        );

        if (this.client != null) {
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
    }

    /**
     * Init.
     */
    public void init() {
        requestFocus();
        this.stick = new BoomStick(
                new ArrayList<>(){{
                    add(new Sprite("equip/boomstick-1.png", 2, 2));
                    add(new Sprite("equip/boomstick-2.png", 2, 2));
                    add(new Sprite("equip/boomstick-3.png", 2, 2));
                    add(new Sprite("equip/boomstick-4.png", 2, 2));
                    add(new Sprite("equip/boomstick-5.png", 2, 2));
                    add(new Sprite("equip/boomstick-6.png", 2, 2));
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
        if (this.clientTemp != null) this.clientTemp.start();
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
