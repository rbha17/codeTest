package com.codingtest.codingtest.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codingtest.codingtest.R;
import com.codingtest.codingtest.custom.HttpHandler;
import com.codingtest.codingtest.custom.data.TransportResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressDialog pDialog;
    ArrayList<TransportResponse> mTransportModesList = new ArrayList<>();
    private Spinner mNameSpinner;
    private TextView mTransportModeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNameSpinner = findViewById(R.id.spinner);
        mTransportModeTextView = findViewById(R.id.tv_transport);

        new GetTransportData().execute();

        findViewById(R.id.btn_navigate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationActivity.this, ShowLocationActivity.class);
                intent.putExtra("Latitude", mTransportModesList.get(mNameSpinner.getSelectedItemPosition()).getLocation().getLatitude());
                intent.putExtra("Longitude", mTransportModesList.get(mNameSpinner.getSelectedItemPosition()).getLocation().getLongitude());
                intent.putExtra("Name", mTransportModesList.get(mNameSpinner.getSelectedItemPosition()).getName());

                startActivity(intent);
            }
        });

        mNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mTransportModesList.get(position).getFromcentral().getCar()!=null)
                mTransportModeTextView.setText("Car : "+mTransportModesList.get(position).getFromcentral().getCar());
                if(mTransportModesList.get(position).getFromcentral().getTrain()!=null) {
                    mTransportModeTextView.setText(mTransportModeTextView.getText() + "\n\n" + "Train : " +
                            mTransportModesList.get(position).getFromcentral().getCar());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetTransportData extends AsyncTask<Void, Void, ArrayList<TransportResponse>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(NavigationActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected ArrayList<TransportResponse> doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String url = "http://express-it.optusnet.com.au/sample.json";
            // Making a request to url and getting response
            String response = sh.makeServiceCall(url);
            Log.e("Transport Modes", "Response from url: " + response);

            Gson gson = new Gson();
            TransportResponse transportModesResponse = null;

            try {
                JSONArray transportArray = new JSONArray(response);
                for (int i = 0; i < transportArray.length(); i++) {
                    transportModesResponse = gson.fromJson(transportArray.get(i).toString(), TransportResponse.class);
                    mTransportModesList.add(transportModesResponse);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mTransportModesList;

        }

        @Override
        protected void onPostExecute(ArrayList<TransportResponse> result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            if(result!=null) {
                List<String> spinnerArray = new ArrayList<>();
                for (int i = 0; i < result.size(); i++) {
                    spinnerArray.add(result.get(i).getName());
                }
                mNameSpinner.setAdapter(new ArrayAdapter<String>(NavigationActivity.this,android.R.layout.simple_spinner_item,spinnerArray));

            }else{
                Toast.makeText(NavigationActivity.this,"Something went wrong. Please try again later.",Toast.LENGTH_LONG).show();
            }

        }

    }
}
