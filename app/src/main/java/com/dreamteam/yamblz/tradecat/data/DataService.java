package com.dreamteam.yamblz.tradecat.data;


import com.dreamteam.yamblz.tradecat.data.exception.NotEnoughMoneyException;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class DataService {

    private static final double INIT_CASH = 1000;
    private static final int COINS_COUNT = 3;

    private static volatile DataService dataService;

    private double currentCash = INIT_CASH;
    private boolean isInitialized = false;
    private CoinHolder[] coinHolders = null;


    private DataService() {

    }

    public synchronized void initWithCoins(CoinType[] coinTypes) {
        if (isInitialized) {
            throw new IllegalStateException("DataService has already been initialized");
        } else if (coinTypes == null || coinTypes.length != COINS_COUNT) {
            throw new IllegalStateException("Coin types count must be " + COINS_COUNT);
        } else {
            isInitialized = true;
            coinHolders = new CoinHolder[COINS_COUNT];
            for (int i = 0; i < coinTypes.length; ++i) {
                coinHolders[i] = new CoinHolder(coinTypes[i]);
            }
        }
    }

    public synchronized Observable<Double> getCoinTypeObservable(CoinType coinType) {
       return getCoinHolder(coinType).getValues();
    }

    public synchronized CoinType[] getCurrentTypes() {
        if (!isInitialized)
            throw new IllegalStateException("DataService must be initialized before get");

        CoinType[] result = new CoinType[COINS_COUNT];
        for (int i = 0; i < COINS_COUNT; ++i) {
            result[i] = coinHolders[i].getCoinType();
        }

        return result;
    }

    public synchronized void incrementCoin(CoinType coinType, int count) throws NotEnoughMoneyException{
        currentCash = getCoinHolder(coinType).incrementAndReturnCurrentCash(currentCash, count);
    }

    public synchronized void decrementCoin(CoinType coinType, int count) throws NotEnoughMoneyException {
        currentCash = getCoinHolder(coinType).decrementAndReturnCurrentCash(currentCash, count);
    }

    public synchronized int getCoinCount(CoinType coinType) {
        if (!isInitialized) throw new IllegalStateException("DataService must be initialized before get");
        return getCoinHolder(coinType).getCount();
    }

    // ----------------------------------------- private ------------------------------------------

    private CoinHolder getCoinHolder(CoinType coinType) {
        for (CoinHolder coinHolder : coinHolders) {
            if (coinHolder.getCoinType() == coinType) {
                return coinHolder;
            }
        }
        throw new IllegalStateException("CoinType is not in current values");
    }

    // ------------------------------------------ static ------------------------------------------

    public static DataService getInstance() {
        DataService localInstance = dataService;
        if (localInstance == null) {
            synchronized (DataService.class) {
                localInstance = dataService;
                if (localInstance == null) {
                    dataService = localInstance = new DataService();
                }
            }
        }
        return localInstance;
    }

    // -------------------------------------- inner types -----------------------------------------

    static class CoinHolder {

        private final double D;

        private int count;
        private double currentDelta;
        private Random random = new Random();
        private double cost;
        private CoinType coinType;

        CoinHolder(CoinType coinType) {
            Preconditions.nonNull(coinType);
            this.count = 0;
            this.currentDelta = 0;
            this.coinType = coinType;
            this.cost = coinType.initCost;
            this.D = cost / 10.0;
        }

        int getCount() {
            return count;
        }

        CoinType getCoinType() {
            return coinType;
        }

        Observable<Double> getValues() {
            return Observable.interval(1, TimeUnit.SECONDS)
                .map(time -> onNextRandomChange());
        }

        private synchronized double decrementAndReturnCurrentCash(double currentCash, int count) throws NotEnoughMoneyException {
            if (this.count < count) throw new NotEnoughMoneyException();

            this.count -= count;
            return currentCash + count * cost;
        }

        private synchronized double incrementAndReturnCurrentCash(double currentCash, int count) throws NotEnoughMoneyException {
            if (currentCash - count * cost < 0) throw new NotEnoughMoneyException();

            this.count += count;
            return currentCash - count * cost;
        }

        private synchronized double onNextRandomChange() {
            currentDelta += random.nextGaussian();
            cost += currentDelta * D;
            return cost;
        }

    }

    public enum CoinType {

        BTC(50),
        ETH(20),
        ETC(10),
        LTC(15),
        Gold(35),
        Gasoline(32),
        USD(15),
        RUR(1),
        GPY(10);

        private final double initCost;

        CoinType(double initCost) {
            this.initCost = initCost;
        }

        public double getInitCost() {
            return initCost;
        }

    }

}
