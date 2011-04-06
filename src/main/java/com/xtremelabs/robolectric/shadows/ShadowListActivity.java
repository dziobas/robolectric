package com.xtremelabs.robolectric.shadows;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import android.app.ListActivity;
import android.view.View;
import android.widget.ListView;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;

/**
 * Shadow of {@code ListActivity} that supports the retrieval of {@code ListViews}
 */

@SuppressWarnings({"UnusedDeclaration"})
@Implements(ListActivity.class)
public class ShadowListActivity extends ShadowActivity {
    private ListView listView;

    @Implementation
    public ListView getListView() {
        if (listView == null) {
            if ((listView = findListView(contentView)) == null) {
                throw new RuntimeException("No ListView found under content view");
            }
        }
        return listView;
    }

    private ListView findListView(View parent) {
        if (parent instanceof ListView) {
            return (ListView) parent;
        }
        ShadowViewGroup shadowViewGroup = (ShadowViewGroup) shadowOf(parent);
        for (int i = 0; i < shadowViewGroup.getChildCount(); i++) {
            ListView listView = findListView(shadowViewGroup.getChildAt(i));
            if (listView != null) {
                return listView;
            }
        }
        return null;
    }
}
