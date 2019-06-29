package Budget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Warnings extends JFrame {
    JLabel title;
    Menu menu;
    JButton backtomenu;
    JTextArea txt;
    ArrayList<String> warns;
    Warnings(Menu f){
        warns = new ArrayList<String>();
        txt=new JTextArea(30, 50);
        txt.setText("");
        txt.setEditable(false);
        JScrollPane text = new JScrollPane(txt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        menu=f;
        title=new JLabel("Pick option", SwingConstants.CENTER);
        title.setFont (title.getFont ().deriveFont (42.0f));
        setTitle("Warnings");
        setLayout (new FlowLayout( FlowLayout . CENTER , 30, 10));
        backtomenu = new JButton("Back to menu");
        backtomenu.addActionListener(new BacktomenuWarningAdapter ( this, menu));
        add(title);
        add(text);
        add(backtomenu);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(100,100,700,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        System.out.println(warns.size());
    }
}
class BacktomenuWarningAdapter implements ActionListener {
    Menu menu;
    Warnings warning;
    BacktomenuWarningAdapter (Warnings war, Menu f) {
        this.menu=f;
        this.warning = war;
    }
    public void actionPerformed ( ActionEvent e) {
        warning.setVisible(false);
        menu.setVisible(true);
    }
}