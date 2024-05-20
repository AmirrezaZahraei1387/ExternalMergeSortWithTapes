package com.github.TapesExMergeSort;

import java.io.Serializable;


public class TapeController <T extends Serializable & Comparable<T>> implements TapeControllerInter<T>{
    private TapeSim<T>[] tapes;
    private ClockCounter clock;

    public TapeController(TapeSim<T>[] tapes, ClockCounter clock){

        if(tapes.length != clock.length())
            throw new IndexOutOfBoundsException("the number of delays in clock is not equal to the number of tapes.");

        this.tapes = tapes;
        this.clock = clock;
    }

    @Override
    public int moveCursor(int which, int records) {
        int status = tapes[which].moveCursor(records);
        clock.submitDelay(which, tapes[which].getCurrentCost());
        return status;
    }

    @Override
    public T read(int which) {
        T temp = tapes[which].read();
        clock.submitDelay(which, tapes[which].getCurrentCost());
        return temp;
    }

    @Override
    public void write(int which, T record) {
        tapes[which].write(record);
        clock.submitDelay(which, tapes[which].getCurrentCost());
    }

    @Override
    public int length(int which) {return tapes[which].length();}

    @Override
    public int getCursor(int which) {
        return tapes[which].getCursor();
    }

    public int length(){return tapes.length;}
}
