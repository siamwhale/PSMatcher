/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.go.rd.tcl.psmatcher;

import org.mapdb.*;
import org.mapdb.serializer.SerializerArrayTuple;
import java.util.Map;

/**
 *
 * @author siamwhale
 */
public class TestMaxdb {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
//        DB db = DBMaker.memoryDB().make();
//        BTreeMap<Object[], String> map = db.treeMap("map")
//                .keySerializer(new SerializerArrayTuple(Serializer.STRING,Serializer.STRING,Serializer.STRING))
//                .valueSerializer(Serializer.STRING)
//                .createOrOpen();
//                
//        String[] nids = {"0000000000000","0000000000001","0000000000002","0000000000003","0000000000004"};        
//        String[] branchs = {"00000","00001","00002","00003","00004"};        
//        String[] outs = {"02000000|-1|0","01000000|125|0","01000000|-1|1","02000000|1142|0","01000000|2534|0"};        
//      
//        for (int i=0;i<nids.length;i++) {
//            map.put(new Object[]{nids[i],branchs[i]},outs[i]);
//        }
//        
//        Map<Object[], String> a = map.prefixSubMap(new Object[]{"0000000000000"});
        //#a1
        // initialize db and map
        DB db = DBMaker.memoryDB().make();
        BTreeMap<Object[], Integer> map = db.treeMap("towns")
                .keySerializer(new SerializerArrayTuple(
                        Serializer.STRING, Serializer.STRING, Serializer.INTEGER))
                .valueSerializer(Serializer.INTEGER)
                .createOrOpen();
        //#z1

        //initial values
        String[] towns = {"Galway", "Ennis", "Gort", "Cong", "Tuam"};
        String[] streets = {"Main Street", "Shop Street", "Second Street", "Silver Strands"};
        int[] houseNums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        for (String town : towns)
            for (String street : streets)
                for (int house : houseNums) {
                    int income = 30000;
                    map.put(new Object[]{town, street, house}, income);
                }
         //#a2
        //get all houses in Cong (town is primary component in tuple)
        Map<Object[], Integer> cong =
                map.prefixSubMap(new Object[]{"Cong"});
        
    }
    
}
