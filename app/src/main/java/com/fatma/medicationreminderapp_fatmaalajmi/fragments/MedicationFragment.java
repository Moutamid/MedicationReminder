package com.fatma.medicationreminderapp_fatmaalajmi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.fatma.medicationreminderapp_fatmaalajmi.R;

public class MedicationFragment extends Fragment {

    public MedicationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medication, container, false);
    }

}
