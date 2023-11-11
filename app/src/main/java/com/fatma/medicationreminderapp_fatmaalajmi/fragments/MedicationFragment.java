package com.fatma.medicationreminderapp_fatmaalajmi.fragments;

import static android.view.LayoutInflater.from;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fatma.medicationreminderapp_fatmaalajmi.Constants;
import com.fatma.medicationreminderapp_fatmaalajmi.R;
import com.fatma.medicationreminderapp_fatmaalajmi.activities.MedicineDetailActivity;
import com.fatma.medicationreminderapp_fatmaalajmi.brain.MedicinesData;
import com.fatma.medicationreminderapp_fatmaalajmi.databinding.FragmentMedicationBinding;
import com.fatma.medicationreminderapp_fatmaalajmi.models.MedicineModel;
import com.fatma.medicationreminderapp_fatmaalajmi.models.ReminderModel;
import com.fxn.stash.Stash;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MedicationFragment extends Fragment {

    public MedicationFragment() {
    }

    private FragmentMedicationBinding b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = FragmentMedicationBinding.inflate(LayoutInflater.from(requireContext()), container, false);

        initRecyclerView();

        b.nameTextviewCreateProfile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return b.getRoot();
    }

    private ArrayList<MedicineModel> tasksArrayList = MedicinesData.medicineModelArrayList();

    private RecyclerView conversationRecyclerView;
    private RecyclerViewAdapterMessages adapter;

    private void initRecyclerView() {

        conversationRecyclerView = b.medicationRecyclerview;
        conversationRecyclerView.addItemDecoration(new DividerItemDecoration(conversationRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewAdapterMessages();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setReverseLayout(true);
        conversationRecyclerView.setLayoutManager(linearLayoutManager);
        conversationRecyclerView.setHasFixedSize(true);
        conversationRecyclerView.setNestedScrollingEnabled(false);

        conversationRecyclerView.setAdapter(adapter);

        /*if (adapter.getItemCount() != 0) {
            b.noDataTextView.setVisibility(View.GONE);
//            chatsRecyclerView.setVisibility(View.VISIBLE);
        }*/

    }

    private class RecyclerViewAdapterMessages extends RecyclerView.Adapter
            <RecyclerViewAdapterMessages.ViewHolderRightMessage> implements Filterable {

        @NonNull
        @Override
        public RecyclerViewAdapterMessages.ViewHolderRightMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = from(parent.getContext()).inflate(R.layout.layout_reminders, parent, false);
            return new RecyclerViewAdapterMessages.ViewHolderRightMessage(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerViewAdapterMessages.ViewHolderRightMessage holder, int position) {
            MedicineModel model = tasksArrayList.get(position);

            holder.title.setText(model.name + "\n" + model.information);

            holder.parentLayout.setOnClickListener(v -> {
                startActivity(new Intent(requireContext(), MedicineDetailActivity.class)
                        .putExtra(Constants.PARAMS, model.other));
            });
        }

        @Override
        public int getItemCount() {
            if (tasksArrayList == null)
                return 0;
            return tasksArrayList.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String query = constraint.toString().toLowerCase().trim();

                    ArrayList<MedicineModel> filtered = new ArrayList<>();

                    if (query.isEmpty()) {
                        filtered.addAll(tasksArrayList);
                    } else {
                        for (MedicineModel item : tasksArrayList) {
                            if (item.name.toLowerCase().contains(query)) {
                                filtered.add(item);
                            }
                        }
                    }

                    FilterResults results = new FilterResults();
                    results.values = filtered;
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    tasksArrayList.clear();
                    tasksArrayList.addAll((ArrayList<MedicineModel>) results.values);
                    notifyDataSetChanged();
                }
            };
        }

        public class ViewHolderRightMessage extends RecyclerView.ViewHolder {

            TextView title;
            RelativeLayout parentLayout;

            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
                title = v.findViewById(R.id.textview_layout);
                parentLayout = v.findViewById(R.id.parentLayout);

            }
        }

    }

}
