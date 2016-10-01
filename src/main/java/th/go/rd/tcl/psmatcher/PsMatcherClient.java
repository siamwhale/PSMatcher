/*
java -cp "PSMatcher-0.0.1.jar;lib/*" th.go.rd.tcl.psmatcher.PsMatcherClient
 */
package th.go.rd.tcl.psmatcher;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author SGH042T3X7
 */
public class PsMatcherClient {
    public static void main(String args[]) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost");
        PsMatcherInf  obj = (PsMatcherInf ) registry.lookup("PsMatcherServer");
        String nid = args[0];
        String branch = args[1];
                
        System.out.println("Check found LTO "+ nid +" : "+ obj.getLTO(nid)); 
        System.out.println("Check not found LTO "+ nid +" : "+ obj.getLTO(nid)); 
        System.out.println("Check found team  "+ nid + " branch " + branch +" : "+ obj.getTeam(nid, branch)); 
        System.out.println("Check not found team  "+ nid + " branch " + branch +" : "+ obj.getTeam(nid, branch)); 
        System.out.println("Check found fvat  "+ nid + " branch " + branch +" : "+ obj.getFVAT(nid, branch)); 
        System.out.println("Check not found fvat  "+ nid + " branch " + branch +" : "+ obj.getFVAT(nid, branch)); 
    }
}
