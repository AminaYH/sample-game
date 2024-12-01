/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.gaming.atom.examples.kombla.main.server.dal.rmi;

import net.thevpc.gaming.atom.examples.kombla.main.client.dal.MainClientDAOListener;
import net.thevpc.gaming.atom.examples.kombla.main.client.dal.rmi.clientOperationRMI;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.MainServerDAOListener;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.model.Player;
import net.thevpc.gaming.atom.model.Sprite;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 */
public class ServerOperationsRMIImpl extends UnicastRemoteObject implements ServerOperationsRMI, Serializable {

    private MainServerDAOListener listener;
    private clientOperationRMI client;
    private ServerOperationsRMI server;
    private DynamicGameModel dynamicGameModel;

    public ServerOperationsRMIImpl(MainServerDAOListener listener) throws RemoteException {
        super();
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null");
        }
        this.listener = listener;
    }

    public clientOperationRMI getClient() {
            return this.client;
    }

    @Override
    public void setClient(clientOperationRMI client) throws RemoteException {
        this.client=client;
        listener.onReceivePlayerJoined("Boss1");
    }

    @Override
    public void keyLeft() throws RemoteException {
       listener.onReceiveMoveLeft(2);
    }

    @Override
    public void keyRight() throws RemoteException {
        listener.onReceiveMoveRight(2);
    }

    @Override
    public void keySpace() throws RemoteException {
   listener.onReceiveReleaseBomb(2);
    }

    @Override
    public void keyUp() throws RemoteException {
      listener.onReceiveMoveUp(2);
    }

    @Override
    public void keyDown() throws RemoteException {
    listener.onReceiveMoveDown(2);
    }


}
