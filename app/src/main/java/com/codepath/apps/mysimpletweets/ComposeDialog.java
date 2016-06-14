package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ssahu6 on 6/6/16.
 */
public class ComposeDialog extends DialogFragment {

    private TextView tvUser;

    public EditText getEtTweet() {
        return etTweet;
    }

    private EditText etTweet;
    private ImageView ivProfile;

    private String textToTweet;

    public String getTextToTweet() {
        return textToTweet;
    }

    private Button btnTweet;
    private Button btnCancel;
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        // Get the layout inflater
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//
//        // Inflate and set the layout for the dialog
//        // Pass null as the parent view because its going in the dialog layout
//
//        builder.setView(inflater.inflate(R.layout.compose, null));
//        return builder.create();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.compose, container);

        // set text to tweet
        etTweet = (EditText)view.findViewById(R.id.etTweet);
        textToTweet = etTweet.getText().toString();
        return view;
    }

    public ComposeDialog(){

    }

    public static ComposeDialog newInstance(String title) {
        ComposeDialog frag = new ComposeDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }
}
