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

public class Server_RMI implements ServerClient_Connect_Interface{

    private ArrayList<TicTacToe> games = new ArrayList<>();

    public Server_RMI(){

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
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }


    public Boolean sendLoginData(String name, String password) throws RemoteException {
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
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    public List<String> scoreboardRequest() throws RemoteException {
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
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }


    public String scoreboardRequestForUser(String name) throws RemoteException {
        try {
            UserDao dao = new UserDao();
            User user = dao.getUser(name);
            if (user != null) {
                return user.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }


    public Boolean createGame(String username) throws RemoteException {
        try {
            TicTacToe game;
            game = new TicTacToe();
            game.setX(username);
            games.add(game);
            return true;
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }


    public Boolean joinGame(String username, int joinCode) throws RemoteException {
        try {
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i).compareJoinCode(joinCode) == 1) {
                    games.get(i).setO(username);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }


    public Boolean setField(String username, int pos) throws RemoteException {
        try {
            int gameNumber;
            gameNumber = playerInWhichGameAsX(username);
            if (gameNumber != -1) {
                TicTacToe.Winner player = TicTacToe.Winner.Player1;
                games.get(gameNumber).setField(player, pos);
                return true;
            } else {
                gameNumber = playerInWhichGameAsO(username);
                if (gameNumber != -1) {
                    TicTacToe.Winner player = TicTacToe.Winner.Player2;
                    games.get(gameNumber).setField(player, pos);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    private int playerInWhichGameAsX(String username) {
        try {
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i).comparePlayerX(username) == 1) {
                    return i;
                }
            }
            return -1;
        } catch (
                Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            return -1;
        }
    }

    private int playerInWhichGameAsO(String username) {
        try {
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i).comparePlayerO(username) == 1) {
                    return i;
                }
            }
            return -1;
        } catch (
                Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            return -1;
        }
    }
}

