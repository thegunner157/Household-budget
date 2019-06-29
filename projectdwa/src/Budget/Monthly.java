package Budget;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Monthly extends JFrame{
    BalanceMenu balancemenu;
    JLabel title;
    JButton backtomenu;
    JButton selectchart;
    JLabel yearnum;
    JTextField year;
    ChartPanel panel;
    MonthlyChart monthlychart;
    ArrayList<OverallData> income;
    ArrayList<OverallData> expenses;
    Monthly(BalanceMenu f,ArrayList<OverallData> in, ArrayList<OverallData> ex) {
        income=in;
        expenses=ex;
        monthlychart = new MonthlyChart("Monthly Balance", "monthly", 2019, income, expenses);
        panel=monthlychart.panel;
        panel.setLayout(new java.awt.BorderLayout());
        balancemenu = f;
        title = new JLabel("Monthly balance", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(55.0f));
        setTitle("Monthly");
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        year = new JTextField("Select year");
        year.setColumns(20);
        year.setFont(year.getFont().deriveFont(Font.PLAIN, 15f));
        yearnum = new JLabel("Select year", SwingConstants.CENTER);
        yearnum.setFont(title.getFont().deriveFont(38.0f));
        selectchart = new JButton("Select");
        selectchart.addActionListener(new SelectMonthlyChartAdapter(panel, year, income, expenses, this));
        //write.addActionListener(write);
        backtomenu = new JButton("Back to menu");
        backtomenu.addActionListener(new BacktobalancemenuMonthlyAdapter(balancemenu, this));
        add(title);
        add(yearnum);
        add(year);
        add(selectchart);
        add(panel);
        add(backtomenu);
        pack();
        panel.setVisible(false);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(100, 100, 580, 660);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
class BacktobalancemenuMonthlyAdapter implements ActionListener {
    Monthly month;
    BalanceMenu menubalance;
    BacktobalancemenuMonthlyAdapter (BalanceMenu dat, Monthly f) {
        this.month=f;
        this.menubalance = dat;
    }
    public void actionPerformed ( ActionEvent e) {
        menubalance.setVisible(true);
        month.setVisible(false);
    }
}
class MonthlyChart {
    ChartPanel panel;
    ArrayList<OverallData> income;
    ArrayList<OverallData> expenses;
    OverallDatatoChart inc;
    OverallDatatoChart exp;
    int year;
    public MonthlyChart(String titl, String type, int yer, ArrayList<OverallData> in, ArrayList<OverallData> ex){
        income=in;
        expenses=ex;
        year=yer;
        inc = new OverallDatatoChart(income);
        exp = new OverallDatatoChart(expenses);
        JFreeChart barChart = ChartFactory.createBarChart(
                titl,
                "Month",
                "Money [pln]",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);
        panel = new ChartPanel( barChart );
        panel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
    }
    private CategoryDataset createDataset( ) {
        final String planned = "PLANNED";
        final String unplanned = "REAL";
        final String days[];
        days = new String[13];
        for (int i = 1; i <= 12; i++) {
            days[i] = Integer.toString(i);
        }
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset( );

        int incounter=0;
        int expcounter=0;
        for (int i = 1; i <= 12;i++){
            inc = new OverallDatatoChart(income);
            exp = new OverallDatatoChart(expenses);
            inc.monthlysum(i, year);
            exp.monthlysum(i, year);
            dataset.addValue(inc.plannedmonthsum - exp.plannedmonthsum, planned, days[i]);
            double val = (inc.plannedmonthsum-exp.plannedmonthsum);
            while(incounter<income.size() && income.get(incounter).change_to_year(income.get(incounter).date)<year) {
                incounter++;
            }
            while( incounter<income.size() && income.get(incounter).change_to_month(income.get(incounter).date)<=i) {
                if (income.get(incounter).change_to_month(income.get(incounter).date) == i &&
                        income.get(incounter).change_to_year(income.get(incounter).date) == year &&
                        income.get(incounter).type == "unplanned") {
                    val += (double) income.get(incounter).value;
                }
                incounter++;
            }
            while(expcounter<expenses.size() && expenses.get(expcounter).change_to_year(expenses.get(expcounter).date)<year) {
                expcounter++;
            }
            while(expcounter<expenses.size() && expenses.get(expcounter).change_to_month(expenses.get(expcounter).date)<=i) {
                if (expenses.get(expcounter).change_to_month(expenses.get(expcounter).date) == i &&
                        expenses.get(expcounter).change_to_year(expenses.get(expcounter).date) == year &&
                        expenses.get(expcounter).type == "unplanned") {
                    val -= (double) expenses.get(expcounter).value;
                }
                expcounter++;
            }
            dataset.addValue(val, unplanned, days[i]);
        }


        return dataset;
    }


}
class SelectMonthlyChartAdapter implements ActionListener{
    ChartPanel pane;
    JTextField year;
    ArrayList<OverallData> income;
    ArrayList<OverallData> expenses;
    Monthly actual;
    SelectMonthlyChartAdapter(ChartPanel panel, JTextField yer, ArrayList<OverallData> inc, ArrayList<OverallData> exp, Monthly f){
        pane=panel;
        year=yer;
        income= inc;
        expenses = exp;
        actual = f;
    }
    public void actionPerformed ( ActionEvent e) {

        try {
            int yer = Integer.parseInt(year.getText());
            MonthlyChart chart = new MonthlyChart("Daily Balance", "daily", yer, income, expenses);
            actual.remove(actual.panel);
            actual.panel = chart.panel;
            actual.add(actual.panel);
            actual.panel.setLayout(new java.awt.BorderLayout());
            actual.panel.setVisible(true);
        }
        catch(NumberFormatException f) {
        } catch(NullPointerException f) {
        }
    }
}