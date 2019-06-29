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
import java.util.Collections;

public class BalanceMenu extends JFrame{
    private static Icon[] icons;
    MyMenuButton daily;
    MyMenuButton monthly;
    JButton backtomenu;
    JLabel title;
    Menu menu;
    Daily day;
    Monthly month;
    ArrayList<OverallData> income;
    ArrayList<OverallData> expenses;
    BalanceMenu(Menu f, Warnings x){
        menu=f;
        icons = new Icon[]{
                new ImageIcon(getClass().getResource("images/dailicon.resized.png")),
                new ImageIcon(getClass().getResource("images/monthlicon.resized.png")),
        };
        String arr[]=null;
        income = new ArrayList<OverallData>();
        expenses = new ArrayList<OverallData>();
        try {
            File file = new File("src/Budget/data/income.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            boolean wasunplanned=false;
            while ((st = br.readLine()) != null) {
                OverallData single;
                if(st!="\n") {
                    if(st.equals("Unplanned:")){
                        wasunplanned=true;
                    }
                    String[] actual = st.split("\t");
                    if(actual.length==6){
                        if(!wasunplanned){
                            single = new OverallData(actual[2], Integer.parseInt(actual[5]), "planned");
                            income.add(single);
                        }
                        else {
                            single = new OverallData(actual[2], Integer.parseInt(actual[5]), "unplanned");
                            income.add(single);
                        }
                    }
                    else if(actual.length==7 && !actual[3].equals("Date:")){
                        if(!wasunplanned){
                            single = new OverallData(actual[3], Integer.parseInt(actual[6]), "planned");
                            income.add(single);
                        }
                        else {
                            single = new OverallData(actual[3], Integer.parseInt(actual[6]), "unplanned");
                            income.add(single);
                        }
                    }



                }
            }
            br.close();
        }
        catch(IOException e) {
            System.out.println("xd2");
        }
        try {
            File file = new File("src/Budget/data/expenses.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            boolean wasunplanned=false;
            while ((st = br.readLine()) != null) {
                OverallData single;
                if(st!="\n") {
                    if(st.equals("Unplanned:")){
                        wasunplanned=true;
                    }
                    String[] actual = st.split("\t");
                    if(actual.length==6){
                        if(!wasunplanned){
                            single = new OverallData(actual[2], Integer.parseInt(actual[5]), "planned");
                            expenses.add(single);
                        }
                        else {
                            single = new OverallData(actual[2], Integer.parseInt(actual[5]), "unplanned");
                            expenses.add(single);
                        }
                    }
                    else if(actual.length==7 && !actual[3].equals("Date:")){
                        if(!wasunplanned){
                            single = new OverallData(actual[3], Integer.parseInt(actual[6]), "planned");
                            expenses.add(single);
                        }
                        else {
                            single = new OverallData(actual[3], Integer.parseInt(actual[6]), "unplanned");
                            expenses.add(single);
                        }
                    }



                }
            }
            br.close();
        }
        catch(IOException e) {
            System.out.println("xd2");
        }
        Collections.sort(income);
        Collections.sort(expenses);
        day = new Daily(this, x, income, expenses);
        month = new Monthly(this, income,expenses);
        title=new JLabel("Pick option", SwingConstants.CENTER);
        title.setFont (title.getFont ().deriveFont (42.0f));
        setTitle("Balance");
        setLayout (new FlowLayout( FlowLayout . CENTER , 30, 10));
        //write.addActionListener(write);
        daily = new MyMenuButton("Daily", icons[0], "daily");
        daily.addActionListener(new GoDaily(this, day));
        monthly = new MyMenuButton("Monthly", icons[1], "monthly");
        monthly.addActionListener(new GoMonthly(this, month));
        backtomenu = new JButton("Back to menu");
        backtomenu.addActionListener(new BacktomenuBalanceAdapter ( this, menu));
        add(title);
        add(daily);
        add(monthly);
        add(backtomenu);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(100,100,500,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
class BacktomenuBalanceAdapter implements ActionListener {
    Menu menu;
    BalanceMenu menubalance;
    BacktomenuBalanceAdapter (BalanceMenu bal, Menu f) {
        this.menu=f;
        this.menubalance = bal;
    }
    public void actionPerformed ( ActionEvent e) {
        menubalance.setVisible(false);
        menu.setVisible(true);
    }
}
class GoDaily implements ActionListener{
    Daily dail;
    BalanceMenu balancemenu;
    GoDaily (BalanceMenu bal, Daily f) {
        this.dail=f;
        this.balancemenu = bal;
    }
    public void actionPerformed ( ActionEvent e) {
        balancemenu.setVisible(false);
        dail.setVisible(true);
    }
}
class GoMonthly implements ActionListener{
    Monthly mont;
    BalanceMenu balancemenu;
    GoMonthly (BalanceMenu bal, Monthly f) {
        this.mont=f;
        this.balancemenu = bal;
    }
    public void actionPerformed ( ActionEvent e) {
        balancemenu.setVisible(false);
        mont.setVisible(true);
    }
}
