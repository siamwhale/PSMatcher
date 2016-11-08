/*
java -cp "PSMatcher-0.0.1.jar;lib/*" th.go.rd.tcl.psmatcher.PsMatcherServer jdbc:db2://10.20.37.12:60000/TAXSERV dasusr1 EdUaTezza53yMy55 2559
 */
package th.go.rd.tcl.psmatcher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author siamwhale
 */
public class PsMatcherServer implements PsMatcherInf {
    static PsLTOindex ltoidx = null;
    static PsTeamindex teamidx = null;
    static PsVatindex vatidx = null;
    
    public PsMatcherServer() throws RemoteException {	
    }

    @Override
    public boolean getLTO(String nid) throws RemoteException {
        return ltoidx.getPsLTO(nid);
    }
    
    @Override
    public String getFVAT(String nid,String branch) throws RemoteException {
        return vatidx.getFVAT(nid, branch);
    }
    
    @Override
    public String getTeam(String nid, String branch) throws RemoteException {
        return teamidx.getPsTeam(nid, branch);
    }
    
    public static void main(String args[]) throws Exception {
        System.out.println("PsMatcherServer starting");
        

        //Instantiate RmiServer
        PsMatcherServer obj = new PsMatcherServer();
 
        try { //special exception handler for registry creation
        	
            PsMatcherInf stub = (PsMatcherInf) UnicastRemoteObject.exportObject(obj,0);
            Registry reg;
            try {
            	reg = LocateRegistry.createRegistry(1099);
                System.out.println("PsMatcherServer RMI registry created.");

            } catch(Exception e) {
            	System.out.println("PsMatcherServer using existing registry");
            	reg = LocateRegistry.getRegistry();
            }
            
            reg.rebind("PsMatcherServer", stub);
            ltoidx = new PsLTOindex(args[0], args[1], args[2]);
            ltoidx.preparePsLTO(args[3]);
            
            //vatidx = new PsVatindex(args[0], args[1], args[2]);
            //vatidx.preparePsVat();
            
            //teamidx = new PsTeamindex(args[0], args[1], args[2]);
            //teamidx.preparePsTeam();
            
            System.out.println("PsMatcherServer started...already to service");
            
            
            
        } catch (RemoteException e) {
        	e.printStackTrace();
        } 
    }
}
