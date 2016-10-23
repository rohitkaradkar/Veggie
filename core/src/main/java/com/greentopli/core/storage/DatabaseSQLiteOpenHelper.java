package com.greentopli.core.storage;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.greentopli.core.BuildConfig;
import com.greentopli.core.storage.product.ProductColumns;
import com.greentopli.core.storage.purchaseditem.PurchasedItemColumns;
import com.greentopli.core.storage.user.UserColumns;

public class DatabaseSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "green_topli.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final DatabaseSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_PRODUCT = "CREATE TABLE IF NOT EXISTS "
            + ProductColumns.TABLE_NAME + " ( "
            + ProductColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ProductColumns.PRODUCT_ID + " TEXT NOT NULL, "
            + ProductColumns.NAME_ENGLISH + " TEXT NOT NULL, "
            + ProductColumns.NAME_HINGLISH + " TEXT NOT NULL, "
            + ProductColumns.IMAGE_URL + " TEXT NOT NULL, "
            + ProductColumns.MIN_VOLUME + " INTEGER NOT NULL, "
            + ProductColumns.MAX_VOLUME + " INTEGER NOT NULL, "
            + ProductColumns.VOLUME_SET + " INTEGER NOT NULL, "
            + ProductColumns.PRICE + " INTEGER NOT NULL, "
            + ProductColumns.TIME + " INTEGER NOT NULL, "
            + ProductColumns.TYPE + " TEXT NOT NULL, "
            + ProductColumns.VOLUME + " TEXT NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (product_id, name_english) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_TABLE_PURCHASED_ITEM = "CREATE TABLE IF NOT EXISTS "
            + PurchasedItemColumns.TABLE_NAME + " ( "
            + PurchasedItemColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PurchasedItemColumns.PURCHASE_ID + " TEXT NOT NULL, "
            + PurchasedItemColumns.USER_ID + " TEXT NOT NULL, "
            + PurchasedItemColumns.PRODUCT_ID + " TEXT NOT NULL, "
            + PurchasedItemColumns.DATE_REQUESTED + " INTEGER NOT NULL, "
            + PurchasedItemColumns.DATE_ACCEPTED + " INTEGER NOT NULL, "
            + PurchasedItemColumns.ACCEPTED + " INTEGER NOT NULL, "
            + PurchasedItemColumns.COMPLETED + " INTEGER NOT NULL, "
            + PurchasedItemColumns.VOLUME + " INTEGER NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (purchase_id,product_id) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS "
            + UserColumns.TABLE_NAME + " ( "
            + UserColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UserColumns.EMAIL + " TEXT NOT NULL, "
            + UserColumns.NAME + " TEXT NOT NULL, "
            + UserColumns.MOBILE_NO + " INTEGER NOT NULL, "
            + UserColumns.ADDRESS + " TEXT NOT NULL, "
            + UserColumns.PINCODE + " INTEGER NOT NULL, "
            + UserColumns.INSTANCE_ID + " TEXT NOT NULL, "
            + UserColumns.AUTH_TOKEN + " TEXT NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (email, instance_id, auth_token) ON CONFLICT REPLACE"
            + " );";

    // @formatter:on

    public static DatabaseSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static DatabaseSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static DatabaseSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new DatabaseSQLiteOpenHelper(context);
    }

    private DatabaseSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new DatabaseSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static DatabaseSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new DatabaseSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private DatabaseSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new DatabaseSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_PRODUCT);
        db.execSQL(SQL_CREATE_TABLE_PURCHASED_ITEM);
        db.execSQL(SQL_CREATE_TABLE_USER);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
