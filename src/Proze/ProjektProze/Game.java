package Proze.ProjektProze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.Random;

/**
 * Klasa odpwiadająca za okno z grą.
 * @author KH AW
 * @version 1.0.0  12 czerwca 2019 21:30
 */

class Game extends JPanel implements ActionListener
{
    /** Zczytywanie z pliku parametrycznego rozmiaru okna
     *
     */
    private Parsujaca kl = new Parsujaca();
    /** Zczytywanie z pliku parametrycznego Level.props informacji o kryteriach poziomów gr.
     *
     */
    private FileR fr = new FileR();
    private Image img;
    private Image img2;
    public int width;
    public int height;
    private int scores = fr.geTsco();
    public int state = 0;
    public float objects_size;
    private int lvlscore = fr.geTlvlsco();
    private double randd = 1;
    private double randy = 1;
    private int boost = fr.getboo();
    public float points = fr.geTpoi();
    private int miss = fr.geTmi();
    private Random generator = new Random();
    private Rectangle2D rect;
    private Ellipse2D circ;
    private Path2D triang;
    private double x = 100, y = 100, velx = 0.7, vely = 0.7;
    private javax.swing.Timer t = new javax.swing.Timer(5,this);
    private long start = System.currentTimeMillis();
    private long time;
    private int sizer;
    private int count_lvl = 0;
    public float wsp = fr.geTws();
    public int pointsmultipl = 1;

    public int lines_numb;
    public Record highscores_tab;
    public boolean file_exist;
    public boolean shorten;
    String g_name;
    private String filepath = "./Highscores.txt";
    private boolean was_changed = false;
    private BufferedWriter bw = null;

    private Sounds click = new Sounds();
    private JLabel bbtime;
    private JLabel score;
    private JLabel Miss;
    private JLabel lvls;
    private ImageIcon image;

    private MouseAdapter mouse_listener;


