import java.net.*;
import java.io.*;
class Server{


    ServerSocket server;
    Socket socket;

    BufferedReader br; // For Read the data // It comes from java.io Package
    PrintWriter out;  //  For Write the data // It comes from java.io Package
    // Constructor
    public Server(){

        try {  // We are using try catch because it shows the exception handling 
            server = new ServerSocket(7777); // The 7777 is port number which we are providing 
            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting...........");
            socket=server.accept(); // This line for Accept our Server 
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Input Stream Reader byte data la character made change karto and then reading ch kam buffer reader karel

            out = new PrintWriter(socket.getOutputStream());

            startReading(); // Fuction or Method
            startWriting(); // Fuction or Method

        } catch (Exception e) {
             e.printStackTrace();
        }

    }

    private void startReading() { // While loop lavat ahe because repeat repeat data read karayach ahe 
        // thread-read karke dega rahega
        Runnable r1=()->{
             System.out.println("Reader Started ...............");
 
             try{
             while (true) {
               
                  String msg = br.readLine(); // To Read lines
                  if(msg.equals("exit")){
                    System.out.println("Client terminated the chat");

                    socket.close(); 
                    break;
                  }

                  System.out.println("Client :- "+msg);
                
                              
             }
            }catch(Exception e) {
                //e.printStackTrace(); 
                System.out.println("Connection Close");
             }
        };
        new Thread(r1).start();
        
    }

    private void startWriting() {    
        // thread-data user lega and then send karega client tak     
        Runnable r2=()->{ // It is a Thread 
            System.out.println("Writer Started .........");
            try {
            while (true && !socket.isClosed()) { // While loop lavat ahe because repeat repeat data write karayach ahe 
                

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content =br1.readLine(); // To Read lines
                    out.println(content);
                    out.flush(); // To Flush the data 
                    if(content.equals("exit")){ // If client want to stop the chatting 
                        socket.close(); // To Close the connection
                        break; // To break the loop
                    }
               
            }
        }
        catch (Exception e) {
            // TODO: handle exception
          //  e.printStackTrace();
            System.out.println("Connection Close");
        }

        };

        new Thread(r2).start();
    }


    public static void main(String[] args) {
        System.out.println("This is server ........ going to start");
        new Server(); // To call the constructor 
    }
}