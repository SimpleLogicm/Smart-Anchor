package cpm.simplelogic.helper;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jaspreetkaur on 06/03/18.
 */

public class ContentProviderData_Customers {

    public static final String AUTHORITY = "cpm.simplelogic.helper.ContentProviderData_Customers";
    public static final String PATH  = "/customers";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + PATH);
    public static final String CONTENT_DICTIONARY_LIST = "vnd.android.cursor.dir/vnd.com.inducesmile.androidcontentprovider.customers";
    public static final String CONTENT_DICTIONARY_ITEM = "vnd.android.cursor.item/vnd.com.inducesmile.androidcontentprovider.customers";

    public static class Dictionary implements BaseColumns {
        private Dictionary(){}
        public static final String TABLE_NAME = "customer_master";
        public static final String NAME = "CUSTOMER_NAME";
        public static final String MOBILE = "CUSTOMER_NAME";
        public static final String CITY = "CITY";
        // public static final String primary_category = "primary_category";
    }
}

