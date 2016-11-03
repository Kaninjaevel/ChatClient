import org.junit.Test;

import java.io.IOException;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class ChatClient_JUnitTest {

    /**
     * Tests port out of range
     * @throws IOException if port out of range
     */
    @Test (expected = Exception.class)
    public void invalidNumberOfArguments() throws IOException, InterruptedException {
        new ChatClient("OOMP","ns","127.0.0.1",2202222);
    }

    /**
     * Tests wrong serverType
     */
    @Test (expected = Exception.class)
    public void invalidServerType() throws IOException, InterruptedException {
        new ChatClient("OOMP","qw","127.0.0.1",2222);
    }

    /**
     * Tests invalid Identity
     */
    @Test (expected = Exception.class)
    public void invalidIdentity() throws IOException, InterruptedException {
        String string = "a";
        for(int i=0;i<300;i++)
            string=string+"a";
        new ChatClient(string,"cs","127.0.0.1",2222);
    }

    /**
     * Tests invalid Server
     */
    @Test (expected = Exception.class)
    public void noValidServer() throws IOException, InterruptedException {
        new ChatClient("Kalle","cs","127.0.0.1",2222);
    }

}
