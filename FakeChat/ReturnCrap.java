package FakeChat;

import java.io.IOException;
import java.util.Random;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class ReturnCrap extends AbstractFakeChat {
    int i=0;
    public ReturnCrap(int port) throws IOException {
        super(port);
    }


    /**
     * #1 Returns Participants correct
     * #2 Returns 20 random bytes
     * @throws IOException
     */
    @Override
    void returnInfo() throws IOException {
        byte[] b= {19,2,0,9,'P','0',0,'K','0','0','L','E',0,0,0,0};
        byte[] b2 = new byte[20];
        new Random().nextBytes(b2);

        if(i==0) {
                out.write(b);
                i++;
        }
        else
            out.write(b2);
    }

    public static void main(String[] args) throws IOException {
        new ReturnCrap(new Integer(args[0]));
    }
}
