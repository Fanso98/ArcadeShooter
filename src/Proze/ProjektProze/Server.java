package Proze.ProjektProze;

import java.net.*;
import java.io.IOException;

/**
 * Serwer, komunikuje sie z klientem, wysluchuje jego zadania
 *
 * @author SJ RK
 * @version 1.0.0  26 maja 2019 21:30
 */
public class Server {
    /// numer portu
    static final int PORT = 1338;
    /**
     * gÄąâ€šÄ‚Ĺ‚wna metoda programu
     */
    public static void main(String args[])
    {
        /// utworzenie socketu serwera
        ServerSocket serverSocket = null;
        Socket socket = null;
        ///Parsowanie pliku z zasobÄ‚Ĺ‚w serwera zawierajacego parametry
        Parsujaca parametry = new Parsujaca();
        /// wpisany numer portu 1338 -> klient polaczy sie z tym portem
        try { serverSocket = new ServerSocket(PORT); }
        catch (IOException e) {e.printStackTrace();}

        while (true)
            try {
                /// "akceptacja" klienta, utworzenie polaczenia
                socket = serverSocket.accept();
                System.out.println("Podlaczyl sie nowy klient: " + socket.getRemoteSocketAddress().toString());
                ///uruchomienie nowego watku obslugi klienta
                ServerThread st = new ServerThread(socket, parametry);
                st.start();
            } catch (Exception e) {System.err.println("Server exception: " + e);}
    }

}
