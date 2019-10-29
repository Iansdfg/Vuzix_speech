package com.vuzix.blade.devkit.gestures_sample;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.vuzix.hud.actionmenu.ActionMenuActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Main Activity that extend ActionMenuActivity.
 * This main class provide the basic information monitoring and overriding Gestures and keyboard events.
 * For more information please reference:
 * https://developer.android.com/training/keyboard-input/commands
 * Used Android API Classes:
 * https://developer.android.com/reference/android/view/KeyEvent
 * https://developer.android.com/reference/android/view/MotionEvent
 */
public class MainActivity extends ActionMenuActivity {

    private final String TAG = "VuzixBDK-Gesture_Sample";
    private EditText logArea;
    private Toast mToast;
    private TextView txvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logArea = findViewById(R.id.logArea);
    }

    /*
        Utilize this method to get keyDown events that the application can override.
        The Keycode can be used in a switch case statement to identify the required and desire events.
        https://developer.android.com/training/keyboard-input/commands
        https://developer.android.com/guide/topics/media-apps/mediabuttons
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // You always want to return the super event to have the system actually handle the event the
        // way they want. This is just for showing the available events.
//        return super.onKeyDown(keyCode, event);

//        Log.d(TAG, "Key Code: " + String.valueOf(keyCode) );
//        Log.d(TAG,"Key Event: " + event.toString());
//
//        if(event.getAction() == KeyEvent.ACTION_DOWN)
//        {
//            logArea.setText("Key Code: " + String.valueOf(keyCode));
//            logArea.append("\n Key Event: " + event.toString());
//            showToast("Key Code: " + String.valueOf(keyCode) +
//                    " \n Shortcut Key Event: " + event.toString());
//        }

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    System.out.println(result.get(0));
//                    txvResult.setText(result.get(0));
                }
//                break;
        }
    }

    /*
        This are all Motion events like Gestures.
     */
    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        // You always want to return the super event to have the system actually handle the event the
        // way they want. This is just for showing the available events.
//        return super.dispatchGenericMotionEvent(ev);

        Log.d(TAG,"Generic motion Event: " + ev.toString());

//        logArea.append("\n Generic motion Event: " + ev.toString());

        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        Log.d(TAG,"DispatchKey Event: " + event.toString());

        if (event.getAction() == KeyEvent.ACTION_DOWN){
            logArea.append("\n DispatchKey Event: " + event.toString());
            showToast("DispatchKey Event: " + event.toString());
        }



        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // You always want to return the super event to have the system actually handle the event the
        // way they want. This is just for showing the available events.
//        return super.dispatchTouchEvent(ev);


        Log.d(TAG,"Touch Event: " + ev.toString());

//        logArea.append("\n Touch Event: " + ev.toString());

        return false;
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent ev) {
        // You always want to return the super event to have the system actually handle the event the
        // way they want. This is just for showing the available events.
//        return super.dispatchTrackballEvent(ev);

        Log.d(TAG,"Trackball Event: " + ev.toString());

//        logArea.append("\n Track Event: " + ev.toString());
        return false;
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        // You always want to return the super event to have the system actually handle the event the
        // way they want. This is just for showing the available events.
//        return super.dispatchKeyShortcutEvent(event);

        Log.d(TAG,"Shortcut Key Event: " + event.toString());

        logArea.append("\n Shortcut Key Event: " + event.toString());
        showToast("Shortcut Key Event: " + event.toString());

        return false;
    }

    /**
     * UI Helper Function to show a toast for dynamic content.
     * @param msg Message to display
     */
    private void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        mToast.show();
    }

}
