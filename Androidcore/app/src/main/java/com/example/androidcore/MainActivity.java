package com.example.androidcore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snackbar_layout);


        //Displaying a Toast Message
        //Toast - A single feedback about an operation
//        Context context = getApplicationContext();
//        CharSequence text = "Hello toast message!";
//        int duration = Toast.LENGTH_LONG;
//        Toast toast = Toast.makeText(context, text, duration);
//
//
//        //Positioning toast
//        toast.setGravity(Gravity.CENTER,0,0);
//        toast.show();

        //Custom Layout
        //LayoutInflater  is like the hot air use to blow up a balloon.
        LayoutInflater inflater = getLayoutInflater();

        //Blowing up the balloon. The balloon is the view layout
        //first parameter is the layout
        //second parameter is the root view
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.text);
        text.setText("This is a custom toast");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        //Snackbar setup - lightweight feedback about an operation
        Snackbar snackbar;
        snackbar = Snackbar.make(findViewById(R.id.coordinator), "I am a snackbar", Snackbar.LENGTH_LONG);


        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Undo action", Toast.LENGTH_SHORT).show();
            }
        });

        snackbar.setActionTextColor(Color.BLUE);
        snackbar.show();

    }
}