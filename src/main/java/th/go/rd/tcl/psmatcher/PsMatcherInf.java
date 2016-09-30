package th.go.rd.tcl.psmatcher;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author siamwhale
 */
public interface PsMatcherInf extends Remote {
    public boolean getLTO(String nid) throws RemoteException;
    public String getFVAT(String nid, String branch) throws RemoteException;
    public String getTeam(String nid, String branch) throws RemoteException;
}
