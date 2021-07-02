package com.example.SimpleVoiceRecorder.gotov.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SimpleVoiceRecorder.R;
import com.example.SimpleVoiceRecorder.gotov.Adapter.MyRecycleAdapter;
import com.example.SimpleVoiceRecorder.gotov.Adapter.RecyclerViewClickListener;
import com.example.SimpleVoiceRecorder.gotov.Model.DeleteFileSound;
import com.example.SimpleVoiceRecorder.gotov.Model.Sound;
import com.example.SimpleVoiceRecorder.gotov.ViewModel.SoundViewModel;

import java.util.List;


public class SaveFragment extends Fragment implements RecyclerViewClickListener {

    MyRecycleAdapter adapter;
    final ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Toast.makeText(getActivity(), "Запись удаленна", Toast.LENGTH_LONG).show();
            DeleteFileSound deleteFileSound = new DeleteFileSound();
            deleteFileSound.deleteFileInBd(adapter.getData(viewHolder.getAdapterPosition()));
            deleteFileSound.deleteFile(adapter.getData(viewHolder.getAdapterPosition()).getFilePath());
            adapter.notifyDataSetChanged();
            deleteFileSound=null;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save,
                container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        adapter = new MyRecycleAdapter( this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        SoundViewModel soundViewModel = new ViewModelProvider(this).get(SoundViewModel.class);
        LiveData<List<Sound>> liveData = soundViewModel.getData();
        liveData.observe(getViewLifecycleOwner(), sounds -> adapter.setData(sounds));
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
        return view;
    }
//Starting a player fragment
    @Override
    public void recyclerViewListClicked(View v, int position) {
        PlaySoundFragment playSoundFragment = new PlaySoundFragment();
        playSoundFragment.setData(adapter.getData(position));
        FragmentTransaction transaction = (getActivity())
                .getSupportFragmentManager()
                .beginTransaction();
        playSoundFragment.show(transaction, "play");

    }
}