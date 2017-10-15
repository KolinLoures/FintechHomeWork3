package com.example.kolin.fintechhomework3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ResultFragment extends Fragment {

    public static final String TAG = ResultFragment.class.getSimpleName();
    private static final String KEY_FIRST = "key_first";
    private static final String KEY_SECOND = "key_second";
    private static final String KEY_OPERATION = "key_operation";

    public ResultFragment() {
        // Required empty public constructor
    }


    public static ResultFragment newInstance(String fisrt, String second, int operation) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(KEY_FIRST, fisrt);
        args.putString(KEY_SECOND, second);
        args.putInt(KEY_OPERATION, operation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String result = null;

        if (getArguments() != null) {
            String first = getArguments().getString(KEY_FIRST);
            String second = getArguments().getString(KEY_SECOND);
            int operation = getArguments().getInt(KEY_OPERATION);

            result = formatFinalResult(first, second, operation);
        } else
            result = "Check your previous steps!";


        ((TextView) view.findViewById(R.id.result_fragment_text)).setText(result);
    }

    private String formatFinalResult(String first, String second, int operation) {
        double d1 = Double.parseDouble(first);
        double d2 = Double.parseDouble(second);

        double result = 0d;

        switch (operation) {
            case 0:
                result = d1 + d2;
                break;
            case 1:
                result = d1 - d2;
                break;
            case 2:
                result = d1 / d2;
                break;
            case 3:
                result = d1 * d2;
                break;
        }

        return String.format("%.2f", result);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
