package com.example.btvn;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btvn.AddActivity.*;

import java.util.List;

import static com.example.btvn.AddActivity.KEY_ID;
import static com.example.btvn.AddActivity.KEY_NAME;
import static com.example.btvn.AddActivity.KEY_PRICE;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private ProductDBHelper mProductDBHelper;

    public AdapterProduct(Context context, ProductDBHelper productDBHelper) {
        mContext = context;
        mProductDBHelper = productDBHelper;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_product, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = mProductDBHelper.query(position);
        int ID = currentProduct.getID();
        String name = currentProduct.getProductName();
        String price = currentProduct.getProductPrice();
//        if (!mCursor.moveToPosition(position)) {
//            return;
//        }
//
//        String name = mCursor.getString(mCursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME));
//        String price = mCursor.getString(mCursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRICE));
//        String id = mCursor.getString(mCursor.getColumnIndex(ProductContract.ProductEntry._ID));
        holder.tvID.setText("ID: "+ ID);
        holder.tvName.setText("Tên: "+ name);
        holder.tvPrice.setText("Giá: "+ price);
        holder.cvProduct.setOnClickListener(new OnProductClickListener(ID,name,price) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,AddActivity.class);
                intent.putExtra(KEY_ID,id);
                intent.putExtra(KEY_NAME,name);
                intent.putExtra(KEY_PRICE,price);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) mProductDBHelper.count();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView tvID,tvName, tvPrice;
        CardView cvProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_id_product);
            tvName = itemView.findViewById(R.id.tv_name_product);
            tvPrice = itemView.findViewById(R.id.tv_price_product);
            cvProduct = itemView.findViewById(R.id.cv_product);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
