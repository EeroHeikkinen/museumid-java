package net.museumid.museumid;

public class MOI extends UUID {

	protected String urn = null;
	protected String ivn = null;
	protected String time = null;
	protected String mns = null;
	protected String mnsurn = null;

	/**
	 * Constructor, creates an MOI object
	 * 
	 * @param ivn
	 *            an inventory number as a string
	 * @param mns
	 *            a MNS UUID as a string
	 * @param tms
	 *            an optional UNIX timestamp
	 * @author Georg Hohmann
	 * @throws Exception
	 * @throws UUIDException
	 */
	public MOI(String ivn, String mns) throws UUIDException {
		// Calling parent constructor for constructing an UUID V5
		super(mintName(SHA1, ivn, mns));
		// Setting variables
		this.ivn = ivn;
		this.mns = mns;
		this.mnsurn = "urn:mns:" + mns;
		this.urn = "urn:moi:" + toString();
	}
	
	public MOI(String ivn, MNS mns) throws UUIDException {
		// Calling parent constructor for constructing an UUID V5
		super(mintName(SHA1, ivn, mns.toString()));
		// Setting variables
		this.ivn = ivn;
		this.mns = mns.toString();
		this.mnsurn = "urn:mns:" + mns;
		this.urn = "urn:moi:" + toString();
	}
	
	public String getMNSURN() {
		return mnsurn;
	}
	
	public String getMNS() {
		return mns;
	}
	
	public String getIvn() {
		return ivn;
	}
	
	public String getURN() {
		return urn;
	}
}