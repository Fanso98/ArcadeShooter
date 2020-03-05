package Proze.ProjektProze;

import java.awt.*;
import java.io.*;
import javax.swing.*;

/**
 * Główna klasa
 * @author KH AW
 * @version 1.0.0  12 czerwca 2019 21:30
 */


 public class Okno extends JFrame {

     /** Zmienne odpowiadajace wymiarom okna z pliku parametrycznego
      *  Oraz zmienne odpowiadajace wymiarom ekranu
      *  a także odpowiadające za czas i dźwięk
      */
    private int width;
    /**
     *wysokość okna
     */
    private int height;
    /**
     *szerokość ekranu
     */
    private int screen_width;
    /**
     *wysokość okna
     */
    private int screen_height;
    /**
     *zmienna przechowująca aktualny czas
     */
    private long timex=0;
    /**
     *zmienna odpowiedzialna za status dźwięku
     */
    private int mute = 0;
    private String s_name;

     /** Instancje wszystkich pozostalych klas gdyz potem odnosimy sie do nich z tej klasy
      */
    private Menu menu;
    /**
     *isntacja Nick
     */
    private Nick nick;
    /**
     *Instancja Highscores
     */
    private Highscores highscores;
    /**
     *Instancja Options
     */
    private Options options;
    /**
     *Instancja Game
     */
    private Game game;
    /**
     *Instancja Ending
     */
    private Ending ending;
    /**
     *Instancja Music
     */
    private Music music;
    /**
     *Instancja Difficulty
     */
    private Difficulty difficulty;
    /**
     *scieżka do pliku z najlepszymi wynikami
     */
    private String filepath = "./Highscores.txt";

    private boolean file_exist = true;

    private boolean shorten = false;

    private String o_nick;
    private int lines_numb;
    /**
     *instanca Record
     */
    private Record highscores_tab;
    /**
     *Wywołanie konstruktora klasy parsującej
     * */
    Parsujaca kl;

    private Okno()
    {
        kl = new Parsujaca();

        kl.pobierzZSerwera("localhost", 1338);

        /** Zczytywanie z pliku parametrycznego rozmiaru okna
         */
        lines_numb = get_file_lines_numb(filepath);
        highscores_tab = new Record(lines_numb);
        get_highscores_file(filepath, highscores_tab);
        width = kl.getPoczatkowaSzerokoscPlanszy();
        height = kl.getPoczatkowaWysokoscPlanszy();
        CustomCursor();
        /** Zczytywanie wymiarow ekranu
         */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        screen_height = screenSize.height;
        screen_width = screenSize.width;
        /** Wyswietlanie okna na srodku ekranu oraz ustawienie jego tytulu na podstawie pliku parametrycznego
         *  Ustawianie widocznosci okna i mozliwosci zmiany jego rozmiarow
         */
        setLocation((screen_width - width)/2, (screen_height - height)/2);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(kl.getNazwaGry()); // Tytul gry jest brany z klasy parsujacej
        setVisible(true);
        setResizable(true); // Możliwość zmiany rozmiaru okna
        /** Tworzenie obiektow pozostalych klas
         */
        menu = new Menu(width, height);
        nick = new Nick(width, height);
        options = new Options(width, height);
        difficulty = new Difficulty(width,height);
        music = new Music();
        music.CustomSoundBackground("Muzyka.wav");
    }
/**
 *Metoda odpowiada za zebranie pewnej liczby wyników która nie może przekroczyć 7
 */

    private int get_file_lines_numb(String filepath)
    {
        int i = 0;
        if ( new File(filepath).exists())
        {
            BufferedReader bf = null;

            try
            {
                bf = new BufferedReader(new FileReader(filepath));
                String line;
                while ((line = bf.readLine()) != null)
                {
                    i++;
                }
            }
            catch (FileNotFoundException ex)
            {
                file_exist = false;
                return 1;
            }
            catch (IOException ex) {}
            finally
            {
                try
                {
                    bf.close();
                }
                catch (IOException ex){}
            }
        }
        if ( i < 7)
        {
            i++;
            shorten = true;
        }
        return i;
    }
    /**
     * Pobranie pliku z wynikami poprzednich użytkowników gry
     * @param filepath scieżka do pliku zawierającego dane o użytkownikach i ich wynikach
     * @param tab tablica zawierająca nazwę użytkownika i wynik
     * */
    private void get_highscores_file(String filepath, Record tab)
    {
        if (new File(filepath).exists())
        {
            BufferedReader bf = null;

            try
            {
                bf = new BufferedReader(new FileReader(filepath));
                System.out.println("File found");
                String line;
                int points;
                int i = 0;

                while ((line = bf.readLine()) != null)
                {
                    String[] words = line.split(" ");
                    try {points = Integer.parseInt(words[1]);}
                    catch (NumberFormatException e) {points = 0;}

                    tab.nickname[i] = words[0];
                    tab.points[i] = points;
                    i++;
                }
            }

            catch (FileNotFoundException ex)
            {
                System.out.println("File disappeard");
                file_exist = false;
            }
            catch (IOException ex){}
            finally
            {
                try
                {
                    bf.close();
                }
                catch (IOException ex){}
            }
        }
        else
        {
            System.out.println("File disappeard");
            file_exist = false;
        }
    }
    /**
     * Zastąpienie domyślnego kursora obrazem typu png (celownik)
     * */

    public void CustomCursor()
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image img = toolkit.getImage("kursor.png");
        Point points = new Point(15,15);
        Cursor Cursor = toolkit.createCustomCursor(img,points,"Cursor");
        setCursor(Cursor);
    }

    /** Metoda wywolujaca menu glowne
     *  Petla nieskonczona while(true) sluzy do zczytywania parametru state klasy Menu
     *  Tak dlugo az nie zostanie wychwycona akcja nacisniecia ktoregos przycisku
     */
    private void call_menu()
    {
        menu.setSize(width, height);
        add(menu);
        repaint();

        while(true)
        {
            try {Thread.sleep(100);}
            catch (InterruptedException ex){}
            if (menu.state != 0)
            {
                getContentPane().removeAll();
                break;
            }
            else continue;
        }

        if (menu.state == 1)
        {
            menu.state = 0;
            call_nick();
        }
        else if (menu.state == 2)
        {
            highscores = new Highscores(width, height);
            highscores.highscores_tab = highscores_tab;
            highscores.lines_numb = lines_numb;
            highscores.set_text();

            menu.state = 0;
            call_highscores();
        }
        else if (menu.state == 3)
        {
            menu.state = 0;
            call_options();
        }
    }

     /** Metoda wywolujaca okno z prosba o podanie nicku
      *  Petla nieskonczona while(true) sluzy do zczytywania parametru state klasy Nick
      *  Tak dlugo az nie zostanie wychwycona akcja nacisniecia ktoregos przycisku
      */
    private void call_nick()
    {
        nick.setSize(width, height);
        add(nick);
        repaint();

        while(true)
        {
            try {Thread.sleep(100);}
            catch (InterruptedException ex){}
            if (nick.state != 0)
            {
                getContentPane().removeAll();
                break;
            }
            else continue;
        }

        if (nick.state == 1)
        {
            o_nick = nick.n_nick;
            nick.state = 0;
            call_difficulty();
        }
        else if (nick.state == -1)
        {
            nick.state = 0;
            call_menu();
        }
    }
    /**
     * Metoda wywołuje okno wybrania trudności gry
     * Umożliwia wybór trudności który ma wpływ na szybkość poruszania się, zmianę wielkości obiektów a także
     * liczbę punktów jakie możemy dostać za trafione cele.
     */

    private void call_difficulty()
     {
         difficulty.setSize(width, height);
         add(difficulty);
         repaint();

         while(true)
         {
             try { Thread.sleep(100); }
             catch (InterruptedException ex) {}
             if (difficulty.state != 0)
             {
                 getContentPane().removeAll();
                 break;
             }
             else continue;
         }

         if (difficulty.state == 1)
         {
             game = new Game(width, height);
             game.g_name = o_nick;
             game.shorten = shorten;
             game.file_exist = file_exist;
             game.lines_numb = lines_numb;
             game.highscores_tab = highscores_tab;

             difficulty.state = 0;
             game.pointsmultipl = 1;

             game.wsp = 0.1f;
             call_game();

         }
         if (difficulty.state == 2)
         {
             game = new Game(width, height);
             game.g_name = o_nick;
             game.shorten = shorten;
             game.file_exist = file_exist;
             game.lines_numb = lines_numb;
             game.highscores_tab = highscores_tab;

             difficulty.state = 0;
             game.pointsmultipl = 2;

             game.wsp = 0.1f + 0.1f * (float) (kl.getZmianaStopniaTrudnosci()/40);
             call_game();

         }
         if (difficulty.state == 3)
         {
             game = new Game(width, height);
             game.g_name = o_nick;
             game.shorten = shorten;
             game.file_exist = file_exist;
             game.lines_numb = lines_numb;
             game.highscores_tab = highscores_tab;

             difficulty.state = 0;
             game.pointsmultipl = 3;

             game.wsp = 0.1f + 0.1f * 2 * (float) (kl.getZmianaStopniaTrudnosci() / 40);
             call_game();
         }
         if (difficulty.state == 4)
         {
             game = new Game(width, height);
             game.g_name = o_nick;
             game.shorten = shorten;
             game.file_exist = file_exist;
             game.lines_numb = lines_numb;
             game.highscores_tab = highscores_tab;

             difficulty.state = 0;
             game.pointsmultipl = 4;

             game.wsp = 0.0f;
             call_game();

         }
         if (difficulty.state == 5)
         {
             game = new Game(width, height);
             game.g_name = o_nick;
             game.shorten = shorten;
             game.file_exist = file_exist;
             game.lines_numb = lines_numb;
             game.highscores_tab = highscores_tab;

             difficulty.state = 0;
             game.pointsmultipl = 5;

             game.wsp = 0.1f + 0.1f * 4 * (float) (kl.getZmianaStopniaTrudnosci() / 40);
             call_game();

         }
         if (difficulty.state == -1)
         {
             game = new Game(width, height);
             game.g_name = o_nick;
             game.shorten = shorten;
             game.file_exist = file_exist;
             game.lines_numb = lines_numb;
             game.highscores_tab = highscores_tab;

             difficulty.state = 0;
             call_nick();
         }
     }

     /** Metoda wywolujaca okno z najlepszymi wynikami
      *  Petla nieskonczona while(true) sluzy do zczytywania parametru state klasy Highscores
      *  Tak dlugo az nie zostanie wychwycona akcja nacisniecia ktoregos przycisku
      */
    private void call_highscores()
    {
        highscores.setSize(width, height);
        add(highscores);
        repaint();

        while(true)
        {
            try {Thread.sleep(100);}
            catch (InterruptedException ex){}
            if (highscores.state != 0)
            {
                getContentPane().removeAll();
                break;
            }
            else continue;
        }

        if (highscores.state == -1)
        {
            highscores.state = 0;
            call_menu();
        }
    }

     /** Metoda wywolujaca okno z mozliwoscia zmiany opcji
      *  Petla nieskonczona while(true) sluzy do zczytywania parametru state klasy Options
      *  Tak dlugo az nie zostanie wychwycona akcja nacisniecia ktoregos przycisku
      */
    private void call_options()
    {
        options.setSize(width, height);
        add(options);
        repaint();

        while(true)
        {
            try {Thread.sleep(100);}
            catch (InterruptedException ex){}
            if (options.state != 0)
            {
                getContentPane().removeAll();
                break;
            }
            else
            {
                if (options.vol == -1)
                {
                    if (music.vol == -30)
                    {
                        music.vol = -30;
                    }
                    else music.vol--;
                    options.vol = 0;


                    timex=music.pauseSound();
                    music.playSound(timex);
                }
                if (options.vol == 1)
                {
                    if (music.vol == 6)
                    {
                        music.vol = 6;
                    }
                    else music.vol++;
                    options.vol = 0;
                    timex=music.pauseSound();
                    music.playSound(timex);
                }
                else
                    {
                        if(options.pause==1)
                        {
                            if(mute%2==0)
                            {
                                timex=music.pauseSound();
                                mute++;
                                options.pause=0;
                            }
                            else
                            {
                                music.playSound(timex);
                                mute++;
                                options.pause=0;
                            }

                        }

                    }
                continue;
            }
        }

        if (options.state == -1)
        {
            options.state = 0;
            call_menu();
        }
    }

     /** Metoda wywolujaca okno z grą
      */
     private void call_game()
     {
         width = getWidth();
         height = getHeight();
         game.setSize(width, height);
         add(game);
         repaint();

         while(true)
         {
             try {Thread.sleep(100);}
             catch (InterruptedException ex){}
             if (game.state != 0)
             {
                 getContentPane().removeAll();
                 break;
             }
             else continue;
         }

         if (game.state == 1)
         {
             ending = new Ending(width, height, o_nick, game.points);

             game.state = 0;
             call_ending();
         }
     }
    /** Metoda wywolujaca okno z końcowe gry
     */
     private void call_ending()
     {
         ending.setSize((int) (width * 0.6), (int) (height * 0.6));
         add(ending);
         repaint();

         while(true)
         {
             try {Thread.sleep(100);}
             catch (InterruptedException ex){}
             if (ending.state != 0)
             {
                 getContentPane().removeAll();
                 break;
             }
             else continue;
         }

         if (ending.state == 1)
         {
             nick.state = 0;
             call_difficulty();
         }
     }

     /** Funkcja main
      */
    public static void main(String[] args)
    {
        Okno okno = new Okno();
        okno.call_menu();
    }
}