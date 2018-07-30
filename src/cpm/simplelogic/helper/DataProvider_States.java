package cpm.simplelogic.helper;

/**
 * Created by jaspreetkaur on 06/03/18.
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.anchor.activities.DataBaseHelper;

import java.util.HashMap;

public class DataProvider_States extends ContentProvider{
    private DataBaseHelper dbManager;
    private SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
    HashMap<String,String> SEARCH_PROJECTION_MAP;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(ContentProviderData_State.AUTHORITY, ContentProviderData_State.PATH, 1);
        sUriMatcher.addURI(ContentProviderData_State.AUTHORITY, ContentProviderData_State.PATH + "/#", 2);
    }
    @Override
    public boolean onCreate() {
        dbManager = new DataBaseHelper(getContext());
        return false;
    }
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projections, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        SEARCH_PROJECTION_MAP = new HashMap<String, String>();

        SEARCH_PROJECTION_MAP.put("code", "code" + " as code" );
        SEARCH_PROJECTION_MAP.put( "name" , "name" + " as name" );

        sqLiteQueryBuilder.setProjectionMap(SEARCH_PROJECTION_MAP);
        sqLiteQueryBuilder.setTables("states");
        sqLiteQueryBuilder.setStrict(true);

        Cursor mCursor = null;
        switch (sUriMatcher.match(uri)){
            case 1:
                mCursor = sqLiteQueryBuilder.query(db, projections, selection, selectionArgs, null, null, null);
                break;
            case 2:
                // selection = selection + ContentProviderData_State.Dictionary.code + " = " + selectionArgs;
                mCursor = sqLiteQueryBuilder.query(db, projections, selection, selectionArgs, null, null, null);
                break;
            default:
                Toast.makeText(getContext(), "Invalid content uri", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
        mCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return mCursor;
    }
    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case 1:
                return ContentProviderData_State.CONTENT_DICTIONARY_LIST;
            case 2:
                return ContentProviderData_State.CONTENT_DICTIONARY_ITEM;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    public void save() {

        Log.d("Test method", "called");
    }
}