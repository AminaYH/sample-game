package net.thevpc.gaming.helloworld;

import net.thevpc.gaming.atom.annotations.AtomSceneController;
import net.thevpc.gaming.atom.engine.GameEngine;
import net.thevpc.gaming.atom.presentation.Scene;
import net.thevpc.gaming.atom.presentation.SceneController;
import net.thevpc.gaming.atom.presentation.SceneKeyEvent;

@AtomSceneController(
        name = "Welcome",
        scene = "Welcome"
)
public class WelcomeContoller implements SceneController {
    public void welcomeController(){

    }
    @Override
    public void keyPressed(SceneKeyEvent e){
        Scene scene =e.getScene();
        switch (e.getKeyCode()){
            case SPACE -> {
                GameEngine gameEngine =scene.getSceneEngine().getGameEngine();
                gameEngine.setActiveSceneEngine("hello");
            }
        }
    }
}

