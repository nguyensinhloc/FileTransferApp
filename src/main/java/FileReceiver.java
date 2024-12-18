import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileReceiver extends Thread {
    private final int port;
    private JFrame parentFrame;
    private final String saveDirectory;

    public FileReceiver(int port, String saveDirectory) {
        this.port = port;
        this.saveDirectory = saveDirectory;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (!Thread.interrupted()) {
                Socket socket = serverSocket.accept();
                receiveFile(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveFile(Socket socket) {
        try (InputStream is = socket.getInputStream()) {
            File directory = new File(saveDirectory);
            if (!directory.exists()) directory.mkdirs();

            String fileName = "received_" + System.currentTimeMillis();
            File outputFile = new File(directory, fileName);

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }

            JOptionPane.showMessageDialog(parentFrame,
                    "File received: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}