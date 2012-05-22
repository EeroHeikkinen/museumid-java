package net.museumid.museumid;

import java.net.MalformedURLException;
import java.net.URL;

public class MID {
	/**
	 * Creates an MNS object based on the BDN
	 * 
	 * @param bdn
	 *            a base domain name as a string
	 * @return a MNS object
	 * @see MNS()
	 * @author Georg Hohmann
	 * @author Eero Heikkinen
	 * @throws Exception
	 * @throws UUIDException
	 */
	public MNS getMNS(String bdn) throws UUIDException, Exception {
		return new MNS(bdn.trim());
	}

	/**
	 * Creates an MOI object based on the IVN and the MNS
	 * 
	 * @param ivn
	 *            an inventory number as a string
	 * @param mns
	 *            a MNS UUID as a string
	 * @return a MOI object
	 * @see MOI()
	 * @author Georg Hohmann
	 * @author Eero Heikkinen
	 * @throws Exception
	 * @throws UUIDException
	 */
	public MOI getMOI(String ivn, String mns) throws UUIDException, Exception {
		return new MOI(ivn.trim(), mns.trim());
	}

	/**
	 * Validates a BDN
	 * 
	 * @param bdn
	 *            as a string
	 * @return TRUE on success
	 * @author Georg Hohmann
	 * @throws UUIDException 
	 */
	public boolean validateBDN(String bdn) throws UUIDException {
		throw new UUIDException("Unimplemented");
	}

	/**
	 * Validates an URN with nss 'mns' or 'mid'
	 * 
	 * @param urn
	 *            a URN as a string
	 * @return TRUE on success
	 * @author Georg Hohmann
	 */
	public boolean validateURN(String urn) throws MIDError {
		// Check if urn is empty
		if (urn.length() == 0) {
			throw new MIDError("No URN submitted.");
		} else {
			urn = urn.trim();
			// Check if urn consists of three parts
			String[] urnparts = urn.split(":");
			if (urnparts.length != 3) {
				throw new MIDError("Submitted value is not a valid URN.");
			} else {
				// Check if namespace is supported
				if (!urnparts[1].equalsIgnoreCase("mns")
						&& !urnparts[1].equalsIgnoreCase("moi")) {
					throw new MIDError("Submitted URN namespace identifier "
							+ urnparts[1] + " is not supported.");
				} else {
					// Check if uuid consists of five valid parts
					String[] idparts = urnparts[2].split("-");

					if (idparts.length != 5)
						throw new MIDError("Submitted URN is not valid.");
					if (idparts[0].length() != 8 || idparts[1].length() != 4
							|| idparts[2].length() != 4
							|| idparts[3].length() != 4
							|| idparts[4].length() != 12) {
						throw new MIDError("Submitted URN is not valid.");
					} else {
						return true;
					}
				}
			}
		}
	}

}
