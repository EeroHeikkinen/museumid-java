package net.museumid.museumid;

/**
 * MNS Class as child of UUID class
 * 
 * @author Georg Hohmann
 */
public class MNS extends UUID {

	protected String urn = null;
	protected String bdn = null;

	/**
	 * Constructor, creates an MNS object
	 * 
	 * @param bdn
	 *            a BDN as a string
	 * @param tms
	 *            an optional UNIX timestamp
	 * @author Georg Hohmann
	 * @author Eero Heikkinen
	 * @throws Exception
	 * @throws UUIDException
	 */
	public MNS(String bdn) throws UUIDException {
		// Calling parent constructor for constructing an UUID V5
		super(mintName(SHA1, bdn, "6ba7b810-9dad-11d1-80b4-00c04fd430c8"));
		// Setting variables
		this.bdn = bdn;
		this.urn = "urn:mns:" + toString();
	}
	
	@Override
	public String getURN() {
		return urn;
	}
	
	public String getBDN() {
		return bdn;
	}
}