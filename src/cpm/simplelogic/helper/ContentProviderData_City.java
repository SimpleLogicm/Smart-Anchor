package cpm.simplelogic.helper;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jaspreetkaur on 06/03/18.
 */

public class ContentProviderData_City {

    public static final String AUTHORITY = "cpm.simplelogic.helper.ContentProviderData_City";
    public static final String PATH  = "/city";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + PATH);
    public static final String CONTENT_DICTIONARY_LIST = "vnd.android.cursor.dir/vnd.com.inducesmile.androidcontentprovider.city";
    public static final String CONTENT_DICTIONARY_ITEM = "vnd.android.cursor.item/vnd.com.inducesmile.androidcontentprovider.city";

    public static class Dictionary implements BaseColumns {
        private Dictionary(){}
        public static final String TABLE_NAME = "cities";
        public static final String CITY = "name";
        // public static final String primary_category = "primary_category";
    }
}

