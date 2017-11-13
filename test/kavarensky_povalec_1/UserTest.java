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
public class UserTest {
    
    private User user;
    
    
    public UserTest() {
        user = new User(0, "email", "passwd", 0, 0);
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
     * Test of getProcessed method, of class User.
     */
    @Test
    public void testGetProcessed() {
        System.out.println("getProcessed");
        User instance = user;
        int expResult = 0;
        int result = instance.getProcessed();
        assertEquals(expResult, result);
    }

    /**
     * Test of setProcessed method, of class User.
     */
    @Test
    public void testSetProcessed() {
        System.out.println("setProcessed");
        int processed = 0;
        User instance = user;
        instance.setProcessed(processed);
    }

    /**
     * Test of getId method, of class User.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        User instance = user;
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class User.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int id = 0;
        User instance = user;
        instance.setId(id);
    }

    /**
     * Test of getEmail method, of class User.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        User instance = user;
        String expResult = "email";
        String result = instance.getEmail();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEmail method, of class User.
     */
    @Test
    public void testSetEmail() {
        System.out.println("setEmail");
        String email = "email";
        User instance = user;
        instance.setEmail(email);
    }

    /**
     * Test of getPasswd method, of class User.
     */
    @Test
    public void testGetPasswd() {
        System.out.println("getPasswd");
        User instance = user;
        String expResult = "passwd";
        String result = instance.getPasswd();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPasswd method, of class User.
     */
    @Test
    public void testSetPasswd() {
        System.out.println("setPasswd");
        String passwd = "passwd";
        User instance = user;
        instance.setPasswd(passwd);
    }

    /**
     * Test of getAdmin method, of class User.
     */
    @Test
    public void testGetAdmin() {
        System.out.println("getAdmin");
        User instance = user;
        int expResult = 0;
        int result = instance.getAdmin();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAdmin method, of class User.
     */
    @Test
    public void testSetAdmin() {
        System.out.println("setAdmin");
        int admin = 0;
        User instance = user;
        instance.setAdmin(admin);
    }
    
}
