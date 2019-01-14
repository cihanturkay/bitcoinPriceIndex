package cihan.samples.bitcoinpriceindex.data.model;

public class Coin {

    private double ask;
    private double bid;
    private double last;
    private double high;
    private double low;
    private double volume;
    private double volumePercent;
    private long timestamp;
    private String displaySymbol;

    public Coin() {
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getVolumePercent() {
        return volumePercent;
    }

    public void setVolumePercent(double volumePercent) {
        this.volumePercent = volumePercent;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDisplaySymbol() {
        return displaySymbol;
    }

    public void setDisplaySymbol(String displaySymbol) {
        this.displaySymbol = displaySymbol;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "ask=" + ask +
                ", bid=" + bid +
                ", last=" + last +
                ", high=" + high +
                ", low=" + low +
                ", volume=" + volume +
                ", volumePercent=" + volumePercent +
                ", timestamp=" + timestamp +
                ", displaySymbol='" + displaySymbol + '\'' +
                '}';
    }
}


