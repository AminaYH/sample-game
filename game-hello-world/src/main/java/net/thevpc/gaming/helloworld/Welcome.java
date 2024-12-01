package net.thevpc.gaming.helloworld;



import net.thevpc.gaming.atom.annotations.AtomScene;
import net.thevpc.gaming.atom.annotations.AtomSceneEngine;
import net.thevpc.gaming.atom.model.Orientation;
import net.thevpc.gaming.atom.presentation.Alignment;
import net.thevpc.gaming.atom.presentation.components.*;
import net.thevpc.gaming.atom.presentation.layers.FillScreenGradientLayer;

import java.awt.*;
import net.thevpc.gaming.atom.annotations.AtomScene;
import net.thevpc.gaming.atom.annotations.OnInit;
import net.thevpc.gaming.atom.model.Orientation;
import net.thevpc.gaming.atom.model.ViewBox;
import net.thevpc.gaming.atom.model.ViewPoint;
import net.thevpc.gaming.atom.presentation.DefaultScene;
import net.thevpc.gaming.atom.presentation.layers.FillScreenGradientLayer;
import net.thevpc.gaming.atom.presentation.layers.Layer;

@AtomScene(
        id = "Welcome",
        title = "Welcome",
        tileWidth = 600,
        sceneEngine = "Welcome",
        tileHeight = 400
)

public class Welcome extends DefaultScene {
    private SButton start = new SButton("Press any key to start...");
    private SImage image;
    public Welcome() {
        super(600, 400);
        addLayer(new FillScreenGradientLayer(new Color(3, 81, 126), Color.DARK_GRAY, Orientation.NORTH));


        ViewBox vp = getCamera().getViewPort();
        ViewBox r2 = new ViewBox(vp.getX(), vp.getY() + vp.getHeight() * 2 / 3, vp.getWidth(), vp.getHeight() / 3);
        start.setBounds(r2);
        start.setTextStyle(SceneComponentState.DEFAULT, new TextStyle("start")
                .setAlignment(Alignment.CENTER)
                .setForeColor(Color.WHITE)
                .setBlinkPeriod(10)
        );

        image = new SImage("1","/back.jpg",getClass());
        image.setBounds(new ViewBox(0, 0, getCamera().getViewBounds().getWidth(), 200));
        image.setLocation(new ViewPoint(vp.getX(), vp.getY()));
        image.setAlignment(Alignment.CENTER);
        addComponent(start, null, Layer.BACKGROUND_LAYER);
        addComponent(image, null, Layer.BACKGROUND_LAYER);
    }


}