package com.greentopli.core.storage.product;

import android.net.Uri;
import android.provider.BaseColumns;

import com.greentopli.core.storage.DatabaseProvider;
import com.greentopli.core.storage.product.ProductColumns;
import com.greentopli.core.storage.purchaseditem.PurchasedItemColumns;
import com.greentopli.core.storage.user.UserColumns;

/**
 * The Product Item wich user is will purchase.
 */
public class ProductColumns implements BaseColumns {
    public static final String TABLE_NAME = "product";
    public static final Uri CONTENT_URI = Uri.parse(DatabaseProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Unique Product Identificaiotn
     */
    public static final String PRODUCT_ID = "product_id";

    /**
     * English name of product
     */
    public static final String NAME_ENGLISH = "name_english";

    /**
     * Hindi English name
     */
    public static final String NAME_HINGLISH = "name_hinglish";

    /**
     * Image Download Url
     */
    public static final String IMAGE_URL = "image_url";

    /**
     * minimum volume that user has to purchase
     */
    public static final String MIN_VOLUME = "min_volume";

    /**
     *  maximum volume that user is allowed to buy
     */
    public static final String MAX_VOLUME = "max_volume";

    /**
     * eg. set 400 gms min user will be to purchase item only in quantity of 400, 800, 1200, 1600, 2000 gms
     */
    public static final String VOLUME_SET = "volume_set";

    public static final String PRICE = "price";

    public static final String TIME = "time";

    public static final String TYPE = "type";

    public static final String VOLUME = "volume";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            PRODUCT_ID,
            NAME_ENGLISH,
            NAME_HINGLISH,
            IMAGE_URL,
            MIN_VOLUME,
            MAX_VOLUME,
            VOLUME_SET,
            PRICE,
            TIME,
            TYPE,
            VOLUME
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(PRODUCT_ID) || c.contains("." + PRODUCT_ID)) return true;
            if (c.equals(NAME_ENGLISH) || c.contains("." + NAME_ENGLISH)) return true;
            if (c.equals(NAME_HINGLISH) || c.contains("." + NAME_HINGLISH)) return true;
            if (c.equals(IMAGE_URL) || c.contains("." + IMAGE_URL)) return true;
            if (c.equals(MIN_VOLUME) || c.contains("." + MIN_VOLUME)) return true;
            if (c.equals(MAX_VOLUME) || c.contains("." + MAX_VOLUME)) return true;
            if (c.equals(VOLUME_SET) || c.contains("." + VOLUME_SET)) return true;
            if (c.equals(PRICE) || c.contains("." + PRICE)) return true;
            if (c.equals(TIME) || c.contains("." + TIME)) return true;
            if (c.equals(TYPE) || c.contains("." + TYPE)) return true;
            if (c.equals(VOLUME) || c.contains("." + VOLUME)) return true;
        }
        return false;
    }

}
