package com.example.medicineapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicineapp.R;
import com.example.medicineapp.RoomDB.search.SearchEntity;
import com.example.medicineapp.interfaces.IDeleteSearch;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.SearchHolderView> {
    public List<SearchEntity> listSearch;
    private IDeleteSearch iDeleteSearch;
    public SearchHistoryAdapter(List<SearchEntity> listSearch, IDeleteSearch iDeleteSearch) {
        this.listSearch = listSearch;
        this.iDeleteSearch = iDeleteSearch;
    }
    @NonNull
    @Override
    public SearchHistoryAdapter.SearchHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_history_item, parent, false);
        return new SearchHistoryAdapter.SearchHolderView(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHistoryAdapter.SearchHolderView searchHolder, int position) {
        searchHolder.searchLabel.setText(listSearch.get(position).text);
        searchHolder.deleteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iDeleteSearch != null) {
                    Integer id = listSearch.get(position).id;
                    iDeleteSearch.onDeleteClick(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSearch.size();
    }

    public static class SearchHolderView extends RecyclerView.ViewHolder {
        public View row;
        public TextView searchLabel;
        public ImageButton deleteSearch;
        public SearchHolderView(View row) {
            super(row);
            this.row = row;
            searchLabel = row.findViewById(R.id.searchLabel);
            deleteSearch = row.findViewById(R.id.deleteSearch);
        }
    }
}
