package com.radiantraccon.probe.fragment;


import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.radiantraccon.probe.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {
    private int imageId;
    private String title;
    private String address;
    private String keywordText;
    private String descText;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add, container, false);

        final EditText keywordEditText = view.findViewById(R.id.editText_keyword);
        final EditText descEditText = view.findViewById(R.id.editText_desc);
        final Button submitButton = view.findViewById(R.id.button_submit);
        final Button addressButton = view.findViewById(R.id.button_address);

        Bundle bundle = getArguments();
        if(bundle != null) {
            imageId = bundle.getInt("imageId");
            title = bundle.getString("title");
            address = bundle.getString("address");
            addressButton.setText(title);

            keywordText = bundle.getString("keywordText");
            descText = bundle.getString("descText");
            keywordEditText.setText(keywordText);
            descEditText.setText(descText);
        }

        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("keywordText", keywordEditText.getText().toString());
                bundle.putString("descText", descEditText.getText().toString());
                Navigation.findNavController(view).navigate(R.id.action_addFragment_to_addressFragment, bundle);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = keywordEditText.getText().toString();
                String desc = descEditText.getText().toString();
                Bundle bundle = new Bundle();
                if(!keyword.equals("")) {
                    bundle.putInt("imageId", imageId);
                    bundle.putString("keyword",keyword);
                    bundle.putString("address", address);
                    bundle.putString("desc", desc);
                    Navigation.findNavController(view).navigate(R.id.action_addFragment_to_mainFragment, bundle);
                } else {
                    Context context = getContext();
                    new MaterialAlertDialogBuilder(context)
                            .setMessage(context.getString(R.string.dialog_nullInput))
                            .setPositiveButton(context.getString(R.string.dialog_confirm), null)
                            .show();

                }
            }
        });

        descEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch(actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        if(address != null){
                            submitButton.performClick();
                        } else {
                            addressButton.performClick();
                        }
                        return true;
                }
                return false;
            }
        });
        return view;
    }

}
