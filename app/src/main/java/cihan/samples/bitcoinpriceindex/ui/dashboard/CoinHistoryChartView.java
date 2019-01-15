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
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.open_sans_light);

        setNoDataText(".....");
        getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        getXAxis().setDrawGridLines(false);
        getXAxis().setTypeface(typeface);

        getAxisRight().setEnabled(false);

        getAxisLeft().setDrawGridLines(true);
        getAxisLeft().setTypeface(typeface);
        getAxisLeft().setDrawAxisLine(false);

        setTouchEnabled(false);
        getLegend().setEnabled(false);
        getDescription().setEnabled(false);
        setExtraOffsets(0, 0, 0, 10);

        getAxisLeft().setGranularityEnabled(true);

//        getXAxis().setValueFormatter(new IAxisValueFormatter() {
//
//            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.ENGLISH);
//
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                long millis = TimeUnit.HOURS.toMillis((long) value);
//                return mFormat.format(new Date(millis));
//            }
//
//        });
    }

    public void setCoinData(List<CoinHistory> coinHistoryList) {
        if (coinHistoryList == null || coinHistoryList.size() == 0)
            return;

        ArrayList<Entry> values = new ArrayList<>();
        Collections.reverse(coinHistoryList);

        for (int i = 0; i < coinHistoryList.size(); i++) {
            CoinHistory item = coinHistoryList.get(i);
            Entry entry = new Entry(i, (float) item.getAverage());
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
}
