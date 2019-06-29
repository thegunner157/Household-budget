package Budget;

public class OverallData extends UsefulTransformations implements Comparable<OverallData>{
    String date;
    int value;
    String type;
    OverallData(String datecome, int valuecome, String typecome){
        date=datecome;
        value=valuecome;
        type=typecome;
    }
    @Override
    public int compareTo(OverallData o) {
        int yearleft = change_to_year(date);
        int yearright = change_to_year(o.date);
        if(yearleft == yearright){
            int monthleft = change_to_month(date);
            int monthright = change_to_month(o.date);
            if(monthleft == monthright){
                int dayleft = change_to_day(date);
                int dayright = change_to_day(o.date);
                return dayleft-dayright;
            }
            else{
                return monthleft-monthright;
            }

        }
        return yearleft-yearright;
    }


}
