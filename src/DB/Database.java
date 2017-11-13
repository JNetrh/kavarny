/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import kavarensky_povalec_1.*;

/**
 * Třída korigující připojení do DB
 * Je situována jako jedináče, má proto privátní konstruktor a
 * je možné požádat pouze o její instanci
 * @author Kuba
 */
public class Database {
   private static Database instance = null;
   
   private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
   private static final String DB_CONNECTION = "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net:3306/ad_74e3927ac2e12c0";
   private static final String DB_USER = "b2fa80fa63aca4";
   private static final String DB_PASSWORD = "c8c69c72";
   
   private Main main;
   private ResultSet rs_kavarny;
   private ResultSet rs_kavarnyHodnoceni;
   Connection connection;
   
   /**
    * Exists only to defeat instantiation.
    */
   protected Database() {}
   
   /**
    * pokud ještě neexistuje instance této třídy, vytvoří ji
    * v opačném případě vrátí sama sebe
    * @return insatnci sama sebe
    */
   public static Database getInstance() {
      if(instance == null) {
         instance = new Database();
      }
      return instance;
   }
   
   /**
    * Základní řízení připojení do db
    * @param function Typ prováděné operace (UPDATE, GET, UNIQUE)
    * @param sqlStatement statement, který se má provést (INSERT, UPDATE, DELETE)
    * @return informační string, jak metoda proběhla. Pokud není stanoveno jinak, vrací ""
    */
   public String databazovaFunkce(String function, String sqlStatement) {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            
            ResultSet rs = null;
            statement = connection.prepareStatement(sqlStatement);
            
            if(function.equals("GET")){
                rs = statement.executeQuery();
                nactiZDB(rs);
                rs.close();
            }
            if(function.equals("GET_PROCESSED")){
                rs = statement.executeQuery();
                String processed = nactiProcessed(rs);
                rs.close();
                return processed;
            }
            
            if(function.equals("UNIQUE")){
                rs = statement.executeQuery();
                String user = uniqueEmail(rs);
                rs.close();
                connection.close();
                return user;
            }
            
            if(function.equals("UPDATE")){
                statement.executeUpdate();
            }
            
        }
        catch(SQLException | NullPointerException | ClassNotFoundException ex){
            System.out.println(ex);
        }
        try {
            connection.close();
            
       } catch (SQLException ex) {
           Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        
        return "inserted succesfully";
    }
   
