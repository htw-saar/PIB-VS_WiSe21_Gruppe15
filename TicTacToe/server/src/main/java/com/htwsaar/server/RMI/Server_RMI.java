package com.htwsaar.server.RMI;
import com.htwsaar.server.Game.TicTacToe;
import com.htwsaar.server.Hibernate.dao.UserDao;
import com.htwsaar.server.Hibernate.entity.User;


import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server_RMI implements ServerClient_Connect_Interface {

    private ArrayList<TicTacToe> games = new ArrayList<>();
    private ArrayList<TicTacToe> waitingGames = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(Server_RMI.class);

    public Server_RMI() {

    }

    public Boolean checkGameStart(String username) {
        UserDao userDao = new UserDao();
        User user = userDao.getUser(username);
        for (TicTacToe game: waitingGames) {
            if (user.getUserId() == game.getJoinCode()) {
                return false;
            }
        }
        return true;
    }

    public void start_Server_RMI() {
        try {
            Server_RMI obj = new Server_RMI();
            ServerClient_Connect_Interface stub = (ServerClient_Connect_Interface) UnicastRemoteObject.exportObject(obj, 0);
            System.out.println(obj.toString());
            // Bind the remote object's stub in the registry
            LocateRegistry.createRegistry(42424);
            Registry registry = LocateRegistry.getRegistry(42424);
            registry.rebind("Hello", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            logger.error("Server exception: " + e.toString());
        }
    }


    public Boolean sendLoginData(String name, String password) {
        try {
            UserDao userDao = new UserDao();
            User user = userDao.getUser(name);
            if (user != null) {
                if (password.equalsIgnoreCase(user.getPassword())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error("Server exception: " + e.toString());
            return false;
        }
    }

    public List<String> scoreboardRequest() {
        try {
            String format = " %2s %12s %2s %6s %2s %6s %2s %6s %2s";
            UserDao userDao = new UserDao();
            List<String> stringList = new ArrayList<>();
            for (User user : userDao.getScoreboard()) {
                String print = String.format(format, "|", user.getUsername(), "|", user.getWins(), "|", user.getLoses(), "|", user.getScore(), "|");
                stringList.add(print);
            }
            return stringList;
        } catch (Exception e) {
            logger.error("Server exception: " + e.toString());
            return null;
        }
    }


    public String scoreboardRequestForUser(String name) {
        try {
            UserDao dao = new UserDao();
            User user = dao.getUser(name);
            if (user != null) {
                return user.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Server exception: " + e.toString());
            return null;
        }
    }


    public Boolean createGame(String username) {
        try {
            TicTacToe game;
            game = new TicTacToe(username);
            waitingGames.add(game);
            return true;
        } catch (Exception e) {
            logger.error("Server exception: " + e.toString());
            return false;
        }
    }

    public String[] returnGameboard(int userId){
        String[] gameboard;
        gameboard = waitingGames.get(userId).outputGameboard();
        return gameboard;
    }

    public Boolean joinGame(String username, int joinCode) {
        try {
            for (int i = 0; i < waitingGames.size(); i++) {
                if (waitingGames.get(i).compareJoinCode(joinCode) == 1) {
                    waitingGames.get(i).setO(username);
                    games.add(waitingGames.get(i));
                    waitingGames.remove(i);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error("Server exception: " + e.toString());
            return false;
        }
    }


    public TicTacToe.Winner setField(String username, int pos) {
        try {
            int gameNumber;
            TicTacToe.Winner player;
            gameNumber = playerInWhichGame(username);
            if (gameNumber != -1) {
                if(games.get(gameNumber).whichPlayer(username) == 1) {
                    player = TicTacToe.Winner.Player1;
                }
                else{
                    player = TicTacToe.Winner.Player2;
                }
                TicTacToe.Winner playState = games.get(gameNumber).setField(player, pos);
                return playState;
            }
            return TicTacToe.Winner.NONE;
        } catch (Exception e) {
            logger.error("Server exception: " + e.toString());
            return TicTacToe.Winner.NONE;
        }
    }

    private int playerInWhichGame(String username) {
        try {
            String[] playerNames;
            for (int i = 0; i < games.size(); i++) {
                playerNames = games.get(i).getPlayers();
                if (playerNames.equals(username) || playerNames.equals(username)) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            logger.error("Server exception: " + e.toString());
            return -1;
        }
    }
}

