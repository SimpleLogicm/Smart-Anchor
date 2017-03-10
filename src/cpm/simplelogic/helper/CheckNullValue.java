package cpm.simplelogic.helper;

public class CheckNullValue {
	public static boolean findNullValue(String Value)
	{
		String compare=Value;
		if(compare==null || compare.length()==0 || compare.equalsIgnoreCase("null") || compare.equalsIgnoreCase("") || compare.equalsIgnoreCase(" "))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean isWhitespace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
}
