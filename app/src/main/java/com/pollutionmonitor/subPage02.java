package com.pollutionmonitor;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static java.util.Arrays.asList;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.pollutionmonitor.helperclass.LinearRegression;
import com.pollutionmonitor.helperclass.MyMarkerView;

import java.util.ArrayList;
import java.util.List;

public class subPage02 extends Fragment implements OnChartValueSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    declaring data globally
    List<Double> co_val = asList(2.5399, 2.5808, 2.6126, 2.6209, 2.6987, 2.7021, 2.7058, 2.7117, 2.7846, 2.7946, 2.8312, 2.8533, 2.8606, 2.8888, 2.9269, 2.9429, 2.9547, 2.9855, 3.0033, 3.0181, 3.0229, 3.0234, 3.061, 3.0625, 3.096, 3.0966, 3.1068, 3.1089, 3.1107, 3.1259, 3.1502, 3.1532, 3.155, 3.1559, 3.2221, 3.2394, 3.2541, 3.2744, 3.3058, 3.3226, 3.3289, 3.3529, 3.3738, 3.3774, 3.3786, 3.5043, 3.5061, 3.5231, 3.5402, 3.5732, 3.5877, 3.5922, 3.6163, 3.6312, 3.6322, 3.6412, 3.6472, 3.6764, 3.6812, 3.6879, 3.6887, 3.7015, 3.7439, 3.7539, 3.7891, 3.822, 3.839, 3.8617, 3.8808, 3.8847, 3.921, 3.9258, 3.9443, 3.9467, 3.958, 3.9758, 3.997, 4.0502, 4.06, 4.068, 4.071, 4.0773, 4.1399, 4.1624, 4.1674, 4.1706, 4.2054, 4.2506, 4.2543, 4.3044, 4.318, 4.3462, 4.3517, 4.3604, 4.3944, 4.4138, 4.4334, 4.4587, 4.4625, 4.4957);
    List<Integer> hc_val_ppm = asList(5087, 5144, 5145, 5148, 5172, 5212, 5313, 5346, 5364, 5429, 5451, 5473, 5477, 5506, 5514, 5556, 5653, 5655, 5668, 5694, 5705, 5736, 5754, 5768, 5788, 5796, 5803, 5862, 5998, 6038, 6040, 6081, 6091, 6101, 6106, 6206, 6239, 6274, 6373, 6413, 6425, 6438, 6459, 6509, 6548, 6609, 6628, 6681, 6682, 6768, 6814, 6822, 6858, 6907, 6908, 6930, 6932, 6968, 6998, 7002, 7045, 7059, 7082, 7086, 7122, 7137, 7170, 7186, 7216, 7231, 7268, 7431, 7433, 7490, 7623, 7641, 7661, 7669, 7739, 7741, 7742, 7760, 7770, 7824, 7841, 7871, 7929, 7933, 7935, 8036, 8052, 8061, 8139, 8202, 8266, 8295, 8309, 8317, 8361, 8370);
    List<Integer> real_num = asList(100,99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83, 82, 81, 80, 79, 78, 77, 76, 75, 74, 73, 72, 71, 70, 69, 68, 67, 66, 65, 64, 63, 62, 61, 60, 59, 58, 57, 56, 55, 54, 53, 52, 51, 50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);


//    charts
    private LineChart chart;
    private LineChart chart2;

    private  FirebaseAuth firebaseAuth;
    private Button  signOut;


    public subPage02() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @author Surya Teja.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment subPage02.
     */
    // TODO: Rename and change types and number of parameters
    public static subPage02 newInstance(String param1, String param2) {
        subPage02 fragment = new subPage02();
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_page02, container, false);

//        code here!!!

