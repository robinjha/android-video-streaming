import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Phone2PCVideoReceiver {

    public static JLabel jlb = new JLabel();// GUI container that will hold the image object

    public static JFrame jfr = new JFrame(); // GUI container that will hold the container containing the image


    public Phone2PCVideoReceiver() {

	}

    public static void main(String[] args) {

        try {

            DataInputStream dis;

            ServerSocket server = new ServerSocket(8000);// listening for connections on port 8000

            Socket socket = server.accept();// accept and connect to a scocket


            while (true) {


                dis = new DataInputStream(socket.getInputStream());    // create an input stream

                int len = dis.readInt();// read the length of the byte array sent by the client transmitting video


                byte[] buffer = new byte[len]; // initialize a byte array of the length received from the client

                dis.readFully(buffer, 0, len);  // read the byte array data sent from the client and write it into the buffer array


                InputStream in = new ByteArrayInputStream(buffer);

                BufferedImage im = ImageIO.read(in); // creating an image from the byte array

                jlb.setIcon(new ImageIcon(im));// setting the image onto the JLabel container

                jfr.add(jlb);// JLabel container being added to the JFrame container

                jfr.pack();

                jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                jfr.setVisible(true);

                System.gc();


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}