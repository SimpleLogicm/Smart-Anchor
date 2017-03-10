package com.anchor.activities;

public class Check_Null_Value {

	public static boolean isNotNullNotEmptyNotWhiteSpaceOnlyByJava(final String string)  
	{  
	   if(string != null && !string.isEmpty() && !string.trim().isEmpty() && !string.equalsIgnoreCase("null"))
	   {
		   return true;
	   }
	   else
	   {
		   return false;
	   }
	}  
}
