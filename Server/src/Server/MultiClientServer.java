/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shoaib
 */
public class MultiClientServer implements Runnable {

    Socket clientsocket;
    BufferedReader reader;
    PrintStream writer;

    String line;

    public MultiClientServer(Socket csoc) {
        try {
            this.clientsocket = csoc;
            reader = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            writer = new PrintStream(clientsocket.getOutputStream());
            ServerGUI.msgview.append("\n Connected to " + clientsocket.getInetAddress().getHostName() + " at " + new java.util.Date().toString());

        } catch (IOException ex) {
            Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void write(String msg) {
        writer.println(msg);
        writer.flush();
        ServerGUI.msgview.append("\n Server to " + clientsocket.getInetAddress().getHostName() + " : "+ msg);
    }

    @Override
    public void run() {
        try {
            while ((line = reader.readLine()) != null) {
                ServerGUI.msgview.append("\n Client " + clientsocket.getInetAddress().getHostName() + " : " + line);
            }
        } catch (IOException ex) {
            Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void exit() {
        try {
            if (clientsocket.isConnected()) {
                writer.println("\n Connection Closed With " + clientsocket.getInetAddress().getHostName());
                clientsocket.close();
             
            }
        } catch (IOException ex) {
            Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
