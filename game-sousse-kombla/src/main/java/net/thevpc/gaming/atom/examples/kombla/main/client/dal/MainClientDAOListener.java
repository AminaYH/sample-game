package net.thevpc.gaming.atom.examples.kombla.main.client.dal;

//import jdk.javadoc.internal.tool.Start;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;

/**
 * Created by vpc on 10/7/16.
 */
public interface MainClientDAOListener {
    /**
     * called by DAO to inform engine of an incoming data
     * @param model
     */
    public void onModelChanged(DynamicGameModel model);
}
