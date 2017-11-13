/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kavarensky_povalec_1;

import DB.Database;

/**
 * Třída, pomocí které se zapíše do db nové hodnocení kavárny
 * @author Kuba
 */
public class InsertNewRating {
    
    private int id;
    private int pointRating;
    private String ratingText;
    
    /**
     * Konstruktor ve kterém se přiřadí id kavárny( nutné )
     * @param id 
     */
    public InsertNewRating(int id) {
        this.id = id;
        pointRating = 0;
        ratingText = null;
    }

    /**
     * nastaví textové hodnocení
     * @param textRating text hodnocení
     */
    public void setTextRating(String textRating) {
        this.ratingText = textRating;
    }

    /**
     * nastaví číselné (1-5) hodnocení
     * @param points číslo 1-5
     */
    public void setIntRating(int points) {
        this.pointRating = points;
    }

    /**
     * zapíše a potvrdí zapsání do db 
     * @return zda se zapsání povedlo úspěšně
     */
    public boolean confirm() {
        Database database = Database.getInstance();
        String statement = "INSERT into ad_74e3927ac2e12c0.caferating (cafeId, ratingText, ratingInt) VALUES (" + id + ",'" + ratingText + "'," + pointRating + ")";
        if(database.databazovaFunkce("UPDATE", statement).equals("inserted succesfully")){
            return true;
        }
        return false;
    }
    
        
}
