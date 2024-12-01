package net.thevpc.gaming.atom.examples.kombla.main.client.dal.rmi;

import net.thevpc.gaming.atom.examples.kombla.main.client.dal.MainClientDAOListener;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.rmi.ServerOperationsRMI;
import net.thevpc.gaming.atom.examples.kombla.main.server.dal.rmi.ServerOperationsRMIImpl;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;
import net.thevpc.gaming.atom.model.Player;
import net.thevpc.gaming.atom.model.Sprite;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class clientOperationRMIImpl extends UnicastRemoteObject implements clientOperationRMI {
    private MainClientDAOListener listener;
    private ServerOperationsRMIImpl serverOperationsRMI;

    public clientOperationRMIImpl(MainClientDAOListener listener) throws RemoteException {
        this.listener = listener;
    }

    @Override
    public void modelChanged(long frame, List<Sprite> sprites, List<Player> players) throws RemoteException {
        if (sprites != null) {
           listener.onModelChanged(new DynamicGameModel(frame,sprites,players));
           System.out.println("wiww");
        } else {
            System.err.println("Received null StartGameInfo from server.");
        }}
    }