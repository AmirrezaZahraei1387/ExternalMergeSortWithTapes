import com.github.TapesExMergeSort.TapeSim;
import com.github.TapesExMergeSort.TapeController;
import com.github.TapesExMergeSort.ClockCounter;

public class Main
{
    public static void main(String[] args) throws InterruptedException {

        TapeSim<Integer>[] tapes = new TapeSim[]{
                new TapeSim<>(10, 0, 0.01, 0.02, 0.02),
                new TapeSim<>(10, 0, 0.01, 0.02, 0.02),
                new TapeSim<>(10, 0, 0.01, 0.02, 0.02),
                new TapeSim<>(10, 0, 0.01, 0.02, 0.02)};

        ClockCounter clock = new ClockCounter(4);

        TapeController<Integer> tapeCont = new TapeController<>(tapes, clock);
        clock.start();
        tapeCont.moveCursor(0, 4);
        tapeCont.write(0, 54);
        tapeCont.moveCursor(0, 4);
        tapeCont.moveCursor(2, 3);
        tapeCont.write(2, 78);
        tapeCont.moveCursor(3, 9);
        tapeCont.moveCursor(3, -9);
        clock.flush();
        System.out.println(tapeCont.length(0));
    }
}