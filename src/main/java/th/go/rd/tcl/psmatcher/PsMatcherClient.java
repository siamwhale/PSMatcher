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
        String nid = "0100472000151";
        String branch = "00001";
                
        System.out.println("LTO "+ nid +" : "+ obj.getLTO(nid)); 
        nid = "0000000000000";
        System.out.println("NONLTO "+ nid +" : "+ obj.getLTO(nid)); 
        
        nid = "0103504005865";
        System.out.println("LTO "+ nid +" : "+ obj.getTeam(nid, branch)); 
        nid = "0000000000000";
        System.out.println("NONLTO "+ nid +" : "+ obj.getLTO(nid)); 
        
    }
}
