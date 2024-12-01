package net.thevpc.gaming.atom.examples.kombla.main.client.dal.rmi;

import net.thevpc.gaming.atom.examples.kombla.main.client.dal.MainClientDAO;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.MainClientDAOListener;
import net.thevpc.gaming.atom.examples.kombla.main.client.engine.MainClientEngine;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.MainServerDAOListener;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.rmi.ServerOperationsRMI;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.rmi.ServerOperationsRMIImpl;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;
import net.thevpc.gaming.atom.model.Player;
import net.thevpc.gaming.atom.model.Sprite;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class DAOClientRMI  implements MainClientDAO {
    private MainClientDAOListener listener;
    private  ServerOperationsRMI server;
    private AppConfig appConfig;
    private MainServerDAOListener Slistener;
    private String adress;
    private String playerName;
    private int port;
    private StartGameInfo startGameInfo;
    private MainClientEngine engine;
    private clientOperationRMIImpl clientOperation;
    public DAOClientRMI(MainClientEngine engine) throws RemoteException {
        this.engine=engine;
    }

    @Override
    public void start(MainClientDAOListener listener, AppConfig properties) {
        try {
            this.listener = listener;
            this.appConfig=properties;
            this.adress=appConfig.getServerAddress();
            this.port= appConfig.getServerPort();
            this.playerName="Boss2";
            appConfig.setPlayerName("Boss2");
            System.out.println("playername\n" + playerName);
             clientOperation = new clientOperationRMIImpl(listener);

            Registry registry = LocateRegistry.getRegistry("localhost", 1234);
            server = (ServerOperationsRMI) registry.lookup("SoPongRMI");
            startGameInfo= (StartGameInfo) registry.lookup("StartGameInfo");

        } catch (Exception ex) {
            ex.printStackTrace();
            //ignore error
        }
    }


    @Override
    public StartGameInfo connect() throws RemoteException {
        if (server != null) {
            System.out.println("Registering client...");
            server.setClient(clientOperation);
            System.out.println(""+clientOperation);
        }

        StartGameInfo result = new StartGameInfo(startGameInfo.getPlayerId(), startGameInfo.getMaze());

        System.out.println("Client connected successfully. Returning game info.");
        return result;
    }

    @Override
    public void sendMoveLeft() throws IOException {
            server.keyLeft();
            }

    @Override
    public void sendMoveRight() throws IOException {
            server.keyRight();
    }

    @Override
    public void sendMoveUp() throws IOException {
        server.keyUp();
    }

    @Override
    public void sendMoveDown() throws IOException {
      server.keyDown();
    }

    @Override
    public void sendFire() throws IOException {
      server.keySpace();

    }
}
