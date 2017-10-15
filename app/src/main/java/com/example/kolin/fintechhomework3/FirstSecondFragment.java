package com.example.kolin.fintechhomework3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FirstSecondFragment extends Fragment {

    public static final String TAG = FirstSecondFragment.class.getSimpleName();
    private static final String KEY_TYPE = "key_type";
    private static final String KEY_TEXT = "key_text";

    private EditText editTxtNumber;
    private Button btnOk;

    private TextWatcher textWatcher;

    private OnFirstFragmentListener listener;

    private int currentType;
    private String currentText;

    public interface OnFirstFragmentListener {
        /**
         * @param text returned text from editText
         * @param type current type of Fragment (first or second in MainActivity.class)
         *             1 first - 2 second
         */
        void onTextResultFirstSecond(String text, int type);

        void onClickOkFirstSecond(int type);
    }

    public FirstSecondFragment() {
    }


    /**
     * @param type current type of Fragment (first or second in MainActivity.class)
     *             1 first - 2 second
     */

    public static FirstSecondFragment newInstance(int type) {
        FirstSecondFragment fragment = new FirstSecondFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            currentType = getArguments().getInt(KEY_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_second, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTxtNumber = view.findViewById(R.id.fragment_first_edit_number);
        btnOk = view.findViewById(R.id.fragment_first_btn_ok);


        initEditTextWatcher();
        setupButtonOnClick();

        if (savedInstanceState != null)
            editTxtNumber.setText(
                    (currentText = savedInstanceState.getString(KEY_TEXT)) == null ? "" : currentText);

    }

    private void setupButtonOnClick() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) listener.onClickOkFirstSecond(currentType);
            }
        });
    }

    private void initEditTextWatcher() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (listener != null) {
                    String text = editable.toString();
                    if (!text.isEmpty()) {
                        listener.onTextResultFirstSecond(text, currentType);
                        btnOk.setVisibility(View.VISIBLE);
                    }
                    else {
                        text = null;
                        listener.onTextResultFirstSecond(text, currentType);
                        btnOk.setVisibility(View.INVISIBLE);
                    }

                    currentText = text;
                }
            }
        };

        editTxtNumber.addTextChangedListener(textWatcher);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFirstFragmentListener)
            listener = (OnFirstFragmentListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement OnFirstFragmentListener");
    }

    @Override
    public void onDestroyView() {
        editTxtNumber.removeTextChangedListener(textWatcher);

        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_TEXT, currentText);
    }

    @Override
    public void onDetach() {
        listener = null;

        super.onDetach();
    }
}
