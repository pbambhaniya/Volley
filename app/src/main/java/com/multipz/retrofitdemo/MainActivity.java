package com.multipz.retrofitdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements ItemClickListener {
    RecyclerView recyclerview;
    ProgressDialog dialog;
    String param;
    ArrayList<StatusModel> list;
    NewsAdapter newsadapter;
    Context context;
    String string = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        list = new ArrayList<>();
        getApicall();


    }

    private void getApicall() {
        String tag_string_req = "string_req";
        String url = Config.Base_url;
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
//        dialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String status = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");

                    if (status.contentEquals("1")) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            String tbl_language_id = obj.getString("tbl_language_id");
                            String language = obj.getString("language");
                            String status1 = obj.getString("status");


                            if (tbl_language_id.contentEquals(string)) {
                                JSONArray textStatus = obj.getJSONArray("textStatus");
                                for (int j = 0; j < textStatus.length(); j++) {
                                    JSONObject object = textStatus.getJSONObject(j);
                                    StatusModel model = new StatusModel();
                                    model.setTbl_status_id(object.getString("tbl_status_id"));
                                    model.setText_status(object.getString("text_status"));
                                    list.add(model);
                                }
                            }


                        }


                        //aa code thi sorting thaai //

                        //atle k abcd emm line ma gothvay//
                        Collections.sort(list, new Comparator<StatusModel>() {
                            public int compare(StatusModel obj1, StatusModel obj2) {
                                return obj1.getText_status().compareToIgnoreCase(obj2.getText_status()); // To compare string values
                            }
                        });


                        newsadapter = new NewsAdapter(list, context);
                        RecyclerView.LayoutManager mLayoutManagers = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        recyclerview.setLayoutManager(mLayoutManagers);
                        recyclerview.setItemAnimator(new DefaultItemAnimator());
                        recyclerview.setAdapter(newsadapter);
                        recyclerview.setNestedScrollingEnabled(false);
                        newsadapter.setClickListener(MainActivity.this);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    JSONObject main = new JSONObject();
                    main.put("action", "langList");
                    param = main.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                params.put("json", param);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }

    @Override
    public void itemClicked(View View, int position) {

    }
}
