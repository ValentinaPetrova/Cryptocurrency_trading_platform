package com.crypto.traiding.api.dto;

public class KrakenTickerResponse {
    private KrakenTickerResult result;

    public KrakenTickerResult getResult() {
        return result;
    }

    public void setResult(KrakenTickerResult result) {
        this.result = result;
    }

    public static class KrakenTickerResult {
        private KrakenTickerData btcusd;
        private KrakenTickerData ethusd;
        private KrakenTickerData ltcusd;

        public KrakenTickerData btcusd() {
            return btcusd;
        }

        public KrakenTickerData ethusd() {
            return ethusd;
        }

        public KrakenTickerData ltcusd() {
            return ltcusd;
        }
    }

    public static class KrakenTickerData {
        private String[] askPrice;

        public String[] getAskPrice() {
            return askPrice;
        }

        public void setAskPrice(String[] askPrice) {
            this.askPrice = askPrice;
        }
    }
}
