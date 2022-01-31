package com.htwsaar.server.RMI;
import com.htwsaar.server.hibernate.dao.UserDao;
import com.htwsaar.server.hibernate.entity.User;


import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;

public class Server_RMI  extends  UnicastRemoteObject implements ServerClient_Connect_Interface{

    protected Server_RMI() throws RemoteException {
        super();
    }

    public void main(String[] args) {
        try{
            int registry_Port = 42424; //Zum Testen auf 42424 gesetzt
            LocateRegistry.createRegistry(registry_Port);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("Server", new Server_RMI());
            System.out.println("Server ist bereit!/n");
        } catch(Exception e){
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public int sendLoginData(String name, String password) throws RemoteException {
        try{
            UserDao userDao = new UserDao();
            User user = userDao.getUser(name);
            if(user != null){
                if(password.equalsIgnoreCase(user.getPassword()))
                {
                    return 1;
                }
            }
            return 0;
        } catch(Exception e){
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<String> scoreboardRequest() throws RemoteException{
        try{
            UserDao userDao = new UserDao();
            User user;
            List<String> stringList = new ArrayList<String>();
            List<User> listUser = userDao.getScoreboard();
            for (int i = 0; i<listUser.size(); i++) {
                user = listUser.get(i);
                stringList.add(user.toString());
            }
            return stringList;
        } catch(Exception e){
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String scoreboardRequestForUser(String name) throws RemoteException{
        UserDao dao = new UserDao();
        User user = dao.getUser(name);
        if(user != null) {
            return user.toString();
        } else {
            return null;
        }
    }

    @Override
    public int createGame() throws  RemoteException{
        return 0;
    }

    @Override
    public int joinGame(int joinCode) throws RemoteException{
        return 0;
    }

    @Override
    public int setX(int x, int y) throws RemoteException{
        return 0;
    }

    @Override
    public int setO(int x, int y) throws RemoteException{
        return 0;
    }
}
