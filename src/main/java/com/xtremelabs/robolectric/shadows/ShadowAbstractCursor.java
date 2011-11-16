package com.xtremelabs.robolectric.shadows;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentResolver;
import android.database.AbstractCursor;
import android.database.CursorWindow;
import android.net.Uri;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.internal.RealObject;


@Implements(AbstractCursor.class)
public class ShadowAbstractCursor {
    @RealObject
    private AbstractCursor realAbstractCursor;

    protected Map<String, Object> currentRow;
    protected int currentRowNumber = -1;
    protected Map<String, Integer> columnNames = new HashMap<String, Integer>();
    protected String[] columnNameArray;
    protected Map<Integer, Map<String, Object>> rows = new HashMap<Integer, Map<String, Object>>();
    protected int rowCount;
    protected Uri notificationUri;

    @Implementation
    public int getCount() {
        return rowCount;
    }
    
    @Implementation
    public boolean moveToFirst() {
        setPosition(0);
        return rowCount > 0;
    }
    
    @Implementation
    public boolean moveToLast() {
    	if( rowCount == 0 ) {
    		return false;
    	}
    	setPosition( rowCount - 1 );
    	return true;
    }

    @Implementation
    public int getPosition() {
        return currentRowNumber;
    }


    @Implementation
    public boolean moveToPosition(int pos) {
        if (pos >= rowCount) {
            return false;
        }

        setPosition(pos);
        return true;
    }

    /**
     * Set currentRowNumber(Int) and currentRow (Map)
     *
     * @param pos = the position to set
     */
    private void setPosition(int pos) {
        currentRowNumber = pos;
        if ((-1 == currentRowNumber) || (rowCount == currentRowNumber)) {
            currentRow = null;
        } else {
            currentRow = rows.get(currentRowNumber);
        }
    }

    @Implementation
    public boolean moveToNext() {
        if (currentRowNumber + 1 >= rowCount) {
            currentRowNumber = rowCount;
            return false;
        }
        setPosition(++currentRowNumber);
        return true;
    }

    @Implementation
    public boolean moveToPrevious() {
        if (currentRowNumber < 0 || rowCount == 0) {
            return false;
        }
        setPosition(--currentRowNumber);
        return true;
    }

    @Implementation
    public CursorWindow getWindow() {
        return null;
    }

    @Implementation
    public String[] getColumnNames() {
        return columnNameArray;
    }

    @Implementation
    public int getColumnCount() {
        return getColumnNames().length;
    }

    @Implementation
    public boolean isFirst() {
        return currentRowNumber == 0;
    }

    @Implementation
    public boolean isLast() {
        return currentRowNumber == rowCount - 1;
    }

    @Implementation
    public boolean isBeforeFirst() {
        return currentRowNumber < 0;
    }

    @Implementation
    public boolean isAfterLast() {
        return currentRowNumber >= rowCount;
    }

    @Implementation
    public void setNotificationUri(ContentResolver cr, Uri notifyUri) {
        notificationUri = notifyUri;
    }

    /**
     * Returns the Uri set by {@code setNotificationUri()}.  Method included for testing
     * pre-API 11 projects.
     */
    public Uri getNotificationUri_Compatibility() {
        return notificationUri;
    }

	@Implementation
	public int getInt(int column) {
		System.out.println("column name: " + columnNameArray[column]);
		for (String k : currentRow.keySet()) {
			System.out.println("currentRow key: " + k + " v: "
					+ currentRow.get(k));
		}
		return (Integer) currentRow.get(columnNameArray[column]);
	}
    
}