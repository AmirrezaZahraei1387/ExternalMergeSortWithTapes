package com.github.TapesExMergeSort;

import java.io.Serializable;

interface TapeSimInter<T extends Serializable & Comparable<T>>{
    int moveCursor(int records);

    T read();

    void write(T record);
    public int length();
    public int getCursor();
}

interface TapeControllerInter<T extends Serializable & Comparable<T>>{
    public int moveCursor(int which, int records);

    public T read(int which);

    public void write(int which, T record);

    public int length(int which);
    public int getCursor(int which);
}

public class TapeSim< T extends Serializable & Comparable<T>> implements TapeSimInter<T>{

    private static class TapeSimCostCounter{

        private final double delay_on_spin;
        private final double delay_on_read;
        private final double delay_on_write;

        private double currentCost;

        public TapeSimCostCounter(double delay_on_spin, double delay_on_read, double delay_on_write){
            this.delay_on_spin = delay_on_spin;
            this.delay_on_read = delay_on_read;
            this.delay_on_write = delay_on_write;
        }

        public void readCost(int recordCount){
            currentCost =  delay_on_read * recordCount + delay_on_spin * (recordCount - 1);
        }

        public void writeCost(int recordCount){
            currentCost = delay_on_write * recordCount + delay_on_spin * (recordCount - 1);
        }

        public void moveCost(int records){
            currentCost = delay_on_spin * records;
        }

        public double getCurrentCost(){
            return currentCost;
        }
    }

    private Object[] records;
    private int size;
    private int cursor;
    private final TapeSimCostCounter tapeSimCostCounter;

    public TapeSim(int size, int cursor, double delay_on_spin, double delay_on_read, double delay_on_write){
        this.size = size;
        this.cursor = cursor;
        this.tapeSimCostCounter = new TapeSimCostCounter(delay_on_spin, delay_on_read, delay_on_write);

        records = new Object[size];

        for(int i = 0; i < records.length; ++i)
            records[i] = new Object();
    }

    @Override
    public int moveCursor(int records){
        if(cursor + records < size && cursor + records >= 0) {
            cursor += records;
            tapeSimCostCounter.moveCost(Math.abs(records));
            return 0;
        }
        return -1;
    }

    @Override
    public T read(){
        tapeSimCostCounter.readCost(1);
        return (T) records[cursor];
    }

    @Override
    public void write(T record){
        tapeSimCostCounter.writeCost(1);
        records[cursor] = record;
    }

    @Override
    public int getCursor() {
        return cursor;
    }

    @Override
    public int length(){
        return size;
    }

    //public
    double getCurrentCost(){
        return tapeSimCostCounter.getCurrentCost();
    }

    //public int getCursor(){return cursor;}
}
