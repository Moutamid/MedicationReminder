package com.fatma.medicationreminderapp_fatmaalajmi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.fatma.medicationreminderapp_fatmaalajmi.Constants;
import com.fatma.medicationreminderapp_fatmaalajmi.R;
import com.fatma.medicationreminderapp_fatmaalajmi.MainActivity;
import com.fxn.stash.Stash;

public class LanguageFragment extends Fragment {

    public LanguageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        RadioGroup radioGroup = view.findViewById(R.id.radioGroupLanguage);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.toggleEnglish) {
                    Stash.put(Constants.CURRENT_LANGUAGE, Constants.LANGUAGE_ENGLISH);
                } else if (checkedId == R.id.toggleArabic) {
                    Stash.put(Constants.CURRENT_LANGUAGE, Constants.LANGUAGE_ARABIC);
                }
            }
        });

        view.findViewById(R.id.continueBtnLanguage).setOnClickListener(v -> {
            ((MainActivity) requireActivity()).goToAllRemindersFragment();
        });

        return view;
    }

}
