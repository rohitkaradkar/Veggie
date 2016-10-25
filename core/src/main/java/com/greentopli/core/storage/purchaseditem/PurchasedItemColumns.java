package com.greentopli.core.storage.purchaseditem;

import android.net.Uri;
import android.provider.BaseColumns;

import com.greentopli.core.storage.DatabaseProvider;
import com.greentopli.core.storage.product.ProductColumns;
import com.greentopli.core.storage.purchaseditem.PurchasedItemColumns;
import com.greentopli.core.storage.user.UserColumns;

/**
 * Order Information
 */
public class PurchasedItemColumns implements BaseColumns {
    public static final String TABLE_NAME = "purchased_item";
    public static final Uri CONTENT_URI = Uri.parse(DatabaseProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Unique id for purchasing entity
     */
    public static final String PURCHASE_ID = "purchase_id";

    /**
     * Unique id for user
     */
    public static final String USER_ID = "user_id";

    /**
     * product that user is purchasing
     */
    public static final String PRODUCT_ID = "product_id";

    public static final String DATE_REQUESTED = "date_requested";

    public static final String DATE_ACCEPTED = "date_accepted";

    public static final String ACCEPTED = "accepted";

    public static final String COMPLETED = "completed";

    /**
     * volume of purchasing item eg. 6pcs or 250gms
     */
    public static final String VOLUME = "volume";

    /**
     * Total price
     */
    public static final String TOTAL_PRICE = "total_price";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            PURCHASE_ID,
            USER_ID,
            PRODUCT_ID,
            DATE_REQUESTED,
            DATE_ACCEPTED,
            ACCEPTED,
            COMPLETED,
            VOLUME,
            TOTAL_PRICE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(PURCHASE_ID) || c.contains("." + PURCHASE_ID)) return true;
            if (c.equals(USER_ID) || c.contains("." + USER_ID)) return true;
            if (c.equals(PRODUCT_ID) || c.contains("." + PRODUCT_ID)) return true;
            if (c.equals(DATE_REQUESTED) || c.contains("." + DATE_REQUESTED)) return true;
            if (c.equals(DATE_ACCEPTED) || c.contains("." + DATE_ACCEPTED)) return true;
            if (c.equals(ACCEPTED) || c.contains("." + ACCEPTED)) return true;
            if (c.equals(COMPLETED) || c.contains("." + COMPLETED)) return true;
            if (c.equals(VOLUME) || c.contains("." + VOLUME)) return true;
            if (c.equals(TOTAL_PRICE) || c.contains("." + TOTAL_PRICE)) return true;
        }
        return false;
    }

}
