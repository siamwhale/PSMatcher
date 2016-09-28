/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.go.rd.tcl.importservice.psmatcher;

import java.util.concurrent.ConcurrentMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 *
 * @author siamwhale
 */
public class TestMaxdb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        DB db = DBMaker.memoryDB().make();
        ConcurrentMap map = db.hashMap("map").createOrOpen();
        String keystr = "k";
        Integer i = 0;
        for (i=0;i<9999;i++) {
            map.put(keystr + Integer.toString(i), Integer.toString(i));
        }
        
        String res = (String) map.get("k982");
        
      
    }
    
}
