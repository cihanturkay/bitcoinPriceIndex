package cihan.samples.bitcoinpriceindex.ui.dashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.NumberFormat;
import java.util.Currency;

import cihan.samples.bitcoinpriceindex.R;
import cihan.samples.bitcoinpriceindex.data.model.CoinHistory;

public class HistoryChartMarker extends MarkerView {

    private TextView valueTextView;
    private TextView timeTextView;
    private MPPointF offset = new MPPointF();
    private String currency;
    NumberFormat format;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public HistoryChartMarker(Context context, int layoutResource) {
        super(context, layoutResource);
        valueTextView = findViewById(R.id.text);
        timeTextView = findViewById(R.id.time);
        format = NumberFormat.getCurrencyInstance();
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        CoinHistory coinHistory = (CoinHistory) e.getData();
        String text = format.format(coinHistory.getAverage());
        valueTextView.setText(text);
        timeTextView.setText(coinHistory.getTime());
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        offset.x = -getWidth() / 2f;
        offset.y = -getHeight();
        return offset;
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        super.draw(canvas, posX, posY);


    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
        format.setCurrency(Currency.getInstance(currency));
    }
}
