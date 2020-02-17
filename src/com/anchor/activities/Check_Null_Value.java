package com.anchor.activities;

public class Check_Null_Value {

	public static boolean isNotNullNotEmptyNotWhiteSpaceOnlyByJava(final String string)  
	{
        return string != null && !string.isEmpty() && !string.trim().isEmpty() && !string.equalsIgnoreCase("null");
	}

	public static boolean isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(final String string) {
        return string != null && !string.isEmpty() && !string.trim().isEmpty() && !string.equalsIgnoreCase("null") && !string.equalsIgnoreCase("") && !string.equalsIgnoreCase(" ") && !string.equalsIgnoreCase("0") && !string.equalsIgnoreCase("0.0");
	}

	public static boolean isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(final String string) {
        return string != null && !string.isEmpty() && !string.trim().isEmpty() && !string.equalsIgnoreCase("null") && !string.equalsIgnoreCase("0") && !string.equalsIgnoreCase("0.0");
	}

	public static String isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(final String string) {
		if (string != null && !string.isEmpty() && !string.trim().isEmpty() && !string.equalsIgnoreCase("null") && !string.equalsIgnoreCase("0")) {
			return string;
		} else {
			return " ";
		}
	}

	public static String isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(final String string) {
		if (string != null && !string.isEmpty() && !string.trim().isEmpty() && !string.equalsIgnoreCase("null") && !string.equalsIgnoreCase("0") && !string.equalsIgnoreCase("0.0")) {
			return string;
		} else {
			return "";
		}
	}

	public static Boolean isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck_b(final String string) {
        return string != null && !string.isEmpty() && !string.trim().isEmpty() && !string.equalsIgnoreCase("null") && !string.equalsIgnoreCase("0") && !string.equalsIgnoreCase("0.0");
	}


}