    /**
     * Klasa zapewniająca efekty dzwiekowe przy trafienia/nacisnieciach w odpowiednich miejscach.
     *@param gx Szerokość okna
     *@param gy Wysokość okna
     */
    public Game(int gx, int gy)
    {

        width = gx;
        height = gy;
        objects_size = ((float) kl.getPoczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy())/100;
        sizer = (int) (objects_size * (float) width);


        /** Zczytywanie z pliku parametrycznego co ma byc kolorem tla
         *  Plik graficzny lub kolor okreslony wartosciami RGB
         */
        if (kl.getTlo().equals("jednolite")) {

            this.setBackground(new Color(kl.getKlorTla(0), kl.getKlorTla(1), kl.getKlorTla(2)));
        } else {
                        img2 = Toolkit.getDefaultToolkit().getImage("przycisk/game.gif");
        }

        setLayout(null);
        addComponentListener(new main_window());

        /** Wypisywanie wyniku w lewym gornym rogu a także liczby możliwych nie trafień i numer aktualnego poziomu gry
         * a w trybie baby także tego ile czasu zostało do zakończenia się rozgrywki
         */
        score = new JLabel("Score");
        add(score);
        scored();

        Miss = new JLabel("Misses left");
        add(Miss);
        misses();

        lvls = new JLabel(kl.getNazwaBazowaPlikuZOpisemPoziomu());
        add(lvls);
        lvl();

        bbtime = new JLabel("Czas do końca gry:");
        add(bbtime);
        babytime();

        /**
         *Tworzenie nowych obiektów po trafieniu lub zliczanie nie trafionych strzałów w zależnosci od tego do czego
         * mamy strzelać figury geometryczne (kwadrat,kółko,trójkąt,prostokąt) czy też obiektów graficznych.
         */
        if(kl.getObiektyGry().equals("plikGraficzny")){

            addMouseListener(mouse_listener = new MouseAdapter()
            {
                public void mouseClicked(MouseEvent me)
                {
                    super.mouseClicked(me);
                    click.playSound("usp1.wav");

                    if(rect.contains(me.getPoint()))
                    {
                        x = generator.nextInt(width - sizer * 2) + sizer;
                        y = generator.nextInt(height - sizer * 2) + sizer;
                        scored();
                        repaint();
                    }
                    else {
                        if (pointsmultipl != 4)
                        {
                            miss--;
                            if (miss == 0)
                            {
                                EndGame();
                            }
                            misses();
                            repaint();
                        }

                    }
                }});
        }
        else if(kl.getFiguraObiektuGry().equals("kwadraty")){
        addMouseListener(mouse_listener = new MouseAdapter()
        {
            public void mouseClicked(MouseEvent me)
            {
                super.mouseClicked(me);
                click.playSound("usp1.wav");

                if(rect.contains(me.getPoint()))
                {
                    x = generator.nextInt(width - sizer * 2) + sizer;
                    y = generator.nextInt(height - sizer * 2) + sizer;
                    scored();
                    repaint();
                }
                else {
                    if (pointsmultipl != 4)
                    {
                        miss--;
                        if (miss == 0)
                        {
                            EndGame();
                        }
                        misses();
                        repaint();
                    }
                }
        }});
        }
        else if(kl.getFiguraObiektuGry().equals("kolka")) {
            addMouseListener(mouse_listener = new MouseAdapter()
            {
                public void mouseClicked(MouseEvent me)
                {
                    super.mouseClicked(me);
                    click.playSound("usp1.wav");

                    if(circ.contains(me.getPoint()))
                    {
                        x = generator.nextInt(width - sizer * 2) + sizer;
                        y = generator.nextInt(height - sizer * 2) + sizer;
                        scored();
                        repaint();
                    }
                    else {
                        if (pointsmultipl != 4)
                        {
                            miss--;
                            if (miss == 0) {
                                EndGame();
                            }
                            misses();
                            repaint();
                        }
                    }
                }
            });
        }
        else if(kl.getFiguraObiektuGry().equals("prostokaty")){
            addMouseListener(mouse_listener = new MouseAdapter()
            {
                public void mouseClicked(MouseEvent me)
                {
                    super.mouseClicked(me);
                    click.playSound("usp1.wav");

                    if(rect.contains(me.getPoint()))
                    {
                        x = generator.nextInt(width - sizer * 2) + sizer;
                        y = generator.nextInt(height - sizer * 2) + sizer;
                        scored();
                        repaint();
                    }
                    else {
                        if (pointsmultipl != 4)
                        {
                            miss--;
                            if (miss == 0)
                            {
                                EndGame();
                            }
                            misses();
                            repaint();
                        }

                    }
                }});
        }
        else if(kl.getFiguraObiektuGry().equals("trojkaty")){
            addMouseListener(mouse_listener = new MouseAdapter()
            {
                public void mouseClicked(MouseEvent me)
                {
                    super.mouseClicked(me);
                    click.playSound("usp1.wav");

                    if(triang.contains(me.getPoint()))
                    {
                        x = generator.nextInt(width - sizer * 2) + sizer;
                        y = generator.nextInt(height - sizer * 2) + sizer;
                        scored();
                        repaint();
                    }
                    else {
                        if (pointsmultipl != 4)
                        {
                            miss--;
                            if (miss == 0)
                            {
                                EndGame();
                            }
                            misses();
                            repaint();
                        }
                        }
                }});
        }

    }

    private void loadImage()
    {
            img = Toolkit.getDefaultToolkit().getImage("bird.gif");
    }

    /**
     * Skalowanie obiektów na podstawie stopnia trudności wybranego przez użytkownika.
     */
    private void reresize()
    {
        sizer = (int) (objects_size * (float) width);

        if (pointsmultipl == 1)
        {
            sizer = (int) (sizer * Math.pow(0.8, count_lvl));
        }
        else if (pointsmultipl == 2)
        {
            sizer = (int) (sizer * Math.pow(0.75, count_lvl));
        }
        else if (pointsmultipl == 3)
        {
            sizer = (int) (sizer * Math.pow(0.70, count_lvl));
        }
        else if (pointsmultipl == 4)
        {
            sizer = sizer;
        }
        else if (pointsmultipl == 5)
        {
            sizer = (int) (sizer * Math.pow(0.5, count_lvl));
        }
    }

