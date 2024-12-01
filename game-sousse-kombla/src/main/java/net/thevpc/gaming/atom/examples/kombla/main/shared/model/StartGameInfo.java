package net.thevpc.gaming.atom.examples.kombla.main.shared.model;

import java.io.Serializable;
import java.rmi.Remote;

/**
 * Created by vpc on 10/7/16.
 */
public class StartGameInfo implements Serializable, Remote {
    private int playerId;
    private int[][] maze;

    public StartGameInfo(int playerId, int[][] maze) {
        this.playerId = playerId;
        this.maze = maze;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int[][] getMaze() {
        return maze;
    }
}
