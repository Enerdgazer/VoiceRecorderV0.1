package com.example.SimpleVoiceRecorder.gotov.Adapter;

import android.view.View;
//interface for processing clicks in the adapter and transmitting data to the fragment
public interface RecyclerViewClickListener {
    void recyclerViewListClicked(View v, int position);
}
