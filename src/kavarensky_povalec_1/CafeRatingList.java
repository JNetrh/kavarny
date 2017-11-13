/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kavarensky_povalec_1;

import DB.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * po query do db Načítá z resultsetu hodnocení kaváren
 * @author Jakub Mareš
 */
public class CafeRatingList {

    private Map<String, CafeRating> hodnoceni;
    private Main main;
    
    /**
     * konstruktor inicializuje mapu hodnocení a
     * přiřadí proměnné hodnotu třídy main
     * spustí metodu pro nahrání kaváren z db
     * @param main Hlavní třída aplikace
     */
    public CafeRatingList(Main main) {
        this.main = main;
        hodnoceni = new HashMap<String, CafeRating>();
        nahrajKavarny();
                
    }
    
    
    /**
     * 
     * @return vrací mapu instancí CafeRating
     */
    public Map<String, CafeRating> getHodnoceni() {
        return hodnoceni;
    }
    
    /**
     * spustí query do db a z vráeceného resultsetu 
     * vytvoří mapu všech hodnocení pomocí instancí třídy CadeRating
     */
    public void nahrajKavarny(){
        Database database = Database.getInstance();
        ResultSet kavarny = database.kavarnyHodnoceni("GET", "SELECT * FROM ad_74e3927ac2e12c0.caferating");

            try{
                if(kavarny != null){
                    while(kavarny.next()){

                        int cafeId = kavarny.getInt("cafeId");
                        //u kavárny již existuje minimálně jedno hodnocení, přidáme další
                        if (hodnoceni.containsKey(Integer.toString(cafeId))) {

                            CafeRating cafe = hodnoceni.get(Integer.toString(cafeId));

                            cafe.getRatingText().add(kavarny.getString("ratingText"));
                            cafe.getRatingInt().add(Integer.toString(kavarny.getInt("ratingInt")));
                            cafe.setCafeId(cafeId);


                        } 
                        //u kavárny ještě není žádné hodnocení, vytvoříme novou instanci CafeRating a přidáme
                        //nové hodnocení načtené z db
                        else {

                            CafeRating cafe = new CafeRating(cafeId);
                            hodnoceni.put(Integer.toString(cafeId), cafe);

                            cafe.getRatingText().add(kavarny.getString("ratingText"));
                            cafe.getRatingInt().add(Integer.toString(kavarny.getInt("ratingInt")));


                        }

                    }
                }
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        
        //nakonec uzavřeme resultSet (NIKOLIV CONNECTION)
        finally{
            database.zavriKavarnyHodnoceni();
        }
}
    

    
}