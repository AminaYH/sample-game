package net.thevpc.gaming.atom.examples.kombla.main.shared.engine.collision;

import net.thevpc.gaming.atom.annotations.AtomSpriteCollisionTask;
import net.thevpc.gaming.atom.engine.collisiontasks.SpriteCollisionTask;

import java.io.Serializable;

/**
 * Created by vpc on 9/25/16.
 */
@AtomSpriteCollisionTask(
        sceneEngine = "mainLocal,mainServer",
        kind = "Bomb"
)
public class BombCollisionTask implements SpriteCollisionTask , Serializable {
}