   /**
    * Vybere všechny uživatele, kteří mají processed hodnotu 1
    * což znamená, že požádali o správcování a vrátí tyto
    * jako arrayList
    * @return ArrayList users, kteří mají processed val 1
    */
   public ArrayList<User> getProcessedUsers(){
       ArrayList<User> userList = new ArrayList<>();
       
       connection = null;
       PreparedStatement statement = null;
       ResultSet rs = null;
       String sqlStatement = "SELECT *  FROM users";
        
       try{
           Class.forName(DB_DRIVER);
           connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            
           statement = connection.prepareStatement(sqlStatement);
           
           rs = statement.executeQuery();
           while(rs.next()){
               if(rs.getInt("processed") == 1){
                   userList.add(new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"), rs.getInt("admin"), 1));
               }
           }
           rs.close();
           connection.close();
       }
       catch(SQLException | NullPointerException | ClassNotFoundException ex){
           System.out.println(ex);
       }
       
       return userList;
   }
   
   /**
    * vrátí id uživatele podle zadaného emailu
    * @param email hledaného uživatele
    * @return id užíívatele
    */
   public int getUserMailById(String email){
       int userId = 0;
       
       connection = null;
       PreparedStatement statement = null;
       ResultSet rs = null;
       String sqlStatement = "SELECT *  FROM users where email='" + email + "'";
        
       try{
           Class.forName(DB_DRIVER);
           connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            
           statement = connection.prepareStatement(sqlStatement);
           
           rs = statement.executeQuery();
           while(rs.next()){
               if(rs.getString("email").equals(email)){
                   userId = rs.getInt("id");
                   break;
               }
           }
           rs.close();
           connection.close();
       }
       catch(SQLException | NullPointerException | ClassNotFoundException ex){
           System.out.println(ex);
       }
       
       return userId;
   }
   
   
   /**
    * metoda pro nastavení instane třídy Main
    * @param main Hlavní třída aplikace
    */
   public void setMain(Main main){
       this.main = main;
   }
   
   
   /**
    * z resultSetu vybere atributy a nastaví přihlášeného uživatele
    * používáno pouze při přihlášení uživatele, nikoliv při jeho registraci!!
    * @param rs který se vrátí po query do db
    */
   private void nactiZDB(ResultSet rs) {
       int id = -1;
       int admin = -1;
       String email = null;
       String passwd = null;
       int processed = -1;
        try{
            while(rs.next()){
                id = rs.getInt("id");
                admin = rs.getInt("admin");
                email = rs.getString("email");
                passwd = rs.getString("password");
                processed = rs.getInt("processed");
            }
            main.setSignedUser(id, email, passwd, admin, processed);
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
    }

   /**
    * Projde query a pokud je prázdné nastaví v main zalogovaného usera
    * @param rs který se vrátí po query z db
    * @return email z db
    */
    private String uniqueEmail(ResultSet rs) {
        int id = -1;
        int admin = -1;
        String email = null;
        String passwd = null;
        int processed = -1;
        try{
            while(rs.next()){
                id = rs.getInt("id");
                admin = rs.getInt("admin");
                email = rs.getString("email");
                passwd = rs.getString("password");
                processed = rs.getInt("processed");
            }
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        if(email == null){
            main.setSignedUser(id, email, passwd, admin, processed);
        }
        return email;
    }
    
    /**
     * Funkce slouží pro načtení kaváren z db
     * Provede se požadované query a vrátí result set, se kterým se
     * dá pracovat do doby, dokud se nuzavře (další metodou)
     * @param function typ funkce, který se má provést (v případě update se vrátí null)
     * @param sqlStatement který se má provést
     * @return result set, který se vrátí z db
     */
    public ResultSet kavarny(String function, String sqlStatement) {
        connection = null;
        PreparedStatement statement = null;
        ResultSet kavarny = null;
        
        try{
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            rs_kavarny = null;
            
            statement = connection.prepareStatement(sqlStatement);
            
            if(function.equals("GET")){
                rs_kavarny = statement.executeQuery();
                kavarny = rs_kavarny;
                
            }
            
            if(function.equals("UPDATE")){
                statement.executeUpdate();
            }
            
        }
        catch(SQLException | NullPointerException | ClassNotFoundException ex){
            System.out.println(ex);
        }
        
        return kavarny;
        
    }
    
    /**
     * zavře připojení i resultSet
     * Je ve zvláštní metodě, neboť je nutné zavolat uzavření
     * až po načtení všech dat z result setu
     */
    public void zavriKavarny(){
        try{
            rs_kavarny.close();
            connection.close();
        }
        
        catch(SQLException | NullPointerException ex){
            System.out.println(ex);
        }
    }

    /**
     * metoda která se používá pro načtení všech hodnocení z db
     * Stejně jako metoda pro načtení kaváren vrací pouze result set
     * @param function typ příkazu, který se má provést
     * @param sqlStatement samotný sql příkaz
     * @return result set, se kterým se dá do uzavření dále pracovat
     */
   public ResultSet kavarnyHodnoceni(String function, String sqlStatement) {
        connection = null;
        PreparedStatement statement = null;
        ResultSet kavarny = null;
        
        try{
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            rs_kavarnyHodnoceni = null;
            
            statement = connection.prepareStatement(sqlStatement);
            
            if(function.equals("GET")){
                rs_kavarnyHodnoceni = statement.executeQuery();
                kavarny = rs_kavarnyHodnoceni;
                
            }
            
            if(function.equals("UPDATE")){
                statement.executeUpdate();
            }
            
        }
        catch(SQLException | NullPointerException | ClassNotFoundException ex){
            System.out.println(ex);
        }
        
        return kavarny;
        
    }
   /**
     * zavře resultSet NIKOLIV připojení 
     * query předcházející tomuto uzavření je rychlejší než načtení kaváren,
     * proto se neuzavře připojení, neboť bychom odstřili ostatní procesy 
     * běžící v jiných vláknech.
     * Je ve zvláštní metodě, neboť je nutné zavolat uzavření
     * až po načtení všech dat z result setu
     */
   public void zavriKavarnyHodnoceni(){
        try{
            rs_kavarnyHodnoceni.close();
            //connection.close();
        }
        
        catch(SQLException | NullPointerException ex){
            System.out.println(ex);
        }
    }
   
   /**
    * Vrátí hodnotu sloupce processed
    * pokud je 100, uživatel se v db nenachází
    * @param rs resultSet z db
    * @return hodnota processed
    */
    private String nactiProcessed(ResultSet rs) {
        int processed = 100;
        try{
            while(rs.next()){
                processed = rs.getInt("processed");
            }
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        return Integer.toString(processed);
    }
   
}