package net.thevpc.gaming.atom.examples.kombla.main.client.dal.rmi;

import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;
import net.thevpc.gaming.atom.model.Player;
import net.thevpc.gaming.atom.model.Sprite;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface clientOperationRMI extends Remote {

    //public void modelChanged(DynamicGameModel model) throws RemoteException;
    void modelChanged(long frame, List<Sprite> sprites, List<Player> players) throws RemoteException;

}
