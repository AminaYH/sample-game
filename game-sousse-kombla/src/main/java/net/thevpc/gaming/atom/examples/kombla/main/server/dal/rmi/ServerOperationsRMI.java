/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.gaming.atom.examples.kombla.main.server.dal.rmi;

import net.thevpc.gaming.atom.examples.kombla.main.client.dal.rmi.clientOperationRMI;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.model.Player;
import net.thevpc.gaming.atom.model.Sprite;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author Taha Ben Salah (taha.bensalah@gmail.com)
 */
public interface ServerOperationsRMI extends Remote, Serializable {

    public void setClient(clientOperationRMI client) throws RemoteException;
    public void keyLeft() throws RemoteException;

    public void keyRight() throws RemoteException;

    public void keySpace() throws RemoteException;
    public void  keyUp() throws RemoteException;
    public void keyDown() throws RemoteException;

//    public void sendModelChanged(DynamicGameModel model) throws RemoteException;



}
