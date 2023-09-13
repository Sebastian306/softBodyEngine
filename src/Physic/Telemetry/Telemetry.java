package Physic.Telemetry;

import java.util.Map;
import java.util.TreeMap;

public class Telemetry {

    private Map<String, TimeData> timeUsesInfo = new TreeMap<String, TimeData>();
    public static Telemetry mainTelemetry = new Telemetry();

    public void addTimeInfo(String tut, double time){
        if(!timeUsesInfo.containsKey(tut))
            timeUsesInfo.put(tut, new TimeData());
        try{
            timeUsesInfo.get(tut).addRecord(time);
        }catch (Exception e){

        }
    }

    public Map<String, TimeData> getTimeUsesInfo() {
        return timeUsesInfo;
    }
}
