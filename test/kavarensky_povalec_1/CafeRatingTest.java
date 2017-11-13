/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kavarensky_povalec_1;

import java.util.ArrayList;
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
public class CafeRatingTest {
    private CafeRating cafeRating;
    public CafeRatingTest() {
        cafeRating = new CafeRating(44);
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

    /**
     * Test of getCafeId method, of class CafeRating.
     */
    @Test
    public void testGetCafeId() {
        System.out.println("getCafeId");
        CafeRating instance = cafeRating;
        int expResult = 44;
        int result = instance.getCafeId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCafeId method, of class CafeRating.
     */
    @Test
    public void testSetCafeId() {
        System.out.println("setCafeId");
        int cafeId = 20;
        CafeRating instance = cafeRating;
        instance.setCafeId(cafeId);
        int result = instance.getCafeId();
        assertEquals(cafeId, result);
    }

    /**
     * Test of getRatingText method, of class CafeRating.
     */
    @Test
    public void testGetRatingText() {
        System.out.println("getRatingText");
        CafeRating instance = cafeRating;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getRatingText();
        assertNotEquals(expResult, result);
    }

    /**
     * Test of getRatingInt method, of class CafeRating.
     */
    @Test
    public void testGetRatingInt() {
        System.out.println("getRatingInt");
        CafeRating instance = cafeRating;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getRatingInt();
        assertNotEquals(expResult, result);
    }
    
}
