package net.museumid.museumid;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;

public class UUID {
	public final static int MD5 = 3;
	public final static int SHA1 = 5;
	public static int clearVer = 15; // 00001111 Clears all bits of version byte
										// with AND
	public static int clearVar = 63; // 00111111 Clears all relevant bits of
										// variant byte with AND
	public static int varRes = 224; // 11100000 Variant reserved for future use
	public static int varMS = 192; // 11000000 Microsft GUID variant
	public static int varRFC = 128; // 10000000 The RFC 4122 variant (this
									// variant)
	public static int varNCS = 0; // 00000000 The NCS compatibility variant
	public static int version1 = 16; // 00010000
	public static int version3 = 48; // 00110000
	public static int version4 = 64; // 01000000
	public static int version5 = 80; // 01010000
	public static long interval = 0x01b21dd213814000L; // Time (in 100ns steps)
														// between the start of
														// the UTC and Unix
														// epochs
	public static String nsDNS = "6ba7b810-9dad-11d1-80b4-00c04fd430c8";
	public static String nsURL = "6ba7b811-9dad-11d1-80b4-00c04fd430c8";
	public static String nsOID = "6ba7b812-9dad-11d1-80b4-00c04fd430c8";
	public static String nsX500 = "6ba7b814-9dad-11d1-80b4-00c04fd430c8";
	// instance properties
	protected byte[] bytes;
	protected String hex;
	protected String string;
	protected String urn;
	protected String version;
	protected String variant;
	protected String node;
	protected String time;

	public static UUID mint(int ver, String node, String ns)
			throws UUIDException, Exception {
		/* Create a new UUID based on provided data. */
		switch (ver) {
		case 0:
		case 1:
			throw new UUIDException("Unimplemented");
			// return new UUID(mintTime(node));
		case 2:
			// Version 2 is not supported
			throw new UUIDException("Version 2 is unsupported.");
		case 3:
			return new UUID(mintName(MD5, node, ns));
		case 4:
			throw new UUIDException("Unimplemented");
			// UUID(mintRand());
		case 5:
			return new UUID(mintName(SHA1, node, ns));
		default:
			throw new UUIDException(
					"Selected version is invalid or unsupported.");
		}
	}

	public static UUID importUUID(String uuid) throws Exception {
		/* Import an existing UUID. */
		return new UUID(makeBin(uuid, 16));
	}

	public static boolean compare(String a, String b) throws UUIDException {
		/*
		 * Compares the binary representations of two UUIDs. The comparison will
		 * return true if they are bit-exact, or if neither is valid.
		 */
		if (Arrays.equals(makeBin(a, 16), makeBin(b, 16)))
			return true;
		else
			return false;
	}

	public String toString() {
		return string;
	}
	
	public byte[] getBytes() {
		return this.bytes;
	}
	
	public String getHex() {
		return getHexString(bytes);
	}
	
	public String getString() {
		return string;
	}
	
	public String getURN() {
		return urn;
	}
	
	public int getVersion() {
		return (int) (bytes[6] >> 4);
	}
	
	public int getVariant() {
		int variant = bytes[8] & 0xff;
		if (variant >= varRes)
			return 3;
		if (variant >= varMS)
			return 2;
		if (variant >= varRFC)
			return 1;
		else
			return 0;
	}

	protected UUID(byte[] uuid) throws UUIDException {

		if (uuid.length != 16)
			throw new UUIDException("Input must be a 128-bit integer.");
		this.bytes = uuid;
		// Optimize the most common use
		this.string = getHexString(get(uuid, 0, 4)) + "-"
				+ getHexString(get(uuid, 4, 2)) + "-"
				+ getHexString(get(uuid, 6, 2)) + "-"
				+ getHexString(get(uuid, 8, 2)) + "-"
				+ getHexString(get(uuid, 10, 6));
	}

	public static byte[] get(byte[] array, int offset, int length) {
		byte[] result = new byte[length];
		System.arraycopy(array, offset, result, 0, length);
		return result;
	}

	public static String getHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	public static byte[] hash(String s, String method) {
		try {
			return hash(s.getBytes("ISO-8859-1"), method);
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] hash(byte[] b, String method) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance(method);
			algorithm.reset();
			algorithm.update(b);
			return algorithm.digest();

		} catch (NoSuchAlgorithmException nsae) {
			return null;
		}
	}

	protected static byte[] mintName(int ver, String node, String ns)
			throws UUIDException {
		/*
		 * Generates a Version 3 or Version 5 UUID. These are derived from a
		 * hash of a name and its namespace, in binary form.
		 */
		if (node.length() == 0)
			throw new UUIDException(
					"A name-string is required for Version 3 or 5 UUIDs.");
		// if the namespace UUID isn't binary, make it so
		byte[] binNs = makeBin(ns, 16);

		int version = 0;
		byte[] uuid = null;

		if (binNs == null)
			throw new UUIDException(
					"A binary namespace is required for Version 3 or 5 UUIDs.");
		switch (ver) {
		case MD5:
			version = version3;
			try {
				uuid = hash(new String(binNs, "ISO-8859-1") + node, "md5");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case SHA1:
			version = version5;
			try {
				byte[] hashed = hash(new String(binNs, "ISO-8859-1") + node,
						"sha1");
				uuid = new byte[16];
				System.arraycopy(hashed, 0, uuid, 0, 16);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		// set variant
		uuid[8] = (byte) (uuid[8] & clearVar | varRFC);
		// set version
		uuid[6] = (byte) (uuid[6] & clearVer | version);
		return (uuid);
	}

	protected static byte[] makeBin(String str, int len) throws UUIDException {
		/*
		 * Insure that an input string is either binary or hexadecimal. Returns
		 * binary representation, or false on failure.
		 */
		byte[] bytes = null;

		if (str.length() == len) {
			try {
				bytes = str.getBytes("ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (bytes.length != len)
				throw new UUIDException("Input contains invalid characters");
		} else {
			str = str.replaceAll("urn:uuid:", ""); // strip URN scheme and
													// namespace
			str = str.replaceAll("[^a-f0-9]", ""); // strip non-hex characters
			if (str.length() == len * 2) {
				bytes = new byte[len];
				for (int i = 0; i < len * 2; i += 2) {
					bytes[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character
							.digit(str.charAt(i + 1), 16));
				}
			}
		}
		return bytes;
	}

	public static void initRandom() throws UUIDException {
		throw new UUIDException("Unimplemented");
	}

	public static void randomBytes(byte[] bytes) throws UUIDException {
		throw new UUIDException("Unimplemented");
	}

	protected static void randomTwister(byte[] bytes) throws UUIDException {
		throw new UUIDException("Unimplemented");
	}
}
