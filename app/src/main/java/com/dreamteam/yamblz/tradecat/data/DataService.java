package com.dreamteam.yamblz.tradecat.data;


import com.dreamteam.yamblz.tradecat.data.exception.CatDeadException;
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
    private Cat cat = null;

    private DataService() {

    }

    public synchronized void init(CoinType[] coinTypes, CatPride catPride) {
        if (isInitialized) {
            throw new IllegalStateException("DataService has already been initialized");
        } else if (coinTypes == null || catPride == null || coinTypes.length != COINS_COUNT) {
            throw new IllegalStateException("Coin types and cat pride count must be " + COINS_COUNT);
        } else {
            isInitialized = true;
            coinHolders = new CoinHolder[COINS_COUNT];
            for (int i = 0; i < coinTypes.length; ++i) {
                coinHolders[i] = new CoinHolder(coinTypes[i]);
            }
            cat = new Cat(catPride);
        }
    }

    public synchronized Observable<Double> getCoinTypeObservable(CoinType coinType) {
        return getCoinHolder(coinType).getCosts();
    }

    public synchronized CoinType[] getCurrentTypes() {
        assetInitialized();

        CoinType[] result = new CoinType[COINS_COUNT];
        for (int i = 0; i < COINS_COUNT; ++i) {
            result[i] = coinHolders[i].getCoinType();
        }

        return result;
    }

    public synchronized void incrementCoin(CoinType coinType, int count) throws NotEnoughMoneyException {
        double previousCash = currentCash;
        currentCash = getCoinHolder(coinType).incrementAndReturnCurrentCash(currentCash, count);

        double difference = currentCash - previousCash;
        if (difference > 0) {
            getCat().addFullness(currentCash - previousCash);
        }
    }

    public synchronized void decrementCoin(CoinType coinType, int count) throws NotEnoughMoneyException {
        currentCash = getCoinHolder(coinType).decrementAndReturnCurrentCash(currentCash, count);
    }

    public synchronized int getCoinCount(CoinType coinType) {
        assetInitialized();
        return getCoinHolder(coinType).getCount();
    }

    public synchronized double getCurrentCash() {
        assetInitialized();
        return currentCash;
    }

    // ----------------------------------------- private ------------------------------------------

    private CoinHolder getCoinHolder(CoinType coinType) {
        assetInitialized();
        for (CoinHolder coinHolder : coinHolders) {
            if (coinHolder.getCoinType() == coinType) {
                return coinHolder;
            }
        }
        throw new IllegalStateException("CoinType is not in current values");
    }

    private Cat getCat() {
        if (cat == null) throw new IllegalStateException("Cat must be non null when getting");
        return cat;
    }

    private void assetInitialized() {
        if (!isInitialized) throw new IllegalStateException("DataService must be initialized before get");
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

    static class Cat {

        private static final double INIT_FULLNESS = 1000.0;

        private final CatPride catPride;
        private final Observable<Double> getFullness;
        private double fullness = INIT_FULLNESS;

        public Cat(CatPride catPride) {
            this.catPride = catPride;
            this.getFullness = Observable.interval(10, TimeUnit.SECONDS)
                .map(time -> onNextEat());
        }

        public Observable<Double> getFullness() {
            return getFullness;
        }

        public synchronized void addFullness(double value) {
            fullness += value;
        }

        public synchronized double onNextEat() throws CatDeadException {
            double newFullness = fullness - catPride.appetite;
            if (newFullness < 0) throw new CatDeadException();
            fullness = newFullness;
            return fullness;
        }

    }

    static class CoinHolder {

        private final double D;
        private final Random random = new Random();
        private final CoinType coinType;
        private final Observable<Double> getCosts;

        private int count;
        private double currentDelta;
        private double cost;

        CoinHolder(CoinType coinType) {
            Preconditions.nonNull(coinType);
            this.count = 0;
            this.currentDelta = 0;
            this.coinType = coinType;
            this.cost = coinType.initCost;
            this.D = cost / 100.0;
            this.getCosts = Observable.interval(1, TimeUnit.SECONDS)
                .map(time -> onNextRandomChange());
        }

        int getCount() {
            return count;
        }

        CoinType getCoinType() {
            return coinType;
        }

        Observable<Double> getCosts() {
            return getCosts;
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
            double newCost = cost + currentDelta * D;
            if (newCost < 0) {
                currentDelta *= -0.1;
                newCost = cost + currentDelta * D;
            }
            cost = newCost;
            return cost;
        }

    }


    public enum CatPride {

        HARD(40),
        MEDIUM(20),
        EASY(10);

        private double appetite;

        CatPride(double appetite) {
            this.appetite = appetite;
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