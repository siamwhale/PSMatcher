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
import java.util.Map;
import org.mapdb.BTreeMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.mapdb.serializer.SerializerArrayTuple;
/**
 *
 * @author SGH042T3X7
 */
public class PsTeamindex {
    private Date date;
    private DB db = null;
    private  BTreeMap<Object[], String> teammap;
    
    private String url;
    private String dbuser;
    private String dbpass;
    
    private Connection connection = null;
    private PreparedStatement getPSTeamStm = null;
    private ResultSet rsteam = null;
    private String sql = "SELECT NID, BRANCH, "
            + " CASE WHEN OFFCODE8 IS NULL THEN '' ELSE OFFCODE8 END || '|' || "
            + " CASE WHEN TECODE IS NULL THEN '-1' ELSE TECODE END  || '|' || "
            + " CASE WHEN PENDING IS NULL THEN '0' ELSE PENDING END AS OTP "
            + " FROM TAXSERVSCHEMA.PS_TEAMTAXPAYER ORDER BY NID, BRANCH ASC";
    
    public PsTeamindex(String url,String user,String pass) {
        if (url == "") url = "jdbc:db2://10.20.37.12:60000/TAXSERV";
        if (user == "") user = "dasusr1";
        if (pass == "") pass = "EdUaTezza53yMy55";
        db = DBMaker.memoryDB().make();
                    teammap = db.treeMap("teammap")
                   .keySerializer(new SerializerArrayTuple(Serializer.STRING, Serializer.STRING))
                   .valueSerializer(Serializer.STRING)
                   .createOrOpen();
                   
        this.url = url;
        this.dbuser = user;
        this.dbpass = pass;
    }
    public void preparePsTeam() {
        Integer cntrow = 0;
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            connection = DriverManager.getConnection(url,dbuser,dbpass);
            this.date = new java.util.Date();
            if (connection!=null) System.out.println(new Timestamp(date.getTime()) + " PsTeamindex DB2 Connected...");
            getPSTeamStm = connection.prepareStatement(sql);
            this.date = new java.util.Date();
            System.out.println(new Timestamp(date.getTime()) + " PsTeamindex create index..");
            rsteam = getPSTeamStm.executeQuery();
            while (rsteam.next()) {
                teammap.put(new Object[]{rsteam.getString("NID"),rsteam.getString("BRANCH")},rsteam.getString("OTP"));
                cntrow++;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rsteam != null) rsteam.close();
                if (getPSTeamStm != null) getPSTeamStm.close();
                connection.close();
                this.date = new java.util.Date();
                System.out.println(new Timestamp(date.getTime()) + " PsTeamindex Loaded..."+ cntrow + " records");
                System.out.println(new Timestamp(date.getTime()) + " PsTeamindex DB2 Disonnected...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getPsTeam(String nid,String branch) {
        this.date = new java.util.Date();
        System.out.println(new Timestamp(date.getTime()) + " PsTeamindex checked nid "+ nid +" branch "+ branch);
        return teammap.get(new Object[]{nid,branch});
    }
}
