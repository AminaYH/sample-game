package net.thevpc.gaming.helloworld;

import net.thevpc.gaming.atom.engine.SceneEngine;
import net.thevpc.gaming.atom.engine.collisiontasks.BorderCollision;
import net.thevpc.gaming.atom.engine.collisiontasks.DefaultSpriteCollisionTask;
import net.thevpc.gaming.atom.engine.collisiontasks.SpriteCollision;
import net.thevpc.gaming.atom.model.*;

public class Ball2DefaultSpriteCollisionManager extends DefaultSpriteCollisionTask {
    @Override
    public void collideWithBorder(BorderCollision borderCollision) {
        CollisionSides collisionSides = borderCollision.getBorderCollisionSides();
        Sprite ball = borderCollision.getSprite();
        ModelPoint lastValidPosition = borderCollision.getLastValidPosition();


        if (collisionSides.isNorth()) {
            System.out.println(collisionSides);
            ball.setDirection(0- ball.getDirection());
            ball.setLocation(new ModelPoint(lastValidPosition.getX(), lastValidPosition.getY() + 1)); // Slight adjustment downward
        } else if (collisionSides.isSouth()) {
            System.out.println(collisionSides);
            ball.setDirection(0-ball.getDirection());
            ball.setLocation(new ModelPoint(lastValidPosition.getX(), lastValidPosition.getY() - 1)); // Slight adjustment upward
        } else if (collisionSides.isEast()) {
            System.out.println(collisionSides);
            ball.setDirection(360 - ball.getDirection());
            ball.setLocation(new ModelPoint(lastValidPosition.getX() - 1, lastValidPosition.getY())); // Slight adjustment to the left
        } else if (collisionSides.isWest()) {
            System.out.println(collisionSides);
            ball.setDirection(360 - ball.getDirection());
            ball.setLocation(new ModelPoint(lastValidPosition.getX() + 1, lastValidPosition.getY())); // Slight adjustment to the right
        }
        else  {
            ball.setDirection(0 - ball.getDirection());
            ball.setLocation(new ModelPoint(lastValidPosition.getX() + 1, lastValidPosition.getY()));
        }
        if (collisionSides.isNorth() && collisionSides.isWest()) {
            ball.setDirection(135);
        } else if (collisionSides.isSouth() && collisionSides.isEast()) {
            ball.setDirection(315);
        }


        borderCollision.adjustSpritePosition();
    }

    @Override
    public void collideWithSprite(SpriteCollision spriteCollision) {
//        Sprite sprite = spriteCollision.getSprite();
//        Sprite otherSprite =  spriteCollision.getOther();

//        ModelPoint lastValidPosition= spriteCollision.getLastValidPosition();
//        ModelPoint otherValidPosition = spriteCollision.getOther().getPreviousLocation();
//        sprite.setDirection(0 - sprite.getDirection());
//        sprite.setLocation( new ModelPoint(lastValidPosition.getX(), lastValidPosition.getY()));
//
//        otherSprite.setDirection(0 - otherSprite.getDirection());
//        otherSprite.setLocation(new ModelPoint(otherValidPosition.getX(),otherValidPosition.getY()));

//        spriteCollision.adjustSpritePosition();
//        spriteCollision.getSprite().setDirection(DirectionTransform.BACKWARD);
        Sprite sprite = spriteCollision.getSprite();
        CollisionSides os = spriteCollision.getOtherCollisionSides();
        boolean north = os.isNorth();
        boolean south = os.isSouth();
        boolean east = os.isEast();
        boolean west = os.isWest();
//        System.out.println("collideWithSprite "+(north ? "N" : "-") + ";" + (east ? "E" : "-") + ";" + (south ? "S" : "-") + ";" + (west ? "W" : "-") + " :: " + velocity + " " + lastValidPosition + " -> " + sprite.getLocation());
        if ((north && west) || (north && east) || (south && west) || (south && east)) {
            sprite.setDirection(DirectionTransform.BACKWARD);
        } else if (north || south) {
            sprite.setDirection(DirectionTransform.HORIZONTAL_MIRROR);
        } else if (east || west) {
            sprite.setDirection(DirectionTransform.VERTICAL_MIRROR);
        }
        spriteCollision.adjustSpritePosition();
    }


}
