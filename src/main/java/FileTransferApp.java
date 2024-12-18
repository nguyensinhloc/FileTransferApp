import javax.swing.*;
import java.awt.*;

public class FileTransferApp extends JFrame {
    private JTextField receiverIPField;
    private JTextField portField;
    private final FileSender fileSender;
    private FileReceiver fileReceiver;

    public FileTransferApp() {
        setTitle("File Transfer Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        fileSender = new FileSender();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FileTransferApp().setVisible(true);
        });
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("My IP:"));
        JTextField myIPField = new JTextField(NetworkUtils.getLocalIPAddress());
        myIPField.setEditable(false);
        panel.add(myIPField);

        panel.add(new JLabel("Receiver IP:"));
        receiverIPField = new JTextField();
        panel.add(receiverIPField);

        panel.add(new JLabel("Port:"));
        portField = new JTextField(String.valueOf(NetworkUtils.findAvailablePort()));
        panel.add(portField);

        JButton sendButton = new JButton("Send File");
        sendButton.addActionListener(e -> sendFile());
        panel.add(sendButton);

        JButton startReceivingButton = new JButton("Start Receiving");
        startReceivingButton.addActionListener(e -> startReceiving());
        panel.add(startReceivingButton);

        add(panel);
    }

    private void sendFile() {
        String receiverIP = receiverIPField.getText();
        int port = Integer.parseInt(portField.getText());
        fileSender.chooseAndSendFile(receiverIP, port);
    }

    private void startReceiving() {
        int port = Integer.parseInt(portField.getText());
        fileReceiver = new FileReceiver(port, System.getProperty("user.home") + "/Downloads");
        fileReceiver.start();
    }
}