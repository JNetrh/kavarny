/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kavarensky_povalec_1;

import java.util.ArrayList;


/**
 * Vytvoří nové hodnocení pro určitou kavárnu a
 * toto následně uloží do listu
 * @author Jakub Mareš
 */
public class CafeRating {
    
   
    
    private int cafeId;
    //list pro uložení slovního hodnocení
    private ArrayList<String> ratingText;
    //list pro uložení hodnocení 1-5
    private ArrayList<String> ratingInt;

    /**
     * konstruktor který inicializuje listy a přiřadí 
     * proměnné cafeId hodnotu idčka konkrétní kavárny
     * @param cafeId 
     */
    public CafeRating(int cafeId) {
        this.cafeId = cafeId;
        ratingText = new ArrayList<String>();
        ratingInt = new ArrayList<String>();
    }
    
    /**
     * 
     * @return vrací id kavárny, ke které toto hodnocení patří
     */
    public int getCafeId() {
        return cafeId;
    }

    /**
     * nastaví nové id kavárny, ke které toto hodnocení patří
     * @param cafeId 
     */
    public void setCafeId(int cafeId) {
        this.cafeId = cafeId;
    }

    /**
     * 
     * @return list slovních hodnocení
     */
    public ArrayList<String> getRatingText() {
        return ratingText;
    }

    /**
     * 
     * @return list bodového ohodnocení kavárny
     */
    public ArrayList<String> getRatingInt() {
        return ratingInt;
    }
    


    
   
    
}