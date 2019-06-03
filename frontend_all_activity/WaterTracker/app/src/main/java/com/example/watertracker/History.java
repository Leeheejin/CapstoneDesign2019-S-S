package com.example.watertracker;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class History extends AppCompatActivity {


    public class MyBarDataSet extends BarDataSet {


        public MyBarDataSet(List<BarEntry> yVals, String label) {
            super(yVals, label);
        }

        @Override
        public int getColor(int index) {
            if(getEntryForXIndex(index).getVal() < 100) // less than 100 green
                return mColors.get(0);
            else // greater or equal than 100 red
                return mColors.get(1);
        }

    }
    public class CreateDataSet {
        public ArrayList createXvals(ArrayList datevalues)
        {
            ArrayList<String> xVals = new ArrayList<String>();
            int numberOfDay = datevalues.size();
            int over100 = 0;

            if(numberOfDay<=100) {
                for (int i = 0; i < numberOfDay; i++) {
                    xVals.add(datevalues.get(i).toString());
                }
                for (int i = 0; i < 100 - numberOfDay; i++) {
                    xVals.add("");
                }
            }

            if(numberOfDay>100) {

                over100 = numberOfDay - 100;
                for (int i = 0; i < numberOfDay; i++) {
                    xVals.add(datevalues.get(over100+i).toString());
                }
            }

            return xVals;
        }
        public ArrayList creatVals(ArrayList<Float> values)
        {
            ArrayList<BarEntry> vals = new ArrayList<BarEntry>();

            int numberOfvalue = values.size();

            int over100 = 0;
            if(numberOfvalue>100)
            {
                over100 = numberOfvalue-100;
            }

            for(int i=0; i<numberOfvalue; i++ )
            {
                vals.add(new BarEntry(values.get(over100+i),i));
            }

            return vals;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        BarChart chart = (BarChart) findViewById(R.id.bargraph);


        //TestData
        ArrayList<Float> dummyVals = new ArrayList<Float>();
        dummyVals.add(109.5f);
        dummyVals.add(96.2f);
        dummyVals.add(106.7f);
        dummyVals.add(103.5f);
        dummyVals.add(122.1f);
        dummyVals.add(110.7f);
        dummyVals.add(89.9f);
        dummyVals.add(104.3f);
        dummyVals.add(68.2f);
        dummyVals.add(101.2f);


        ArrayList<String> dummyxVals = new ArrayList<String>();
        dummyxVals.add("5/8");
        dummyxVals.add("5/9");
        dummyxVals.add("5/10");
        dummyxVals.add("5/11");
        dummyxVals.add("5/12");
        dummyxVals.add("5/13");
        dummyxVals.add("5/14");
        dummyxVals.add("5/15");
        dummyxVals.add("5/16");
        dummyxVals.add("5/17");
        //TestDataEnd

        //그래프에 값 입력
        ArrayList<BarEntry> vals = new ArrayList<BarEntry>();
        CreateDataSet cds = new CreateDataSet();
        vals = cds.creatVals(dummyVals);


        //그래프 X축 입력
        ArrayList<String> xVals = new ArrayList<String>();
        xVals = cds.createXvals(dummyxVals);


        MyBarDataSet dataset = new MyBarDataSet(vals,"");
        dataset.setColors(new int[]{ContextCompat.getColor(this, R.color.graph_red),
                                ContextCompat.getColor(this, R.color.graph_blue)});
        dataset.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset.setBarSpacePercent(30f);



        BarData data = new BarData(xVals,dataset);
        chart.setData(data);
        chart.animateY(2000);
        chart.getLegend().setEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getAxisLeft().setAxisMaxValue(150);
        chart.getAxisLeft().setAxisMinValue(0);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        LimitLine lline = new LimitLine(100.0f);
        chart.getAxisLeft().addLimitLine(lline);
        chart.setDescription("");
        chart.setMaxVisibleValueCount(100);


        chart.invalidate();



        int acc_day = 6; //누적 사용일
        int achivement_week = acc_day%7; // 일주일 달성률
        int achivement_30day = acc_day%30; // 30일 달성률
        int achivement_100day = acc_day%100; // 100일 달성률

        ProgressBar progress_week = (ProgressBar) findViewById(R.id.progress_goal_week);
        progress_week.setProgress(achivement_week);
        ProgressBar progress_30day = (ProgressBar) findViewById(R.id.progress_goal_30day);
        progress_30day.setProgress(achivement_30day);
        ProgressBar progress_100day = (ProgressBar) findViewById(R.id.progress_goal_100day);
        progress_100day.setProgress(achivement_100day);

        TextView txt_progress_week = (TextView) findViewById(R.id.txt_goal_week);
        txt_progress_week.setText(String.format("%.2f",((float)achivement_week/7*100)) + "%");
        TextView txt_progress_30day = (TextView) findViewById(R.id.txt_goal_30day);
        txt_progress_30day.setText(String.format("%.2f",((float)achivement_30day/30*100)) + "%");
        TextView txt_progress_100day = (TextView) findViewById(R.id.txt_goal_100day);
        txt_progress_100day.setText(String.format("%.2f",((float)achivement_100day/100*100)) + "%");

    }


}
