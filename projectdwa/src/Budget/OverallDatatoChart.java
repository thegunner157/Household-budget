package Budget;

import java.util.ArrayList;

public class OverallDatatoChart extends UsefulTransformations{
    ArrayList<OverallData> list;
    int plannedmonthsum;
    int unplannedmonthsum;
    OverallDatatoChart(ArrayList<OverallData> listcome){
        list=listcome;
    }
    void monthlysum(int month, int year){
        for(int i=0;i<list.size();i++){
            String date= list.get(i).date;
            int currmonth = change_to_month(date);
            int curryear = change_to_year(date);
            if(curryear>year){
                break;
            }
            if(curryear==year && currmonth>month){
                break;
            }
            if(month==currmonth && year==curryear && list.get(i).type=="planned") {
                plannedmonthsum += list.get(i).value;
            }
            if(month==currmonth && year==curryear && list.get(i).type=="unplanned") {
                unplannedmonthsum += list.get(i).value;
            }
        }
    }
}
