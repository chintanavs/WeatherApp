package com.example.csdud.theweatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText cityname;
    public void click(View view){


        Log.i("city",cityname.getText().toString());
        download task =new download();
        try {
            String encode= URLEncoder.encode(cityname.getText().toString(),"UTF-8");
            task.execute("http://api.openweathermap.org/data/2.5/forecast?q="+encode+"&APPID=4cc4215831ad6063d246732c66039834");
            InputMethodManager mng=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            mng.hideSoftInputFromWindow(cityname.getWindowToken(),0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



    }


    public class download extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {


            URL url;
            String result = "";
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char val = (char) data;
                    result += val;
                    data = reader.read();
                }
                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                Log.i("fuck",s);
                JSONObject jsonObject=new JSONObject(s);
                String info= jsonObject.getString("weather");
                Log.i("tree",info);
                JSONArray arr=new JSONArray(info);
                for(int i=0;i<arr.length();i++){

                    JSONObject obj=arr.getJSONObject(i);
                    Log.i("chintan",obj.getString("main"));
                    Log.i("manali",obj.getString("description"));

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityname=(EditText)findViewById(R.id.cityname);
        //ImageView imageView=(ImageView)findViewById(R.id.imageView);


              //http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22
    }//http://api.openweathermap.org/data/2.5/weather?q=London,uk
}
