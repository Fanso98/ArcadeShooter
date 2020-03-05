package Proze.ProjektProze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
/**
 * Klasa odpowiadająca za okno Options
 * @author KH AW
 * @version 1.0.0  12 czerwca 2019 21:30
 */


class Options extends JPanel implements ActionListener
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

    public int vol = 0;
    public int pause = 0;

    private JButton return_button;
    private JButton Vol_UP;
    private JButton Vol_Down;
    private JButton Pause;
    private JLabel nickname;
    private Image image;
    private Image b1;
    private Image b2;
    private Image b3;
    private Image b4;

    /**
     * Konstruktor klasy Options
     * @param x szerokość okna;
     * @param y wysokość okna;
     */

    public Options(int x, int y)
    {
        width = x;
        height = y;

        button_width = (int) (width * 0.2);
        button_height = (int) (height * 0.1);

        /** Zczytywanie z pliku parametrycznego co ma byc kolorem tla
         *  Plik graficzny lub kolor okreslony wartosciami RGB
         */
        if (kl.getTlo().equals("jednolite")) {

            this.setBackground(new Color(kl.getKlorTla(0), kl.getKlorTla(1), kl.getKlorTla(2)));
        }
        else
        {
            image = Toolkit.getDefaultToolkit().getImage("przycisk/option.gif");
        }

        setLayout(null);
        addComponentListener(new main_window());

        /** Stworzenie i umiejscowienie przycisku odpowiadającego za powrót do menu głównego
         * a także przycisków odpowiedzialnych za zmianę poziomu głośności i przycisku pauzy i odpauzowania
         */
        return_button = new JButton();
        b1 = Toolkit.getDefaultToolkit().getImage("przycisk/return.gif");
        b1 = b1.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        return_button.setIcon(new ImageIcon(b1));
        return_button.setSize(button_width, button_height);
        return_button.setLocation((int) ((width - button_width) / 2), (int) (height * 0.8));
        return_button.addActionListener(new return_b());
        add(return_button);
        setComponentZOrder(return_button, 0);

        Vol_UP = new JButton();
        b2 = Toolkit.getDefaultToolkit().getImage("przycisk/volup.gif");
        b2 = b2.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        Vol_UP.setIcon(new ImageIcon(b2));
        Vol_UP.setSize(button_width, button_height);
        Vol_UP.setLocation((int) ((width - button_width) / 2), (int) (height * 0.6));
        Vol_UP.addActionListener(new Volup());
        add(Vol_UP);
        setComponentZOrder(Vol_UP, 0);

        Vol_Down = new JButton();
        b3 = Toolkit.getDefaultToolkit().getImage("przycisk/voldown.gif");
        b3 = b3.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        Vol_Down.setIcon(new ImageIcon(b3));
        Vol_Down.setSize(button_width, button_height);
        Vol_Down.setLocation((int) ((width - button_width) / 2), (int) (height * 0.4));
        Vol_Down.addActionListener(new Voldown());
        add(Vol_Down);
        setComponentZOrder(Vol_Down, 0);

        Pause = new JButton();
        b4 = Toolkit.getDefaultToolkit().getImage("przycisk/mute.gif");
        b4 = b4.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        Pause.setIcon(new ImageIcon(b4));
        Pause.setSize(button_width, button_height);
        Pause.setLocation((int) ((width - button_width) / 2), (int) (height * 0.2));
        Pause.addActionListener(new Pause());
        add(Pause);
        setComponentZOrder(Pause, 0);

        /** Stworzenie i umiejscowienie napisu informujacego w jakiej zakladce menu jestesmy
         */
        if ( (int) (height * 0.069686) <= (int) (width * 0.045)) { font_size = (int) (height * 0.069686); }
        else { font_size = (int) (width * 0.045); }
        nickname = new JLabel("Options", SwingConstants.CENTER);
        nickname.setFont(new Font("Serif", Font.PLAIN, font_size));
        nickname.setSize((int) (width * 0.2125),(int) (height * 0.08711));
        nickname.setLocation((width - ((int) (width * 0.2125)))/2, (int) (height * 0.0348));
        nickname.setForeground(Color.lightGray);
        add(nickname);
        setComponentZOrder(nickname, 0);
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

        b1 = Toolkit.getDefaultToolkit().getImage("przycisk/return.gif");
        b1 = b1.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        return_button.setIcon(new ImageIcon(b1));
        return_button.setSize(button_width, button_height);
        return_button.setLocation((x - button_width)/2, (int) (y * 0.8));

        b2 = Toolkit.getDefaultToolkit().getImage("przycisk/volup.gif");
        b2 = b2.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        Vol_UP.setIcon(new ImageIcon(b2));
        Vol_UP.setSize(button_width, button_height);
        Vol_UP.setLocation((x - button_width)/2, (int) (y * 0.6));

        b3 = Toolkit.getDefaultToolkit().getImage("przycisk/voldown.gif");
        b3 = b3.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        Vol_Down.setIcon(new ImageIcon(b3));
        Vol_Down.setSize(button_width, button_height);
        Vol_Down.setLocation((x - button_width)/2, (int) (y * 0.4));

        b4 = Toolkit.getDefaultToolkit().getImage("przycisk/mute.gif");
        b4 = b4.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        Pause.setIcon(new ImageIcon(b4));
        Pause.setSize(button_width, button_height);
        Pause.setLocation((x - button_width)/2, (int) (y * 0.2));

        nickname.setSize((int) (width * 0.2125),(int) (height * 0.08711));
        nickname.setLocation((width - ((int) (width * 0.2125)))/2, (int) (height * 0.0348));
        if ( (int) (height * 0.069686) <= (int) (width * 0.045)) { font_size = (int) (height * 0.069686); }
        else { font_size = (int) (width * 0.045); }
        nickname.setFont(new Font("Serif", Font.PLAIN, font_size));

    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setDoubleBuffered(true);
        g.drawImage(image, 0, 0, width, height, this);
    }

    /** Akcja odpowiadajaca zdarzeniu nacisnieciu przycisku
     *  Zmieniamy zmienna state na wartosc rozna od 0
     *  Wartosc -1 odpowiada akcji powrotu do menu glownego
     */
    private class return_b implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            state = -1;
        }
    }
    /** Akcja odpowiadajaca zdarzeniu nacisnieciu przycisku
     *  Zmieniamy zmienna vol na wartosc rozna od 0
     *  Wartosc 1 odpowiada akcji zwiększenia poziomu głośności muzyki.
     */
    private class Volup implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            vol= 1;
        }

    }
    /** Akcja odpowiadajaca zdarzeniu nacisnieciu przycisku
     *  Zmieniamy zmienna vol na wartosc rozna od 0
     *  Wartosc -1 odpowiada akcji zmniejszenia poziomu głośności muzyki.
     */

    private class Voldown implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            vol= -1;
        }

    }
    /** Akcja odpowiadajaca zdarzeniu nacisnieciu przycisku
     *  Zmieniamy zmienna pause na wartosc rozna od 0
     *  Wartosc 1 odpowiada naprzemiennie akcji całkowitego wyciszenia lub wznowienia
     *  muzyki w zależności od momentu w którym się skończyło.
     */
    private class Pause implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            pause= 1;
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