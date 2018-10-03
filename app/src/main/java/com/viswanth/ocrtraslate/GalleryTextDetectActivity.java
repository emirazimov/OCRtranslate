package com.viswanth.ocrtraslate;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.DocumentsContract.Document;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class GalleryTextDetectActivity extends AppCompatActivity implements
        TextToSpeech.OnInitListener {


    View view,viewT;
    ClipboardManager myClipboard;
    String OCRresult;
    TextToSpeech tts;
    TextView OCREditable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_text_detect);


        String path = getIntent().getExtras().getString("path");
        Toast.makeText(this,path,Toast.LENGTH_LONG).show();



        File f = new File(path);
        TessBaseAPI mTess = new TessBaseAPI();
        mTess.init(Environment.getExternalStorageDirectory().getPath(),"eng");
        mTess.setImage(f);
        OCRresult = mTess.getUTF8Text();

        if(OCRresult != null){

            OCREditable = (TextView) findViewById(R.id.textDetectedGallery);
            OCREditable.setText(OCRresult);
        }



        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);




    }




    //COPY TEXT TO CLIPBOARD



    public void copyToClip(View v){

        ClipData myClip = ClipData.newPlainText("text", OCREditable.getText());
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(GalleryTextDetectActivity.this,"Copied to clipboard",Toast.LENGTH_SHORT).show();

    }






    //TRANSLATE





    public void translate(View v){



        final Dialog translateDialog = new Dialog(GalleryTextDetectActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        viewT = inflater.inflate(R.layout.translate_dialog, null);
        translateDialog.setContentView(viewT);

        Window w = translateDialog.getWindow();
        if(w != null) {
            w.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }


        String[] languages = {"Afrikaans","Albanian","Amharic","Arabic","Armenian"
                ,"Azerbaijani","Basque","Belarusian","Bengali","Bosnian","Bulgarian",
                "Catalan","Cebuano","Chichewa","Chinese","Corsican","Croatian","Czech",
                "Danish","Dutch","English","Esperanto","Estonian","Filipino","Finnish",
                "French","Frisian","Galician","Georgian","German","Greek","Gujarati",
                "Haitian Creole","Hausa","Hawaiian","Hebrew","Hindi","Hmong","Hungarian",
                "Icelandic","Igbo","Indonesian","Irish","Italian","Japanese","Javanese",
                "Kannada","Kazadh","Khmer","Korean","Kurdish","Kyrgyz","Lao","Latin","Latvian",
                "Lithuanian","Luxembourgish","Macedonian","Malagasy","Malay","Malayalam",
                "Maltese","Maori","Marathi","Mongolian","Myanmar","Nepali","Norwegian","Pashto",
                "Persian","Polish","Portuguese","Punjabi","Romanian","Russian","Samoan","Scots Gaelic",
                "Serbian","Sesotho","Shona","Sindhi","Sinhala","Slovak","Slovenian","Somali","Spanish",
                "Sundanese","Swahili","Swedish","Tajik","Tamil","Telugu","Thai","Turkish","Ukrainian",
                "Urdu","Uzbek","Vietnamese","Welsh","Xhosa","Yiddish","Yoruba","Zulu"};

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,R.layout.item_translate,languages);

        ListView listView = (ListView) translateDialog.findViewById(R.id.translatetolan);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(GalleryTextDetectActivity.this,TranslateActivity.class);
                intent.putExtra("lang",position);
                intent.putExtra("text",OCRresult);
                startActivity(intent);

            }
        });





        translateDialog.show();

    }





    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {

            } else {

                Toast.makeText(GalleryTextDetectActivity.this,"Language not supported",Toast.LENGTH_SHORT).show();

            }

        } else {

            Toast.makeText(GalleryTextDetectActivity.this,"TextToSpeech is not initialized successfully",Toast.LENGTH_SHORT).show();

        }
    }

}



