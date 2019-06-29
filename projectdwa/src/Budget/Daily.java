package Budget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


import static demo.GanttDemo3.createDataset;

public class Daily extends JFrame{
    BalanceMenu balancemenu;
    JLabel title;
    JLabel monthnum;
    JLabel yearnum;
    JButton backtomenu;
    JButton selectchart;
    DailyChart dailychart;
    ChartPanel panel;
    ArrayList<OverallData> income;
    ArrayList<OverallData> expenses;
    JTextField month;
    JTextField year;
    Daily(BalanceMenu f, Warnings e, ArrayList<OverallData> in, ArrayList<OverallData> ex) {
        income=in;
        expenses=ex;
        dailychart = new DailyChart("Daily Balance", "daily", 6, 2019, income, expenses, this, e);
        panel=dailychart.panel;
        panel.setLayout(new java.awt.BorderLayout());
        balancemenu = f;
        month = new JTextField("Select month");
        month.setColumns(20);
        month.setFont(month.getFont().deriveFont(Font.PLAIN, 15f));
        year = new JTextField("Select year");
        year.setColumns(20);
        year.setFont(year.getFont().deriveFont(Font.PLAIN, 15f));
        title = new JLabel("Daily balance", SwingConstants.CENTER);
        monthnum = new JLabel("Select month", SwingConstants.CENTER);
        yearnum = new JLabel("Select year", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(65.0f));
        monthnum.setFont(title.getFont().deriveFont(48.0f));
        yearnum.setFont(title.getFont().deriveFont(48.0f));
        selectchart = new JButton("Select");
        selectchart.addActionListener(new SelectChartAdapter(panel, month, year, income, expenses, this, e));
        setTitle("Daily");
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        backtomenu = new JButton("Back to menu");
        backtomenu.addActionListener(new BacktobalancemenuDailyAdapter(balancemenu, this));
        add(title);
        add(monthnum);
        add(yearnum);
        add(month);
        add(year);
        add(selectchart);
        add(panel);
        add(backtomenu);
        pack();
        panel.setVisible(false);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(100, 100, 900, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
class BacktobalancemenuDailyAdapter implements ActionListener {
    Daily day;
    BalanceMenu menubalance;
    BacktobalancemenuDailyAdapter (BalanceMenu dat, Daily f) {
        this.day=f;
        this.menubalance = dat;
    }
    public void actionPerformed ( ActionEvent e) {
        menubalance.setVisible(true);
        day.setVisible(false);
    }
}
class DailyChart {
    ChartPanel panel;
    int month;
    ArrayList<OverallData> income;
    ArrayList<OverallData> expenses;
    OverallDatatoChart inc;
    OverallDatatoChart exp;
    int year;
    Daily curr;
    Warnings warn;
    public DailyChart(String titl, String type, int mon, int yer, ArrayList<OverallData> in, ArrayList<OverallData> ex, Daily f, Warnings w){
        month=mon;
        income=in;
        expenses=ex;
        year=yer;
        curr=f;
        warn = w;
        JFreeChart barChart = ChartFactory.createBarChart(
                titl,
                "Day",
                "Money [pln]",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);
        panel = new ChartPanel( barChart );
        panel.setPreferredSize(new java.awt.Dimension( 900 , 700 ) );
    }
    private CategoryDataset createDataset( ) {
        final String planned = "PLANNED";
        final String unplanned = "REAL";
        final String days[];
        inc = new OverallDatatoChart(income);
        exp = new OverallDatatoChart(expenses);
        inc.monthlysum(month, year);
        exp.monthlysum(month, year);
        if((month%2==1 && month<=7) || (month%2==0 && month>=8)) {
            days = new String[32];
            for (int i = 1; i <= 31; i++) {
                days[i] = Integer.toString(i);
            }
        }
        else{
            days = new String[31];
            for (int i = 1; i <= 30; i++) {
                days[i] = Integer.toString(i);
            }
        }
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset( );

        if((month%2==1 && month<=7) || (month%2==0 && month>=8)) {
            int incounter=0;
            int expcounter=0;
            for (int i = 1; i <= 31;i++){
                dataset.addValue((inc.plannedmonthsum-exp.plannedmonthsum)/31, planned, days[i]);
                double val = (inc.plannedmonthsum-exp.plannedmonthsum)/31;
                while(incounter<income.size() && income.get(incounter).change_to_year(income.get(incounter).date)<year) {
                    incounter++;
                }
                while(incounter<income.size() && income.get(incounter).change_to_month(income.get(incounter).date)<month) {
                    incounter++;
                }
                while( incounter<income.size() && income.get(incounter).change_to_day(income.get(incounter).date)<=i) {
                    if (income.get(incounter).change_to_day(income.get(incounter).date) == i &&
                            income.get(incounter).change_to_month(income.get(incounter).date) == month &&
                            income.get(incounter).change_to_year(income.get(incounter).date) == year &&
                            income.get(incounter).type == "unplanned") {

                        val += (double) income.get(incounter).value;
                    }
                    incounter++;
                }
                while(expcounter<expenses.size() && expenses.get(expcounter).change_to_year(expenses.get(expcounter).date)<year) {
                    expcounter++;
                }
                while(expcounter<expenses.size() && expenses.get(expcounter).change_to_month(expenses.get(expcounter).date)<month) {
                    expcounter++;
                }
                while(expcounter<expenses.size() && expenses.get(expcounter).change_to_day(expenses.get(expcounter).date)<=i) {
                    if (expenses.get(expcounter).change_to_day(expenses.get(expcounter).date) == i &&
                            expenses.get(expcounter).change_to_month(expenses.get(expcounter).date) == month &&
                            expenses.get(expcounter).change_to_year(expenses.get(expcounter).date) == year &&
                            expenses.get(expcounter).type == "unplanned") {
                        val -= (double) expenses.get(expcounter).value;
                    }
                    expcounter++;
                }
                if(val<0){
                    if(!warn.warns.contains(expenses.get(expcounter-1).date)){
                        warn.warns.add(expenses.get(expcounter-1).date);
                    }
                }
                dataset.addValue(val, unplanned, days[i]);
            }
        }
        else{
            int incounter=0;
            int expcounter=0;
            for (int i = 1; i <= 30;i++){
                dataset.addValue((inc.plannedmonthsum-exp.plannedmonthsum)/30, planned, days[i]);
                double val = (inc.plannedmonthsum-exp.plannedmonthsum)/30;
                while(incounter<income.size() && income.get(incounter).change_to_year(income.get(incounter).date)<year) {
                    incounter++;
                }
                while(incounter<income.size() && income.get(incounter).change_to_month(income.get(incounter).date)<month) {
                    incounter++;
                }
                while( incounter<income.size() && income.get(incounter).change_to_day(income.get(incounter).date)<=i) {
                    if (income.get(incounter).change_to_day(income.get(incounter).date) == i &&
                            income.get(incounter).change_to_month(income.get(incounter).date) == month &&
                            income.get(incounter).change_to_year(income.get(incounter).date) == year &&
                            income.get(incounter).type == "unplanned") {
                        val += (double) income.get(incounter).value;
                    }
                    incounter++;
                }
                while(expcounter<expenses.size() && expenses.get(expcounter).change_to_year(expenses.get(expcounter).date)<year) {
                    expcounter++;
                }
                while(expcounter<expenses.size() && expenses.get(expcounter).change_to_month(expenses.get(expcounter).date)<month) {
                    expcounter++;
                }
                while(expcounter<expenses.size() && expenses.get(expcounter).change_to_day(expenses.get(expcounter).date)<=i) {
                    if (expenses.get(expcounter).change_to_day(expenses.get(expcounter).date) == i &&
                            expenses.get(expcounter).change_to_month(expenses.get(expcounter).date) == month &&
                            expenses.get(expcounter).change_to_year(expenses.get(expcounter).date) == year &&
                            expenses.get(expcounter).type == "unplanned") {
                        val -= (double) expenses.get(expcounter).value;
                    }
                    expcounter++;
                }
                if(val<0){
                    if(!warn.warns.contains(expenses.get(expcounter-1).date)){
                        warn.warns.add(expenses.get(expcounter-1).date);
                    }
                }
                dataset.addValue(val, unplanned, days[i]);
            }
        }
        return dataset;
    }

}
class SelectChartAdapter implements ActionListener{
    ChartPanel pane;
    JTextField month;
    JTextField year;
    ArrayList<OverallData> income;
    ArrayList<OverallData> expenses;
    Daily actual;
    Warnings x;
    SelectChartAdapter(ChartPanel panel, JTextField mon, JTextField yer, ArrayList<OverallData> inc, ArrayList<OverallData> exp, Daily f, Warnings q){
        pane=panel;
        month=mon;
        year=yer;
        income= inc;
        expenses = exp;
        actual = f;
        x=q;
    }
    public void actionPerformed ( ActionEvent e) {

        try {
            int mon=Integer.parseInt(month.getText());
            int yer = Integer.parseInt(year.getText());
            if(mon>0 && mon<=12 && yer>0){
                DailyChart chart = new DailyChart("Daily Balance", "daily", mon, yer, income, expenses, actual, x);
                actual.remove(actual.panel);
                actual.panel = chart.panel;
                actual.add(actual.panel);
                actual.panel.setLayout(new java.awt.BorderLayout());
                actual.panel.setVisible(true);
            }
        } catch(NumberFormatException f) {
        } catch(NullPointerException f) {
        }
    }
}