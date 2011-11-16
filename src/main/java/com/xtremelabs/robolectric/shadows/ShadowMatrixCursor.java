package com.xtremelabs.robolectric.shadows;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.database.MatrixCursor;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;

@Implements(MatrixCursor.class)
public class ShadowMatrixCursor extends ShadowAbstractCursor {

	public void __constructor__(String[] columnNames) {
		columnNameArray = new String[columnNames.length];
		for(int i=0;i<columnNames.length;i++){
			columnNameArray[i] = columnNames[i];
			this.columnNames.put(columnNames[i], i);
		}
	}

	@Implementation
	public void addRow(Iterable<?> columnValues) {
		System.out.println("add Row in ShadowMatrixCursor");
		Iterator<?> iterator = columnValues.iterator();
		Map<String, Object> row = new HashMap<String, Object>();
		int columnCount = 0;
		String key;
		while (iterator.hasNext()) {
			try {
				key = columnNameArray[columnCount];
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new IllegalArgumentException("Column count is to small");
			}
			row.put(key, iterator.next());
			columnCount++;
		}

		if (columnCount != getColumnCount()) {
			throw new IllegalArgumentException("Column count is to small");
		}
		rows.put(rowCount++, row);
	}

	@Implementation
	public void addRow(Object[] columnValues) {
		if (columnValues.length != getColumnCount()) {
			throw new IllegalArgumentException("Column count is to small");
		}
		Map<String, Object> row = new HashMap<String, Object>();
		for (int i = 0; i < columnValues.length; i++) {
			row.put(columnNameArray[i], columnValues[i]);
		}
		rows.put(rowCount++, row);
	}

}
