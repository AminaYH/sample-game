/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.gaming.atom.examples.kombla.main.server.dal.rmi;

import net.thevpc.gaming.atom.engine.SceneEngine;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.MainClientDAO;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.MainClientDAOListener;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.rmi.clientOperationRMI;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.rmi.clientOperationRMIImpl;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.MainServerDAO;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.MainServerDAOListener;
import net.thevpc.gaming.atom.examples.kombla.main.server.engine.MainServerEngine;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;
import net.thevpc.gaming.atom.model.Player;
import net.thevpc.gaming.atom.model.Sprite;


import java.io.IOException;
import java.rmi.MarshalledObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 */
public class DAOServerRMI implements MainServerDAO {

    private MainServerDAOListener listener;
    private MainClientDAOListener listenerClient;
    private ServerOperationsRMIImpl server;
    private AppConfig properties;
    private  StartGameInfo data;
    private String adress;
    private String playerName;
    private Registry registry;
    private clientOperationRMI clientOperation;
    private DynamicGameModel gameModel;
    private SceneEngine sceneEngine;
    private static final int GAME_LOOP_DELAY = 100;
    public DAOServerRMI(SceneEngine sceneEngine) throws RemoteException {

        this.sceneEngine = sceneEngine;

    }
    @Override
    public void start(MainServerDAOListener listener, AppConfig properties) {
        try {
            if (listener == null) {
                throw new IllegalStateException("Listener cannot be null");
            }
            clientOperationRMI clientOp = new clientOperationRMIImpl(listenerClient);

            this.listener = listener;
            this.properties=properties;
            this.adress="localhost";
            this.playerName="Boss1";
            properties.setPlayerName(playerName);
            this.server = new ServerOperationsRMIImpl(listener);
            this.data = this.listener.onReceivePlayerJoined(playerName);
            registry = LocateRegistry.createRegistry(1234);
            registry.bind("SoPongRMI", server);
            registry.rebind("StartGameInfo", data);
            new Thread(this::gameLoop).start();

        } catch (Exception ex) {
            throw new RuntimeException("Impossible de lancer le serveur");
        }
    }
    private void gameLoop() {
        while (true) {
            try {
                updateGameModel();
                sendModelChanged(gameModel);
                Thread.sleep(GAME_LOOP_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break; // Exit loop if interrupted
            }
        }
    }

    @Override
    public void sendModelChanged(DynamicGameModel dynamicGameModel)  {
        clientOperationRMI client = server.getClient();
        System.out.println("Client from server: " + client);

        if (client != null ) {
            try {
                if (dynamicGameModel != null) {

                    client.modelChanged(dynamicGameModel.getFrame(),dynamicGameModel.getSprites(),dynamicGameModel.getPlayers());
                    System.out.println("Model changes sent to client.");
                } else {
                    System.out.println("DynamicGameModel is null. Nothing to send.");
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();

            }
        } else {
            System.out.println("No client connected to send model changes.");
        }
    }


    public void updateGameModel() {
        List<Sprite> sprites = sceneEngine.getSprites();
        List<Player> players = sceneEngine.getPlayers();

        Long frames = sceneEngine.getFrame();

        gameModel = new DynamicGameModel(frames ,sprites, players );

        System.out.println("Game model updated with " + sprites.size() + " sprites and " + players.size() + " players.");
    }


}
