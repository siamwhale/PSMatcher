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

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

/**
 *
 * @author siamwhale
 */
public class PsLTOindex {
    private Date date;
    private DB db = null;
    private HTreeMap<String, Boolean> ltomap;
    
    private String url;
    private String dbuser;
    private String dbpass;
    
    private Connection connection = null;
    private PreparedStatement getPSLTOStm = null;
    private ResultSet rslto = null;
    private String sql = "SELECT NID FROM TAXSERVSCHEMA.PS_LTO WHERE FISCALYEAR = ?";
    
    public PsLTOindex(String url,String user,String pass) {
        if (url == "") url = "jdbc:db2://10.20.37.12:60000/TAXSERV";
        if (user == "") user = "dasusr1";
        if (pass == "") pass = "EdUaTezza53yMy55";
        db = DBMaker.memoryDB().make();
                    ltomap = db.hashMap("ltomap")
                   .keySerializer(Serializer.STRING)
                   .valueSerializer(Serializer.BOOLEAN)
                   .create();
        this.url = url;
        this.dbuser = user;
        this.dbpass = pass;
    }
    public void preparePsLTO(String fiscalyear) {
        Integer cntrow = 0;
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            connection = DriverManager.getConnection(url,dbuser,dbpass);
            this.date = new java.util.Date();
            if (connection!=null) System.out.println(new Timestamp(date.getTime()) + " PsLTOindex DB2 Connected...");
            getPSLTOStm = connection.prepareStatement(sql);
            this.date = new java.util.Date();
            System.out.println(new Timestamp(date.getTime()) + " PsLTOindex create index..");
            getPSLTOStm.setString(1, fiscalyear);
            rslto = getPSLTOStm.executeQuery();
            while (rslto.next()) {
                ltomap.put(rslto.getString("NID"),true);
                cntrow++;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rslto != null) rslto.close();
                if (getPSLTOStm != null) getPSLTOStm.close();
                connection.close();
                System.out.println(new Timestamp(date.getTime()) + " PsLTOindex Loaded..."+ cntrow + " records");
                System.out.println(new Timestamp(date.getTime()) + " PsLTOindex DB2 Disonnected...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean getPsLTO(String nid) {
        this.date = new java.util.Date();
        Object res = ltomap.get(nid);
        if (res != null) {
            System.out.println(new Timestamp(date.getTime()) + " PsLTOindex checked nid "+nid+" = 1");
            return true;
        }else{
            System.out.println(new Timestamp(date.getTime()) + " PsLTOindex checked nid "+nid+" = 0");
            return false;
        }
    }
            
    
}
