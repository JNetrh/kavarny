/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kavarensky_povalec_1;

import javafx.scene.image.Image;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kuba
 */
public class CafeTest {
    
    private Cafe cafe;
    
    public CafeTest() {
        cafe = new Cafe();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

//    /**
//     * Test of getListek method, of class Cafe.
//     */
//    @Test
//    public void testGetListek() {
//        System.out.println("getListek");
//        Cafe instance = new Cafe();
//        Image expResult = null;
//        Image result = instance.getListek();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of setListek method, of class Cafe.
//     */
//    @Test
//    public void testSetListek() {
//        System.out.println("setListek");
//        Image listek = null;
//        Cafe instance = new Cafe();
//        instance.setListek(listek);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getDobaOd method, of class Cafe.
     */
    @Test
    public void testGetDobaOd() {
        System.out.println("getDobaOd");
        Cafe instance = new Cafe();
        instance.setDobaOd("23");
        String expResult = "23";
        String result = instance.getDobaOd();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDobaOd method, of class Cafe.
     */
    @Test
    public void testSetDobaOd() {
        System.out.println("setDobaOd");
        String dobaOd = "22";
        Cafe instance = new Cafe();
        instance.setDobaOd(dobaOd);
        String result = instance.getDobaOd();
        assertEquals("22", result);
    }

    /**
     * Test of getDobaDo method, of class Cafe.
     */
    @Test
    public void testGetDobaDo() {
        System.out.println("getDobaDo");
        Cafe instance = new Cafe();
        instance.setDobaDo("23");
        String expResult = "23";
        String result = instance.getDobaDo();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDobaDo method, of class Cafe.
     */
    @Test
    public void testSetDobaDo() {
        System.out.println("setDobaDo");
        String dobaDo = "22";
        String expResult = "22";
        Cafe instance = new Cafe();
        instance.setDobaDo(dobaDo);
        assertEquals(expResult, "22");
    }

//    /**
//     * Test of getId method, of class Cafe.
//     */
//    @Test
//    public void testGetId() {
//        System.out.println("getId");
//        Cafe instance = new Cafe();
//        int expResult = 0;
//        int result = instance.getId();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of setId method, of class Cafe.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int id = 100;
        Cafe instance = new Cafe();
        instance.setId(id);
        int result = instance.getId();
        assertEquals(id, result);
    }

//    /**
//     * Test of getNazev method, of class Cafe.
//     */
//    @Test
//    public void testGetNazev() {
//        System.out.println("getNazev");
//        Cafe instance = new Cafe();
//        String expResult = "";
//        String result = instance.getNazev();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setNazev method, of class Cafe.
//     */
//    @Test
//    public void testSetNazev() {
//        System.out.println("setNazev");
//        String nazev = "";
//        Cafe instance = new Cafe();
//        instance.setNazev(nazev);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMesto method, of class Cafe.
//     */
//    @Test
//    public void testGetMesto() {
//        System.out.println("getMesto");
//        Cafe instance = new Cafe();
//        String expResult = "";
//        String result = instance.getMesto();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setMesto method, of class Cafe.
//     */
//    @Test
//    public void testSetMesto() {
//        System.out.println("setMesto");
//        String mesto = "";
//        Cafe instance = new Cafe();
//        instance.setMesto(mesto);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMisto method, of class Cafe.
//     */
//    @Test
//    public void testGetMisto() {
//        System.out.println("getMisto");
//        Cafe instance = new Cafe();
//        String expResult = "";
//        String result = instance.getMisto();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setMisto method, of class Cafe.
//     */
//    @Test
//    public void testSetMisto() {
//        System.out.println("setMisto");
//        String misto = "";
//        Cafe instance = new Cafe();
//        instance.setMisto(misto);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAdresa method, of class Cafe.
//     */
//    @Test
//    public void testGetAdresa() {
//        System.out.println("getAdresa");
//        Cafe instance = new Cafe();
//        String expResult = "";
//        String result = instance.getAdresa();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setAdresa method, of class Cafe.
//     */
//    @Test
//    public void testSetAdresa() {
//        System.out.println("setAdresa");
//        String adresa = "";
//        Cafe instance = new Cafe();
//        instance.setAdresa(adresa);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGpsX method, of class Cafe.
//     */
//    @Test
//    public void testGetGpsX() {
//        System.out.println("getGpsX");
//        Cafe instance = new Cafe();
//        String expResult = "";
//        String result = instance.getGpsX();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGpsY method, of class Cafe.
//     */
//    @Test
//    public void testGetGpsY() {
//        System.out.println("getGpsY");
//        Cafe instance = new Cafe();
//        String expResult = "";
//        String result = instance.getGpsY();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setGpsX method, of class Cafe.
//     */
//    @Test
//    public void testSetGpsX() {
//        System.out.println("setGpsX");
//        String gpsX = "";
//        Cafe instance = new Cafe();
//        instance.setGpsX(gpsX);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setGpsY method, of class Cafe.
//     */
//    @Test
//    public void testSetGpsY() {
//        System.out.println("setGpsY");
//        String gpsY = "";
//        Cafe instance = new Cafe();
//        instance.setGpsY(gpsY);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getLogo method, of class Cafe.
//     */
//    @Test
//    public void testGetLogo() {
//        System.out.println("getLogo");
//        Cafe instance = new Cafe();
//        Image expResult = null;
//        Image result = instance.getLogo();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setLogo method, of class Cafe.
//     */
//    @Test
//    public void testSetLogo() {
//        System.out.println("setLogo");
//        Image logo = null;
//        Cafe instance = new Cafe();
//        instance.setLogo(logo);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of isWifi method, of class Cafe.
     */
    @Test
    public void testIsWifi() {
        System.out.println("isWifi");
        Cafe instance = new Cafe();
        boolean expResult = true;
        instance.setWifi(true);
        boolean result = instance.isWifi();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWifi method, of class Cafe.
     */
    @Test
    public void testSetWifi() {
        System.out.println("setWifi");
        boolean wifi = true;
        Cafe instance = new Cafe();
        instance.setWifi(wifi);
        boolean result = instance.isWifi();
        assertEquals(wifi, result);
    }

//    /**
//     * Test of isBezbarierova method, of class Cafe.
//     */
//    @Test
//    public void testIsBezbarierova() {
//        System.out.println("isBezbarierova");
//        Cafe instance = new Cafe();
//        boolean expResult = false;
//        boolean result = instance.isBezbarierova();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setBezbarierova method, of class Cafe.
//     */
//    @Test
//    public void testSetBezbarierova() {
//        System.out.println("setBezbarierova");
//        boolean bezbarierova = false;
//        Cafe instance = new Cafe();
//        instance.setBezbarierova(bezbarierova);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPopis method, of class Cafe.
//     */
//    @Test
//    public void testGetPopis() {
//        System.out.println("getPopis");
//        Cafe instance = new Cafe();
//        String expResult = "";
//        String result = instance.getPopis();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setPopis method, of class Cafe.
//     */
//    @Test
//    public void testSetPopis() {
//        System.out.println("setPopis");
//        String popis = "";
//        Cafe instance = new Cafe();
//        instance.setPopis(popis);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
