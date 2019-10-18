package cpm.simplelogic.helper;

public class Check_Null_Value {

	public static boolean isNotNullNotEmptyNotWhiteSpaceOnlyByJava(final String string)
	{
        return string != null && !string.isEmpty() && !string.trim().isEmpty() && string != "null" && !string.equalsIgnoreCase("") && !string.equalsIgnoreCase(" ") && !string.equalsIgnoreCase("null");
	}



}
