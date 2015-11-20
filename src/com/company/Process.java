package com.company;

/**
 * Created by leon on 17.10.15.
 */
public class Process {
    private int timeOfrunning;
    private int realrun;
    private int WhenCreared;
    private int id;
    public int WhenEnded;
    public int WhenRealStart;
    public int GetTotalTimeOfRunning()
    {
        return timeOfrunning;
    }
    public int GetWhenCreated()
    {
        return WhenCreared;
    }
    public Process(int t, int w,int i)
    {
        timeOfrunning=t;
        realrun=t;
        WhenCreared=w;
        id=i;
    }
    public int GetId()
    {
        return id;
    }
    public int StepOfWork()
    {
        realrun--;
        if(realrun==0) return -1;
        return realrun;
    }
}
