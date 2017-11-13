/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kavarensky_povalec_1;

import DB.Database;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Toto je zajímavá třída
 * Je to odnož třídy databáze hlavně kvůli vkládání obrázků, které je před vložením
 * nutné zpracovat a poslat do db jako binární stream, což klasícký select neumožňuje
 * Zároveň je odolná vůči SQL injectům.
 * Třída je potomkem třídy Thread, tudíž běží jako samostatné vlákno
 * @author Kuba
 */
public class InsertCafe extends Thread{
    
    //nastavení driverů a signUp údajů
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net:3306/ad_74e3927ac2e12c0";
    private static final String DB_USER = "b2fa80fa63aca4";
    private static final String DB_PASSWORD = "c8c69c72";
    
    
    
    
    
    private Cafe cafe;
    
    //prvky, ze kterých se složí finální insert
    private final String table = "INSERT into ad_74e3927ac2e12c0.cafes ";
    private String insertNames = "(";
    private String finalStr = "(";
    private final String values = "VALUES ";
    private final String comma = ", ";
    private final String quotes = "'";
    //toto se nyní nepoužívá
    private final String[] columnNames = {"name", "city", "place", "adress", "gpsX", "gpsY", "dobaOd", "dobaDo", "logo", "listek", "wifi", "access", "description"};
    
    private File logo;
    private File listek;
    
        
    /**
     * konstruktor, který potřebuje instanci třídy kavárna, kterou následně vloží
     * do DB a soubory, ve kterých jsou uložené obrázky
     * (soubory jsou jako zvláštní parametr, neboť třída kavárna si nedrží typ File,
     * ale Image)
     * @param cafe kavárna s daty pro vložení do DB
     * @param logo soubor s obrázkem loga zvolený uživatelem
     * @param listek soubor s obrázkem nápojového lístku zvoleného uživatelem
     */
    public InsertCafe(Cafe cafe, File logo, File listek) {
        this.cafe = cafe;
        this.logo = logo;
        this.listek = listek;
    }
    
    /**
     * Vytvoří string do db (nyní nepoužíváno)
     * spustí metodu ve které se provede insert do db
     */
    @Override
    public void run(){
        inserts();
        
        try {
            execute();
        } catch (Exception ex) {
            Logger.getLogger(InsertCafe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * V metodě se spustí dva streamy, které přetvoří datový typ File na binární streamy, které je 
     * následně možné pohodlně vložit do db
     * @throws Exception výjimka obecná
     * @throws IOException Input Output výjimka při zápisu do DB, např příliš mnoho connections
     * @throws SQLException SQL statemen nemá správný tvar
     */
    public void execute() throws Exception, IOException, SQLException {
    Class.forName(DB_DRIVER);
    Connection conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
    String INSERT_PICTURE = table + insertNames + values + " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    
    FileInputStream fis_1 = null;
    FileInputStream fis_2 = null;
    
    
    PreparedStatement ps = null;
    try {
      conn.setAutoCommit(false);
      fis_1 = new FileInputStream(logo);
      fis_2 = new FileInputStream(listek);
      ps = conn.prepareStatement(INSERT_PICTURE);
      ps.setString(1, cafe.getNazev());
      ps.setString(2, cafe.getMesto());
      ps.setString(3, cafe.getMisto());
      ps.setString(4, cafe.getAdresa());
      ps.setString(5, cafe.getGpsX());
      ps.setString(6, cafe.getGpsY());
      ps.setString(7, cafe.getDobaOd());
      ps.setString(8, cafe.getDobaDo());
      ps.setBinaryStream(9, fis_1, (int) logo.length());
      ps.setBinaryStream(10, fis_2, (int) listek.length());
      ps.setBoolean(11, false);
      ps.setBoolean(12, true);
      ps.setString(13, cafe.getPopis());
      ps.executeUpdate();
      conn.commit();
    } finally {
      ps.close();
      fis_1.close();
      fis_2.close();
      
      
        try {
            conn.close();
       } catch (SQLException ex) {
           Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
       }
      
    }
  }
    
    /**
     * metoda ve které se vytvoří finální String pro insert do db (NOT USED YET)
     */
    private void inserts(){
        for (String columnName : columnNames) {
            if(columnName.equals("logo")){
                insertNames += columnName + comma;
                finalStr += "null" + comma;
               
            }
            else if(columnName.equals("listek")){
                insertNames += columnName + comma;
                finalStr += "null" + comma;
                
            }
            else if(columnName.equals("access")){
                insertNames += columnName + comma;
                finalStr += "null" + comma;
                
                
            }
            else if(columnName.equals("wifi")){
                insertNames += columnName + comma;
                finalStr += "null" + comma;
                
                
            }
            
            else if(columnName.equals("name")){
                insertNames += columnName + comma;
                finalStr += quotes + cafe.getNazev()+ quotes + comma;
                
                
            }
            
           else if(columnName.equals("city")){
                insertNames += columnName + comma;
                finalStr += quotes + cafe.getMisto()+ quotes + comma;
                
                
            }
            
           else if(columnName.equals("place")){
                insertNames += columnName + comma;
                finalStr += quotes + cafe.getMesto()+ quotes + comma;
                
                
            }
            
           else if(columnName.equals("adress")){
                insertNames += columnName + comma;
                finalStr += quotes + cafe.getAdresa()+ quotes + comma;
                
                
            }
            
           else if(columnName.equals("gpsX")){
                insertNames += columnName + comma;
                finalStr += quotes + cafe.getGpsX()+ quotes + comma;
                
                
            }
            
           else if(columnName.equals("gpsY")){
                insertNames += columnName + comma;
                finalStr += quotes + cafe.getGpsY()+ quotes + comma;
                
                
            }
            
           else if(columnName.equals("dobaOd")){
                insertNames += columnName + comma;
                finalStr += quotes + cafe.getDobaOd()+ quotes + comma;
                
                
            }
            
           else if(columnName.equals("dobaDo")){
                insertNames += columnName + comma;
                finalStr += quotes + cafe.getDobaDo()+ quotes + comma;
                
                
            }
            
           else if(columnName.equals("description")){
                insertNames += columnName;
                finalStr += quotes + cafe.getPopis()+ quotes;
                
                
            }
            
        }
        insertNames += ") ";
        finalStr += ") ";
    }
    
    /**
     * metoda, která nefunguje, ale měla by uříznou posledních pár znaků ze Stringu
     * možná proto ji nepoužívám
     * @param str který má být ořezán
     * @return ořezaný string
     */
    private static String removeLastChar(String str) {
        System.out.println(str.length());
        String str2 = str.substring(0,str.length()-2);
        System.out.println(str2.length());
        return str2;
    }
    
}
