package com.example.btvn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.btvn.ProductContract.*;

import java.util.ArrayList;
import java.util.List;

public class ProductDBHelper extends SQLiteOpenHelper {
    public static final String TAG = ProductDBHelper.class.getSimpleName();
    public static final String DATABASE_NAME = "productlist.db";
    public static final int DATABASE_VERSION = 1;
    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;
    private static ProductDBHelper instance;

//    public static synchronized ProductDBHelper getInstance(Context context) {
//        if (instance == null) {
//            instance = new ProductDBHelper(context.getApplicationContext());
//        }
//        return instance;
//    }

    public ProductDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PRODUCTLIST_TABLE = "CREATE TABLE " +
                ProductEntry.TABLE_NAME + " (" +
                ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProductEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                ProductEntry.COLUMN_PRICE + " TEXT NOT NULL " +
                ");";
        db.execSQL(SQL_CREATE_PRODUCTLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME);
        onCreate(db);
    }

    public void addProduct(String name, String price) {
        ContentValues cv = new ContentValues();
        cv.put(ProductEntry.COLUMN_NAME, name);
        cv.put(ProductEntry.COLUMN_PRICE, price);
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            mWritableDB.insert(ProductEntry.TABLE_NAME, null, cv);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
    }

    public void deleteProduct(int id) {
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            mWritableDB.delete(ProductEntry.TABLE_NAME,
                    ProductEntry._ID + "= ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d(TAG, "DELETE EXCEPTION! " + e.getMessage());
        }

    }

    public void updateProduct(int id, String name, String price) {
        // New value for one column
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(ProductEntry._ID, id);
            values.put(ProductEntry.COLUMN_NAME, name);
            values.put(ProductEntry.COLUMN_PRICE, price);

            mWritableDB.update(ProductEntry.TABLE_NAME, //table to change
                    values, // new values to insert
                    ProductEntry._ID + " = ?", // selection criteria for row (in this case, the _id column)
                    new String[]{String.valueOf(id)}); //selection args; the actual value of the id

        } catch (Exception e) {
            Log.d(TAG, "UPDATE EXCEPTION! " + e.getMessage());
        }
    }

    public Product query(int position) {
        String query = "SELECT  * FROM " + ProductEntry.TABLE_NAME +
                " ORDER BY " + ProductEntry._ID + " ASC " +
                "LIMIT " + position + ",1";

        Cursor cursor = null;
        Product entry = new Product();

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            entry.setID(cursor.getInt(cursor.getColumnIndex(ProductEntry._ID)));
            entry.setProductName(cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_NAME)));
            entry.setProductPrice(cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRICE)));
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return entry;
        }
    }

    public long count() {
        if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
        return DatabaseUtils.queryNumEntries(mReadableDB, ProductEntry.TABLE_NAME);
    }

//    public List<Product> getAllProducts() {
//        List<Product> productList = new ArrayList<>();
//        db = getReadableDatabase();
//        Cursor c = db.rawQuery("SELECT * FROM " + ProductEntry.TABLE_NAME, null);
//        if (c.moveToFirst()) {
//            do {
//                Product product = new Product();
//                product.setID(c.getInt(c.getColumnIndex(ProductEntry._ID)));
//                product.setProductName(c.getString(c.getColumnIndex(ProductEntry.COLUMN_NAME)));
//                product.setProductPrice(c.getString(c.getColumnIndex(ProductEntry.COLUMN_PRICE)));
//                productList.add(product);
//            }
//            while (c.moveToNext());
//        }
//        c.close();
//        db.close();
//        return productList;
//    }
}
