package cpm.simplelogic.helper;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jaspreetkaur on 29/05/18.
 */

public class ContentProviderData_State {

    public static final String AUTHORITY = "cpm.simplelogic.helper.ContentProviderData_State";
    public static final String PATH  = "/states";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + PATH);
    public static final String CONTENT_DICTIONARY_LIST = "vnd.android.cursor.dir/vnd.com.inducesmile.androidcontentprovider.states";
    public static final String CONTENT_DICTIONARY_ITEM = "vnd.android.cursor.item/vnd.com.inducesmile.androidcontentprovider.states";

    public static class Dictionary implements BaseColumns {
        private Dictionary(){}
        public static final String TABLE_NAME = "states";
        public static final String STATE = "name";
        // public static final String primary_category = "primary_category";
    }
}
