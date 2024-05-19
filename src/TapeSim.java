import java.util.ArrayList;
import java.io.Serializable;


public class TapeSim< T extends Serializable & Comparable<T>> {
    private Object[] records;

    private int size;
    private int cursor;
    final private double delay_per_record_mv;
    final private double delay_per_read;
    final private double delay_per_write;

    public TapeSim(int init_size, int cursor, double delay_per_record_mv, double delay_per_read, double delay_per_write){
        this.size = size;
        this.delay_per_record_mv = delay_per_record_mv;
        this.delay_per_read = delay_per_read;
        this.delay_per_write = delay_per_write;
        this.cursor = cursor;

        records = new Object[size];
        for(int i = 0; i < size; ++i)
            records[i] = new Object();
    }

}
