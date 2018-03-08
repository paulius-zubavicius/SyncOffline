package com.owr.so.commons;

public enum OSType {

	UNIX("nix", "nux", "aix"), WIN("win"), MAC("mac"), SOLARIS("sunos"), OTHER();

	private String[] oScodes;

	private OSType(String... codes) {
		oScodes = codes;
	}

	public static OSType getOSTypeByCode(String code) {

		if (code == null || code == "") {
			return OTHER;
		}

		code = code.toLowerCase();

		for (OSType type : OSType.values()) {
			for (String typeCode : type.oScodes) {
				typeCode = typeCode.toLowerCase();
				if (code.indexOf(typeCode) >= 0) {
					return type;
				}
			}
		}

		return OTHER;

	}

	public static OSType getOSTypeCurrent() {
		return getOSTypeByCode(getOSTypeCurrentStr());
	}

	public static String getOSTypeCurrentStr() {
		return System.getProperty("os.name");
	}

	public static boolean isTheSame(String code, OSType type) {
		return getOSTypeByCode(code).equals(type);
	}

	public static String getOSFileSeparator() {
		return System.getProperty("file.separator");
	}

}
