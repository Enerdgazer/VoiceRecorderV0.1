package com.example.SimpleVoiceRecorder.gotov.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SimpleVoiceRecorder.R;
import com.example.SimpleVoiceRecorder.gotov.Model.Sound;

import java.util.ArrayList;
import java.util.List;


public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.ViewHolder> {
    private static RecyclerViewClickListener itemListener;

    List<Sound> soundDataList;


    public MyRecycleAdapter(RecyclerViewClickListener itemListener) {
        this.soundDataList = new ArrayList<>();

        MyRecycleAdapter.itemListener = itemListener;
    }

    @Override
    public int getItemCount() {
        return soundDataList.size();
    }

    public void setData(List<Sound> newData) {
        if (soundDataList != null) {

            soundDataList.clear();
            soundDataList.addAll(newData);
            notifyDataSetChanged();
        } else {
            soundDataList = newData;
        }
    }

    public Sound getData(int position) {
        return soundDataList.get(position);

    }

    @Override
    public MyRecycleAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardsound, parent, false);

        return new ViewHolder(cv);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        CardView cardView = holder.cardView;

        TextView fileName = cardView.findViewById(R.id.file_name);
        TextView fileLength = cardView.findViewById(R.id.file_length);
        TextView fileDateAdded = cardView.findViewById(R.id.file_date_added);
        fileName.setText(soundDataList.get(position).getRecordName());
        fileLength.setText(soundDataList.get(position).getLength());
        fileDateAdded.setText(soundDataList.get(position).getTimeAdded());


    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CardView cardView;


        public ViewHolder(CardView v) {
            super(v);
            this.cardView = v;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());

        }
    }
}
