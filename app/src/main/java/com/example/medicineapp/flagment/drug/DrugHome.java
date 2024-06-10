package com.example.medicineapp.flagment.drug;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medicineapp.R;
import com.example.medicineapp.RoomDB.search.SearchEntity;
import com.example.medicineapp.RoomDB.search.SearchRepository;
import com.example.medicineapp.interfaces.IGetDrug;
import com.example.medicineapp.networks.DrugService;
import com.example.medicineapp.networks.IDrugService;
import com.example.medicineapp.networks.response.drug.Drug;
import com.example.medicineapp.networks.response.drug.Openfda;
import com.example.medicineapp.networks.response.drug.Results;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DrugHome extends Fragment {
    DrugService drugServices;
    AutoCompleteTextView searchInput;
    List<String> listSearch = new ArrayList<String>();
    SearchRepository searchRepo;
    ImageButton searchButton;
    ImageButton searchHistoryButton;
    ArrayAdapter<String> searchAdapter;
    Drug drugInfo;
    TextView brandValue;
    TextView nameValue;
    TextView purposeValue;
    TextView indicationsAndUsageValue;
    TextView warningsValue;
    TextView doNotUseValue;
    LinearLayout drugInforLayout;
    private NavController controller;
    public DrugHome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drug_home, container, false);
        searchInput = rootView.findViewById(R.id.searchDrug);
        searchButton = rootView.findViewById(R.id.searchButton);
        brandValue = rootView.findViewById(R.id.brandValue);
        nameValue = rootView.findViewById(R.id.nameValue);
        purposeValue = rootView.findViewById(R.id.purposeValue);
        indicationsAndUsageValue = rootView.findViewById(R.id.indicationsAndUsageValue);
        warningsValue = rootView.findViewById(R.id.warningsValue);
        doNotUseValue = rootView.findViewById(R.id.doNotUseValue);
        searchHistoryButton = rootView.findViewById(R.id.searchHistoryButton);
        drugInforLayout = rootView.findViewById(R.id.drugInforLayout);
        drugServices  = new DrugService(getActivity());
        searchRepo = new SearchRepository(getActivity());
        searchAdapter  = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listSearch);
        searchInput.setAdapter(searchAdapter);

        List<String> searchList = searchRepo.getAllSearches();
        if (!listSearch.isEmpty()) {
            listSearch.clear();
        }
        if (searchList != null && !searchList.isEmpty()) {
            listSearch.addAll(searchList);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller =
                (NavController) NavHostFragment.findNavController(this);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchValue = searchInput.getText().toString().trim().toLowerCase();
                if(!(searchValue.isEmpty())) {
                    drugServices.searchDrug(searchValue, new IGetDrug() {
                        @Override
                        public void onGetDrug(Drug drug) {
                            drugInfo = drug;
                            Results results = drug.getResults().get(0);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    drugInforLayout.setVisibility(View.VISIBLE);
                                    if(results.getOpenfda().getBrandName() != null) {
                                        brandValue.setText(results.getOpenfda().getBrandName().get(0));
                                    } else {
                                        brandValue.setText("Not specified");
                                    }
                                    if(results.getOpenfda().getGenericName() != null) {
                                        nameValue.setText(results.getOpenfda().getGenericName().get(0));
                                    } else {
                                        nameValue.setText("Not specified");
                                    }
                                    if(results.getPurpose() != null) {
                                        purposeValue.setText(results.getPurpose().get(0));
                                    } else {
                                        purposeValue.setText("Not specified");
                                    }
                                    if(results.getIndicationsAndUsage() != null) {
                                        indicationsAndUsageValue.setText(results.getIndicationsAndUsage().get(0));
                                    } else {
                                        indicationsAndUsageValue.setText("Not specified");
                                    }
                                    if(results.getWarnings() != null) {
                                        warningsValue.setText(results.getWarnings().get(0));
                                    } else {
                                        warningsValue.setText("Not specified");
                                    }
                                    if(results.getDoNotUse() != null) {
                                        doNotUseValue.setText(results.getDoNotUse().get(0));
                                    } else {
                                        doNotUseValue.setText("Not specified");
                                    }
                                }
                            });
                        }
                    });
                    if (!listSearch.contains(searchValue)) {
                        searchRepo.insert(searchValue);
                        listSearch.add(searchValue);
                        searchAdapter.clear();
                        searchAdapter.addAll(listSearch);
                        searchAdapter.notifyDataSetChanged();
                    }
                    searchInput.setText("");
                }
                else {
//                    notifi search not
                }
            }
        });
        searchHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.searchHistory);
            }
        });

    }
}