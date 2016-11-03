package PDU;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

public class PDU_SLIST extends PDU {

    int noOfServers;
    InputStream in;
    ServerInfo[] serverInfos;

    public PDU_SLIST(InputStream inputStream) throws IOException {
        byte[] bA;
        byte b;
        in=inputStream;
        byteSequenceBuilder= new ByteSequenceBuilder((byte)4);
        byteSequenceBuilder.append((byte)in.read());
        boolean isCorrupt=false;

        //Checking number of servers.

        bA=new byte[2];
        in.read(bA,0,2);
        byteSequenceBuilder.append(bA);
        noOfServers=((bA[0] & 0xff) << 8) | (bA[1] & 0xff);


        serverInfos = new ServerInfo[noOfServers];

        //Collecting information of all available servers and saving that data.
        for(int i=0;i<noOfServers;i++) {
            serverInfos[i]=new ServerInfo();


            //Setting address of server.
            bA=new byte[4];
            in.read(bA,0,4);
            byteSequenceBuilder.append(bA);
            serverInfos[i].setAddress(bA);


            //Setting port of server
            bA=new byte[2];
            in.read(bA,0,2);
            byteSequenceBuilder.append(bA[0],bA[1]);
            serverInfos[i].setPort(((bA[0] & 0xff) << 8)
                    | (bA[1] & 0xff));


            //Setting number of clients on server
            serverInfos[i].setNoOfClients(in.read());
            byteSequenceBuilder.append((byte)serverInfos[i].getNoOfClients());


            //Setting server namelength
            int serverNameLength=in.read();
            byteSequenceBuilder.append((byte)serverNameLength);

            //Setting server name

            bA=new byte[serverNameLength];

            in.read(bA,0,serverNameLength);
            byteSequenceBuilder.append(bA);
            serverInfos[i].setName(new String(bA,"UTF-8"));

            bA= new byte[1];
            //Adding Padding from stream and checking for non-zero paddings
            if(!(serverNameLength%4==0))
                for(int j=0;j<(4-(serverNameLength%4));j++) {
                    in.read(bA,0,1);
                    if(bA[0]!=0)
                        isCorrupt=true;
                    byteSequenceBuilder.append(bA[0]);
                }
        }
        if(isCorrupt) {
            bytes = byteSequenceBuilder.toByteArray();
            for(int j=0;j<bytes.length;j++)
                System.out.print(bytes[j] + " ");
            System.out.println("\n");
            bytes = null;
        }
        else
            bytes=byteSequenceBuilder.toByteArray();
    }

    @Override
    public void print() {
        for(int i=0;i<noOfServers;i++){serverInfos[i].print();}
    }

    /**
     * Inner class used to list and save information about servers.
     */
    private class ServerInfo{
        private String address;
        private int port;
        private String name;
        private int noOfClients;

        ServerInfo(){}

        void setAddress(byte[] address){
            this.address=(address[0]&0xFF) + "." + (address[1]& 0xFF)
                    +"."+(address[2]& 0xFF)+"."+(address[3]& 0xFF);
        }
        void setPort(int port){this.port=port;}
        void setName(String name){this.name=name;}
        void setNoOfClients(int noOfClients){
            this.noOfClients=noOfClients;}

        String getAddress(){return address;}
        int getPort(){return port;}
        String setName(){return name;}
        int getNoOfClients(){return noOfClients;}

        void print(){
            System.out.println("<" + name + "> " + address + ":" + port
                    +" Number of clients: " + noOfClients);
        }
    }
}
