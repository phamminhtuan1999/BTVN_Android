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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        productDBHelperInstance = ProductDBHelper.getInstance(this);
        productDBHelperInstance = new ProductDBHelper(this);
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
        adapterProduct = new AdapterProduct(this,productDBHelperInstance);
        rvProduct.setAdapter(adapterProduct);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDBHelperInstance.deleteAll();
                adapterProduct.notifyDataSetChanged();
            }
        });
    }

}