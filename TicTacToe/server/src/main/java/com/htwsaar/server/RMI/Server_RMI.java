package com.htwsaar.server.RMI;
import com.htwsaar.server.hibernate.dao.UserDao;
import com.htwsaar.server.hibernate.entity.User;


import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;

public class Server_RMI implements ServerClient_Connect_Interface{

    public Server_RMI(){

    }
    
    public void start_Server_RMI(){
        try {
            Server_RMI obj = new Server_RMI();
            ServerClient_Connect_Interface stub = (ServerClient_Connect_Interface) UnicastRemoteObject.exportObject(obj, 0);
            System.out.println(obj.toString());
            // Bind the remote object's stub in the registry
            LocateRegistry.createRegistry(42424);
            Registry registry = LocateRegistry.getRegistry(42424);
            registry.bind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
  
    
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

    
    public String scoreboardRequestForUser(String name) throws RemoteException{
        UserDao dao = new UserDao();
        User user = dao.getUser(name);
        if(user != null) {
            return user.toString();
        } else {
            return null;
        }
    }

    
    public int createGame() throws  RemoteException{
        return 0;
    }

    
    public int joinGame(int joinCode) throws RemoteException{
        return 0;
    }

    
    public int setX(int x, int y) throws RemoteException{
        return 0;
    }

    
    public int setO(int x, int y) throws RemoteException{
        return 0;
    }
}
