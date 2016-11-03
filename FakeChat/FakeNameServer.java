package FakeChat;

import java.io.IOException;

/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class FakeNameServer extends AbstractFakeChat {

    public FakeNameServer(int port) throws IOException {
        super(port);
    }

    /**
     * Returns an invalid SLIST after receiving a pdu
     * @throws IOException
     */
    @Override
    void returnInfo() throws IOException {
        byte[] b= {(byte)4,0,0,2,127,0,0,1,0,20,1,2,'T','A',0
                ,1,127,0,0,1,0,20,1,2,'T','S',0,0};
            out.write(b);
    }

    public static void main(String[] args) throws IOException {
        new FakeNameServer(new Integer(args[0]));
    }
}
