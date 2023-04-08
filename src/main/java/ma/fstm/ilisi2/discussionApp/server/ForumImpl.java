package ma.fstm.ilisi2.discussionApp.server;

import proxy.Proxy;

import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.TreeMap;

public class ForumImpl extends UnicastRemoteObject implements Forum {
    int i;//id d'un client;
    String hostname;
    TreeMap<Integer,Proxy> ListClient;
    public ForumImpl() throws RemoteException {
        super();
        i=0;
        ListClient= new TreeMap<Integer,Proxy>();

        try{
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(9099);
            Forum server = new ForumImpl();
            String hostname = InetAddress.getLocalHost().getHostName();
            Naming.rebind("rmi://localhost:9099/Server", server);
            System.out.println("Server listening on "+ 9099 +"...");
        } catch (Exception ex) {
            System.err.println("Server exception: " + ex.toString());
            ex.printStackTrace();
        }
    }
    @Override
    public synchronized int entrer(Proxy pr) throws RemoteException {
        i++;
        System.out.println("Client numero "+i+" connecté");
        pr.ecouter("client connecté a "+9099+ " avec Id "+i);
        ListClient.put(i,pr);
        return i;
    }

    @Override
    public synchronized void dire(int id, String msg) throws RemoteException {
//        Proxy proxy;
//        proxy=ListClient.get(id);
//        proxy.ecouter(msg);
        for (Proxy proxy : ListClient.values()) {
            proxy.ecouter(msg);
        }
    }

    @Override
    public synchronized String qui() throws RemoteException {
        StringBuilder s= new StringBuilder();
        for (Integer key : ListClient.keySet()) {
            s.append(key).append(" ");
        }
        return s.toString();
    }

    @Override
    public synchronized void quiter(int id) throws RemoteException {
        Proxy cb;
        cb=ListClient.get(id);
        ListClient.remove(id);
        cb.ecouter("Vous etes deconnecté de "+hostname);
    }
}
