package com.example.kolin.fintechhomework3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;


public class ThirdFragment extends Fragment {

    public static final String TAG = ThirdFragment.class.getSimpleName();

    private OnThirdFragmentListener listener;

    public interface OnThirdFragmentListener {
        /**
         * @param num number of operation (0 +, 1 -, 2 /, 3 *)
         */
        void onCheckOperationThird(int num);

        void onClickOkThird();
    }


    public ThirdFragment() {
        // Required empty public constructor
    }


    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button btnOk = view.findViewById(R.id.fragment_third_btn);
        ((RadioGroup) view.findViewById(R.id.fragment_third_radio_group))
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        int num = -1;
                        switch (i) {
                            case R.id.radio_btn_sum:
                                num = 0;
                                break;
                            case R.id.radio_btn_diff:
                                num = 1;
                                break;
                            case R.id.radio_btn_div:
                                num = 2;
                                break;
                            case R.id.radio_btn_mult:
                                num = 3;
                                break;
                        }

                        if (listener != null)
                            listener.onCheckOperationThird(num);

                        if (num == -1)
                            btnOk.setVisibility(View.INVISIBLE);
                        else
                            btnOk.setVisibility(View.VISIBLE);
                    }
                });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onClickOkThird();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnThirdFragmentListener)
            listener = (OnThirdFragmentListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement OnThirdFragmentListener");
    }

    @Override
    public void onDetach() {
        listener = null;

        super.onDetach();
    }

}
