package com.greentopli.core.storage.user;

import android.net.Uri;
import android.provider.BaseColumns;

import com.greentopli.core.storage.DatabaseProvider;
import com.greentopli.core.storage.product.ProductColumns;
import com.greentopli.core.storage.purchaseditem.PurchasedItemColumns;
import com.greentopli.core.storage.user.UserColumns;

/**
 * Order Information
 */
public class UserColumns implements BaseColumns {
    public static final String TABLE_NAME = "user";
    public static final Uri CONTENT_URI = Uri.parse(DatabaseProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String USER_ID = "user_id";

    public static final String EMAIL = "email";

    public static final String NAME = "name";

    public static final String MOBILE_NO = "mobile_no";

    public static final String ADDRESS = "address";

    public static final String PINCODE = "Pincode";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            USER_ID,
            EMAIL,
            NAME,
            MOBILE_NO,
            ADDRESS,
            PINCODE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(USER_ID) || c.contains("." + USER_ID)) return true;
            if (c.equals(EMAIL) || c.contains("." + EMAIL)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(MOBILE_NO) || c.contains("." + MOBILE_NO)) return true;
            if (c.equals(ADDRESS) || c.contains("." + ADDRESS)) return true;
            if (c.equals(PINCODE) || c.contains("." + PINCODE)) return true;
        }
        return false;
    }

}
