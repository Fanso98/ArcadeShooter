package Proze.ProjektProze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Klasa zapewniająca zmianę poziomu trudności gry na podstawie decyzji gracza
 * @author KH AW
 * @version 1.0.0  12 czerwca 2019 21:30
 */

class Difficulty extends JPanel implements ActionListener
{
    /** Zczytywanie z pliku parametrycznego rozmiaru okna
     */
    Parsujaca kl = new Parsujaca();

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
    private JButton return_button;
    private JButton Baby_Mode;
    private JButton Easy;
    private JButton Medium;
    private JButton Hard;
    private JButton Suicide;
    private JLabel difficult;
    private Image image;
    private Image b1;
    private Image b2;
    private Image b3;
    private Image b4;
    private Image b5;
    private Image b6;

/**
 * Konstruktor Difficulty
 * @param y wysokość okna
 * @param x szerokość okna
 * */

    public Difficulty(int x, int y)
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

        /** Stworzenie i umiejscowienie przycisku odpowiadajacego za powrot do menu glownego
         * a także przycisków  wyboru poziomu trudności zależnie od liczby stopni trudności z pliku parametrycznego.
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
/**
 * Nadanie ilości stopni trudności w zależności od parametru źródłowego
 */

        if ( kl.getLiczbaStopniTrudnosci() >= 4)
        {
            Baby_Mode = new JButton();
            b2 = Toolkit.getDefaultToolkit().getImage("przycisk/baby.gif");
            b2 = b2.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
            Baby_Mode.setIcon(new ImageIcon(b2));
            Baby_Mode.setSize(button_width, button_height);
            Baby_Mode.setLocation((int) ((width - button_width) / 4), (int) (height * 0.6));
            Baby_Mode.addActionListener(new Baby_Modee());
            add(Baby_Mode);
            setComponentZOrder(Baby_Mode, 0);
        }

        if ( kl.getLiczbaStopniTrudnosci() >= 2)
        {
            Easy = new JButton();
            b3 = Toolkit.getDefaultToolkit().getImage("przycisk/easy.gif");
            b3 = b3.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
            Easy.setIcon(new ImageIcon(b3));
            Easy.setSize(button_width, button_height);
            if ( kl.getLiczbaStopniTrudnosci() >= 4)
            {
                Easy.setLocation((int) ((width - button_width) * 0.75), (int) (height * 0.6));
            }
            else
            {
                Easy.setLocation((int) ((width - button_width) / 2), (int) (height * 0.6));
            }
            Easy.addActionListener(new Eaasy());
            add(Easy);
            setComponentZOrder(Easy, 0);
        }

        if ( kl.getLiczbaStopniTrudnosci() >= 2)
        {
            Medium = new JButton();
            b4 = Toolkit.getDefaultToolkit().getImage("przycisk/medium.gif");
            b4 = b4.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
            Medium.setIcon(new ImageIcon(b4));
            Medium.setSize(button_width, button_height);
            Medium.setLocation((int) ((width - button_width) / 2), (int) (height * 0.4));
            Medium.addActionListener(new Meedium());
            add(Medium);
            setComponentZOrder(Medium, 0);
        }

        if ( kl.getLiczbaStopniTrudnosci() >= 3)
        {
            Hard = new JButton();
            b5 = Toolkit.getDefaultToolkit().getImage("przycisk/hard.gif");
            b5 = b5.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
            Hard.setIcon(new ImageIcon(b5));
            Hard.setSize(button_width, button_height);
            if ( kl.getLiczbaStopniTrudnosci() == 5)
            {
                Hard.setLocation((int) ((width - button_width) / 4), (int) (height * 0.2));
            }
            else
            {
                Hard.setLocation((int) ((width - button_width) / 2), (int) (height * 0.2));
            }
            Hard.addActionListener(new Haard());
            add(Hard);
            setComponentZOrder(Hard, 0);
        }

        if ( kl.getLiczbaStopniTrudnosci() == 5)
        {
            Suicide = new JButton();
            b6 = Toolkit.getDefaultToolkit().getImage("przycisk/suicide.gif");
            b6 = b6.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
            Suicide.setIcon(new ImageIcon(b6));
            Suicide.setSize(button_width, button_height);
            Suicide.setLocation((int) ((width - button_width) * 0.75), (int) (height * 0.2));
            Suicide.addActionListener(new Suicidee());
            add(Suicide);
            setComponentZOrder(Suicide, 0);
        }

