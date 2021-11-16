package com.pollutionmonitor;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link subPage03#newInstance} factory method to
 * create an instance of this fragment.
 */
public class subPage03 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //    declared global variables
    TextView city_name;
    TextView city_name_aqi;

    JSONObject stateName;
    int arr_length;
    final ArrayList<String> cityNameG = new ArrayList<>(50);
    final ArrayList<String> cityAQIG = new ArrayList<>(50);
    final ArrayList<dataItem> data = new ArrayList<>(50);
    Location location_g ;

    private FusedLocationProviderClient fusedLocationClient;

    final int itemCount = 0;
    private int queueCount = 0;


    public subPage03() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment subPage03.
     */
    // TODO: Rename and change types and number of parameters
    public static subPage03 newInstance(String param1, String param2) {
        subPage03 fragment = new subPage03();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_page03, container, false);

//        hooks
        city_name = view.findViewById(R.id.city_name);
        city_name_aqi = view.findViewById(R.id.city_name_aqi);

//        network queue
        RequestQueue queue = Volley.newRequestQueue(getContext());


//        getting data at user's location.
        String url = "https://api.airvisual.com/v2/nearest_city?key=4ed7c2d0-ba8c-4a3c-a2f1-be2e26506c4d";
        queueCount++;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        System.out.println("Response: " + response.toString() + "\n" + response.getClass().getSimpleName());
//                        try {
//                            String state = response.getJSONObject("data").getString("state");
//                            String aqi_val = response.getJSONObject("data")
//                                    .getJSONObject("current")
//                                    .getJSONObject("pollution")
//                                    .getString("aqius");
//                            city_name.setText(state);
//                            city_name.setTextColor(getResources().getColor(R.color.white));
//                            city_name_aqi.setText(aqi_val + " AQI");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                        try {
                            String state = response.getJSONObject("data").getString("state");
                            if(state.equals("Telangana") ){
                                state = "Andhra Pradesh";
                            }

                            updateLocationData(queue , state , view);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        System.out.println(error.getCause().toString());
                    }
                });

        queue.add(jsonObjectRequest);



//        list of states in india
        String stateNames[] = new String[]{ "Andhra Pradesh",
                "Arunachal Pradesh",
                "Assam",
                "Bihar",
                "Chhattisgarh",
                "Goa",
                "Gujarat",
                "Haryana",
                "Himachal Pradesh",
                "Jammu and Kashmir",
                "Jharkhand",
                "Karnataka",
                "Kerala",
                "Madhya Pradesh",
                "Maharashtra",
                "Meghalaya",
                "Mizoram",
                "Nagaland",
                "Odisha",
                "Punjab",
                "Rajasthan",
                "Sikkim",
                "Tamil Nadu",
                "Telangana",
                "Tripura",
                "Uttarakhand",
                "Uttar Pradesh",
                "West Bengal",
                "Andaman and Nicobar Islands",
                "Chandigarh",
                "Dadra and Nagar Haveli",
                "Daman and Diu",
                "Delhi",
                "Lakshadweep",
                "Puducherry"} ;

//        get aqi values based on the items in list



        for (int i = 0 ; i< stateNames.length ; i++)
        {
            queueCount++;
            url = "https://api.weatherapi.com/v1/current.json?key=9a018894cbc7409987765810210511&q=" + stateNames[i] + "&aqi=yes";
            JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String object = null;
                            String state = null;
                            try {
                                object = response.getJSONObject("current").getJSONObject("air_quality").getString("gb-defra-index");
                                state  = response.getJSONObject("location").getString("region");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            cityNameG.add(state);
                            cityAQIG.add(object);

                            dataItem d = new dataItem(state , Integer.parseInt(object));
                            data.add(d);

                            System.out.println(state +" : " +object);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.fillInStackTrace();
                        }
                    });

            queue.add(jsonObjectRequest2);
        }
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onRequestFinished(Request<Object> request) {
                queueCount--;
                if(queueCount == 0)
                {
                    view.findViewById(R.id.loading).setVisibility(View.GONE);
                    LinearLayout mainLayout0 = (LinearLayout) view.findViewById(R.id.layout3);
                    View myLayout0 = inflater.inflate(R.layout.places_adapter, mainLayout0, false);
                    TextView textView0 = (TextView) myLayout0.findViewById(R.id.tv);
                    TextView textView20 = (TextView) myLayout0.findViewById(R.id.val);
                    textView0.setText("State");
                    textView20.setText("AQI(/10)");
                    mainLayout0.addView(myLayout0);

                    System.out.println("the queue count"+queueCount);
                    postDataObtainedWork();
                    LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.layout3);

                    for (int i = 0; i < data.size(); i++) {
                        View myLayout = inflater.inflate(R.layout.places_adapter, mainLayout, false);
                        TextView textView = (TextView) myLayout.findViewById(R.id.tv);
                        TextView textView2 = (TextView) myLayout.findViewById(R.id.val);

                        dataItem item = data.get(i);
                        if(item.getAqi() <= 3){
                            textView.setTextColor(getResources().getColor(R.color.md_light_green_900));
                            textView2.setTextColor(getResources().getColor(R.color.md_light_green_900));
                        }
                        else if (item.getAqi() <= 7){
                            textView.setTextColor(getResources().getColor(R.color.md_blue_600));
                            textView2.setTextColor(getResources().getColor(R.color.md_blue_600));
                        }
                        else{
                            textView.setTextColor(getResources().getColor(R.color.md_red_900));
                            textView2.setTextColor(getResources().getColor(R.color.md_red_900));
                        }
                        textView.setText(item.getCity());
                        textView2.setText(Integer.toString(item.getAqi()));
                        mainLayout.addView(myLayout);
                    }
//                    TextView textviewAdd = (TextView) view.findViewById(R.id.def_tv);
                }
            }
        });

//        LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.layout3);
//        for (int i = 0; i < stateNames.length; i++) {
//            View myLayout = inflater.inflate(R.layout.places_adapter, mainLayout, false);
//            TextView textView = (TextView) myLayout.findViewById(R.id.tv);
//            TextView textView2 = (TextView) myLayout.findViewById(R.id.val);
//
//            textView.setText(stateNames[i]);
//            mainLayout.addView(myLayout);
//        }
        return view;

    }

    private void updateLocationData(RequestQueue queue, String state , View view) {
        String url = "https://api.weatherapi.com/v1/current.json?key=9a018894cbc7409987765810210511&q=" + state + "&aqi=yes";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String object = null;
                        try {
                            object = response.getJSONObject("current").getJSONObject("air_quality").getString("gb-defra-index");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        city_name.setText(state);
                        city_name.setTextColor(getResources().getColor(R.color.white));
                        city_name_aqi.setText(object + " /10 AQI");
                        if(Integer.parseInt(object) <= 3)
                            view.findViewById(R.id.aqi_good).setVisibility(View.VISIBLE);
                        else if(Integer.parseInt(object) <= 7)
                            view.findViewById(R.id.aqi_avg).setVisibility(View.VISIBLE);
                        else
                            view.findViewById(R.id.aqi_haz).setVisibility(View.VISIBLE);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getCause().toString());
                    }
                });

        queue.add(jsonObjectRequest);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void postDataObtainedWork() {
        System.out.println(cityNameG.size());
        System.out.println(cityAQIG.size());
        data.sort(Comparator.comparingInt(dataItem::getAqi));

    }
}


class dataItem{
    String City;
    int aqi;
    dataItem()
    {

    }
    dataItem(String c , int a)
    {
        City = c; aqi = a;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }
}

