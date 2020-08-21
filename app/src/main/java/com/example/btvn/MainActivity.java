package com.example.btvn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static com.example.btvn.AddActivity.KEY_ID;
import static com.example.btvn.AddActivity.KEY_NAME;
import static com.example.btvn.AddActivity.KEY_PRICE;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_PRODUCT_CODE = 1;
    public static final int EDIT_PRODUCT_CODE = 2;
    RecyclerView rvProduct;
    RecyclerView.LayoutManager layoutManager;
    static AdapterProduct adapterProduct;
    Button btnAdd, btnDelete;
    static List<Product> products;
    ProductDBHelper productDBHelperInstance;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productDBHelperInstance = ProductDBHelper.getInstance(this);
        database=productDBHelperInstance.getWritableDatabase();
//        products = productDBHelperInstance.getAllProducts();
//        products.add(new Product("1","iPhone","300"));
//        products.add(new Product("2","iPhone","500"));
//        products.add(new Product("3","iPhone","600"));
//        products.add(new Product("4","iPhone","700"));
//        products.add(new Product("5","iPhone","800"));
//        products.add(new Product("6","iPhone","300"));
        rvProduct = findViewById(R.id.rv_product);
        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_del);
        rvProduct.hasFixedSize();
        layoutManager = new LinearLayoutManager(this);
        rvProduct.setLayoutManager(layoutManager);
        adapterProduct = new AdapterProduct(this,getAllItems());
        rvProduct.setAdapter(adapterProduct);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PRODUCT_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(KEY_NAME);
            String price = data.getStringExtra(KEY_PRICE);
            ContentValues cv = new ContentValues();
            cv.put(ProductContract.ProductEntry.COLUMN_NAME,name);
            cv.put(ProductContract.ProductEntry.COLUMN_PRICE,price);
            database.insert(ProductContract.ProductEntry.TABLE_NAME,null,cv);
//            Product product = new Product(name, price);
//            productDBHelperInstance.addProduct(product);

            adapterProduct.swapCursor(getAllItems());
        } else if (requestCode == EDIT_PRODUCT_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(KEY_ID, -1);
            if (id == -1) {
                return;
            }
            String name = data.getStringExtra(KEY_NAME);
            String price = data.getStringExtra(KEY_PRICE);
            Product product = new Product(name, price);
            product.setID(String.valueOf(id));
            productDBHelperInstance.updateProduct(product);
        }
    }

    private Cursor getAllItems() {
        return database.query(
                ProductContract.ProductEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ProductContract.ProductEntry._ID + " DESC"
        );
    }


}