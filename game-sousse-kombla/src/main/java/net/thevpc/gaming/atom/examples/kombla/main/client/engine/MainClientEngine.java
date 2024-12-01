/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.gaming.atom.examples.kombla.main.client.engine;

import net.thevpc.gaming.atom.examples.kombla.main.client.dal.MainClientDAO;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.MainClientDAOListener;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.SocketMainClientDAO;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.rmi.DAOClientRMI;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.rmi.DAOServerRMI;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AbstractMainEngine;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;
import net.thevpc.gaming.atom.annotations.AtomSceneEngine;
import net.thevpc.gaming.atom.model.*;

import java.io.IOException;
import java.rmi.RemoteException;


/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 */
@AtomSceneEngine(id = "mainClient", columns = 12, rows = 12)
public class MainClientEngine extends AbstractMainEngine {
    //private DAOClientRMI dao;
    //new TCPMainClientDAO();
    private DAOClientRMI dao;
    public MainClientEngine() {
    }

    @Override
    protected void sceneActivating() {
        //put here your MainClientDAO instance
//        dao = new UDPMainClientDAO();
//        dao=new SocketMainClientDAO();
        try {
            dao= new DAOClientRMI(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
            dao.start(new MainClientDAOListener() {
                @Override
                public void onModelChanged(final DynamicGameModel model) {
                    invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            resetSprites();
                            resetPlayers();
                            getModel().setFrame(model.getFrame());
                            for (Player player : model.getPlayers()) {
                                Player p = createPlayer().copyFrom(player);
                                addPlayer(p);
                            }
                            for (Sprite sprite : model.getSprites()) {
                                Sprite s = createSprite(sprite.getKind()).copyFrom(sprite);
                                if("Person".equals(sprite.getKind()) || "Bomb".equals(sprite.getKind())){
                                    s.setSize(new ModelDimension(0.5,0.5));
                                }
                                addSprite(s);
                            }
                            MainClientEngine.this.getModel().setProperty("modelChanged",System.currentTimeMillis());
                        }
                    });
                }
            }, getAppConfig(getGameEngine()));

        //call server to connect

        StartGameInfo startGameInfo = null;
        try {
            startGameInfo = dao.connect();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        //configure model's maze with data retrieved.
        setModel(new DefaultSceneEngineModel(startGameInfo.getMaze()));
        //create new player
        setCurrentPlayerId(startGameInfo.getPlayerId());

    }

    public void releaseBomb() throws IOException {
        dao.sendFire();
    }

    public void move(Orientation direction) throws IOException {
        switch (direction){
            case EAST:{
                dao.sendMoveRight();
                break;
            }
            case WEST:{
                dao.sendMoveLeft();
                break;
            }
            case NORTH:{
                dao.sendMoveUp();
                break;
            }
            case SOUTH:{
                dao.sendMoveDown();
                break;
            }
        }
    }
}
//private SocketMainClientDAO dao;


