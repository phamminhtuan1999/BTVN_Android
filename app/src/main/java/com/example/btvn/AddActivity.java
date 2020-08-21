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
    Button btnAddProduct,btnSave,btnDel;
    EditText edtName, edtPrice;
    ProductDBHelper productDBHelper;
    int id;
    String name,price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        productDBHelper = new ProductDBHelper(AddActivity.this);
        edtName = findViewById(R.id.edt_name_product);
        edtPrice = findViewById(R.id.edt_price_product);
        btnAddProduct = findViewById(R.id.btn_add_product);
        btnSave = findViewById(R.id.btn_save_product);
        btnDel = findViewById(R.id.btn_del_product);
//        getSupportActionBar().setHomeAsUpIndicator();
        Intent intent = getIntent();
        if(intent.hasExtra(KEY_ID)&&
                intent.hasExtra(KEY_NAME)&&
                intent.hasExtra(KEY_PRICE)){
                setTitle("Chỉnh sửa sản phẩm");
                btnAddProduct.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);
                btnDel.setVisibility(View.VISIBLE);
                id = intent.getIntExtra(KEY_ID,-1);
                name = intent.getStringExtra(KEY_NAME);
                price = intent.getStringExtra(KEY_PRICE);
                edtName.setText(name);
                edtPrice.setText(price);
        }
        else{setTitle("Thêm sản phẩm");}

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchInfo();
                productDBHelper.updateProduct(id,name,price);
                MainActivity.adapterProduct.notifyDataSetChanged();
                finish();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDBHelper.deleteProduct(id);
                MainActivity.adapterProduct.notifyDataSetChanged();
                finish();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String productName = edtName.getText().toString();
//                String productPrice = edtPrice.getText().toString();
//                MainActivity.products.add(new Product("10",productName,productPrice));
//                MainActivity.adapterProduct.notifyDataSetChanged();
//                finish();
                fetchInfo();
                productDBHelper.addProduct(name,price);
                MainActivity.adapterProduct.notifyDataSetChanged();
                finish();

            }
        });
    }
    private void fetchInfo(){
        name = edtName.getText().toString().trim();
        price = edtPrice.getText().toString().trim();
        if(name.isEmpty()||price.isEmpty()){
            Toast.makeText(this,"Hãy điền đủ thông tin",Toast.LENGTH_SHORT).show();
            return;
        }
        //        Intent intent = new Intent();
//        intent.putExtra(KEY_NAME,name);
//        intent.putExtra(KEY_PRICE,price);
//        int id = getIntent().getIntExtra(KEY_ID,-1);
//        if(id!=-1){
//            intent.putExtra(KEY_ID,id);
//        }
//        setResult(RESULT_OK,intent);
    }


}