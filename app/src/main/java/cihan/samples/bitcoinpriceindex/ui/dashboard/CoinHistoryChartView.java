package cihan.samples.bitcoinpriceindex.ui.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import cihan.samples.bitcoinpriceindex.R;
import cihan.samples.bitcoinpriceindex.data.model.CoinHistory;

public class CoinHistoryChartView extends LineChart {

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private int periodIndex = 0;
    private List<CoinHistory> historyList;
    private String currency;
    private HistoryChartMarker historyChartMarker;

    public CoinHistoryChartView(Context context) {
        super(context);
    }

    public CoinHistoryChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoinHistoryChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        historyChartMarker = new HistoryChartMarker(getContext(), R.layout.histoy_chart_marker);
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.open_sans_light);

        setNoDataText(".....");
        getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        getXAxis().setDrawGridLines(false);
        getXAxis().setTypeface(typeface);

        getAxisRight().setEnabled(false);

        getAxisLeft().setDrawGridLines(true);
        getAxisLeft().setTypeface(typeface);
        getAxisLeft().setDrawAxisLine(false);

        getLegend().setEnabled(false);
        getDescription().setEnabled(false);
        setExtraOffsets(0, 0, 0, 10);

        getAxisLeft().setGranularityEnabled(true);

        getXAxis().setValueFormatter((value, axis) -> {
            int position = (int) value;
            if (historyList != null && position < historyList.size()) {
                String time = historyList.get((int) value).getTime();

                switch (periodIndex) {
                    case 0:
                        return time.substring(11, 16);
                    case 1:
                        return time.substring(5, 10);
                    case 2:
                        return time.substring(0, 7);
                }
            }
            return "";
        });


        historyChartMarker.setChartView(this);
        setMarker(historyChartMarker);
    }

    public void setCoinData(List<CoinHistory> coinHistoryList) {
        historyList = coinHistoryList;

        if (coinHistoryList == null || coinHistoryList.size() == 0) {
            clear();
            return;
        }


        ArrayList<Entry> values = new ArrayList<>();
        Collections.reverse(coinHistoryList);

        for (int i = 0; i < coinHistoryList.size(); i++) {
            CoinHistory item = coinHistoryList.get(i);
            Entry entry = new Entry(i, (float) item.getAverage());
            entry.setData(item);
            values.add(entry);
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);

        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        setData(data);
        notifyDataSetChanged();
        invalidate();
    }

    public void setPeriodIndex(int periodIndex) {
        this.periodIndex = periodIndex;
    }

    public List<CoinHistory> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<CoinHistory> historyList) {
        this.historyList = historyList;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
        historyChartMarker.setCurrency(currency);

    }
}
