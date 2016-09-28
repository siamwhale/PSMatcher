package th.go.rd.tcl.importservice.psmatcher;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author siamwhale
 */
public interface PsMatcherInf extends Remote {
    public String getLTO(String nid) throws RemoteException;
    public String getFVAT(String nidbranch) throws RemoteException;
}
