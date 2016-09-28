/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.go.rd.tcl.importservice.psmatcher;

import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 *
 * @author siamwhale
 */
public class PsLTOindex {
    DB ltomap = null;

    public PsLTOindex() {
        ltomap = DBMaker.memoryDB().make();
    }
    
}
