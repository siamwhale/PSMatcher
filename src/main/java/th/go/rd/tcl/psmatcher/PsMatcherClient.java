/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        PsMatcherInf  obj = (PsMatcherInf ) registry.lookup("PSMatcherServer");
        String nid = "0100472000151";
        System.out.println("LTO "+ nid +" : "+ obj.getLTO(nid)); 
        nid = "0000000000000";
        System.out.println("NONLTO "+ nid +" : "+ obj.getLTO(nid)); 
        
    }
}
