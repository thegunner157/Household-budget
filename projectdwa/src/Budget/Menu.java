package Budget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Menu extends JFrame{
    private static Icon[] icons;
    MyMenuButton data;
    MyMenuButton warnings;
    MyMenuButton balance;
    JLabel title;
    DataMenu dataMenu;
    BalanceMenu balanceMenu;
    Warnings warning;
    Menu(){
        dataMenu = new DataMenu(this);
        warning = new Warnings(this);
        balanceMenu = new BalanceMenu(this, warning);

        icons = new Icon[]{
                new ImageIcon(getClass().getResource("images/dataicon.resized.png")),
                new ImageIcon(getClass().getResource("images/warningicon.resized.png")),
                new ImageIcon(getClass().getResource("images/balanceicon.resized.png")),
        };
        title=new JLabel("Pick options", SwingConstants.CENTER);
        title.setFont (title.getFont ().deriveFont (48.0f));
        setTitle("Personal Budget");
        setLayout (new FlowLayout( FlowLayout . CENTER, 10, 15 ));
        //write.addActionListener(write);
        data = new MyMenuButton("Data", icons[0], "data");
        warnings = new MyMenuButton("Warnings", icons[1], "warnings");
        balance = new MyMenuButton("Balance", icons[2], "balance");
        data.addActionListener ( new MyMenuButtonDataAdapter ( dataMenu, this ));
        balance.addActionListener ( new MyMenuButtonBalanceAdapter ( balanceMenu, this));
        warnings.addActionListener ( new MyMenuButtonWarningAdapter ( warning, this));
        add(title);
        add(data);
        add(warnings);
        add(balance);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(100,100,500,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

class MyMenuButton extends JButton {
    String description;
    public MyMenuButton(String s, Icon x, String describe) {
        setLabel(s);
        setIcon(x);
        description=describe;
    }
}


class MyMenuButtonDataAdapter implements ActionListener {
    DataMenu menudata;
    Menu menu;
    MyMenuButtonDataAdapter ( DataMenu dat, Menu f) {
        this.menu=f;
        this.menudata = dat;
    }
    public void actionPerformed ( ActionEvent e) {
        menudata.setVisible(true);
        menu.setVisible(false);
    }
}
class MyMenuButtonBalanceAdapter implements ActionListener {

    BalanceMenu balancemenu;
    Menu menu;
    MyMenuButtonBalanceAdapter ( BalanceMenu bal, Menu f) {
        this.menu=f;
        this.balancemenu = bal;
    }
    public void actionPerformed ( ActionEvent e) {
        balancemenu.setVisible(true);
        menu.setVisible(false);
    }
}
class MyMenuButtonWarningAdapter implements ActionListener {
    Warnings warning;
    Menu menu;
    MyMenuButtonWarningAdapter ( Warnings war, Menu f) {
        this.menu=f;
        this.warning = war;
    }
    public void actionPerformed ( ActionEvent e) {
        String curr = warning.txt.getText();
        for(int i=0;i<warning.warns.size();i++){
            curr+="Daily limit overdrawn on day: ";
            curr+=warning.warns.get(i);
            curr+="\n";
        }
        warning.txt.setText(curr);
        warning.setVisible(true);
        menu.setVisible(false);
    }
}
