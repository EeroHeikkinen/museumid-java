package net.museumid.museumid;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.museumid.museumid.*;

/**
 * Unit test for simple App.
 */
public class MuseumIDTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MuseumIDTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MuseumIDTest.class );
    }

    public void testApp() throws UUIDException
    {
		MNS	mns = new MNS("nba.fi");
		assertEquals(mns.getURN(), "urn:mns:6a8c84f7-e6ec-5142-b141-d33a72a04422");
		
		MOI	moi = new MOI("TEST1", mns);
        assertEquals(moi.getURN(), "urn:moi:ee167043-81b5-5228-a375-b95fcc1fcdd2");
    }
}
