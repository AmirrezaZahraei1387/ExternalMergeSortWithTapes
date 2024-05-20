import com.github.TapesExMergeSort.TapeSim;


public class Main
{
    public static void main(String[] args) throws InterruptedException {

        TapeSim<Integer> tape = new TapeSim<>(10, 0, 0.01, 0.02, 0.02);

        tape.moveCursor(3);
        tape.moveCursor(2);

        tape.write(45);

        System.out.println(tape.length());
    }
}