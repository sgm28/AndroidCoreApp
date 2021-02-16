package com.example.androidcore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

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

    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snackbar_layout);

        coordinatorLayout = findViewById(R.id.coordinator);



////////////////////////////////////////////////////////////////////
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
 //       LayoutInflater inflater = getLayoutInflater();

        //Blowing up the balloon. The balloon is the view layout
        //first parameter is the layout
        //second parameter is the root view
//        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));
//
//        TextView text = layout.findViewById(R.id.text);
//        text.setText("This is a custom toast");
//
//        Toast toast = new Toast(getApplicationContext());
//        toast.setGravity(Gravity.CENTER, 0,0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();
/////////////////////////////////////////////////////////////////////////

        //Snackbar setup - lightweight feedback about an operation
        //The following code came from https://medium.com/android-beginners/android-snackbar-example-tutorial-a40aae0fc620
        //with slight modification.
        Snackbar snackbar;


    }

    public void simplySnackbar(View view)
    {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.simpleSnackBarMessage, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void snackbarWithAction(View view)
    {
         Snackbar  snackbar= Snackbar.make(coordinatorLayout, R.string.SnackbarWithActionsMessage, Snackbar.LENGTH_SHORT);


        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.UndoActionMessage, Toast.LENGTH_SHORT).show();
            }
        });
        snackbar.show();

    }

    public void customSnackbar(View view)
    {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.CustomSnackbarMessage, Snackbar.LENGTH_SHORT);
        snackbar.setAction("UNDO", new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.UndoActionMessage, Toast.LENGTH_SHORT).show();

            }
        });
        snackbar.setActionTextColor(Color.BLUE);

        View snackbarView = snackbar.getView();
        //snackbar_text is special id associated with a snackbar
        TextView snackbarText = snackbarView.findViewById(R.id.snackbar_text);
        snackbarText.setTextColor(Color.YELLOW);
        snackbar.show();
    }


}