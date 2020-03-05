package Proze.ProjektProze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Klasa odpowiadająca za okno menu gry
 * @author KH AW
 * @version 1.0.0  12 czerwca 2019 21:30
 */




class Menu extends JPanel implements ActionListener
{
    /** Zczytywanie z pliku parametrycznego rozmiaru okna
     */
    Parsujaca kl= new Parsujaca();

    public int width;
    public int height;

    /** Ustawianie rozmiarow przyciskow w zaleznosci od rozmiarow okna
     */
    int button_width;
    int button_height;
    public int font_size;

    /** Zmienna odpowiadajaca za zmiane aktualnej zakladki menu na inna
     */
    int state = 0;

    private Sounds click = new Sounds();

    private JButton start_button;
    private JButton highscores_button;
    private JButton options_button;
    private JButton exit_button;
    private JLabel MenuStart;
    private Image image;
    private Image b1;
    private Image b2;
    private Image b3;
    private Image b4;

    public Menu(int x, int y)
    {
        width = x;
        height = y;

        button_width = (int) (width * 0.2);
        button_height = (int) (height * 0.1);

        /** Zczytywanie z pliku parametrycznego co ma byc kolorem tla
         *  Plik graficzny lub kolor okreslony wartosciami RGB
         */
        if (kl.getTlo().equals("jednolite"))
        {

            this.setBackground(new Color(kl.getKlorTla(0),kl.getKlorTla(1),kl.getKlorTla(2)));
        }
        else
        {
            image = Toolkit.getDefaultToolkit().getImage("przycisk/end.gif");
        }

        setLayout(null);
        addComponentListener(new main_window());

        /** Stworzenie i umiejscowienie przyciskow odpowiadajacych za :
         *  Start -> przejscie do okna z prosba o podanie nicku, a nastepnie rozpoczecie gry
         *  Highscores -> przejcie do okna z najlepszymi wynikami
         *  Options -> przejscie do okna z mozliwoscia zmiany opcji
         *  Exit -> wyjscie z menu i zamkniecie okna gry
         *  a także przypisanie im odpowiednich plików graficznych typu gif
         */
        start_button = new JButton();
        b1 = Toolkit.getDefaultToolkit().getImage("przycisk/start.gif");
        b1 = b1.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        start_button.setIcon(new ImageIcon(b1));
        start_button.setSize(button_width, button_height);
        start_button.setLocation((width - button_width)/2, (int) (height * 0.2));
        start_button.addActionListener(new start());
        add(start_button);
        setComponentZOrder(start_button, 0);

        highscores_button = new JButton();
        b2 = Toolkit.getDefaultToolkit().getImage("przycisk/highscores.gif");
        b2 = b2.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        highscores_button.setIcon(new ImageIcon(b2));
        highscores_button.setSize(button_width, button_height);
        highscores_button.setLocation((width - button_width)/2, (int) (height * 0.4));
        highscores_button.addActionListener(new highscores());
        add(highscores_button);
        setComponentZOrder(highscores_button, 0);

        options_button = new JButton();
        b3 = Toolkit.getDefaultToolkit().getImage("przycisk/options.gif");
        b3 = b3.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        options_button.setIcon(new ImageIcon(b3));
        options_button.setSize(button_width, button_height);
        options_button.setLocation((width - button_width)/2, (int) (height * 0.6));
        options_button.addActionListener(new options());
        add(options_button);
        setComponentZOrder(options_button, 0);

        exit_button = new JButton();
        b4 = Toolkit.getDefaultToolkit().getImage("przycisk/exit.gif");
        b4 = b4.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        exit_button.setIcon(new ImageIcon(b4));
        exit_button.setSize(button_width, button_height);
        exit_button.setLocation((width - button_width)/2, (int) (height * 0.8));
        exit_button.addActionListener(new exit());
        add(exit_button);
        setComponentZOrder(exit_button, 0);

        /** Stworzenie i umiejscowienie napisu informujacego w jakiej zakladce menu jestesmy
         */
        if ( (int) (height * 0.069686) <= (int) (width * 0.045)) { font_size = (int) (height * 0.069686); }
        else { font_size = (int) (width * 0.045); }
        MenuStart = new JLabel("Menu Start", SwingConstants.CENTER);
        MenuStart.setFont(new Font("Serif", Font.PLAIN, font_size));
        MenuStart.setSize((int) (width * 0.2125),(int) (height * 0.08711));
        MenuStart.setLocation((width - ((int) (width * 0.2125)))/2, (int) (height * 0.0348));
        MenuStart.setForeground(Color.lightGray);
        add(MenuStart);
        setComponentZOrder(MenuStart, 0);
    }
    /**
     * Zmiana rozmiarów obiektów na podstawie zmiany rozmiarów okna gry
     * @param y wysokość okna
     * @param x szerokość okna
     */


