import PDU.*;
import Threads.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;
/**
 * Course: 5DV167
 * Written by: Thomas Sarlin
 * @Author Thomas Sarlin
 * @Version 1.0 13/10-2016
 */

/**
 * Class representing a terminal based chat-application.
 * Uses TCP to connect to a given chatserver/nameserver via
 * address and port. The server must be running the same PDU-protocol as
 * given by the course 5DV167.
 */

public class ChatClient {
    private String serverAddress;
    private int serverPort;
    private String clientName;
    private Socket socket;
    private CSInThread inThread;
    private CSOutThread outThread;


    /**
     * Constructs a chat-client
     * @param identity name of client
     * @param serverType ns/cs representing chat-server/name-server.
     * @param host address to specified name/chat-server.
     * @param port port that the specified server listens to.
     * @throws IOException in case the server is invalid/non-existing. or
     *                      if the arguments are invalid.
     */
    public ChatClient(String identity, String serverType
            , String host,int port) throws IOException, InterruptedException {

        checkInput(identity,serverType,host,port);
        clientName=identity;
        serverAddress=host;
        serverPort=port;


        System.out.println("Welcome " + clientName);

        switch (serverType) {
            case "cs":
                connectToChatServer();
                chat();
                break;
            case "ns":
                connectToNameServer();
                selectChatServer();
                connectToChatServer();
                chat();
                break;
        }
    }

    /**
     * Checks so that all input arguments are valid.
     * @param identity name of client.
     * @param serverType ns/cs representing name/chatserver
     * @param host address of server
     * @param port port of server.
     * @throws IOException
     */
    private void checkInput(String identity, String serverType
            , String host,int port) throws IOException{

        if(identity.length()>255){
            throw new IOException("Identity larger than 255 characters");
        }
        else if(!serverType.equals("cs")&&!serverType.equals("ns")){
            throw new IOException("Invalid serverType,(cs,ns)");
        }
        else if(!correctPort(port)){
            throw new IOException("Invalid port");
        }
    }

    /**
     * Method to connect to name-server.
     * @throws IOException if socket is unable to connect.
     */
    private void connectToNameServer() throws IOException {
        socket = new Socket(serverAddress,serverPort);
        new NSCommunication(socket);

    }

    /**
     * Lets the user select Chat-server by writing it's ipv4-address and port.
     */
    private void selectChatServer(){
        String[] splitAddress;
        String[] splitIP;
        boolean validInput=false;

        while(!validInput) {
            System.out.print("Write address and port of selected " +
                    "server: ");

            //Separating IP-address & port
            splitAddress = new Scanner(System.in).nextLine().split("\\:");

            //Separating all parts of the Ipv4 address.
            splitIP = splitAddress[0].split("\\.");

            if (splitAddress.length != 2||splitIP.length!=4) {
                System.out.println("\n--Invalid format " +
                        "(xxx.xxx.xxx.xxx:p)--\n");
            } else if (correctPort(new Integer(splitAddress[1]))
                    &&correctAddress(splitAddress[0])) {
                serverAddress=splitAddress[0];
                serverPort=new Integer(splitAddress[1]);
                validInput = true;
            }
        }
    }

    /**
     * Used to connect to a chat-server
     * @throws IOException in case socket is unable to connect/is rejected.
     */
    private void connectToChatServer() throws IOException {
        System.out.println("Connecting to chat-server.. "
                + "<" + serverAddress +":" + serverPort+ ">");

        socket = new Socket(serverAddress,serverPort);
        inThread = new CSInThread(socket);
        outThread = new CSOutThread(socket);
        outThread.start();
        inThread.start();
        outThread.sendPDU(new PDU_JOIN(clientName));
    }

    /**
     * Used in the select server method to see if ip is within ipv4 range.
     * @param address Ipv4-address
     * @return representation of the ipv4s validity.
     */
    private boolean correctAddress(String address) {
        boolean isValid=true;

        String[] temp = address.split("\\.");
        for(int i =0;i<temp.length;i++)
            if((new Integer(temp[i])<0||new Integer(temp[i])>255)){
                isValid=false;
                System.out.println("\n--IP out of range" +
                        "(0-255)--\n");
            }
        return isValid;
    }

    /**
     * Checks if port is within range.
     * @param port port to be checked.
     * @return representation of the ports validity.
     */
    private boolean correctPort(int port){
        boolean validPort=true;

        if (port<0 | port> 65535) {
            System.out.println("\n--Port number out of range" +
                    "(0-65553)--\n");
            validPort=false;
        }
        return validPort;
    }

    /**
     * The method responsible for running the chat, reading from system in
     * and creating a PDU representing the message of the client.
     * Chat commands:
     * /quit
     * /help
     * @throws IOException
     */
    private void chat() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String message;
        boolean chat=true;

        //Thread that prints PDUs to console.
        Thread thread = new Thread(){
            @Override
            public void run(){
                boolean receive= true;
                while(receive) {
                    if (!inThread.queueIsEmpty()) {
                        if (inThread.pduIsQuit()) {
                            inThread.printNextPDU();
                            receive = false;
                        } else if (inThread.isValidPDU() &&
                                !inThread.pduIsQuit())
                            inThread.printNextPDU();
                        else if(!inThread.isValidPDU()){
                            inThread.printNextPDU();
                            receive=false;
                            try {
                                outThread.stop();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        };
        thread.start();


        //Takes input from user and sends to chat-server.
        while(thread.isAlive()){
            if(scanner.hasNextLine()){
                message = scanner.nextLine();
                if(!message.equals("/quit")&&chat&&thread.isAlive())
                    outThread.sendPDU(new PDU_MESS(message,clientName));
                else if(chat&&thread.isAlive()){
                    outThread.stop();
                    chat=false;
                }
            }
        }

        //Waiting for the threads to close
        System.exit(0);
        socket.close();
    }
    /**
     * @param args Identity,Server Type,Host Address,Host Port.
     * @throws IOException if the arguments are invalid.
     */

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length==4){
            new ChatClient(args[0],args[1],args[2],new Integer(args[3]));
        }
        else
            throw new IOException("Invalid number of arguments" +
                    ",(Identity,ServerType,HostAddress,HostPort)");
    }
}
