import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class FileSender {
    private JFrame parentFrame;

    public void sendFile(File file, String receiverIP, int port) {
        try (Socket socket = new Socket(receiverIP, port);
             FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
             OutputStream os = socket.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
            JOptionPane.showMessageDialog(parentFrame, "File sent successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parentFrame,
                    "Error sending file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void chooseAndSendFile(String receiverIP, int port) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(parentFrame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            sendFile(selectedFile, receiverIP, port);
        }
    }
}