package Budget;

public class UsefulTransformations {
    int change_to_month(String dat){
        char left = dat.charAt(3);
        char right = dat.charAt(4);
        int tens = Character.getNumericValue(left);
        int singles = Character.getNumericValue(right);
        int currmonth = tens*10+singles;
        return currmonth;
    }
    int change_to_year(String dat){
        char first = dat.charAt(6);
        char second = dat.charAt(7);
        char third = dat.charAt(8);
        char fourth= dat.charAt(9);
        int tens = Character.getNumericValue(third);
        int singles = Character.getNumericValue(fourth);
        int thousands = Character.getNumericValue(first);
        int hunderds = Character.getNumericValue(second);
        int curryear = thousands*1000+ hunderds*100 +tens*10 +singles;
        return curryear;
    }
    int change_to_day(String dat){
        char left = dat.charAt(0);
        char right = dat.charAt(1);
        int tens = Character.getNumericValue(left);
        int singles = Character.getNumericValue(right);
        int currday = tens*10+singles;
        return currday;
    }
}
