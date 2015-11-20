package com.company;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        port(9090);
        get("/out", (req, res) -> {

        String Head="<html><head><title>Lab1</title><script src=\"https://www.google.com/jsapi\"></script>";

        String log=new String();//=Head+"<body>Log:<br>";

        ManagerOfProcesses mop=new ManagerOfProcesses();
        mop.AddProcess(new Process(3,0,1));
        mop.AddProcess(new Process(5,6,2));
        mop.AddProcess(new Process(10,2,3));
        mop.AddProcess(new Process(8,5,4));

        mop.AddProcess(new Process(5,9,5));
        mop.AddProcess(new Process(8, 9, 6));
            log+="<div id=\"columnchart_values\" style=\"width: 500px; height: 400px;position:absolute;left:300;top:"+(10+mop.GetAllProcess().size()*20)+"\"></div>";


            Snapshot sn=new Snapshot();
        while(!sn.Stop)
        {
            sn=mop.DoStepOfWork();
            if(!sn.Stop)
            log+="<font size=1> All time="+sn.AllTime+" Time left="+sn.howmany+" id="+sn.id+" time="+mop.GetTime()+"<br></font>";
        }
            String table="<table style=\"position:absolute;left:300;top:10;border:1px solid black;\"><tr><td>№ Процесу</td><td>Час надхождення</td><td>Час виконання</td><td>Час завершення</td>";
            table+="<td> Час затримки</td></tr>";
            LinkedList<Process> result=mop.GetWasted();

            Collections.sort(result, new Comparator<Process>() {
                @Override
                public int compare(Process o1, Process o2) {
                    if(o1.WhenRealStart>o2.WhenRealStart) return 1;
                    else return -1;
                }
            });

            log+="<br> <font style=\"position:absolute;top:500;\" size=\"+2\" color=\"#005555\"> Порядок выполнения: ";
            for(Process f:result)
            {
                log+=f.GetId()+"(нчт в "+f.WhenRealStart+",зкнчлся в  "+f.WhenEnded+") ";
            }
            log+="</font>";

            Collections.sort(result, new Comparator<Process>() {
                @Override
                public int compare(Process o1, Process o2) {
                    if(o1.GetId()>o2.GetId()) return 1;
                    else return -1;
                }
            });

          /*  Head+="<script>\n" +
                    "   google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});\n" +
                    "   google.setOnLoadCallback(drawChart);\n" +
                   " function drawChart() {\n" +
                    "      var data = google.visualization.arrayToDataTable([\n" +
                    "        [\"Номер процесса\", \"Длительность\", { role: \"style\" } ],\n";
                    for(Process f:result)
                    {
                        Head+="[\""+f.GetId()+"\","+f.GetTotalTimeOfRunning()+","+"\"black\"],\n";
                    }
                  Head=Head.substring(0,Head.length()-2);
                    //Head+=" [\"Platinum\", 21.45, \"color: #e5e4e2\"]";
            Head+= "]);\n" +
                    "\n" +
                    "      var view = new google.visualization.DataView(data);\n" +
                    "      view.setColumns([0, 1,\n" +
                    "                       { calc: \"stringify\",\n" +
                    "                         sourceColumn: 1,\n" +
                    "                         type: \"string\",\n" +
                    "                         role: \"annotation\" },\n" +
                    "                       2]);\n" +
                    "\n" +
                    "      var options = {\n" +
                    "        title: \"Длительность задач:\",\n" +
                    "        width: 600,\n" +
                    "        height: 400,\n" +
                    "        bar: {groupWidth: \"95%\"},\n" +
                    "        legend: { position: \"none\" },\n" +
                    "      };"+
                    "    var chart = new google.visualization.ColumnChart(document.getElementById('columnchart_values'));\n" +
                    "    chart.draw(data, options);\n" +
                    "   }\n" +
                    "  </script>\n"; */
            Head+="</head><body>";




            for(Process f:result)
            {
                table+="<tr>";
                table+="<td>"+f.GetId()+"</td>";
                table+="<td>"+f.GetWhenCreated()+"</td>";
                table+="<td>"+f.GetTotalTimeOfRunning()+"</td>";
                table+="<td>"+f.WhenEnded+"</td>";
                table+="<td>"+(f.WhenRealStart-f.GetWhenCreated())+"</td>";
            }
            String foot="</body></html>";
                return Head+log+table+foot;
            });
    }
}
