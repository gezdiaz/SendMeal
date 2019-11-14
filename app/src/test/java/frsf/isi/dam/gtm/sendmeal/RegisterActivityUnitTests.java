package frsf.isi.dam.gtm.sendmeal;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RegisterActivityUnitTests {
    @Test
    public void test_emailIsValid(){
        RegisterActivity registerActivity = new RegisterActivity();
        assertTrue(registerActivity.emailIsValid("email@isvalid.com"));
        assertFalse(registerActivity.emailIsValid("invalidEmail"));
    }
    @Test
    public void test_dateIsValid(){
        RegisterActivity registerActivity = new RegisterActivity();
        assertTrue(registerActivity.dateIsValid(03, 20, Calendar.getInstance()));
        assertFalse(registerActivity.dateIsValid(02, 20, Calendar.getInstance()));
    }
}