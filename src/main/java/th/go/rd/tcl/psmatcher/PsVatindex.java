/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.go.rd.tcl.psmatcher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.mapdb.serializer.SerializerArrayTuple;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author SGH042T3X7
 */
public class PsVatindex {
    private Date date;
    private DB db = null;
    private  BTreeMap<Object[], String> vatmap;
    
    private String url;
    private String dbuser;
    private String dbpass;
    
    private Connection connection = null;
    private PreparedStatement getPSVatStm = null;
    private ResultSet rsvat = null;
    private String sql = "SELECT TIN,LPAD(BRANO,5,'0') AS BRANCH, OFFCODE8 FROM TAXSERVSCHEMA.PS_PP01_PP01B_FVAT";
    
    public PsVatindex (String url,String user,String pass) {
        if (url == "") url = "jdbc:db2://10.20.37.12:60000/TAXSERV";
        if (user == "") user = "dasusr1";
        if (pass == "") pass = "EdUaTezza53yMy55";
        db = DBMaker.memoryDB().make();
                    vatmap = db.treeMap("vatmap")
                   .keySerializer(new SerializerArrayTuple(Serializer.STRING, Serializer.STRING))
                   .valueSerializer(Serializer.STRING)
                   .createOrOpen();
                   
        this.url = url;
        this.dbuser = user;
        this.dbpass = pass;
    }
    public void preparePsVat() {
        Integer cntrow = 0;
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            connection = DriverManager.getConnection(url,dbuser,dbpass);
            this.date = new java.util.Date();
            if (connection!=null) System.out.println(new Timestamp(date.getTime()) + " PsVatindex DB2 Connected...");
            getPSVatStm = connection.prepareStatement(sql);
            this.date = new java.util.Date();
            System.out.println(new Timestamp(date.getTime()) + " PsVatindex create index..");
            rsvat = getPSVatStm.executeQuery();
            while (rsvat.next()) {
                vatmap.put(new Object[]{rsvat.getString("TIN"),rsvat.getString("BRANCH")},rsvat.getString("OFFCODE8"));
                cntrow++;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rsvat != null) rsvat.close();
                if (getPSVatStm != null) getPSVatStm.close();
                connection.close();
                this.date = new java.util.Date();
                System.out.println(new Timestamp(date.getTime()) + " PsVatindex Loaded..."+ cntrow + " records");
                System.out.println(new Timestamp(date.getTime()) + " PsVatindex DB2 Disonnected...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getFVAT(String nid,String branch) {
        this.date = new java.util.Date();
        String res = vatmap.get(new Object[]{nid,branch});
        if (StringUtils.isEmpty(res)) { 
            System.out.println(new Timestamp(date.getTime()) + " PsVatindex checked nid "+ nid +" branch "+ branch + " = 0");
            return "false";
        } else {
            System.out.println(new Timestamp(date.getTime()) + " PsVatindex checked nid "+ nid +" branch "+ branch + " = 1");
            return res;
        }
    }
}
