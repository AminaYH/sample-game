package net.thevpc.gaming.atom.examples.kombla.main.shared.model;

import net.thevpc.gaming.atom.model.CollisionSides;
import net.thevpc.gaming.atom.model.Player;
import net.thevpc.gaming.atom.model.Sprite;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vpc on 10/7/16.
 */
public class DynamicGameModel implements Serializable {
    long frame; List<Sprite> sprites; java.util.List<Player> players;
    private static final long serialVersionUID = 1L;

    public DynamicGameModel() {

    }
    public DynamicGameModel(long frame, List<Sprite> sprites, List<Player> players) {
        this.frame = frame;
        this.sprites = sprites;
        this.players = players;
    }

    public DynamicGameModel setFrame(long frame) {
        this.frame = frame;
        return this;
    }

    public DynamicGameModel setSprites(List<Sprite> sprites) {
        this.sprites = sprites;
        return this;
    }

    public DynamicGameModel setPlayers(List<Player> players) {
        this.players = players;
        return this;
    }

    public long getFrame() {
        return  frame;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
