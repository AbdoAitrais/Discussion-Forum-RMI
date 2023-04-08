package proxy;

import ma.fstm.ilisi2.discussionApp.client.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ProxyImpl extends UnicastRemoteObject implements Proxy{
    public static User user;
    public ProxyImpl(User c) throws RemoteException {
        super();
        user = c;
//        try{
//            Registry registry = LocateRegistry.createRegistry(9090);
//            Naming.rebind("rmi://localhost:9090/proxy", this);
//            System.out.println("Server listening on "+ 9090 + "...");
//        } catch (Exception e) {
//            System.err.println("Server exception: " + e.toString());
//            e.printStackTrace();
//        }
    }
    @Override
    public void ecouter(String msg) throws RemoteException {
        user.ecrire(msg);
    }
//    public static void main(String[] args) {
//        try {
//            new ProxyImpl(user);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
}