    private void reresize(int x, int y)
    {
        button_width = (int) (x * 0.2);
        button_height = (int) (y * 0.1);

        b1 = Toolkit.getDefaultToolkit().getImage("przycisk/start.gif");
        b1 = b1.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        start_button.setIcon(new ImageIcon(b1));
        start_button.setSize(button_width, button_height);
        start_button.setLocation((x - button_width)/2, (int) (y * 0.2));

        b2 = Toolkit.getDefaultToolkit().getImage("przycisk/highscores.gif");
        b2 = b2.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        highscores_button.setIcon(new ImageIcon(b2));
        highscores_button.setSize(button_width, button_height);
        highscores_button.setLocation((x - button_width)/2, (int) (y * 0.4));

        b3 = Toolkit.getDefaultToolkit().getImage("przycisk/options.gif");
        b3 = b3.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        options_button.setIcon(new ImageIcon(b3));
        options_button.setSize(button_width, button_height);
        options_button.setLocation((x - button_width)/2, (int) (y * 0.6));

        b4 = Toolkit.getDefaultToolkit().getImage("przycisk/exit.gif");
        b4 = b4.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        exit_button.setIcon(new ImageIcon(b4));
        exit_button.setSize(button_width, button_height);
        exit_button.setLocation((x - button_width)/2, (int) (y * 0.8));

        MenuStart.setSize((int) (width * 0.2125),(int) (height * 0.08711));
        MenuStart.setLocation((width - ((int) (width * 0.2125)))/2, (int) (height * 0.0348));
        if ( (int) (height * 0.069686) <= (int) (width * 0.045)) { font_size = (int) (height * 0.069686); }
        else { font_size = (int) (width * 0.045); }
        MenuStart.setFont(new Font("Serif", Font.PLAIN, font_size));

    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setDoubleBuffered(true);
        g.drawImage(image, 0, 0, width, height, this);
    }

    /** Akcje odpowiadajaca zdarzeniu nacisnieciu przycisku
     *  Zmieniamy zmienna state na wartosc rozna od 0
     *  Wartosc 1 odpowiada akcji przejscia do okna z prosba o nick gracza
     *  Wartosc 2 odpowiada akcji przejscia do okna z najlepszymi wynikami
     *  Wartosc 3 odpowiada akcji przejscia do okna ze zmiana opcji
     *  Przycisk Exit odpowiada opuszczeniu menu i zamknieciu programu'
     *  Przy wykonaniu każdej z tych akcji zabrzmi dźwięk Button.wav
     */
    private class start implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            state = 1;
        }
    }

    private class highscores implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            state = 2;
        }
    }

    private class options implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            state = 3;
        }
    }

    private class exit implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            System.exit(0);
        }
    }

    private class main_window implements ComponentListener
    {
        @Override
        public void componentResized(ComponentEvent e)
        {
            width = getWidth();
            height = getHeight();

            reresize(width, height);

            repaint();
        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }
    }

    /** Klasa zawiera repaint potrzebne do aktualizacji wygladu aplikacji
     */
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }
}
