import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {


        public MainWindow() {
            super("LL");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(800, 600));
            Container contentPane = getContentPane();
            setLocationRelativeTo(null);
            pack();
            setVisible(true);
            Container container = getLayeredPane();

            // Making settings button (in progress)
            JButton settings = new JButton("settings");
            settings.setBounds(10, 10, 100, 20);
            container.add(settings);


            // Making start button
            JButton start = new JButton("START");
            start.setBounds(10, 40, 100, 20);
            start.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JLabel word = new JLabel("Fuck");
                    word.setBounds(350, 250, 80, 40);

                    container.add(word);
                }
            });
            container.add(start);

        }
}
