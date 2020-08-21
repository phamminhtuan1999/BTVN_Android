package com.example.btvn;

import android.view.View;

public class OnProductClickListener implements View.OnClickListener{
/**
 * Instantiated for the Edit and Delete buttons in WordListAdapter.
 */
    int id;
    String name,price;

    public OnProductClickListener(int id, String name,String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void onClick(View v) {
        // Implemented in WordListAdapter
    }
}
