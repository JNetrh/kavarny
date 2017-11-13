/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kavarensky_povalec_1;

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
public class MasterAdminTest {
    
    private MasterAdmin admin;
    
    public MasterAdminTest() {
        admin = new MasterAdmin("master1", "master1");
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
     * Test of isSigned method, of class MasterAdmin.
     */
    @Test
    public void testIsSigned() {
        System.out.println("isSigned");
        MasterAdmin instance = admin;
        boolean expResult = false;
        boolean result = instance.isSigned();
        assertEquals(expResult, result);
    }
    
    

    /**
     * Test of setSigned method, of class MasterAdmin.
     */
    @Test
    public void testSetSigned() {
        System.out.println("setSigned");
        boolean signed = true;
        MasterAdmin instance = admin;
        instance.setSigned(signed);
        boolean result = instance.isSigned();
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class MasterAdmin.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        MasterAdmin instance = admin;
        String expResult = "master1";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class MasterAdmin.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "master2";
        String expResult = "master2";
        MasterAdmin instance = admin;
        instance.setName(name);
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPassword method, of class MasterAdmin.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        MasterAdmin instance = admin;
        String expResult = "master1";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPassword method, of class MasterAdmin.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "master2";
        String expResult = "master2";
        MasterAdmin instance = admin;
        instance.setPassword(password);
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }
    
}
