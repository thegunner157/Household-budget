package Budget;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Expenses extends JFrame {
    DataMenu datamenu;
    JLabel title;
    JButton backtomenu;
    JTextArea txt;
    String writeexpens="";
    Expenses(DataMenu f) {
        datamenu = f;
        title = new JLabel("Expenses", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(42.0f));
        setTitle("Expenses");
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        //write.addActionListener(write);
        backtomenu = new JButton("Back to menu");
        backtomenu.addActionListener(new BacktodatamenuExpensesAdapter(datamenu, this));

        try {
            File file = new File("src/Budget/data/expenses.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                if(st!="\n") {
                    writeexpens += st;
                    writeexpens += "\n";
                }
            }
            br.close();
        }
        catch(IOException e){
            System.out.println("xd2");
        }
        txt=new JTextArea(40, 60);
        txt.setText(writeexpens);
        txt.setEditable(false);
        JScrollPane text = new JScrollPane(txt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        text.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(title);
        add(text, BorderLayout.SOUTH);
        add(backtomenu);
        pack();
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(100, 100, 800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
class BacktodatamenuExpensesAdapter implements ActionListener {
    Expenses expenses;
    DataMenu menudata;
    BacktodatamenuExpensesAdapter (DataMenu dat, Expenses f) {
        this.expenses=f;
        this.menudata = dat;
    }
    public void actionPerformed ( ActionEvent e) {
        menudata.setVisible(true);
        expenses.setVisible(false);
    }
}