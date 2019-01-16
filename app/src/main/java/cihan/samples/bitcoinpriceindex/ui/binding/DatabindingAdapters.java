package cihan.samples.bitcoinpriceindex.ui.binding;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Currency;

import cihan.samples.bitcoinpriceindex.R;
import cihan.samples.bitcoinpriceindex.data.model.Coin;

public class DatabindingAdapters {


    @BindingAdapter({"value", "currency"})
    public static void setDoubleValue(TextView textView, double value, String currency) {
        if (currency != null) {
            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setCurrency(Currency.getInstance(currency));
            String text = format.format(value);
            textView.setText(text);
        }
    }

    @BindingAdapter({"percentage", "currency"})
    public static void setDoubleValue(TextView textView, Coin coin, String currency) {
        if (coin != null && currency != null) {
            double todayLast = coin.getLast();
            double todayOpen = coin.getTodayOpen();
            float percentage = (float) (todayLast / todayOpen - 1.0);
            float change = (float) (todayOpen * percentage);

            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setCurrency(Currency.getInstance(currency));
            String priceChangeValue = format.format(change);

            String text = String.format("%s (%.2f%%)", priceChangeValue, percentage * 100);
            textView.setText(text);

            if (percentage < 0) {
                textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.red));
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down, 0, 0, 0);
            } else {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up, 0, 0, 0);
                textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.green));
            }
        }

    }
}
