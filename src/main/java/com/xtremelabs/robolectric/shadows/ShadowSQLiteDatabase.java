package com.xtremelabs.robolectric.shadows;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import static com.xtremelabs.robolectric.Robolectric.newInstanceOf;
import static com.xtremelabs.robolectric.Robolectric.shadowOf;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.util.SQLite.SQLStringAndBindings;
import static com.xtremelabs.robolectric.util.SQLite.buildDeleteString;
import static com.xtremelabs.robolectric.util.SQLite.buildInsertString;
import static com.xtremelabs.robolectric.util.SQLite.buildUpdateString;
import static com.xtremelabs.robolectric.util.SQLite.buildWhereClause;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Iterator;


/**
 * Shadow for {@code SQLiteDatabase} that simulates the movement of a {@code Cursor} through database tables.
 * Implemented as a wrapper around an embedded SQL database, accessed via JDBC.  The JDBC connection is
 * made available to test cases for use in fixture setup and assertions.
 */
@Implements(SQLiteDatabase.class)
public class ShadowSQLiteDatabase {
    private Connection connection;

    @Implementation
    public static SQLiteDatabase openDatabase(String path, SQLiteDatabase.CursorFactory factory, int flags) {
    	SQLiteDatabase db=null;
    	try {
            Class.forName("org.sqlite.JDBC").newInstance();
            Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db");
             db= newInstanceOf(SQLiteDatabase.class);
            Robolectric.shadowOf(db).setConnection(connection);
        } catch(Exception e) {
            throw new RuntimeException("SQL exception in openDatabase", e);
        }

		return db;
    }

    private void setConnection(Connection connection) {
		this.connection = connection;		
	}

	@Implementation
    public long insert(String table, String nullColumnHack, ContentValues values) {
        SQLStringAndBindings sqlInsertString = buildInsertString(table, values);

        try {
            PreparedStatement statement = connection.prepareStatement(sqlInsertString.sql, Statement.RETURN_GENERATED_KEYS);
            Iterator<Object>  columns   = sqlInsertString.columnValues.iterator();
            int               i         = 1;

            while(columns.hasNext()) {
                statement.setObject(i++, columns.next());
            }

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.first()) {
                return resultSet.getLong(1);
            }
        } catch(SQLException e) {
            throw new RuntimeException("SQL exception in insert", e);
        }

        return -1;
    }

    @Implementation
    public Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        String where = selection;

        if((selection != null) && (selectionArgs != null)) {
            where = buildWhereClause(selection, selectionArgs);
        }

        String    sql       = SQLiteQueryBuilder.buildQueryString(distinct, table, columns, where, groupBy, having, orderBy, limit);

        ResultSet resultSet;

        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet       = statement.executeQuery(sql);
        } catch(SQLException e) {
            throw new RuntimeException("SQL exception in query", e);
        }

        SQLiteCursor cursor = new SQLiteCursor(null, null, null, null);
        shadowOf(cursor).setResultSet(resultSet);

        return cursor;
    }

    @Implementation
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, null);
    }

    @Implementation
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return query(false, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    @Implementation
    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLStringAndBindings sqlUpdateString = buildUpdateString(table, values, whereClause, whereArgs);

        try {
            PreparedStatement statement = connection.prepareStatement(sqlUpdateString.sql);
            Iterator<Object>  columns   = sqlUpdateString.columnValues.iterator();
            int               i         = 1;

            while(columns.hasNext()) {
                statement.setObject(i++, columns.next());
            }

            return statement.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException("SQL exception in update", e);
        }
    }

    @Implementation
    public int delete(String table, String whereClause, String[] whereArgs) {
        String sql = buildDeleteString(table, whereClause, whereArgs);

        try {
            return connection.prepareStatement(sql).executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException("SQL exception in delete", e);
        }
    }

    @Implementation
    public void execSQL(String sql) throws android.database.SQLException {
        if(!isOpen()) {
            throw new IllegalStateException("database not open");
        }

        try {
            System.out.println(sql);
            connection.createStatement().execute(sql);
        } catch(java.sql.SQLException e) {
            android.database.SQLException ase = new android.database.SQLException();
            ase.initCause(e);
            throw ase;
        }
    }

    @Implementation
    public void execSQL(String sql, Object[] bindArgs)
                 throws android.database.SQLException {
        if(bindArgs == null) {
            throw new IllegalArgumentException("Empty bindArgs");
        }

        if(!isOpen()) {
            throw new IllegalStateException("database not open");
        }

        if(bindArgs != null) {
            int numArgs = bindArgs.length;

            for(int i = 0; i < numArgs; i++) {
                Object value = bindArgs[i];

                System.out.println("sql:" + sql);
                System.out.println("value:" + value.toString());

                if(value instanceof String) {
                    sql = sql.replaceFirst("\\?", "\'" + value.toString() + "\'");
                } else if(value instanceof Integer || value instanceof Double) {
                    sql = sql.replaceFirst("\\?", ((Number) value).toString());
                } else {
                }
            }

            execSQL(sql);
        }
    }

    @Implementation
    public Cursor rawQuery(java.lang.String sql, java.lang.String[] selectionArgs) {
        ResultSet resultSet;

        if(selectionArgs != null) {
            int numArgs = selectionArgs.length;

            for(int i = 0; i < numArgs; i++) {
                String value = selectionArgs[i];
                sql = sql.replaceFirst("\\?", "\'" + value.toString() + "\'");
            }
        }

        System.out.println(sql);

        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(sql);
            System.out.println(resultSet.toString());
        } catch(SQLException e) {
            throw new RuntimeException("SQL exception in query", e);
        }

        SQLiteCursor cursor = new SQLiteCursor(null, null, null, null);
        shadowOf(cursor).setResultSet(resultSet);

        return cursor;
    }

    @Implementation
    public boolean isOpen() {
        return (connection != null);
    }

    @Implementation
    public void close() {
        if(!isOpen()) {
            return;
        }

        try {
            connection.close();
            connection = null;
        } catch(SQLException e) {
            throw new RuntimeException("SQL exception in close", e);
        }
    }

    /**
     * Allows test cases access to the underlying JDBC connection, for use in
     * setup or assertions.
     *
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }
}
