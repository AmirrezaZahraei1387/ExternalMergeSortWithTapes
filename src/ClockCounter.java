import java.time.Duration;
import java.time.Instant;

class ClockNotActivatedException extends RuntimeException {
    public ClockNotActivatedException(String message){
        super(message);
    }
}

class PurePointClock{
    private Instant start_point;    // defines the start point of time

    private boolean isActive = false; // weather the clock is active or not

    public PurePointClock(){}

    public void activate(){
        isActive = true;
        start_point = Instant.now();
    }

    public double now(){
        if(isActive)
            return Duration.between(start_point, Instant.now()).toSeconds();
        throw new ClockNotActivatedException("can not use an unactivated clock.");
    }

    public void finish(){isActive = false;}
}

public class ClockCounter {

    private static class TapeTimeInfo{

        public double start;    // start time of the job

        public double length;   // duration of the job

        public boolean isActive = false;    // indicates if the tape is activated or not

        public boolean isFinished(double now){return (start + length) <= now;}

        public void activate(){isActive = true;}

        public void deactivate(){isActive = false;}

        public double left(double now){
            if(!isFinished(now)) return (start + length) - now;
            return 0;
        }
    }

    private TapeTimeInfo[] timeline;

    private PurePointClock clock = new PurePointClock();

    private boolean activated_l = false;

    private double clock_offset = 0;

    public ClockCounter(int tapeCount){
        this.timeline = new TapeTimeInfo[tapeCount];
        for(int i = 0; i < timeline.length; ++i)
            timeline[i] = new TapeTimeInfo();
        stopAll();
    }

    public void start(){
        clock.activate();
        activated_l = true;
    }

    public double getCurrentTime(){
        checkClockActivation();
        return current();
    }

    public double end(){
        checkClockActivation();
        double currentTime = current();

        int max_index;
        int i;

        for(i = 0; i < timeline.length; ++i) if(timeline[i].isActive) break;

        max_index = i;

        for(++i; i < timeline.length; ++i)
            if(timeline[i].isActive)
                if(timeline[max_index].left(currentTime) < timeline[i].left(currentTime))
                    max_index = i;

        clock_offset += (max_index < timeline.length ? timeline[max_index].left(currentTime): 0.0);

        currentTime = current();
        eraseFinishedJobs(currentTime);
        stopAll();
        return currentTime;
    }

    public void submitDelay(int which, double length){
        checkClockActivation();
        double currentTime = current();
        eraseFinishedJobs(current());

        if(timeline[which].isActive){
            clock_offset += timeline[which].left(currentTime);
        }
        timeline[which].length = length;
        timeline[which].start = currentTime;
        timeline[which].activate();
    }

    private double current(){
        return clock.now() + clock_offset;
    }

    private void eraseFinishedJobs(double now){

        for(TapeTimeInfo tape: timeline){
            if (tape.isFinished(now) && tape.isActive)
                tape.deactivate();
        }

    }

    private void stopAll(){
        clock.finish();

        for(TapeTimeInfo tape: timeline)
            tape.deactivate();
        activated_l = false;
        clock_offset = 0;
    }

    private void checkClockActivation(){
        if(!activated_l)
            throw new ClockNotActivatedException("can not use an unactivated clock.");
    }
}
