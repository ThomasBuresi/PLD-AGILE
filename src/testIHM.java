import java.awt.Color;
import java.awt.geom.*;
import java.awt.*;
import javax.swing.*;

public class testIHM extends JFrame{
    

    public static void main(String[] args) {
        //Frame
        testIHM  test = new testIHM();
        test.setVisible(true);
    }
    
    testIHM(){
        setTitle("Deliver'IF");
        setSize(1300,720);
        setLocation(100,100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        JButton load_file = new JButton("Load File");
        JScrollPane scroll = new JScrollPane();
        for (int i = 0; i < 5; i++) {
            scroll.add(new JButton("Button n°" + i));
        }
        
        JPanel graphical_map = new JPanel();
        graphical_map.setBounds(20,20,900,500);
        graphical_map.setBackground(Color.green);
        
        JPanel textual_list = new JPanel();
        textual_list.setBounds(950,20,300,500);
        textual_list.setBackground(Color.white);
        textual_list.add(load_file);
        textual_list.add(scroll);
        
        JPanel textual_box = new JPanel();
        textual_box.setBounds(20,550,900,100);
        textual_box.setBackground(Color.white);
        
        add(graphical_map);
        add(textual_list);
        add(textual_box);
    }
    
}