package proxy;

import ma.fstm.ilisi2.discussionApp.client.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ProxyImpl extends UnicastRemoteObject implements Proxy{
    public static User user;
    public ProxyImpl(User c) throws RemoteException {
        super();
        user = c;
    }
    @Override
    public void ecouter(String msg) throws RemoteException {
        user.ecrire(msg);
    }
}
