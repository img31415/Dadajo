package com.example.student.dadajo;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.numetriclabz.numandroidcharts.ChartData;
import com.numetriclabz.numandroidcharts.MultiLineChart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {


    private String title;
    private int page;
    MultiLineChart multiLineChart;
    List<ChartData> InDust;
    List<ChartData> OutDust;
    private Handler mHandler;
    private Thread mThread;


    // newInstance constructor for creating fragment with arguments
    public static SecondFragment newInstance(int page, String title) {
        SecondFragment fragmentSecond = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentSecond.setArguments(args);
        return fragmentSecond;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 1);
        title = getArguments().getString("someTitle");

        InDust = new ArrayList<>();
        OutDust = new ArrayList<>();
        getDustValue();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        multiLineChart = (MultiLineChart) view.findViewById(R.id.chart);
        List<String> h_lables = new ArrayList<>();
        h_lables.add("1");
        h_lables.add("2");
        h_lables.add("3");
        h_lables.add("4");
        h_lables.add("5");
        h_lables.add("6");
        h_lables.add("7");
        h_lables.add("8");
        h_lables.add("9");
        h_lables.add("10");
        h_lables.add("11");
        h_lables.add("12");

        multiLineChart.setHorizontal_label(h_lables);


        showChart();


        /*multiLineChart.setScaleY(1);
        List<ChartData> InDust = new ArrayList<>();

        InDust.add(new ChartData(30f, 0f)); //values.add(new ChartData(y,x));<br />
        InDust.add(new ChartData(20f, 1f));
        InDust.add(new ChartData(20f, 2f));
        InDust.add(new ChartData(5f, 3f));
        InDust.add(new ChartData(9f, 4f));
        InDust.add(new ChartData(8f, 5f));
        InDust.add(new ChartData(6f, 6f));
        InDust.add(new ChartData(7f, 7f));
        InDust.add(new ChartData(10f, 8f));
        InDust.add(new ChartData(11f,9f));
        InDust.add(new ChartData(13f,10f));

        List<ChartData> OutDust = new ArrayList<>();
        OutDust.add(new ChartData(4f, 0f));
        OutDust.add(new ChartData(5f, 1f));
        OutDust.add(new ChartData(6f, 2f));
        OutDust.add(new ChartData(7f, 3f));
        OutDust.add(new ChartData(7f, 4f));
        OutDust.add(new ChartData(9f, 5f));
        OutDust.add(new ChartData(5f, 6f));
        OutDust.add(new ChartData(3f, 7f));
        OutDust.add(new ChartData(5f, 8f));
        OutDust.add(new ChartData(15f,9f));
        OutDust.add(new ChartData(15f,10f));

        List<ChartData> chart = new ArrayList<>();
        chart.add(new ChartData(InDust));
        chart.add(new ChartData(OutDust));

        multiLineChart.setLeft(1);
        multiLineChart.setRight(50);
        //    mScaleFactor = jononoj  Math.max(.1f, Math.min(mScaleFactor, 10.0f));


        //define the lines
        List<String> legends = new ArrayList<>();
        legends.add("In");
        legends.add("Out");
        multiLineChart.setLegends(legends);
        //string value of the x axis
        multiLineChart.setCircleSize(8f);
        multiLineChart.setData(chart);
        multiLineChart.setGesture(true);*/
        return view;
    }

    //한 시간마다 미세먼지 데이터 받아오는 메서드
    public void getDustValue() {

        Timer t = new Timer();

        t.scheduleAtFixedRate(
                new TimerTask()
                {
                    public void run()
                    {
                        try {
                            Response<Queue> inGraph = SensorApi.service.getChartIn().execute(); // 현재 스레드에서 네트워크 작업 요청.
                            Response<Queue> outGraph = SensorApi.service.getChartOut().execute(); //Out 차트 받아오기

                            if (inGraph.code() == 200) {
                                Queue result = inGraph.body();
                                if (result == null) {
                                    //System.out.println("window 가져오기 실패");
                                    Log.d("결과", "Inchart 가져오기 실패");
                                } else {
                                    // System.out.println("window 가져오기 성공");
                                    Log.d("결과", "Inchart 가져오기 성공 " + result);
                                }
                            } else {
                                // System.out.println("에러 코드: "+res.code());
                                Log.d("결과", "에러 코드: " + inGraph.code());
                            }


                            if (outGraph.code() == 200) {
                                Queue result2 = outGraph.body();
                                if (result2 == null) {
                                    //System.out.println("window 가져오기 실패");
                                    Log.d("결과", "Outchart 가져오기 실패");
                                } else {
                                    // System.out.println("window 가져오기 성공");
                                    Log.d("결과", "Outchart 가져오기 성공 " + result2);
                                }
                            } else {
                                // System.out.println("에러 코드: "+res.code());
                                Log.d("결과", "에러 코드: " + outGraph.code());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                0,
                5000);
    };






/*        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {*/
        /*Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Looper.prepare();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable(){

                        @Override
                        public void run(){
                            try{
                                Response<Queue> res = SensorApi.service.getChartIn().execute(); // 현재 스레드에서 네트워크 작업 요청.
                                if (res.code() == 200) {
                                    Queue result = res.body();
                                    if (result == null) {
                                        //System.out.println("window 가져오기 실패");
                                        Log.d("결과", "chart 가져오기 실패");
                                    } else {
                                        // System.out.println("window 가져오기 성공");
                                        Log.d("결과", "chart 가져오기 성공 " + result);
                                    }
                                } else {
                                    // System.out.println("에러 코드: "+res.code());
                                    Log.d("결과", "에러 코드: " + res.code());
                                }
                            }catch(IOException e){
                                    e.printStackTrace();
                            }
                        }
                    }, 5000);




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });*/


             /*   new Thread() {
                    public void run() {
                        try {
                            Response<Queue> res = SensorApi.service.getChartIn().execute(); // 현재 스레드에서 네트워크 작업 요청.
                            if(res.code()==200) {
                                Queue result = res.body();
                                if(result == null) {
                                    //System.out.println("window 가져오기 실패");
                                    Log.d("결과","chart 가져오기 실패");
                                }else {
                                    // System.out.println("window 가져오기 성공");
                                    Log.d("결과","chart 가져오기 성공 " + result);
                                }
                            }else {
                                // System.out.println("에러 코드: "+res.code());
                                Log.d("결과","에러 코드: "+res.code());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();*/
 /*           }
        },5000);
        }
*/


    //한 시간마다 차트 뿌려주는 메서드
    public void showChart(){
        multiLineChart.setScaleY(1);

        InDust.add(new ChartData(30f, 0f)); //values.add(new ChartData(y,x));<br />
        InDust.add(new ChartData(20f, 1f));
        InDust.add(new ChartData(20f, 2f));
        InDust.add(new ChartData(5f, 3f));
        InDust.add(new ChartData(9f, 4f));
        InDust.add(new ChartData(8f, 5f));
        InDust.add(new ChartData(6f, 6f));
        InDust.add(new ChartData(7f, 7f));
        InDust.add(new ChartData(10f, 8f));
        InDust.add(new ChartData(11f,9f));
        InDust.add(new ChartData(13f,10f));

        OutDust.add(new ChartData(4f, 0f));
        OutDust.add(new ChartData(5f, 1f));
        OutDust.add(new ChartData(6f, 2f));
        OutDust.add(new ChartData(7f, 3f));
        OutDust.add(new ChartData(7f, 4f));
        OutDust.add(new ChartData(9f, 5f));
        OutDust.add(new ChartData(5f, 6f));
        OutDust.add(new ChartData(3f, 7f));
        OutDust.add(new ChartData(5f, 8f));
        OutDust.add(new ChartData(15f,9f));
        OutDust.add(new ChartData(15f,10f));

        List<ChartData> chart = new ArrayList<>();
        chart.add(new ChartData(InDust));
        chart.add(new ChartData(OutDust));

        multiLineChart.setLeft(1);
        multiLineChart.setRight(50);
        //    mScaleFactor = jononoj  Math.max(.1f, Math.min(mScaleFactor, 10.0f));


        //define the lines
        List<String> legends = new ArrayList<>();
        legends.add("In");
        legends.add("Out");
        multiLineChart.setLegends(legends);
        //string value of the x axis
        multiLineChart.setCircleSize(8f);
        multiLineChart.setData(chart);
        multiLineChart.setGesture(true);
    }
}