/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kavarensky_povalec_1;

import DB.Database;


/**
 * třída slouží hlavně při přihlašování uživatele do aplikace
 * @author Kuba
 */
public class Confirm {
    
    private Main main;
    /**
     * konstruktor třídy Confirm
     * @param main Hlavní řída aplikace
     */
    public Confirm(Main main){
        this.main = main;
    }

    /**
     * metoda zkontroluje, zda se uživatel nachází v databázi a zda zadal správné heslo
     * dle toho vrátí boolean hodnotu
     * @param email kterým se uživatel přihlašuje
     * @param passwd které uživatel zadal
     * @return zda jsou přihlašovací údaje správné
     */
    public boolean confirmSignUp(String email, String passwd){
        //uživatel nevyplnil všechny pole formuláře
        if(email.equals("") || passwd.equals("")){
            return false;
        }
        
        Database database = Database.getInstance();
        database.databazovaFunkce("GET", "SELECT * FROM ad_74e3927ac2e12c0.users where email='" + email + "'");
        //v databazove funkci se po selectu vytvoří přihlášený uživatel...
        User user = main.getSignedUser();
        //...pokud následující údaje nejsou připraveny, uživatel zadal špatné jméno nebo heslo
        if(user.getEmail() == null || user.getPasswd() == null){
            return false;
        }
        //pokud je nastaven email i heslo uživatele, je možno jít dále
        else if(user.getEmail().equals(email) && user.getPasswd().equals(passwd)){
            System.out.println("YES!");
            return true;
        }
        return false;
    }
    
    /**
     * proces při registraci.
     * Nejprve se zkontroluje, zda ještě není email použit
     * @param email email, který chce user používat
     * @param passwd heslo, které chce user používat
     * @return zda se podařilo uživatele založit
     */
    public String confirmSignIn(String email, String passwd) {
        Database database = Database.getInstance();
        //nevím, proč je to tu dvakrát, ale funguje to, možná se dá ten první select smazat
        database.databazovaFunkce("UNIQUE", "SELECT * FROM ad_74e3927ac2e12c0.users where email='" + email + "'");
        String email_db = database.databazovaFunkce("UNIQUE", "SELECT * FROM ad_74e3927ac2e12c0.users where email='" + email + "'");
        //jméno ještě neexistuje, je možno založit nového uživatele
        if(email_db == null){
            database.databazovaFunkce("UPDATE", "INSERT into ad_74e3927ac2e12c0.users (email, password, admin, processed) values ('" + email + "','" + passwd + "', 0, 0)");
            System.out.println("user registered");
            return "";
        }
        //email byl již v DB nalezen, není možno nového uživatele založit
        else{
            System.out.println("not registered");
            return "not unique";
        }
        
        
    }

    
}
