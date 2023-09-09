package Physic.Telemetry;

import java.util.ArrayList;
import java.util.Collections;

public class TimeData {
    private double timeSum;
    private double recordsCount;
    private double maxRecordToColect = 10000;
    private ArrayList<Double> records;

    public TimeData(){
        this.timeSum = 0;
        this.records = new ArrayList<>();
        this.recordsCount = 0;
    }

    public void addRecord(double time){
        if(records.size() < maxRecordToColect)
            records.add(time);
        timeSum += time;
        recordsCount++;
    }

    public double getTimeSum() {
        return timeSum;
    }

    public ArrayList<Double> getRecords() {
        return records;
    }

    public double getMeanTime(){
        return timeSum/recordsCount;
    }

    public double getMaxRecord(){
        return Collections.max(records);
    }

    public double getMinRecord(){
        return Collections.min(records);
    }


}
