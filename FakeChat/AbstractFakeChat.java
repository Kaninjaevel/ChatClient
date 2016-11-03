package FakeChat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */


 /**
 * An abstract class representing a similated chat-server
 * that returns information specified by implementations.
 */
abstract class AbstractFakeChat {
    ServerSocket sSocket;
    Socket socket;
    InputStream in;
    OutputStream out;

    public AbstractFakeChat(int port) throws IOException {
        sSocket = new ServerSocket(port);
        socket = sSocket.accept();
        in = socket.getInputStream();
        out = socket.getOutputStream();

        new Thread(){
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        byte b[] = new byte[in.available()];
                        if (in.available() != 0) {
                            in.read(b, 0, b.length);
                            returnInfo();
                            sleep(500);
                        }

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    //Specifies what information should be returned when a pdu is received.
    abstract void returnInfo() throws IOException;

}
