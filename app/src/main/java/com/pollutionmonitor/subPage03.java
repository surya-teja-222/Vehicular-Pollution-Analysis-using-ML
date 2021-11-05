package com.pollutionmonitor;

import android.os.Bundle;

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
import com.google.firebase.database.annotations.NotNull;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


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

        RequestQueue queue = Volley.newRequestQueue(getContext());

//        getting data at user's location.
        String url = "https://api.airvisual.com/v2/nearest_city?key=4ed7c2d0-ba8c-4a3c-a2f1-be2e26506c4d";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString() + "\n" + response.getClass().getSimpleName());
                        try {
                            String state = response.getJSONObject("data").getString("state");
                            String aqi_val = response.getJSONObject("data")
                                    .getJSONObject("current")
                                    .getJSONObject("pollution")
                                    .getString("aqius");
                            city_name.setText(state);
                            city_name.setTextColor(getResources().getColor(R.color.white));
                            city_name_aqi.setText(aqi_val + " AQI");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getCause().toString());
                    }
                });

        queue.add(jsonObjectRequest);

//        getting the list of supported states in india
//        url = "https://api.airvisual.com/v2/states?country=india&key=4ed7c2d0-ba8c-4a3c-a2f1-be2e26506c4d";
//        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        stateName = response;
//                        try {
//                            JSONArray array = (JSONArray) stateName.getJSONArray("data");
//                            int array_len = array.length();
//                            ArrayList<String> stateNames = new ArrayList<>(array_len);
//                            ArrayList<String> stateNames_AQI = new ArrayList<>(array_len);
//                            for (int i = 0; i < array_len; i++) {
//                                JSONObject innerObj = array.getJSONObject(i);
//                                for (Iterator it = innerObj.keys(); it.hasNext(); ) {
//                                    String key = (String) it.next();
//                                    System.out.println(innerObj.get(key));
//                                    stateNames.add((String) innerObj.get(key));
//                                }
//                            }
//
//
//
////                            for (int i = 0; i < array_len; i++) {
//////                                initially get supported cities. then find aqi of that city.
////                                String url = "https://api.weatherapi.com/v1/current.json?key=9a018894cbc7409987765810210511&q=" + stateNames.get(i) + "&aqi=yes";
////                                if(i == 10 || i == 14) {
////                                    url = "https://api.weatherapi.com/v1/current.json?key=9a018894cbc7409987765810210511&q=" + stateNames.get(0) + "&aqi=yes";
////                                }
////
////                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
////                                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
////                                            @Override
////                                            public void onResponse(JSONObject response) {
////                                                String object = null;
////                                                try {
////                                                    object = response.getJSONObject("current").getJSONObject("air_quality").getString("gb-defra-index");
////                                                } catch (JSONException e) {
////                                                    e.printStackTrace();
////                                                }
////                                                stateNames_AQI.add(object);
////                                            }
////                                        }, new Response.ErrorListener() {
////
////                                            @Override
////                                            public void onErrorResponse(VolleyError error) {
////                                                System.out.println(error.getCause().toString());
////                                            }
////                                        });
////
////                                queue.add(jsonObjectRequest);
////                            }
////                            LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.layout3);
//////                            int array_len = stateNames.size();
////                            for (int i = 0; i < array_len; i++) {
////                                View myLayout = inflater.inflate(R.layout.places_adapter, mainLayout, false);
////                                TextView textView = (TextView) myLayout.findViewById(R.id.tv);
////                                TextView textView2 = (TextView) myLayout.findViewById(R.id.val);
////
////                                textView.setText(stateNames.get(i));
////                                textView2.setText(stateNames_AQI.get(i));
////                                mainLayout.addView(myLayout);
////                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        System.out.println(error.getCause().toString());
//
//                    }
//                });
//        queue.add(jsonObjectRequest2);




//        LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.layout3);
//        for (int i = 0 ; i<10 ; i++)
//        {
//            LayoutInflater inflater2 = getLayoutInflater();
//            View myLayout = inflater.inflate(R.layout.places_adapter, mainLayout, false);
//            TextView textView = (TextView) myLayout.findViewById(R.id.tv);
//            textView.setText("New Layout " + i);
//            mainLayout.addView(myLayout);
//        }

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
        LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.layout3);
        View myLayout = inflater.inflate(R.layout.places_adapter, mainLayout, false);
        TextView textView = (TextView) myLayout.findViewById(R.id.tv);
        TextView textView2 = (TextView) myLayout.findViewById(R.id.val);

        textView.setText("Location");
        textView2.setText("AQI(/10)");
        mainLayout.addView(myLayout);
        for (int i = 0 ; i< stateNames.length ; i++)
        {
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
                            System.out.println(state +" : " +object);
                            View myLayout = inflater.inflate(R.layout.places_adapter, mainLayout, false);
                            TextView textView = (TextView) myLayout.findViewById(R.id.tv);
                            TextView textView2 = (TextView) myLayout.findViewById(R.id.val);

                            textView.setText(state);
                            textView2.setText(object);
                            mainLayout.addView(myLayout);

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.fillInStackTrace();
                        }
                    });

            queue.add(jsonObjectRequest2);
        }



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
}