        /** Stworzenie i umiejscowienie napisu informujacego w jakiej zakladce menu jestesmy
         */
        if ( (int) (height * 0.069686) <= (int) (width * 0.045)) { font_size = (int) (height * 0.069686); }
        else { font_size = (int) (width * 0.045); }
        difficult = new JLabel("Difficulty", SwingConstants.CENTER);
        difficult.setFont(new Font("Serif", Font.PLAIN, font_size));
        difficult.setSize((int) (width * 0.2125),(int) (height * 0.08711));
        difficult.setLocation((width - ((int) (width * 0.2125)))/2, (int) (height * 0.0348));
        difficult.setForeground(Color.pink);
        add(difficult);
        setComponentZOrder(difficult, 0);
    }
/**
 * Zmiana rozmiarów obiektów w oknie na podstawie rozmiaru okna
 *
 * @param x szerokość okna
 * @param y wysokość okna
 * @param z położenie na osi z
 * */
    private void reresize(int x, int y, int z)
    {
        button_width = (int) (x * 0.2);
        button_height = (int) (y * 0.1);

        b1 = Toolkit.getDefaultToolkit().getImage("przycisk/return.gif");
        b1 = b1.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        return_button.setIcon(new ImageIcon(b1));
        return_button.setSize(button_width, button_height);
        return_button.setLocation((x - button_width)/2, (int) (y * 0.8));

        if ( z >= 4)
        {
            b2 = Toolkit.getDefaultToolkit().getImage("przycisk/baby.gif");
            b2 = b2.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
            Baby_Mode.setIcon(new ImageIcon(b2));
            Baby_Mode.setSize(button_width, button_height);
            Baby_Mode.setLocation((x - button_width) / 4, (int) (y * 0.6));
        }

        b3 = Toolkit.getDefaultToolkit().getImage("przycisk/easy.gif");
        b3 = b3.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        Easy.setIcon(new ImageIcon(b3));
        Easy.setSize(button_width, button_height);
        if ( z >= 4)
        {
            Easy.setLocation((int) ((x - button_width) * 0.75), (int) (y * 0.6));
        }
        else
        {
            Easy.setLocation((x - button_width) / 2, (int) (y * 0.6));
        }

        b4 = Toolkit.getDefaultToolkit().getImage("przycisk/medium.gif");
        b4 = b4.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        Medium.setIcon(new ImageIcon(b4));
        Medium.setSize(button_width, button_height);
        Medium.setLocation((x - button_width)/2, (int) (y * 0.4));

        b5 = Toolkit.getDefaultToolkit().getImage("przycisk/hard.gif");
        b5 = b5.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
        Hard.setIcon(new ImageIcon(b5));
        Hard.setSize(button_width, button_height);
        if ( z == 5)
        {
            Hard.setLocation((x - button_width) / 4, (int) (y * 0.2));
        }
        else
        {
            Hard.setLocation((x - button_width) / 2, (int) (y * 0.2));
        }

        if ( z == 5)
        {
            b6 = Toolkit.getDefaultToolkit().getImage("przycisk/suicide.gif");
            b6 = b6.getScaledInstance(button_width, button_height, Image.SCALE_DEFAULT);
            Suicide.setIcon(new ImageIcon(b6));
            Suicide.setSize(button_width, button_height);
            Suicide.setLocation((int) ((x - button_width) * 0.75), (int) (y * 0.2));
        }

        difficult.setSize((int) (width * 0.2125),(int) (height * 0.08711));
        difficult.setLocation((width - ((int) (width * 0.2125)))/2, (int) (height * 0.0348));
        if ( (int) (height * 0.069686) <= (int) (width * 0.045)) { font_size = (int) (height * 0.069686); }
        else { font_size = (int) (width * 0.045); }
        difficult.setFont(new Font("Serif", Font.PLAIN, font_size));
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setDoubleBuffered(true);
        g.drawImage(image, 0, 0, width, height, this);
    }
/**
 * W zależności od wybranej opcji zmienia się wartość zmiennej state
 * w naszym przypadku wartość -1 to cofnięcie się do poprzedniego okna
 * a wartośći od 1 do 5 odpowiadają za przypisane im poziomy trudności
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

    private class Baby_Modee implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            state = 4;
        }

    }

    private class Eaasy implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            state = 1;
        }

    }

    private class Meedium implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            state = 2;
        }

    }

    private class Haard implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            state = 3;
        }

    }

    private class Suicidee implements  ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            click.playSound("Button.wav");
            state = 5;
        }

    }

    private class main_window implements ComponentListener
    {
        @Override
        public void componentResized(ComponentEvent e)
        {
            width = getWidth();
            height = getHeight();

            reresize(width, height, kl.getLiczbaStopniTrudnosci());

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

    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }

}