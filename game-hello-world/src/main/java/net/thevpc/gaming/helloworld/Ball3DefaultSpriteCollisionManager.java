package net.thevpc.gaming.helloworld;

import net.thevpc.gaming.atom.engine.collisiontasks.BorderCollision;
import net.thevpc.gaming.atom.engine.collisiontasks.DefaultSpriteCollisionTask;
import net.thevpc.gaming.atom.engine.collisiontasks.SpriteCollision;
import net.thevpc.gaming.atom.model.Sprite;

public class Ball3DefaultSpriteCollisionManager extends DefaultSpriteCollisionTask {
    @Override
    public void collideWithBorder(BorderCollision borderCollision) {
        borderCollision.getSprite().die();
    }

    @Override
    public void collideWithSprite(SpriteCollision spriteCollision) {
            spriteCollision.getSprite().die();

    }
}
