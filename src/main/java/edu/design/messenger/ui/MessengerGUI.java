package edu.design.messenger.ui;

import edu.design.messenger.network.SocketMessageListener;
import edu.design.messenger.network.SocketMessageSender;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.io.IOException;
import java.text.NumberFormat;


@Slf4j
public class MessengerGUI extends JFrame {
    private JPanel rootPanel;
    private JTextField nickTextField;
    private JTextField buddyAddressTextField;
    private JTextArea chatArea;
    private JTextField messageTextField;
    private JButton connectButton;
    private JButton sendButton;
    private JFormattedTextField portTextField;

    public MessengerGUI() throws HeadlessException {
        super("Instant chat!");

        setContentPane(rootPanel);

        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        connectButton.addActionListener(e -> {

            try {
                String nick = nickTextField.getText();
                String address = buddyAddressTextField.getText();
                int port = (int) portTextField.getValue();

                SocketMessageSender sender = new SocketMessageSender(address, port);
                sendButton.addActionListener(e1 -> {
                    try {
                        sender.sendMessage(nick, messageTextField.getText());
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        try {
                            sender.close();
                        } catch (IOException e3) {
                            log.error("Exception while closing sender", e3);
                        }
                    }
                });

                SocketMessageListener listener = new SocketMessageListener(textMessage -> chatArea.append(textMessage.getNick() + ": " + textMessage.getText()));
                new Thread(() -> {
                    try {
                        listener.listen(port);
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(this, "Exception during listening." + e1.getMessage());
                    } finally {
                        setEnabledSettings(true);
                    }
                }).start();

                setEnabledSettings(false);

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, "Exception: " + e1.getMessage());
                setEnabledSettings(true);
            }
        });


        setEnabledSettings(true);


        setVisible(true);
    }

    private void setEnabledSettings(boolean value) {
        nickTextField.setEnabled(value);
        buddyAddressTextField.setEnabled(value);
        portTextField.setEnabled(value);
        connectButton.setEnabled(value);

        sendButton.setEnabled(!value);
    }

    public static void main(String[] args) {
        MessengerGUI messengerGUI = new MessengerGUI();

    }


    private void createUIComponents() {
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        portTextField = new JFormattedTextField(formatter);

    }
}
