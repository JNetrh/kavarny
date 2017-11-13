/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kavarensky_povalec_1;

import javafx.scene.image.Image;

/**
 * Třída kavárna představuje jednotlivou kavárnu
 * Přepravka, ve které se nesou informace o dané kavárně
 * @author Kuba
 */
public class Cafe {
    
    //artibuty, které si o sobě kavárna pamatuje
    private int id;
    private String nazev;
    private String mesto;
    private String misto;
    private String adresa;
    private String gpsX;
    private String gpsY;
    private String dobaOd;
    private String dobaDo;
    private Image logo;
    private Image listek;
    private boolean wifi;
    private boolean bezbarierova;
    private String popis;
    
    /**
     * Defaultní konstruktor
     */
    public Cafe(){};
    
    /**
     * přetížený konstruktor, ve kterém se zadají všechny potřebné informace o nově založené
     * kavárně
     * @param id kavárny
     * @param nazev kavárny
     * @param mesto kavárny
     * @param misto kavárny
     * @param adresa kavárny
     * @param gpsX kavárny
     * @param gpsY kavárny
     * @param dobaOd kavárny
     * @param dobaDo kavárny
     * @param logo kavárny
     * @param listek kavárny
     * @param wifi kavárny
     * @param bezbarierova kavárny
     * @param popis  kavárny
     */
    public Cafe(int id, String nazev, String mesto, String misto, String adresa, String gpsX, String gpsY, String dobaOd, String dobaDo, Image logo, Image listek, boolean wifi, boolean bezbarierova, String popis) {
        this.id = id;
        this.nazev = nazev;
        this.mesto = mesto;
        this.misto = misto;
        this.adresa = adresa;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.dobaOd = dobaOd;
        this.dobaDo = dobaDo;
        this.logo = logo;
        this.listek = listek;
        this.wifi = wifi;
        this.bezbarierova = bezbarierova;
        this.popis = popis;
    }
    
    /**
     * 
     * @return obrázek nápojáku
     */
    public Image getListek() {
        return listek;
    }
    
    /**
     * 
     * @param listek nový obrázek nápojáku
     */
    public void setListek(Image listek) {
        this.listek = listek;
    }
    
    /**
     * @return začátku otevírací doby
     */
    public String getDobaOd() {
        return dobaOd;
    }

    /**
     * nastavení začátku otevírací doby
     * @param dobaOd kdy má otevřeno
     */
    public void setDobaOd(String dobaOd) {
        this.dobaOd = dobaOd;
    }

    /**
     * 
     * @return dokdy mají otevřeno
     */
    public String getDobaDo() {
        return dobaDo;
    }

    /**
     * nastavení začátku otevírací doby
     * @param dobaDo kdy má otevřeno
     */
    public void setDobaDo(String dobaDo) {
        this.dobaDo = dobaDo;
    }

    /**
     * 
     * @return id kavárny
     */
    public int getId() {
        return id;
    }
    
    /**
     * nastavení id kavárny (BE CAREFOUL TO USE)
     * @param id kavárny
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * jméno kavárny
     * @return jméno kavárny
     */
    public String getNazev() {
        return nazev;
    }

    /**
     * 
     * @param nazev nový pro kavárnu
     */
    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    /**
     * 
     * @return město ve kterém se kavárna nachází
     */
    public String getMesto() {
        return mesto;
    }

    /**
     * nastaví nové město
     * @param mesto 
     */
    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    /**
     * 
     * @return místo, kde se nachází
     */
    public String getMisto() {
        return misto;
    }
    
    /**
     * nastaví místo, kde se nachází
     * @param misto nové místo
     */
    public void setMisto(String misto) {
        this.misto = misto;
    }

    /**
     * 
     * @return adresa kavárny
     */
    public String getAdresa() {
        return adresa;
    }

    /**
     * 
     * @param adresa nová adresa kavárny
     */
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    /**
     * 
     * @return x-ovou souřadnici GPS
     */
    public String getGpsX() {
        return gpsX;
    }
    
    /**
     * 
     * @return y-ovou souřadnici GPS
     */
     public String getGpsY() {
        return gpsY;
    }

     /**
      * 
      * @param gpsX nová gps-x souřadnice
      */
    public void setGpsX(String gpsX) {
        this.gpsX = gpsX;
    }
    
    /**
     * 
     * @param gpsY nová gps-y souřadnice
     */
    public void setGpsY(String gpsY) {
        this.gpsY = gpsY;
    }

    /**
     * 
     * @return logo kavárny
     */
    public Image getLogo() {
        return logo;
    }

    /**
     * 
     * @param logo nastaví nové logo kavárny
     */
    public void setLogo(Image logo) {
        this.logo = logo;
    }

    /**
     * 
     * @return zda má kavárna wiFi
     */
    public boolean isWifi() {
        return wifi;
    }

    /**
     * 
     * @param wifi nasatví, zde je v kavárně WiFi
     */
    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    /**
     * 
     * @return zda je kavárna bezbariérová
     */
    public boolean isBezbarierova() {
        return bezbarierova;
    }

    /**
     * 
     * @param bezbarierova zda je kavána bezbariérová
     */
    public void setBezbarierova(boolean bezbarierova) {
        this.bezbarierova = bezbarierova;
    }

    /**
     * 
     * @return popis kavárničky
     */
    public String getPopis() {
        return popis;
    }

    /**
     * 
     * @param popis nastaví nový popis kavárny
     */
    public void setPopis(String popis) {
        this.popis = popis;
    }
    
}
