package com.example.btvn;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public AdapterProduct(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_product, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME));
        String price = mCursor.getString(mCursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRICE));
        String id = mCursor.getString(mCursor.getColumnIndex(ProductContract.ProductEntry._ID));
        holder.tvID.setText("ID: "+id);
        holder.tvName.setText("Tên: "+name);
        holder.tvPrice.setText("Giá: "+price);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView tvID,tvName, tvPrice;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_id_product);
            tvName = itemView.findViewById(R.id.tv_name_product);
            tvPrice = itemView.findViewById(R.id.tv_price_product);
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
