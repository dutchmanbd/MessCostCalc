package com.example.dutchman.messcostcalc.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutchman.messcostcalc.constants.ConnectionDetector;
import com.example.dutchman.messcostcalc.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    private Context context;

    private TextView tvDevFBLink;
    private TextView tvDevGmailLink;

    public AboutFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context){

        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        tvDevFBLink    = (TextView) view.findViewById(R.id.tvDevFBLink);
        tvDevGmailLink = (TextView) view.findViewById(R.id.tvDevGmailLink);

        tvDevGmailLink.setText("E-mail:zxdmjr@gmail.com");

        ConnectionDetector detector = new ConnectionDetector(context);

        if(detector.isConnectingToInternet()) {
            Linkify.addLinks(tvDevGmailLink, Linkify.EMAIL_ADDRESSES);
            Linkify.addLinks(tvDevFBLink, Linkify.WEB_URLS);
        } else{
            Toast.makeText(context,"Connect your data or wifi",Toast.LENGTH_SHORT).show();
        }


        return view;
    }

}