////        main linear regression.
//        LinearRegression linearRegression = new LinearRegression();
        TextView textView = view.findViewById(R.id.test);
        textView.setPaintFlags(textView.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        {   // // Chart Style // //
            chart = view.findViewById(R.id.LineChart1);

            // background color
//            chart.setBackgroundColor(Color.WHITE);

            // disable description text
            chart.getDescription().setEnabled(false);

            // enable touch gestures
            chart.setTouchEnabled(true);

            // set listeners
            chart.setOnChartValueSelectedListener(this);
            chart.setDrawGridBackground(true);

            // create marker to display box when values are selected
            MyMarkerView mv = new MyMarkerView(getContext(), R.layout.custom_marker_view);

            // Set the marker to the chart
            mv.setChartView(chart);
            chart.setMarker(mv);

            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
             chart.setScaleXEnabled(true);
             chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            chart.setPinchZoom(true);
        }

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(9000f);
            yAxis.setAxisMinimum(4000f);
        }

        {   // // Create Limit Lines // //
            LimitLine llXAxis = new LimitLine(10f, "Index 10");
            llXAxis.setLineWidth(4f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);
//            llXAxis.setTypeface(tfRegular);

            LimitLine ll1 = new LimitLine(8500f, "Upper Limit");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(10f);
//            ll1.setTypeface(tfRegular);



            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true);
            xAxis.setDrawLimitLinesBehindData(true);

            // add limit lines
            yAxis.addLimitLine(ll1);

            //xAxis.addLimitLine(llXAxis);
        }

        // add data
        // draw points over time
        chart.animateX(1500);
        setData(45, 180);
        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);


//        2nd chart
        {   // // Chart Style // //
            chart2 = view.findViewById(R.id.LineChart2);

            // background color
//            chart.setBackgroundColor(Color.WHITE);

            // disable description text
            chart2.getDescription().setEnabled(false);

            // enable touch gestures
            chart2.setTouchEnabled(true);

            // set listeners
            chart2.setOnChartValueSelectedListener(this);
            chart2.setDrawGridBackground(true);

            // create marker to display box when values are selected
            MyMarkerView mv = new MyMarkerView(getContext(), R.layout.custom_marker_view);

            // Set the marker to the chart
            mv.setChartView(chart2);
            mv.setBackgroundColor(Color.WHITE);
            chart2.setMarker(mv);

            // enable scaling and dragging
            chart2.setDragEnabled(true);
            chart2.setScaleEnabled(true);
            chart2.setScaleXEnabled(true);
            chart2.setScaleYEnabled(true);

            // force pinch zoom along both axis
            chart2.setPinchZoom(true);
        }


        {   // // X-Axis Style // //
            xAxis = chart2.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }


        {   // // Y-Axis Style // //
            yAxis = chart2.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            chart2.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(5f);
            yAxis.setAxisMinimum(2f);
        }

        {   // // Create Limit Lines // //
            LimitLine llXAxis = new LimitLine(10f, "Index 10");
            llXAxis.setLineWidth(4f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);
//            llXAxis.setTypeface(tfRegular);

            LimitLine ll1 = new LimitLine(4.5f, "Upper Limit");
            ll1.setLineColor(Color.WHITE);
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(10f);
//            ll1.setTypeface(tfRegular);



            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true);
            xAxis.setDrawLimitLinesBehindData(true);

            // add limit lines
            yAxis.addLimitLine(ll1);

            //xAxis.addLimitLine(llXAxis);
        }

        // add data
        // draw points over time
        chart2.animateX(1500);
        setData2(45, 180);
        // get the legend (only possible after setting data)
        l = chart2.getLegend();
        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);

        return view;
    }
    public String arrayListToString(List<Double> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public void onValueSelected(Entry entry, Highlight highlight) {

    }

    @Override
    public void onNothingSelected() {

    }
    private void setData(int count, float range ) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < hc_val_ppm.size(); i++) {

            float val = (float) hc_val_ppm.get(i);
            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.img_1)));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "Hydro Carbons(in ppm)");

//            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }


    private void setData2(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < co_val.size(); i++) {

            Double val =  co_val.get(i);
            values.add(new Entry(i, val.floatValue(), getResources().getDrawable(R.drawable.img_1)));
        }

        LineDataSet set1;

        if (chart2.getData() != null &&
                chart2.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart2.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart2.getData().notifyDataChanged();
            chart2.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "Percent of Carbon Monoxide ( CO ) (in %)");

//            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });


            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart2.setData(data);
        }
    }



}



//stacked 2
//Pie Value Lies(For both types)
//Radar chart for safety levels
