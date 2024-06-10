package com.example.medicineapp.flagment.drug;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicineapp.MainActivity;
import com.example.medicineapp.R;
import com.example.medicineapp.RoomDB.search.SearchEntity;
import com.example.medicineapp.RoomDB.search.SearchRepository;
import com.example.medicineapp.adapter.SearchHistoryAdapter;
import com.example.medicineapp.flagment.task.TaskHomeDirections;
import com.example.medicineapp.interfaces.IDeleteSearch;
import com.example.medicineapp.interfaces.IFragmentListener;
import com.example.medicineapp.interfaces.INoti;

import java.util.ArrayList;
import java.util.List;

public class SearchHistory extends Fragment {
    private IFragmentListener fragmentListener;
    public SearchHistory() {
    }
    private List<SearchEntity> listSearch = new ArrayList<SearchEntity>();
    SearchHistoryAdapter searchAdapter;
    SearchRepository searchRepo;
    IDeleteSearch iDeleteSearch;
    ImageButton deleteAll;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentListener.onFragmentChanged("fragmentSearchHistory");
        searchRepo = new SearchRepository(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_search_history, container, false);
        RecyclerView listSearchView = (RecyclerView) rootView.findViewById(R.id.listSearch);
        listSearchView.setLayoutManager(new LinearLayoutManager(getActivity()));
        deleteAll = rootView.findViewById(R.id.deleteAll);
        searchAdapter  = new SearchHistoryAdapter(listSearch, new IDeleteSearch() {
            @Override
            public void onDeleteClick(Integer id) {
//                iDeleteSearch.onDeleteClick(id);
                for (SearchEntity search : listSearch) {
                    if (search.id == id) {
                        listSearch.remove(search);
                        searchRepo.delete(search);
                        searchAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure to delete all search history?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchRepo.deleteAll();
                        listSearch.clear();
                        searchAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        listSearchView.setAdapter(searchAdapter);
        listSearchView.setItemAnimator(new DefaultItemAnimator());
        List<SearchEntity> searchList = searchRepo.getListSearches();
        if (!listSearch.isEmpty()) {
            listSearch.clear();
        }
        if (searchList != null && !searchList.isEmpty()) {
            listSearch.addAll(searchList);
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentListener = (IFragmentListener) context;
    }
}
