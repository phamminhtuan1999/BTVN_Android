package com.example.btvn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.btvn.ProductContract.*;

import java.util.ArrayList;
import java.util.List;

public class ProductDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "productlist.db";
    public static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    private static ProductDBHelper instance;

    public static synchronized ProductDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ProductDBHelper(context.getApplicationContext());
        }
        return instance;
    }

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
        this.db = db;
        db.execSQL(SQL_CREATE_PRODUCTLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME);
        onCreate(db);
    }

    void addProduct(Product product) {
        ContentValues cv = new ContentValues();
        cv.put(ProductEntry.COLUMN_NAME, product.getProductName());
        cv.put(ProductEntry.COLUMN_PRICE, product.getProductPrice());
        db.insert(ProductEntry.TABLE_NAME, null, cv);
    }

    void deleteProduct(Product product) {
        db.delete(ProductEntry.TABLE_NAME, ProductEntry._ID + "=" + product.getID(), null);
    }

    void updateProduct(Product product) {
        // New value for one column
        String ID = product.getID();
        String name = product.getProductName();
        String price = product.getProductPrice();
        ContentValues values = new ContentValues();
        values.put(ProductEntry._ID, ID);
        values.put(ProductEntry.COLUMN_NAME, name);
        values.put(ProductEntry.COLUMN_PRICE, price);

    // Which row to update, based on the title
        String selection = ProductEntry._ID + " LIKE ?";
        String[] selectionArgs = {ID};

        int count = db.update(
                ProductEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + ProductEntry.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Product product = new Product();
                product.setID(c.getString(c.getColumnIndex(ProductEntry._ID)));
                product.setProductName(c.getString(c.getColumnIndex(ProductEntry.COLUMN_NAME)));
                product.setProductPrice(c.getString(c.getColumnIndex(ProductEntry.COLUMN_PRICE)));
                productList.add(product);
            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
        return productList;
    }
}
