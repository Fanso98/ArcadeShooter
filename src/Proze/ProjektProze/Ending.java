package Proze.ProjektProze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
/**
 * Klasa odpwiadająca za okno z grą.
 * @author KH AW
 * @version 1.0.0  12 czerwca 2019 21:30
 */

public class Ending extends JPanel implements ActionListener
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
    int font_size;

    /** Zmienna odpowiadajaca za zmiane aktualnej zakladki menu na inna
     */
    int state = 0;

    private Sounds click = new Sounds();

    public float points;
    public String e_nick;

    private JButton end_game_button;
    private JButton one_more_time_button;
    private JLabel nickname;
    private JLabel nickname2;
    private Image image;
    private Image b1;
    private Image b2;

    /**
     * Konstruktor klasy Nick
     * @param x szerokość okna;
     * @param y wysokość okna;
     */
    public Ending(int x, int y, String nick, float z)
    {
        width = x;
        height = y;
        e_nick = nick;
        points = z;

        button_width = (int) (width * 0.2);
        button_height = (int) (height * 0.1);

        /** Zczytywanie z pliku parametrycznego co ma byc kolorem tla
         *  Plik graficzny lub kolor okreslony wartosciami RGB
         */
        if (kl.getTlo().equals("jednolite"))
        {
            this.setBackground(new Color(kl.getKlorTla(0),kl.getKlorTla(1),kl.getKlorTla(2)));
            this.setSize(width, height);
        }
        else
        {
            image = Toolkit.getDefaultToolkit().getImage("przycisk/end.gif");
        }

        setLayout(null);
        addComponentListener(new main_window());


        /** Stworzenie i umiejscowienie przycisku odpowiadajacego za przejscie dalej
         *  W przyszlosci bedzie to przycisk akceptujacy nasz nick
         */
        end_game_button = new JButton("Exit the game");
        b1 = Toolkit.getDefaultToolkit().getImage("przycisk/exit.gif");
        b1 = b1.getScaledInstance(button_width + 11, button_height, Image.SCALE_DEFAULT);
        end_game_button.setIcon(new ImageIcon(b1));
        end_game_button.setSize(button_width, button_height);
        end_game_button.setLocation((width - button_width)/4, (int) (height * 0.6));
        end_game_button.addActionListener(new end_game_b());
        add(end_game_button);
        setComponentZOrder(end_game_button, 0);

        /** Stworzenie i umiejscowienie przycisku odpowiadajacego za powrot do menu glownego
         */
        one_more_time_button = new JButton("One more time?");
        b2 = Toolkit.getDefaultToolkit().getImage("przycisk/goagain.gif");
        b2 = b2.getScaledInstance(button_width + 11, button_height, Image.SCALE_DEFAULT);
        one_more_time_button.setIcon(new ImageIcon(b2));
        one_more_time_button.setSize(button_width, button_height);
        one_more_time_button.setLocation((int) ((width - button_width)*0.75), (int) (height * 0.6));
        one_more_time_button.addActionListener(new one_more_time_b());
        add(one_more_time_button);
        setComponentZOrder(one_more_time_button, 0);

        /** Stworzenie i umiejscowienie napisu proszacego nas o podanie nicku
         *  W przyszlosci w tym oknie bedziemy podawac swoj nick
         */
        if ( (int) (height * 0.069686) <= (int) (width * 0.045)) { font_size = (int) (height * 0.069686); }
        else { font_size = (int) (width * 0.045); }
        nickname = new JLabel("Great job " + e_nick + "!!", SwingConstants.CENTER);
        nickname.setFont(new Font("Serif", Font.PLAIN, font_size));
        nickname.setSize((int) (width * 0.2125 * 4),(int) (height * 0.08711));
        nickname.setLocation((width - ((int) (width * 0.2125 * 2)))/2, (int) (height * 0.0348));
        nickname.setForeground(Color.lightGray);
        add(nickname);
        setComponentZOrder(nickname, 0);

        nickname2 = new JLabel("Your score : " + (int)points, SwingConstants.CENTER);
        nickname2.setFont(new Font("Serif", Font.PLAIN, font_size));
        nickname2.setSize((int) (width * 0.2125 * 2),(int) (height * 0.08711));
        nickname2.setLocation((width - ((int) (width * 0.2125 * 4)))/2, (int) (height * (0.0348 * 4)));
        nickname2.setForeground(Color.lightGray);
        add(nickname2);
        setComponentZOrder(nickname2, 0);
    }

    /**
     * Skalowanie obiektów w oknie na podstawie rozmiarów okna
     * @param x szerokość okna;
     * @param y wysokość okna;
     */
    private void reresize(int x, int y)
    {
        button_width = (int) (x * 0.2);
        button_height = (int) (y * 0.1);

        b1 = Toolkit.getDefaultToolkit().getImage("przycisk/exit.gif");
        b1 = b1.getScaledInstance(button_width + 11, button_height, Image.SCALE_DEFAULT);
        end_game_button.setIcon(new ImageIcon(b1));
        end_game_button.setSize(button_width, button_height);
        end_game_button.setLocation((x - button_width)/4, (int) (y * 0.6));

        b2 = Toolkit.getDefaultToolkit().getImage("przycisk/goagain.gif");
        b2 = b2.getScaledInstance(button_width + 11, button_height, Image.SCALE_DEFAULT);
        one_more_time_button.setIcon(new ImageIcon(b2));
        one_more_time_button.setSize(button_width, button_height);
        one_more_time_button.setLocation((int) ((x - button_width) * 0.75), (int) (y * 0.6));

        nickname.setSize((int) (width * 0.2125 * 4),(int) (height * 0.08711));
        nickname.setLocation((width - ((int) (width * 0.2125 * 4)))/2, (int) (height * 0.0348));
        if ( (int) (height * 0.069686) <= (int) (width * 0.045)) { font_size = (int) (height * 0.069686); }
        else { font_size = (int) (width * 0.045); }
        nickname.setFont(new Font("Serif", Font.PLAIN, font_size));

        nickname2.setSize((int) (width * 0.2125 * 2),(int) (height * 0.08711));
        nickname2.setLocation((width - ((int) (width * 0.2125 * 2)))/2, (int) (height * 0.0348 * 4));
        nickname2.setFont(new Font("Serif", Font.PLAIN, font_size));
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setDoubleBuffered(true);
        g.drawImage(image, 0, 0, width, height, this);
    }

    /** Akcje odpowiadajaca zdarzeniu nacisnieciu przycisku
     *  Zmieniamy zmienna state na wartosc rozna od 0
     *  Wartosc +1 odpowiada akcji powrotu do wyboru poziomu trudności
     */
    private class end_game_b extends JFrame implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            System.exit(0);
        }
    }

    private class one_more_time_b implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            state = 1;
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
