import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Locale;

import ms.SuperScanner;

public class MainWindow extends JFrame {


        public MainWindow() {
            super("LL");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            setPreferredSize(new Dimension(800, 600));
            Container contentPane = getContentPane();
            setLocationRelativeTo(null);
            pack();
            setVisible(true);
            Container staticContainer = getLayeredPane();

            // Making settings button (in progress)
            JButton settings = new JButton("settings");
            settings.setBounds(10, 10, 100, 20);
            staticContainer.add(settings);


            // Making start button with some diffs
            JButton start = new JButton("START");
            start.setBounds(10, 40, 100, 20);


            JLabel checkWord = new JLabel("Fuck");
            checkWord.setBounds(350, 150, 80, 40);
            checkWord.setVisible(false);
            staticContainer.add(checkWord);

            JTextField word = new JTextField();
            word.setBounds(350, 200, 80, 30);
            word.setVisible(false);
            staticContainer.add(word);


            try {
                SuperScanner in = new SuperScanner(
                        new InputStreamReader(
                                new FileInputStream("src/repeat.txt"),
                                StandardCharsets.UTF_8
                        )
                );
                try {

                    checkWord.setText(in.next());

                } finally {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }


            start.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    checkWord.setVisible(true);
                    word.setVisible(true);


                }
            });
            staticContainer.add(start);


            JButton addButton = new JButton("add");
            addButton.setBounds(10, 70, 100, 20);
            staticContainer.add(addButton);

            JLabel addingPanel = new JLabel("Введите слово и его перевод:");
            addingPanel.setBounds(270, 150, 250, 20);
            addingPanel.setVisible(false);
            staticContainer.add(addingPanel);

            JTextField newWord = new JTextField();
            newWord.setBounds(270, 180, 100, 20);
            newWord.setVisible(false);
            staticContainer.add(newWord);

            JTextField translation = new JTextField();
            translation.setBounds(270, 210, 100, 20);
            translation.setVisible(false);
            staticContainer.add(translation);

            JButton submit = new JButton("submit");
            submit.setBounds(270, 240, 100, 20);
            submit.setVisible(false);
            staticContainer.add(submit);

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    submit.setVisible(true);
                    translation.setVisible(true);
                    newWord.setVisible(true);
                    addingPanel.setVisible(true);
                }
            });

            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent a) {
                    String file = "src/repeat.txt";
                    String addStr = newWord.getText().toLowerCase(Locale.ROOT) + " " +
                            translation.getText().toLowerCase(Locale.ROOT);
                    try {
                        Files.writeString(Paths.get(file), addStr,
                                StandardOpenOption.APPEND);
                        newWord.setText("");
                        translation.setText("");
                    } catch(IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });


        }





}
