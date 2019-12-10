package com.fycedwin.chicken;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;

    private ArrayList<String>dataNama;
    private ArrayList<String>dataCreator;
    private ArrayList<String>dataReview;
    private ArrayList<String>dataRating;
    private ArrayList<String>dataImage;
    private ArrayList<String>dataIngredients;
    private ArrayList<String>dataIngredients2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dataNama= new ArrayList<>();
        dataCreator= new ArrayList<>();
        dataReview= new ArrayList<>();
        dataRating= new ArrayList<>();
        dataImage= new ArrayList<>();
        dataIngredients= new ArrayList<>();
        dataIngredients2= new ArrayList<>();

        rvView = findViewById(R.id.rv_main);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvView.setHasFixedSize(true);
       // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        int spanCount = 2;
        int spacing = 10;
        boolean includeEdge = true;
        rvView.setLayoutManager(gridLayoutManager);
        rvView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        rvView.setItemAnimator(new DefaultItemAnimator());
        initDataSet();

        adapter = new RecyclerViewAdapter(dataNama,dataCreator,dataReview,dataRating,dataImage,dataIngredients2,this);

    }



    public void updateNewsList(){
        rvView.setAdapter(adapter);

    }



    public void initDataSet() {

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, AppConfig.URL_CALLDATA, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    dataNama.clear();
                    dataCreator.clear();
                    dataImage.clear();
                    dataRating.clear();
                    dataReview.clear();
                    dataIngredients.clear();
                    dataIngredients2.clear();

                    JSONArray jsonArray = response.getJSONArray("recipe");
                    jsonArray= reverseJsonArray(jsonArray);

                    int len = jsonArray.length();
                    for (int i = 0; i < len; i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        dataNama.add(jsonObject.get("name").toString());
                        dataCreator.add(jsonObject.get("creator").toString());
                        dataReview.add(jsonObject.get("review").toString());
                        dataRating.add(jsonObject.get("rating").toString());
                        dataImage.add(jsonObject.get("image").toString());

                        JSONArray ingred = jsonObject.getJSONArray("ingredients");
                        int lengIng = ingred.length();

                        for (int j = 0; j < lengIng; j++) {

                            dataIngredients.add(ingred.get(j).toString());
                        }
                        dataIngredients2.addAll(dataIngredients);

                    }




                    updateNewsList();

                } catch (Exception e) {
                    e.printStackTrace();




                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(getRequest);

    }

    public static JSONArray sortJsonArray(JSONArray array)  {
        List<JSONObject> jsons = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                jsons.add(array.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(jsons, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject lhs, JSONObject rhs) {
                String lid="";
                String rid="";
                try {
                    lid = lhs.getString("rating");
                    rid = rhs.getString("rating");
                }
               catch (Exception e){

               }
                // Here you could parse string id to integer and then compare.
                return lid.compareTo(rid);
            }
        });
        return new JSONArray(jsons);
    }

    public static JSONArray reverseJsonArray(JSONArray array)  {
        List<JSONObject> jsons = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                jsons.add(array.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(jsons, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject lhs, JSONObject rhs) {
                String lid="";
                String rid="";
                try {
                    lid = lhs.getString("rating");
                    rid = rhs.getString("rating");
                }
                catch (Exception e){

                }
                // Here you could parse string id to integer and then compare.
                return lid.compareTo(rid);
            }
        });
        Collections.reverse(jsons);
        return new JSONArray(jsons);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}
