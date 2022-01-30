package com.htwsaar.server.RMI;
import com.htwsaar.server.hibernate.dao.UserDao;
import com.htwsaar.server.hibernate.entity.User;


import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server_RMI implements ServerClient_Connect_Interface{
    public Server_RMI(){
    }
    public void main(String[] args) {
        try{
            int registry_Port = 42424; //Zum Testen auf 42424 gesetzt
            int stub_Port = 0;         //Zum Testen auf 0 gesetzt
            Server_RMI server = new Server_RMI();
            ServerClient_Connect_Interface server_Stub = (ServerClient_Connect_Interface) UnicastRemoteObject.exportObject(server, stub_Port);
            LocateRegistry.createRegistry(registry_Port);
            Registry registry = LocateRegistry.getRegistry(registry_Port);
            registry.bind("Server", server_Stub);
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
    public int scoreboardRequest() throws RemoteException{
        return 0;
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
