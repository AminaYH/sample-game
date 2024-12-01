package net.thevpc.gaming.helloworld;

import net.thevpc.gaming.atom.annotations.*;
import net.thevpc.gaming.atom.debug.AdjustViewController;
import net.thevpc.gaming.atom.engine.SceneEngine;
import net.thevpc.gaming.atom.engine.SpriteFilter;
import net.thevpc.gaming.atom.engine.maintasks.MoveSpriteMainTask;
import net.thevpc.gaming.atom.engine.maintasks.MoveToPointSpriteMainTask;
import net.thevpc.gaming.atom.model.ModelPoint;
import net.thevpc.gaming.atom.model.Orientation;
import net.thevpc.gaming.atom.model.Point;
import net.thevpc.gaming.atom.model.Sprite;
import net.thevpc.gaming.atom.presentation.*;
import net.thevpc.gaming.atom.presentation.components.SLabel;
import net.thevpc.gaming.atom.presentation.layers.ImageScreenLayer;
import net.thevpc.gaming.atom.presentation.layers.Layers;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by vpc on 9/23/16.
 */
@AtomScene(
        id = "hello",
        title = "Hello World",
        tileWidth = 80,
        tileHeight = 80
        
)
@AtomSceneEngine(
        id="hello",
        columns = 10,
        rows = 10,
        fps = 25
)
public class HelloWorldScene {

    @Inject
    private Scene scene;
    @Inject
    private SceneEngine sceneEngine;
    private BufferedImage backgroundImage;
    private SLabel lifeLabel;
    private ModelPoint targetPoint;
    @OnSceneStarted
    private void init() {
        lifeLabel = new SLabel("Vies : 3")
                .setLocation(Point.ratio(0.1f, 0.1f));
        String imagePath = "/back.jpg";
        scene.addLayer(new ImageScreenLayer(imagePath));
//        scene.addLayer(Layers.fillBoardGradient(
//                Color.BLACK,
//                Color.GREEN, Orientation.NORTH));
        scene.addLayer(Layers.debug());

        scene.addController(new SpriteController(SpriteFilter.byName("Ball1")).setArrowKeysLayout());

        scene.addController(new SpriteController(SpriteFilter.byName("Ball2")).setESDFLayout());
        scene.addController(new SceneController() {
            @Override
            public void mouseClicked(SceneMouseEvent e) {
                targetPoint = new ModelPoint(e.getX(), e.getY());
                sceneEngine.findSpriteByName("Ball2").setMainTask(new MoveToPointSpriteMainTask(targetPoint));
            }

            @Override
            public boolean acceptEvent(int event) {
                return (event & SceneController.MOUSE_CLICKED) != 0;
            }
        });

        scene.addController(new SceneController() {
            @Override
            public void keyPressed(SceneKeyEvent e) {

                switch (e.getKeyCode()){
                   case SPACE ->{
                       try {
                           Sprite ball = sceneEngine.findSpriteByName("Ball2"); // Adjust to your ball's name
                           if (ball != null) {
                               createMissileAtBall(ball);
                           }
                       } catch (InstantiationException ex) {
                           throw new RuntimeException(ex);
                       } catch (IllegalAccessException ex) {
                           throw new RuntimeException(ex);
                       }
                   }


                }
            }

            @Override
            public boolean acceptEvent(int event) {
                return (event & SceneController.KEY_PRESSED) != 0;
            }
        });

        //        scene.setSpriteView(SpriteFilter.byName("Ball2"), new DefaultSpriteView() {
//            @Override
//            public void draw(SpriteDrawingContext spriteDrawingContext) {
//                Graphics2D g = spriteDrawingContext.getGraphics();
//                g.setColor(Color.MAGENTA);
//                Shape shape = spriteDrawingContext.getSpriteShape();
//                g.fill(shape);
//            }
//        });

        scene.addController(new AdjustViewController());

        scene.addComponent(
                new SLabel("Click CTRL-D to switch debug mode, use Arrows to move Ball1, WASD for Ball2")
                        .setLocation(Point.ratio(0.5f, 0.5f))
        );

        scene.setSpriteView(SpriteFilter.byKind("Ball"), new ImageSpriteView("/ball.png", 8, 4));
        scene.setSpriteView(SpriteFilter.byKind("Egg"), new ImageSpriteView("/pngegg.png", 4, 3));
        sceneEngine.findSpriteByName("ball2") ;


    }
    public void createMissileAtBall(Sprite ball) throws InstantiationException, IllegalAccessException {
        // Get the current position and direction of the ball
        ModelPoint ballPosition = ball.getLocation();
        double ballDirection = ball.getDirection();

        double missileDistance = 2.5;

        double missileX = ballPosition.getX() + Math.cos(ballDirection) * missileDistance;
        double missileY = ballPosition.getY() + Math.sin(ballDirection) * missileDistance;

        Sprite missile = sceneEngine.createSprite("Egg");
        missile.setLocation(new ModelPoint(missileX, missileY));
        missile.setSpeed(ball.getSpeed() * 2);
        missile.setDirection(ballDirection);
        sceneEngine.addSprite(missile);
        missile.setMainTask(MoveSpriteMainTask.class.newInstance());
        Ball3DefaultSpriteCollisionManager collisionManager = new Ball3DefaultSpriteCollisionManager();
        missile.setCollisionTask(collisionManager);
    }

    @OnNextFrame
    private void onEachFrame() {
        Sprite ball1 = sceneEngine.findSpriteByName("Ball1");
        assert ball1 != null;
        int livesRemaining = ball1.getLife();
        lifeLabel.setText("Vies : " + livesRemaining);
        Sprite ball2 = sceneEngine.findSpriteByName("Ball2");


    }



}
