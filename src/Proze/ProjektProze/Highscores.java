package Proze.ProjektProze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
/**
 * Klasa odpwiadająca za okno z najlepszymi wynikami wszystkich z graczy.
 * @author KH AW
 * @version 1.0.0  12 czerwca 2019 21:30
 */

class Highscores extends JPanel implements ActionListener
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

    public Record highscores_tab;
    public int lines_numb;

    private JButton return_button;
    private JLabel nickname;
    private JLabel scores;
    private Image image;
    private Image b1;
    /**
     * Konstruktor klasy Highscores
     * @param x szerokość okna;
     * @param y wysokość okna;
     *
     */

    public Highscores(int x, int y)
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
            image = Toolkit.getDefaultToolkit().getImage("przycisk/high.gif");
        }

        setLayout(null);
        addComponentListener(new main_window());

        /** Stworzenie i umiejscowienie przycisku odpowiadajacego za powrot do menu glownego
         */
        return_button = new JButton();
        b1 = Toolkit.getDefaultToolkit().getImage("przycisk/return.gif");
        b1 = b1.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        return_button.setIcon(new ImageIcon(b1));
        return_button.setSize(button_width, button_height);
        return_button.setLocation((int) ((width - button_width)/2), (int) (height * 0.8));
        return_button.addActionListener(new return_b());
        add(return_button);
        setComponentZOrder(return_button, 0);

        /** Stworzenie i umiejscowienie napisu informujacego w jakiej zakladce menu jestesmy
         */
        if ( (int) (height * 0.069686) <= (int) (width * 0.045)) { font_size = (int) (height * 0.069686); }
        else { font_size = (int) (width * 0.045); }
        nickname = new JLabel("Highscores", SwingConstants.CENTER);
        nickname.setFont(new Font("Serif", Font.PLAIN, font_size));
        nickname.setSize((int) (width * 0.2125 * 2),(int) (height * 0.08711));
        nickname.setLocation((width - ((int) (width * 0.2125 * 2)))/2, (int) (height * 0.0348));
        nickname.setForeground(Color.cyan);
        add(nickname);
        setComponentZOrder(nickname, 0);

        scores = new JLabel("", SwingConstants.CENTER);
        scores.setFont(new Font("Serif", Font.PLAIN, font_size));
        scores.setSize((int) (width * 0.2125 * 2),(int) (height * 0.08711 * 8));
        scores.setLocation((width - ((int) (width * 0.2125 * 2)))/2, (int) (height * 0.0348 * 2));
        scores.setForeground(Color.pink);
        setComponentZOrder(scores, 0);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setDoubleBuffered(true);
        g.drawImage(image, 0, 0, width, height, this);
    }
/**
 * na ba
 *
 */

    public void set_text()
    {
        String tmp;
        if (highscores_tab.nickname[0] == null)
        {
            tmp = "";
        }
        else
        {
            if ( lines_numb > 1) {
                tmp = "<html>";
                for (int i = 0; i < lines_numb - 2; i++) {
                    tmp = tmp + (i + 1) + ". " + highscores_tab.nickname[i] + " : " + highscores_tab.points[i] + "<br>";
                }
                tmp = tmp + (lines_numb - 1) + ". " + highscores_tab.nickname[lines_numb - 2] + " : " + highscores_tab.points[lines_numb - 2] + "</html>";
            }
            else
            {
                tmp = "1. " + highscores_tab.nickname[0] + " : " + highscores_tab.points[0];
            }
        }
        scores.setText(tmp);
    }
/**
 * Metoda odpowiedzialna za dynamiczną zmianę obiektów na planszy w zależności od zmian rozmiaru okna.
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

        nickname.setSize((int) (width * 0.2125 * 2),(int) (height * 0.08711));
        nickname.setLocation((width - ((int) (width * 0.2125 * 2)))/2, (int) (height * 0.0348));
        if ( (int) (height * 0.069686) <= (int) (width * 0.045)) { font_size = (int) (height * 0.069686); }
        else { font_size = (int) (width * 0.045); }
        nickname.setFont(new Font("Serif", Font.PLAIN, font_size));

        scores.setSize((int) (width * 0.2125 * 2),(int) (height * 0.08711 * 8));
        scores.setLocation((width - ((int) (width * 0.2125 * 2)))/2, (int) (height * 0.0348 * 2));
        scores.setFont(new Font("Serif", Font.PLAIN, font_size));
    }

    /** Akcja odpowiadajaca zdarzeniu nacisnieciu przycisku
     *  Zmieniamy zmienna state na wartosc rozna od 0
     *  Wartosc -1 odpowiada akcji powrotu do menu glownego
     *
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