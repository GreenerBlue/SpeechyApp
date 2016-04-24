package com.gears.yashodhan.speechy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Vars
    private static final String TAG = "spchActivity";
    TextView mText;
    TextView spText;
    ArrayList data;
    SpeechRecognizer speechRecog;
    //End of Vars

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.errorText);
        spText = (TextView) findViewById(R.id.speechText);
    }

    public void initVoiceRecog(View v){
        Toast.makeText(this,"Okay, Speak!",Toast.LENGTH_SHORT).show();
        speechRecog = SpeechRecognizer.createSpeechRecognizer(this);

        speechRecog.setRecognitionListener(new listener());


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
        speechRecog.cancel();
        speechRecog.startListening(intent);
//
    }


    public void OnDestroy(){
        super.onDestroy();
        speechRecog.stopListening();
        speechRecog.destroy();
    }
    Context mContext = this;
    public class listener implements RecognitionListener{

        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");

        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");
            Toast.makeText(mContext,"Finished",Toast.LENGTH_SHORT).show();
        }
        public void onError(int error)
        {
            Log.d(TAG,  "error " +  error);


        }
        public void onResults(Bundle results)
        {

            Log.d(TAG, "onResults " + results);
            data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++)
            {
                Log.d(TAG, "result " + data.get(i));
            }
            int s = data.size()-1;
            spText.setText(String.valueOf(data.get(s)));
            if(data.size()!=0) {
                if ( data.get(data.size() - 1).toString().toLowerCase().equals("play music")) {
                    Intent i = new Intent(mContext, musicPlayer.class);
                    mText.setText("starting activity");
                    startActivity(i);
                }
            }
        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
}


