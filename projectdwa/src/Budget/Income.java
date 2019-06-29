package Budget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Income extends JFrame {
    DataMenu datamenu;
    JLabel title;
    JButton backtomenu;
    JTextArea datas;
    String writeincome="";
    Income(DataMenu f) {
        datamenu = f;
        title = new JLabel("Income", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(42.0f));
        setTitle("Income");
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        //write.addActionListener(write);
        backtomenu = new JButton("Back to menu");
        backtomenu.addActionListener(new BacktodatamenuIncomeAdapter(datamenu, this));
        try {
            File file = new File("src/Budget/data/income.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                if(st!="\n") {
                    //String arr[] = st.split(".\t" );
                    writeincome += st;
                    writeincome += "\n";
                }
            }
            br.close();
        }
        catch(IOException e){
            System.out.println("xd2");
        }
        datas=new JTextArea(20,60);
        datas.setText(writeincome);
        datas.setEditable(false);
        JScrollPane text = new JScrollPane(datas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(title);
        add(text);
        add(backtomenu);
        pack();
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(100, 100, 800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
class BacktodatamenuIncomeAdapter implements ActionListener {
    Income inc;
    DataMenu menudata;
    BacktodatamenuIncomeAdapter (DataMenu dat, Income f) {
        this.inc=f;
        this.menudata = dat;
    }
    public void actionPerformed ( ActionEvent e) {
        menudata.setVisible(true);
        inc.setVisible(false);
    }
}
