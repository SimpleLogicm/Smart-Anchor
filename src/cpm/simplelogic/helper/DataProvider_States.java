package cpm.simplelogic.helper;

/**
 * Created by jaspreetkaur on 06/03/18.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.anchor.activities.DataBaseHelper;

public class DataProvider_States extends ContentProvider{
    private DataBaseHelper dbManager;
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
        Cursor mCursor = null;


        switch (sUriMatcher.match(uri)){
            case 1:
                mCursor = db.query(ContentProviderData_State.Dictionary.TABLE_NAME, projections, selection, selectionArgs, null, null, null);
                break;
            case 2:
                // selection = selection + ContentProviderData_State.Dictionary.code + " = " + selectionArgs;
                mCursor = db.query(ContentProviderData_State.Dictionary.TABLE_NAME, projections, selection, selectionArgs, null, null, null);
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
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        if(sUriMatcher.match(uri) != 1) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        long rowId = db.insert(ContentProviderData_State.Dictionary.TABLE_NAME, null, contentValues);
        if(rowId > 0) {
            Uri articleUri = ContentUris.withAppendedId(ContentProviderData_State.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(articleUri, null);
            return articleUri;
        }
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        int count = 0;
        switch(sUriMatcher.match(uri)) {
            case 1:
                count = db.delete(ContentProviderData_State.Dictionary.TABLE_NAME, selection, selectionArgs);
                break;
            case 2:
                String rowId = uri.getPathSegments().get(1);
                count = db.delete(ContentProviderData_State.Dictionary.TABLE_NAME, ContentProviderData_State.Dictionary.STATE + " = " + rowId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        int count = 0;
        switch (sUriMatcher.match(uri)){
            case 1:
                count = db.update(ContentProviderData_State.Dictionary.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case 2:
                String rowId = uri.getPathSegments().get(1);
                count = db.update(ContentProviderData_State.Dictionary.TABLE_NAME, contentValues, ContentProviderData_State.Dictionary.STATE + " = " + rowId +
                        (!TextUtils.isEmpty(selection) ? " AND (" + ")" : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    public void save() {

        Log.d("Test method", "called");
    }
}