import java.util.concurrent.TimeUnit;


public class Main
{
    public static void main(String[] args) throws InterruptedException {

        ClockCounter clock = new ClockCounter(3);

        clock.start();
        clock.submitDelay(0, 0.1);
        clock.submitDelay(1, 2.9);
        clock.submitDelay(2, 0);

        TimeUnit.SECONDS.sleep(1);

        clock.submitDelay(0, 2.75);
        clock.submitDelay(0, 3);

        System.out.println(clock.flush());
        clock.end();

        clock.start();
        clock.submitDelay(0, 0.1);
        clock.submitDelay(1, 2.9);
        clock.submitDelay(2, 0);

        TimeUnit.SECONDS.sleep(1);

        clock.submitDelay(0, 2.75);
        clock.submitDelay(0, 3);
        System.out.println(clock.flush());
        clock.end();
    }
}