

import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.File;
/**
 * @author cesar
 *
 *The Following program runs on the command line. If the project foldder is to be run on eclipse
 * the file address would need to  be changed to "./src/www" because the java file and www file
 * are  in the src folder
 */
public class WebServer {

    public static void main(String[] args) throws Exception {

    	try(ServerSocket serverSocket = new ServerSocket(8080)) {
    		System.out.println("Listening on port 8080...");
    		
    		while(true) {
    			try(Socket socket = serverSocket.accept()) {
         
    				PrintStream ps = new PrintStream(socket.getOutputStream(), true, "UTF-8");

    				InputStreamReader isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
    				BufferedReader br = new BufferedReader(isr);
          
    				String httpReq = br.readLine();
    				System.out.println(httpReq);
    				
    				String[] split = httpReq.split(" ");
    				String path = split[1];
    				//System.out.println("./www"+path);
          
    				File file = new File("./www" + path);
    				 //String current = new java.io.File( "." ).getCanonicalPath();
    				 //System.out.println("Current dir:"+current);
    
    				if(file.exists() && file.isFile()) {
       
						BufferedReader forFile = new BufferedReader(new FileReader(file));
    					ps.println("HTTP/1.1 200 OK");
    					ps.println("Content-type: text/html");
    					ps.println("Content-length: " + file.length() + "\n");
    					while(forFile.ready()) {
    						ps.println(forFile.readLine());
    					}
    				} else {

    					File notFound = new File("./www/notFound.html");
    			
						BufferedReader notFoundBR = new BufferedReader(new FileReader(notFound));
    					
    					ps.println("HTTP/1.1 404 Not Found");
    					ps.println("Content-type: text/html");
    					ps.println("Content-length: " + notFound.length() + "\n");
    					
    					while(notFoundBR.ready()) {
    						ps.println(notFoundBR.readLine());
    					}
    				}
    			}
    		}
    	}
    }
}
