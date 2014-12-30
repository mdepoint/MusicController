package com.example.dude.musiccontroller;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.InputStream;
import android.widget.Button;
import android.view.View;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.util.Log;
import android.os.AsyncTask;

public class MainActivity extends ActionBarActivity {


    public void OnClickWhiteStripes(View v) {
        new HttpAsyncTask().execute("pandora","12", "");
    }

    public void OnClickGratefulDead(View v) {
        new HttpAsyncTask().execute("pandora","11", "");
    }

    public void OnClickAlternative(View v) {
        new HttpAsyncTask().execute("pandora","13", "");
    }

    public void OnClickClassicRock(View v) {
        new HttpAsyncTask().execute("pandora","3", "");
    }

    public void OnClickQuickMix(View v) {
        new HttpAsyncTask().execute("pandora","8", "");
    }

    public void OnClickPause(View v) {
        new HttpAsyncTask().execute("pandora","", "pause");
    }

    public void OnClickStop(View v) {
        new HttpAsyncTask().execute("pandora","", "stop");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public static String POST(String app, String station, String action){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost("http://192.168.2.50:8100");

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("app", app);

            if( station.length()>0 )
                jsonObject.accumulate("station", station);

            if( action.length()>0 )
                jsonObject.accumulate("action", action);

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
            // 9. receive response as inputStream
           // inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
           // if(inputStream != null)
             //   result = convertInputStreamToString(inputStream);
            //else
              //  result = "Did not work!";

        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg != null)
                Log.d("MIKE", e.getMessage());
            else {
                Log.d("MIKE", "BAD MESSEG");
                Log.d("MIKE", e.toString());
//            Log.d("InputStream", e.getLocalizedMessage());
            }
        }

        // 11. return result
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return POST(params[0], params[1], params[2]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
          //  Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
