package Budget;

public class ExpensesData extends OverallData {
    String category;
    ExpensesData(String datecome, int valuecome, String typecome, String categorycome){
        super(datecome, valuecome, typecome);
        category = categorycome;
    }
}
