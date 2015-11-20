package com.company;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leon on 17.10.15.
 */
public class ManagerOfProcesses {
    private LinkedList<Process> que=new LinkedList<Process>();
    private LinkedList<Process> wasted=new LinkedList<Process>();
    Process currentProcess=null;
    int time=0;
    public ManagerOfProcesses() {}
    public ManagerOfProcesses(LinkedList<Process> p)
    {
        que=p;
       // wasted=p;
    }
    public LinkedList<Process> GetWasted()
    {
        return wasted;
    }
    public void AddProcess(Process p)
    {
        que.add(p);
   //     wasted.add(p);
    }
    public LinkedList<Process> GetAllProcess()
    {
        return que;
    }
    public Snapshot DoStepOfWork()
    {
        time++;
        Snapshot sn=new Snapshot();
        if(currentProcess==null) currentProcess=GetNextProcess();
        if(currentProcess==null) {
            sn.Stop=true;
            return sn;
        }
        sn.howmany=currentProcess.StepOfWork();
        if(sn.howmany==-1)
        {
            currentProcess.WhenEnded=time;
            wasted.add(currentProcess);
            que.remove(currentProcess);
            if(que.size()==0) {
                sn.Stop=true;
                return sn;
            }
            currentProcess=GetNextProcess();
            currentProcess.WhenRealStart=time;
            sn.howmany=currentProcess.GetTotalTimeOfRunning();
        }
        sn.AllTime=currentProcess.GetTotalTimeOfRunning();
        sn.id=currentProcess.GetId();

        return sn;
    }
    public int GetTime()
    {return time;}
    private Process GetNextProcess()
    {
        Process pr=null;
        if(que.size()>0)
            while(pr==null){
        for(Process c:que) {
            if (c.GetWhenCreated() <= time) {
                pr = c;
                break;
            }

        }
            if(pr==null)
            {
                time++;
            }
            }
        for(Process cur:que)
        if(pr.GetTotalTimeOfRunning()>cur.GetTotalTimeOfRunning())
        {
            if(cur.GetWhenCreated()<=time)
            pr=cur;
        }
        return pr;
    }
}
