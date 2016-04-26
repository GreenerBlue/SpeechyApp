package com.gears.yashodhan.speechy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    //Vars
    private static final String TAG = "speechActivity";
    TextView mText;
    TextView spText;
    ArrayList data;
    SpeechRecognizer speechRecog;

    private static final HashMap<String,String> regexMatchers;
    static {
        regexMatchers = new HashMap<>();
        regexMatchers.put("Music","\\wlay\\s?musi[ck]");
        regexMatchers.put("Location","where[\\s\\w+]?am\\s?i");
        regexMatchers.put("Timer","set\\s?timer\\s?(for\\s+(\\d*))?");
    }
    HashMap<String,Pattern> speechPatterns = new HashMap<>();
    private static final HashMap<String,Class> listOfActivities;
    static {
        listOfActivities = new HashMap<>();
        listOfActivities.put("MusicPattern",musicPlayer.class);
        listOfActivities.put("LocationPattern",GPSLocatorActivity.class);
        //listOfActivities.put("TimerPattern",);
    }
    Context mContext = this;
    //End of Vars

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.errorText);
        spText = (TextView) findViewById(R.id.speechText);
        speechPatterns.put("MusicPattern",Pattern.compile(regexMatchers.get("Music")));
        speechPatterns.put("LocationPattern",Pattern.compile(regexMatchers.get("Location")));
        speechPatterns.put("TimerPattern",Pattern.compile(regexMatchers.get("Timer")));
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

    @Override
    protected void onResume() {
        super.onResume();
        mText.setText("");
        spText.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecog.stopListening();
        speechRecog.destroy();
    }

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
        public void onEndOfSpeech(){
            Log.d(TAG, "onEndofSpeech");
            Toast.makeText(mContext,"Finished",Toast.LENGTH_SHORT).show();
        }
        public void onError(int error){
            Log.d(TAG,  "error " +  error);


        }
        public void onResults(Bundle results)
        {
            Log.d(TAG, "onResults " + results);
            data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if(data.size()!=0) {
                int lastIndex = data.size()-1;
                String processedString = data.get(lastIndex).toString().toLowerCase();
                spText.setText(String.valueOf(data.get(lastIndex)));

                for(Map.Entry<String,Pattern> entry:speechPatterns.entrySet()){
                    if(entry.getValue().matcher(processedString).find()){
                        Intent i;
                        if(entry.getKey()=="TimerPattern"){
                            Log.d(TAG, String.valueOf(entry.getValue().matcher(processedString).groupCount()));
                            i = new Intent(AlarmClock.ACTION_SET_TIMER);
                            if(entry.getValue().matcher(processedString).groupCount()>0){
                                //i.putExtra(AlarmClock.EXTRA_LENGTH,Integer.getInteger(entry.getValue().matcher(processedString).group(1)));
                            }
                        }
                        else {
                            i = new Intent(mContext, listOfActivities.get(entry.getKey()));
                        }
                        mText.setText("starting activity");
                        startActivity(i);
                        break;
                    }
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


