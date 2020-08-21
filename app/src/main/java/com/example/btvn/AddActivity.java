package com.example.btvn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "productName";
    public static final String KEY_PRICE = "productPrice";
    Button btnAddProduct;
    EditText edtName, edtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edtName = findViewById(R.id.edt_name_product);
        edtPrice = findViewById(R.id.edt_price_product);
        btnAddProduct = findViewById(R.id.btn_add_product);
//        getSupportActionBar().setHomeAsUpIndicator();
        Intent intent = getIntent();
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String productName = edtName.getText().toString();
//                String productPrice = edtPrice.getText().toString();
//                MainActivity.products.add(new Product("10",productName,productPrice));
//                MainActivity.adapterProduct.notifyDataSetChanged();
//                finish();
                saveProduct();
            }
        });
    }
    private void saveProduct(){
        String name = edtName.getText().toString().trim();
        String price = edtPrice.getText().toString().trim();
        if(name.isEmpty()||price.isEmpty()){
            Toast.makeText(this,"Hãy điền đủ thông tin",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(KEY_NAME,name);
        intent.putExtra(KEY_PRICE,price);
        int id = getIntent().getIntExtra(KEY_ID,-1);
        if(id!=-1){
            intent.putExtra(KEY_ID,id);
        }
        setResult(RESULT_OK,intent);
        finish();
    }

}