package com.fatma.medicationreminderapp_fatmaalajmi.fragments;

import static android.view.LayoutInflater.from;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fatma.medicationreminderapp_fatmaalajmi.Constants;
import com.fatma.medicationreminderapp_fatmaalajmi.activities.AddReminderActivity;
import com.fatma.medicationreminderapp_fatmaalajmi.R;
import com.fatma.medicationreminderapp_fatmaalajmi.databinding.FragmentAllRemindersBinding;
import com.fatma.medicationreminderapp_fatmaalajmi.models.ReminderModel;
import com.fxn.stash.Stash;

import java.util.ArrayList;

public class AllRemindersFragment extends Fragment {

    public AllRemindersFragment() {
    }

    private FragmentAllRemindersBinding b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Constants.setAppLocale(requireContext(), Stash.getString(Constants.CURRENT_LANGUAGE, "en"));
        b = FragmentAllRemindersBinding.inflate(from(requireContext()), container, false);

        initRecyclerView();

        b.addReminderFab.setOnClickListener(v -> {
            startActivity(new Intent(requireActivity(), AddReminderActivity.class));
        });

        return b.getRoot();
    }

    private ArrayList<ReminderModel> tasksArrayList = new ArrayList<>();

    private RecyclerView conversationRecyclerView;
    private RecyclerViewAdapterMessages adapter;

    private void initRecyclerView() {

        tasksArrayList = Stash.getArrayList(Constants.REMINDERS_LIST, ReminderModel.class);

        conversationRecyclerView = b.remindersRecyclerview;
        conversationRecyclerView.addItemDecoration(new DividerItemDecoration(conversationRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewAdapterMessages();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setReverseLayout(true);
        conversationRecyclerView.setLayoutManager(linearLayoutManager);
        conversationRecyclerView.setHasFixedSize(true);
        conversationRecyclerView.setNestedScrollingEnabled(false);

        conversationRecyclerView.setAdapter(adapter);

        if (adapter.getItemCount() != 0) {
            b.noDataTextView.setVisibility(View.GONE);
//            chatsRecyclerView.setVisibility(View.VISIBLE);
        }

    }

    private class RecyclerViewAdapterMessages extends RecyclerView.Adapter
            <RecyclerViewAdapterMessages.ViewHolderRightMessage> {

        @NonNull
        @Override
        public ViewHolderRightMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = from(parent.getContext()).inflate(R.layout.layout_reminders, parent, false);
            return new ViewHolderRightMessage(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderRightMessage holder, int position) {
            ReminderModel model = tasksArrayList.get(position);

            holder.title.setText(model.medicationName + getString(R.string.reminder_scheduled_for) + model.reminderTime + getString(R.string.daily));

        }

        @Override
        public int getItemCount() {
            if (tasksArrayList == null)
                return 0;
            return tasksArrayList.size();
        }

        public class ViewHolderRightMessage extends RecyclerView.ViewHolder {

            TextView title;

            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
                title = v.findViewById(R.id.textview_layout);

            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
    }
}
