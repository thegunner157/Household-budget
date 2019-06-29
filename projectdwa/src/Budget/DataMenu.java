package Budget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataMenu extends JFrame{
    private static Icon[] icons;
    MyMenuButton exp;
    MyMenuButton inc;
    JButton backtomenu;
    JLabel title;
    Menu menu;
    Expenses expenses;
    Income income;
    DataMenu(Menu f){
        menu=f;
        icons = new Icon[]{
                new ImageIcon(getClass().getResource("images/expenses.png")),
                new ImageIcon(getClass().getResource("images/income.resized.png")),
        };
        expenses= new Expenses (this);
        income= new Income (this);
        title=new JLabel("Pick option", SwingConstants.CENTER);
        title.setFont (title.getFont ().deriveFont (42.0f));
        setTitle("Data");
        setLayout (new FlowLayout( FlowLayout . CENTER , 30, 10));
        //write.addActionListener(write);
        exp = new MyMenuButton("Expenses", icons[0], "expenses");
        exp.addActionListener(new GoExpenses(this, expenses));
        inc = new MyMenuButton("Income", icons[1], "income");
        inc.addActionListener(new GoIncome(this, income));
        backtomenu = new JButton("Back to menu");
        backtomenu.addActionListener(new BacktomenuAdapter ( this, menu));
        add(title);
        add(exp);
        add(inc);
        add(backtomenu);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(100,100,500,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

class BacktomenuAdapter implements ActionListener {
    Menu menu;
    DataMenu menudata;
    BacktomenuAdapter (DataMenu dat, Menu f) {
        this.menu=f;
        this.menudata = dat;
    }
    public void actionPerformed ( ActionEvent e) {
        menudata.setVisible(false);
        menu.setVisible(true);
    }
}
class GoExpenses implements ActionListener {
    Expenses exp;
    DataMenu menudata;
    GoExpenses (DataMenu dat, Expenses f) {
        this.exp=f;
        this.menudata = dat;
    }
    public void actionPerformed ( ActionEvent e) {
        menudata.setVisible(false);
        exp.setVisible(true);
    }
}
class GoIncome implements ActionListener {
    Income inc;
    DataMenu menudata;
    GoIncome (DataMenu dat, Income f) {
        this.inc=f;
        this.menudata = dat;
    }
    public void actionPerformed ( ActionEvent e) {
        menudata.setVisible(false);
        inc.setVisible(true);
    }
}