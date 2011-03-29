package com.xtremelabs.robolectric.shadows;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.ListIterator;

import android.database.sqlite.SQLiteCursor;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;


/**
 * Simulates an Android Cursor object, by wrapping a JDBC ResultSet.
 */
@Implements(SQLiteCursor.class)
public class ShadowSQLiteCursor extends ShadowAbstractCursor {
    private LinkedList<Object[]>   resultList  = new LinkedList<Object[]>();
    private ListIterator<Object[]> iterator;
    private Object[]               current;
    private String[]               columnNames;
    private int                    columnCount;

    @Implementation
    public int getCount() {
        if(resultList != null) {
            return resultList.size();
        } else {
            return 0;
        }
    }

    @Implementation
    public String[] getColumnNames() {
        return columnNames;
    }

    @Implementation
    public int getColumnIndex(String columnName) {
        if(columnName == null) {
            return -1;
        }

        String[] columnNames                   = getColumnNames();

        for(int columnIndex = 0; columnIndex < columnNames.length; columnIndex++) {
            if(columnNames[columnIndex].equalsIgnoreCase(columnName)) {
                return columnIndex;
            }
        }

        return -1;
    }

    @Implementation
    public int getColumnIndexOrThrow(String columnName) {
        int columnIndex = getColumnIndex(columnName);

        if(columnIndex == -1) {
            throw new IllegalArgumentException("Column index does not exist");
        }

        return columnIndex;
    }

    @Implementation
    @Override
    public final boolean moveToFirst() {
        try {
            if(resultList.isEmpty()) {
                return false;
            } else {
                current  = resultList.getFirst();
                iterator = resultList.listIterator(0);
            }
        } catch(Exception e) {
            throw new RuntimeException("Exception in moveToFirst", e);
        }

        return super.moveToFirst();
    }

    @Implementation
    @Override
    public boolean moveToNext() {
        try {
            if(iterator.hasNext()) {
                current = iterator.next();

                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            throw new RuntimeException("Exception in moveToNext", e);
        }
    }

    @Implementation
    public byte[] getBlob(int columnIndex) {
        try {
            return (byte[]) current[columnIndex];
        } catch(Exception e) {
            throw new RuntimeException("Exception in getBlob", e);
        }
    }

    @Implementation
    public String getString(int columnIndex) {
        try {
            return (String) current[columnIndex];
        } catch(Exception e) {
            throw new RuntimeException("Exception in getString", e);
        }
    }

    @Implementation
    public int getInt(int columnIndex) {
        try {
            return (Integer) current[columnIndex];
        } catch(Exception e) {
            throw new RuntimeException("Exception in getInt", e);
        }
    }

    @Implementation
    public long getLong(int columnIndex) {
        try {
            return (long)((int)(Integer)current[columnIndex]);
        } catch(Exception e) {
            throw new RuntimeException("Exception in getLong", e);
        }
    }

    @Implementation
    public float getFloat(int columnIndex) {
        //        try {
        //            return resultSet.getFloat(columnIndex + 1);
        //        } catch(SQLException e) {
        //            throw new RuntimeException("SQL exception in getFloat", e);
        //        }
        throw new UnsupportedOperationException("Exception in getFloat");
    }

    @Implementation
    public double getDouble(int columnIndex) {
        try {
            return (Double) current[columnIndex];
        } catch(Exception e) {
            throw new RuntimeException("Exception in getDouble", e);
        }
    }

    @Implementation
    public void close() {
        if(resultList == null) {
            return;
        }

        try {
            resultList.clear();
            resultList = null;
        } catch(Exception e) {
            throw new RuntimeException("Exception in close", e);
        }
    }

    @Implementation
    public boolean isClosed() {
        return (resultList == null);
    }

    @Implementation
    public boolean isNull(int columnIndex) {
        try {
            return current[columnIndex] == null;
        } catch(Exception e) {
            throw new RuntimeException("Exception in isNull", e);
        }
    }

    public void setResultSet(ResultSet result) {
        try {
            resultList = new LinkedList<Object[]>();

            ResultSetMetaData metaData = result.getMetaData();

            columnNames = new String[metaData.getColumnCount()];
            columnCount = metaData.getColumnCount();

            for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                columnNames[columnIndex - 1] = metaData.getColumnName(columnIndex);
            }

            int columnCount = metaData.getColumnCount();

            while(result.next()) {
                Object[] row = new Object[columnCount];

                for(int i = 0; i < columnCount; i++) {
                    switch(metaData.getColumnType(i + 1)) {
                        case Types.INTEGER:
                            row[i] = result.getInt(i + 1);

                            break;

                        case Types.BIGINT:
                            row[i] = result.getLong(i + 1);

                            break;

                        case Types.REAL:
                            row[i] = result.getDouble(i + 1);

                            break;

                        case Types.VARCHAR:
                            row[i] = result.getString(i + 1);

                            break;

                        case Types.BLOB:
                            row[i] = result.getBlob(i + 1);

                            break;
                    }
                }

                resultList.add(row);
            }

            iterator = resultList.listIterator();
        } catch(SQLException e) {
            throw new RuntimeException("SQL exception in setResultSet", e);
        } finally {
            try {
                result.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
