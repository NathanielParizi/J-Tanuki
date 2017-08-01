package com.example.go.j_tanuki;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{



    private TextToSpeech tts;
    private Button speak;
    private EditText enterText;
    private Button getHTML;

    private TextView HTML;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HTML = (TextView) findViewById(R.id.HTML);
        getHTML = (Button) findViewById(R.id.getHTML);
        enterText = (EditText) findViewById(R.id.enterText);


        tts = new TextToSpeech(this,this);

        enterText = (EditText) findViewById(R.id.enterText);
        speak = (Button) findViewById(R.id.SPEAKBUTTON);


        // parse HTML with Jsoup button
        getHTML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String vocab = enterText.getText().toString();


                new scrape().execute();

            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOutNow();

            }
        });
    }



// Async Task for jSoup parser

    public class scrape extends AsyncTask<Void, Void, Void>{

        String words;

        @Override
        protected Void doInBackground(Void... params) {


            try {

                Document doc = Jsoup.connect("http://www.japaneseverbconjugator.com/SentenceFinder.asp?txtWord=green&amp;Go=Find+Sentences").get();

              words = doc.text();

            }catch(Exception e){e.printStackTrace();}

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            HTML.setText(words);

        }
    }


// Text To Speech Method
    public void onInit(int text) {

        if (text == TextToSpeech.SUCCESS) {

            int language = tts.setLanguage(Locale.JAPANESE);
            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                speak.setEnabled(true);
                speakOutNow();


            } else {
            }
        } else {
        }

    }

        private void speakOutNow(){

        String text = enterText.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);

            Toast.makeText(getApplicationContext(),"Loading Text To Speech",Toast.LENGTH_LONG).show();


    //Ready to Run

    }
}
