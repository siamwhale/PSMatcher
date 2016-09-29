/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public PsMatcherServer() throws RemoteException {	
    }

    @Override
    public boolean getLTO(String nid) throws RemoteException {
        return ltoidx.getPsLTO(nid);
    }

    @Override
    public String getFVAT(String nidbranch) throws RemoteException {
        return "1";
    }
    
    public static void main(String args[]) throws Exception {
        System.out.println("PsMatcherServer started");
        

        //Instantiate RmiServer
        PsMatcherServer obj = new PsMatcherServer();
 
        try { //special exception handler for registry creation
        	
            PsMatcherInf stub = (PsMatcherInf) UnicastRemoteObject.exportObject(obj,0);
            Registry reg;
            try {
            	reg = LocateRegistry.createRegistry(1099);
                System.out.println("java RMI registry created.");

            } catch(Exception e) {
            	System.out.println("Using existing registry");
            	reg = LocateRegistry.getRegistry();
            }
            
            reg.rebind("PsMatcherServer", stub);
            ltoidx = new PsLTOindex(args[0], args[1], args[2]);
            ltoidx.preparePsLTO(args[3]);
            

        } catch (RemoteException e) {
        	e.printStackTrace();
        }
    }
}
