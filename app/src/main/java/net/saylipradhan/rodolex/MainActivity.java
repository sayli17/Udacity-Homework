package net.saylipradhan.rodolex;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CustomAdapter adapter;
    private ArrayList<HashMap<String, String>> data_list;
    private ProgressDialog progressDialog;

    private static String url = "https://s3-us-west-2.amazonaws.com/udacity-mobile-interview/CardData.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        data_list = new ArrayList<>();

        adapter = new CustomAdapter(this, data_list);
        recyclerView.setAdapter(adapter);

        new AsyncFetch().execute();
    }

    private class AsyncFetch extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();

            String jsonStr = httpHandler.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String lastName = jsonObject.getString("lastName");
                        String firstName = jsonObject.getString("firstName");
                        String email = jsonObject.getString("email");
                        String company = jsonObject.getString("company");
                        String startDate = jsonObject.getString("startDate");
                        String bio = jsonObject.getString("bio");
                        String avatar = jsonObject.getString("avatar");

                        HashMap<String, String> details = new HashMap<>();

                        details.put("firstName", firstName);
                        details.put("lastName", lastName);
                        details.put("email", email);
                        details.put("company", company);
                        details.put("startDate", startDate);
                        details.put("bio", bio);
                        details.put("avatar", avatar);

                        data_list.add(details);
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (progressDialog.isShowing())
                progressDialog.dismiss();

            adapter.notifyDataSetChanged();
        }
    }
}

