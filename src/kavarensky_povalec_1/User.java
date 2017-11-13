/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kavarensky_povalec_1;

/**
 * Třída uživatel drží informace o přihlášeném uživateli
 * a zároveň jsou její instance použity v listu uživatelů
 * @author Kuba
 */
public class User {
    
    private int id;
    private String email;
    private String passwd;
    private int admin;
    private int processed;
    
    /**
     * Konstruktor inicializuje všechny informace, které můžeme o uživateli znát
     * každá z nich je důležita
     * @param id uživatele
     * @param email uživatele
     * @param passwd uživatele
     * @param admin  uživatele
     * @param processed stav uživatele
     */
   public User(int id, String email, String passwd, int admin, int processed) {
        this.id = id;
        this.email = email;
        this.passwd = passwd;
        this.admin = admin;
        this.processed = processed;
    }
    
   /**
    * 
    * @return processed value
    */
    public int getProcessed() {
        return processed;
    }

    /**
     * nastavuje nový stav uživatele v rámci processed
     * @param processed stav uživatele
     */
    public void setProcessed(int processed) {
        this.processed = processed;
    }
    
   /**
    * 
    * @return id uživatele
    */
    public int getId() {
        return id;
    }

    /**
     * nastavuje nové id uživatele
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * varcí jméno (email) uživatele
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * nastaví nový email pro uživatele
     * @param email nový email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return heslo uživatele
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * nastaví uživateli nové heslo
     * @param passwd nové heslo
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * vrátí hodnotu práv pro uživatele
     * @return míra práv
     */
    public int getAdmin() {
        return admin;
    }

    /**
     * nastaví míru práv pro uživatele
     * @param admin míra práv
     */
    public void setAdmin(int admin) {
        this.admin = admin;
    }
    
    
}
