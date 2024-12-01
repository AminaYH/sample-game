package net.thevpc.gaming.atom.examples.kombla.main.server.dal;

import net.thevpc.gaming.atom.examples.kombla.main.shared.dal.ProtocolConstants;
import net.thevpc.gaming.atom.examples.kombla.main.shared.engine.AppConfig;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.DynamicGameModel;
import net.thevpc.gaming.atom.examples.kombla.main.shared.model.StartGameInfo;
import net.thevpc.gaming.atom.model.ModelPoint;
import net.thevpc.gaming.atom.model.Player;
import net.thevpc.gaming.atom.model.Sprite;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketMainServerDAO implements MainServerDAO{
    private ServerSocket serverSocket;
    private MainServerDAOListener listener;
    private final Map<Integer, ClientSession> players = new HashMap<>();

    @Override
    public void start(MainServerDAOListener listener, AppConfig properties) {
        this.listener = listener;
        int port = properties.getServerPort();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("port " + port);

            new Thread(() -> {
                while (true) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        ClientSession session = new ClientSession(-1, clientSocket);
                        new Thread(() -> processClient(session)).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            throw new RuntimeException("Failed to start server", e);
        }
    }

    public void sendModelChanged(DynamicGameModel dynamicGameModel) {
        List<Player> players = dynamicGameModel.getPlayers();
        if (players == null) {
            players = new ArrayList<>();
            dynamicGameModel.setPlayers(players);
        }

        List<Sprite> sprites = dynamicGameModel.getSprites();
        if (sprites == null) {
            sprites = new ArrayList<>();
            dynamicGameModel.setSprites(sprites);
        }

        for (ClientSession session : this.players.values()) {
            try {
                session.out.writeLong(dynamicGameModel.getFrame());

                session.out.writeInt(players.size());
                for (Player player : players) {
                    writePlayer(session.out, player);
                }

                session.out.writeInt(sprites.size());
                for (Sprite sprite : sprites) {
                    writeSprite(session.out, sprite);
                }
                session.out.flush();

            } catch (IOException e) {
                e.printStackTrace();
                session.close();
                this.players.remove(session.playerId);
            }
        }
    }

    private void processClient(ClientSession sessionplayer) {
        try {
            while (true) {
                int command = sessionplayer.in.readInt();
                switch (command) {
                    case ProtocolConstants.CONNECT:
                        String playerName = sessionplayer.in.readUTF();
                        StartGameInfo startGameInfo = listener.onReceivePlayerJoined(playerName);
                        sessionplayer.playerId = startGameInfo.getPlayerId();
                        players.put(sessionplayer.playerId, sessionplayer);
                        writeStartGameInfo(sessionplayer, startGameInfo);
                        break;

                    case ProtocolConstants.LEFT:
                        listener.onReceiveMoveLeft(sessionplayer.playerId);
                        break;

                    case ProtocolConstants.RIGHT:
                        listener.onReceiveMoveRight(sessionplayer.playerId);
                        break;

                    case ProtocolConstants.UP:
                        listener.onReceiveMoveUp(sessionplayer.playerId);
                        break;

                    case ProtocolConstants.DOWN:
                        listener.onReceiveMoveDown(sessionplayer.playerId);
                        break;

                    case ProtocolConstants.FIRE:
                        listener.onReceiveReleaseBomb(sessionplayer.playerId);
                        break;

                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            players.remove(sessionplayer.playerId);
            sessionplayer.close();
        }
    }
    private void writeStartGameInfo(ClientSession session, StartGameInfo startGameInfo) {
        try {
            session.out.writeInt(ProtocolConstants.CONNECT);
            session.out.writeInt(startGameInfo.getPlayerId());
            int[][] maze = startGameInfo.getMaze();
            session.out.writeInt(maze.length);
            session.out.writeInt(maze[0].length);
            for (int[] row : maze) {
                for (int cell : row) {
                    session.out.writeInt(cell);
                }
            }
            session.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writePlayer(DataOutputStream out, Player player) throws IOException {
        out.writeInt(player.getId());
        out.writeUTF(player.getName());
        Map<String, Object> properties = player.getProperties();
        out.writeInt(properties.size());
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            out.writeUTF(entry.getKey());
            out.writeUTF(entry.getValue().toString());
        }
    }

    private void writeSprite(DataOutputStream out, Sprite sprite) throws IOException {
        out.writeInt(sprite.getId());
        out.writeUTF(sprite.getKind());
        out.writeUTF(sprite.getName());

        ModelPoint location = sprite.getLocation();
        out.writeDouble(location.getX());
        out.writeDouble(location.getY());
        out.writeDouble(location.getZ());

        out.writeDouble(sprite.getDirection());
        out.writeInt(sprite.getPlayerId());
        Map<String, Object> properties = sprite.getProperties();
        out.writeInt(properties.size());
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            out.writeUTF(entry.getKey());
            out.writeUTF(entry.getValue().toString());
        }
    }
    private class ClientSession {
        int playerId;
        Socket socket;
        DataInputStream in;
        DataOutputStream out;

        public ClientSession(int playerId, Socket socket) throws IOException {
            this.playerId = playerId;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        }

        public void close() {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
