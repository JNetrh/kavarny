/*
 * To change this license header, choose License Headekavarny in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kavarensky_povalec_1;

import DB.Database;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

/**
 * Třída, která je potomkem třídy Tnread je spuštěna po zapnutí aplikace
 * v průběhu přihlašování tato třída načte kavárny z db a uloží je do 
 * listu
 * @author Kuba
 */
public class CafeList extends Thread{
    
    private Main main;
    private ArrayList<Cafe> seznamKavaren;
    
    
    /**
     * konstruktor ve kterém se nastaví proměnná hlavní třídy
     * inicializuje se List, pro uložení kaváren
     * @param main Hlavní třída aplikace
     */
    public CafeList(Main main) {
        this.main = main;
        seznamKavaren = new ArrayList<Cafe>();
                
    }
    
    /**
     * toto třída prochází result set a postupně vytvoří instance kaváren, 
     * které uloží do listu
     * Nokonec provede uzavření spojení s databází
     */
    @Override
    public void run(){
        Database database = Database.getInstance();
        ResultSet kavarny = database.kavarny("GET", "SELECT * FROM ad_74e3927ac2e12c0.cafes");
        
        Image logo = null;
        Image listek = null;
        
        try{
            while(kavarny.next()){
                
                try {
                    if(kavarny.getBlob("logo") != null || kavarny.getBlob("listek") != null){
                    logo = convertImage((kavarny.getBlob("logo")).getBytes(1, (int) (kavarny.getBlob("logo")).length()), 100, 100);
                    listek = convertImage((kavarny.getBlob("listek")).getBytes(1, (int) (kavarny.getBlob("listek")).length()), 100, 100);}
                } catch (SQLException ex) {
                    Logger.getLogger(CafeList.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                seznamKavaren.add(new Cafe(kavarny.getInt("id"), kavarny.getString("name"), kavarny.getString("city"), 
                        kavarny.getString("place"), kavarny.getString("adress"), kavarny.getString("gpsX"), kavarny.getString("gpsY"), 
                        kavarny.getString("dobaOd"), kavarny.getString("dobaDo"), logo, listek, kavarny.getBoolean("wifi"), 
                        kavarny.getBoolean("access"), kavarny.getString("description")));
            }
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        finally{
            database.zavriKavarny();        
        }
    }

    /**
     * vrací seznam kaváren
     * @return seznam všech kaváren načtených z db
     * nebo následně přidaných 
     */
    public ArrayList<Cafe> getSeznamKavaren() {
        return seznamKavaren;
    }

    /**
     * nastaví nový seznam kaváren (FORBIDDEN TO USE)
     * @param seznamKavaren 
     */
    public void setSeznamKavaren(ArrayList<Cafe> seznamKavaren) {
        this.seznamKavaren = seznamKavaren;
    }
    
    /**
     * tohle je hodně hustá metoda. Napsat mi ji trvalo celý den a jsem na ni pyšný :D
     * tenhle zmetek vezme pole bytů z db vytvoří z něho obrázek
     * @param raw tvrdá data (BLOB) získaný z db
     * @param width výsledná šířka
     * @param heightvýsledný váška
     * @return Krásnej obrázek se kterym si už umim v programu poradit
     */
    private static Image convertImage(byte[] raw, final int width, final int height) {
        WritableImage image = new WritableImage(width, height);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(raw);
            BufferedImage read = ImageIO.read(bis);
            image = SwingFXUtils.toFXImage(read, null);
        } catch (IOException ex) {
            Logger.getLogger(CafeList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }
    
    
}
