package com.fatma.medicationreminderapp_fatmaalajmi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.fatma.medicationreminderapp_fatmaalajmi.Constants;
import com.fatma.medicationreminderapp_fatmaalajmi.R;
import com.fatma.medicationreminderapp_fatmaalajmi.activities.MainActivity;
import com.fxn.stash.Stash;

public class LanguageFragment extends Fragment {

    public LanguageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        RadioButton englishRadio = view.findViewById(R.id.toggleEnglish);
        RadioButton arabicRadio = view.findViewById(R.id.toggleArabic);

        if (Stash.getString(Constants.CURRENT_LANGUAGE, "en").equals("en")) {
            englishRadio.setChecked(true);
            arabicRadio.setChecked(false);
        } else {
            englishRadio.setChecked(false);
            arabicRadio.setChecked(true);
        }

        RadioGroup radioGroup = view.findViewById(R.id.radioGroupLanguage);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.toggleEnglish) {
                Stash.put(Constants.CURRENT_LANGUAGE, Constants.LANGUAGE_ENGLISH);
            } else if (checkedId == R.id.toggleArabic) {
                Stash.put(Constants.CURRENT_LANGUAGE, Constants.LANGUAGE_ARABIC);
            }
        });

        view.findViewById(R.id.continueBtnLanguage).setOnClickListener(v -> {
            Stash.put(Constants.IS_LANGUAGE_SELECTED, true);
            ((MainActivity) requireActivity()).goToAllRemindersFragment();
            requireActivity().recreate();
        });

        return view;
    }

}
