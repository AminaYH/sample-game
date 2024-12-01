package net.thevpc.gaming.atom.examples.kombla.main.client.dal;

import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;

import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Created by vpc on 10/7/16.
 */
public interface MainClientDAO {
    /**
     * !!non blocking!! start method.
     * called once by Engine when game starts
     * @param listener dal listener
     * @param properties config properties
     */
    public void start(MainClientDAOListener listener, AppConfig properties) throws IOException;


    public StartGameInfo connect() throws RemoteException;

    public void sendMoveLeft() throws IOException;

    public void sendMoveRight() throws IOException;

    public void sendMoveUp() throws IOException;

    public void sendMoveDown() throws IOException;

    public void sendFire() throws IOException;
}
