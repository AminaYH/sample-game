package net.thevpc.gaming.atom.examples.kombla.main.shared.model;

import net.thevpc.gaming.atom.model.Sprite;

import java.io.Serializable;

public class SerializableSprite implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private double x, y;

    public SerializableSprite(Sprite sprite) {
        this.id = sprite.getId();
        this.name = sprite.getName();
        this.x = sprite.getX();
        this.y = sprite.getY();
    }

    // Getters and setters...
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