    /** Rysowanie elementow graficznych w oknie wlasciwym gry w zależności od pliku parametrycznego
     *
     */

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setDoubleBuffered(true);
        g.drawImage(img2, 0, 0, width, height, this);
        if (kl.getObiektyGry().equals("plikGraficzny"))
        {
            loadImage();
            g.drawImage(img,(int)x,(int)y,sizer,sizer,this);
            //Graphics2D g2d = (Graphics2D) g;
            rect = new Rectangle2D.Double(x, y, sizer, sizer);
        }
        else if (kl.getFiguraObiektuGry().equals("kwadraty"))
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            rect = new Rectangle2D.Double(x, y, sizer, sizer);
            g2d.fill(rect);
        }
        else if (kl.getFiguraObiektuGry().equals("kolka"))
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            circ = new Ellipse2D.Double(x, y, sizer, sizer);
            g2d.fill(circ);
        } else if (kl.getFiguraObiektuGry().equals("prostokaty"))
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            rect = new Rectangle2D.Double(x, y, sizer / 2, sizer * 2);
            g2d.fill(rect);
        } else if (kl.getFiguraObiektuGry().equals("trojkaty"))
        {
            super.paintComponent(g);
            triang = new Path2D.Double();
            triang.moveTo((int)(x-0.5*sizer), (int)(y+0.5*sizer));
            triang.lineTo((int)(x+0.5*sizer), (int)(y+0.5*sizer));
            triang.lineTo((int)x, (int)(y-0.5*sizer));
            triang.closePath();

            Graphics2D g2 = (Graphics2D) g;
            g2.fill(triang);
        }
        t.start();
    }

    /**
     * Zliczanie liczby pktów jakie uzyskaliśmy w zależności od poziomu trudności ilość zdobywanych pkt za jeden
     * trafiony strzał jest inna.
     * Wprowadzenie utrudnień typu przyśpieszenie naszych obiektów do których strzelamy po każdym trafieniu.
     * Co 16 trafionych strzałów zmiana wielkości obiektów do których strzelamy (zmniejszenie) wartość zmniejszania
     * zależna od poziomu trudności wybranego przez użytkownika
     */
    public void scored()
    {
        scores++;

        score.setBounds(15, 5, 300, 30);

        if (pointsmultipl == 1)
        {
            points++;
        }
        else if (pointsmultipl == 2)
        {
            points = points + 1+1*(((float)(kl.getZmianaStopniaTrudnosci()))/100) ;
        }
        else if (pointsmultipl == 3)
        {
            points = points + 1 + (1 * 2 * ((((float)(kl.getZmianaStopniaTrudnosci()))/100)));
        }
        else if (pointsmultipl == 4)
        {
            points= points;
        }
        else if (pointsmultipl == 5)
        {
            points = points + 1 + (1 * 4 * ((((float)(kl.getZmianaStopniaTrudnosci()))/100)));
        }

        if (points > 1)
        {
            click.playSound("hit.wav");
            boost++;

            if (kl.getObiektyGry().equals("plikGraficzny"))
            {
                boost = boost + 5;
            }

            move(boost);

        }

        if ((((int) (scores))%16) == 0 && scores > 1)
        {
            if (lvlscore <= kl.getLiczbaPoziomow())
            {
                count_lvl++;

                if (pointsmultipl == 1)
                {
                sizer = (int) (sizer * 0.8);
                }
                else if (pointsmultipl == 2)
                {
                    sizer = (int) (sizer * 0.75);
                }
                else if (pointsmultipl == 3)
                {
                    sizer = (int) (sizer * 0.70);
                }
                else if (pointsmultipl == 4)
                {
                    sizer = sizer;
                }
                else if (pointsmultipl == 5)
                {
                    sizer = (int) (sizer * 0.5);
                }

                lvlscore++;
                lvl();
                repaint();
                if (sizer == 25)
                {
                    //sizer = sizer;
                }
            }
        }
        score.setText("Score: " + (int)points);

        score.setForeground(Color.red);
    }
    /**
     * Wyświetlanie liczby możliwych nie trafień przed zakończeniem gry.
     * */
    public void misses(){
        Miss.setBounds(15, 20, 300, 30);
        Miss.setText("Misses Left: " + miss);
        Miss.setForeground(Color.red);
    }
    /**
     * Wyświetlanie na ekranie z grą aktualnego poziomu gry.
     * */
    public void lvl(){
        lvls.setBounds(15,35,300,30);
        lvls.setText(kl.getNazwaBazowaPlikuZOpisemPoziomu()+":"+lvlscore);
        lvls.setForeground(Color.red);
    }
    /**
     * Wyświetleine na ekranie czasu jaki pozostał do zakończenia gry.
     */

    public void babytime(){

        if(pointsmultipl==4) {
            bbtime.setBounds(15, 50, 300, 30);
            bbtime.setText("Time left to play:" + (100 - (System.currentTimeMillis() - start) / 1000));
            bbtime.setForeground(Color.red);
        }

    }
    /**
     * Zakończenie gry i zapisanie wyniku do pliku tekstowego. W przypadku uzyskania odpowiednio dużej ilości punktów.
     * */
    public void EndGame()
    {
        if ( file_exist == false)
        {
            highscores_tab.nickname[0] = g_name;
            highscores_tab.points[0] = (int)points;
            was_changed = true;
        }
        else {
            if ( shorten == false)
            {
                for (int i = 0; i < lines_numb; i++)
                {
                    if (points > highscores_tab.points[i])
                    {
                        was_changed = true;
                        for (int j = i + 1; j < lines_numb; j++)
                        {
                            highscores_tab.points[j] = highscores_tab.points[j - 1];
                            highscores_tab.nickname[j] = highscores_tab.nickname[j - 1];
                        }
                        highscores_tab.points[i] = (int)points;
                        highscores_tab.nickname[i] = g_name;
                        break;
                    } else continue;
                }
            }
            else
            {
                for (int i = 0; i < lines_numb - 1; i++)
                {
                    if (points > highscores_tab.points[i])
                    {
                        was_changed = true;
                        for (int j = i + 1; j < lines_numb - 1; j++)
                        {
                            highscores_tab.points[j] = highscores_tab.points[j - 1];
                            highscores_tab.nickname[j] = highscores_tab.nickname[j - 1];
                        }
                        highscores_tab.points[i] = (int)points;
                        highscores_tab.nickname[i] = g_name;
                        break;
                    } else continue;
                }
                if ( was_changed == false)
                {
                    highscores_tab.points[lines_numb - 1] = (int)points;
                    highscores_tab.nickname[lines_numb - 1] = g_name;
                    was_changed = true;
                }
                else {}
            }
        }
        if ( was_changed == true)
        {
            try
            {
                bw = new BufferedWriter(new FileWriter(filepath));
                for (int i = 0; i < lines_numb; i++)
                {
                    bw.write(highscores_tab.nickname[i] + " ");
                    bw.write(Integer.toString(highscores_tab.points[i]));
                    bw.newLine();
                }
            }
            catch (IOException ex) {}

            try
            {
                bw.close();
            }
            catch (IOException ex) {}
        }
        t.removeActionListener(this);
        t.stop();
        removeMouseListener(mouse_listener);
        state = 1;
    }

    private class main_window implements ComponentListener
    {
        @Override
        public void componentResized(ComponentEvent e)
        {
            width = getWidth();
            height = getHeight();

            reresize();

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

    /** Ustawienie początkowej prędkości i kierunku poruszania się obiektów.
     */
    public void actionPerformed(ActionEvent e)
    {
        if ((time = System.currentTimeMillis() - start) / 1000F >= 100)
        {
            EndGame();
        }

        else {
            if (x <= 0 || x >= width - sizer)
            {
                velx = -velx;
            }
            if (y <= 0 || y >= height - sizer)
            {
                vely = -vely;
            }
            x += randd * (1 + wsp * boost) * velx;
            y += randy * (1 + wsp * boost) * vely;
            babytime();
            repaint();
        }
    }
    /** metoda Zmiana prędkości i kierunków poruszania się obiektów w losowy sposób
     * zmiana prędkości na podstawie zmiennej boost która po każdym trafieniu
     * zwiększa swoją wartość.
     */
    public void move(int boost)
    {

            Random r = new Random();
            randd = -1 + (1 - -1) * r.nextDouble();
            randy = -1 + (1 - -1) * r.nextDouble();
            x += randd * (1 + 0.5 * boost) * velx;
            y += randy * (1 + 0.5 * boost) * vely;
            repaint();

    }

}