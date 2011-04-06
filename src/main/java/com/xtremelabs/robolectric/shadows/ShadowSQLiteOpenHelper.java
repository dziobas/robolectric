package com.xtremelabs.robolectric.shadows;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.internal.RealObject;


/**
 * Shadow for {@code SQLiteOpenHelper}.  Provides basic support for retrieving
 * databases and partially implements the subclass contract.  (Currently,
 * support for {@code #onUpgrade} is missing).
 */
@Implements(SQLiteOpenHelper.class)
public class ShadowSQLiteOpenHelper {
    private static SQLiteDatabase       database;
    @RealObject
    private SQLiteOpenHelper            realHelper;
    int                                 version;

    public void __constructor__(Context context, String name, CursorFactory factory, int version) {
        if(database != null) {
            database.close();
        }

        database     = null;
        this.version = version;
    }

    @Implementation
    public synchronized void close() {
        if(database != null) {
            database.close();
        }

        database = null;
    }

    @Implementation
    public synchronized SQLiteDatabase getReadableDatabase() {
        return getDB();
    }

    @Implementation
    public synchronized SQLiteDatabase getWritableDatabase() {
        return getDB();
    }

    private SQLiteDatabase getDB() {
        if(database == null) {
            database = SQLiteDatabase.openDatabase("path", null, 0);

            Cursor c = null;

            try {
                c = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' ORDER BY name", null);

                if(!c.moveToFirst()) {
                    try {
                        database.beginTransaction();
                        realHelper.onCreate(database);
                        database.setVersion(version);
                        database.setTransactionSuccessful();
                    } finally {
                        database.endTransaction();
                    }
                }
            } finally {
                if(c != null) {
                    c.close();
                }
            }
        }

        realHelper.onOpen(database);

        return database;
    }
}
