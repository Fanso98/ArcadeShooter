package Proze.ProjektProze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class Parsujaca {
    //private final PIN pin = PIN.PIN;
    private	int	kolorTla[];
    private	int liczbaPoziomow;
    private	int	numeracjaPoziomowZaczynaSieOd;
    private	int poczatkowaWysokoscPlanszy;
    private	double poczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy;
    private	int poczatkowaSzerokoscPlanszy;
    private	int liczbaStopniTrudnosci;
    private	int zmianaStopniaTrudnosci;
    private	String rozszerzeniePlikuZOpisemPoziomu;
    private	String tlo;
    public	String nazwaGry;
    private	String figuraObiektuGry;
    private	String obiektyGry;
    private	String nazwaBazowaPlikuZOpisemPoziomu;
    private String plikTla;
    private String plikObiektu;


    public int getKlorTla(int i) {
        return kolorTla[i];
    }

    public int getNumeracjaPoziomowZaczynaSieOd() {
        return numeracjaPoziomowZaczynaSieOd;
    }

    public int getLiczbaPoziomow() {
        return liczbaPoziomow;
    }

    public int getPoczatkowaWysokoscPlanszy() {
        return poczatkowaWysokoscPlanszy;
    }

    public double getPoczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy() {
        return poczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy;
    }

    public int getPoczatkowaSzerokoscPlanszy() {
        return poczatkowaSzerokoscPlanszy;
    }

    public int getLiczbaStopniTrudnosci() {
        return liczbaStopniTrudnosci;
    }

    public int getZmianaStopniaTrudnosci() {
        return zmianaStopniaTrudnosci;
    }

    public String getFiguraObiektuGry() {
        return figuraObiektuGry;
    }

    public String getObiektyGry() {
        return obiektyGry;
    }

    public String getTlo() {
        return tlo;
    }

    public String getNazwaGry() {
        return nazwaGry;
    }

    public String getNazwaBazowaPlikuZOpisemPoziomu() {
        return nazwaBazowaPlikuZOpisemPoziomu;
    }

    public String getPlikTla() {
        return plikTla;
    }

    public String getPlikObiektu() {
        return plikObiektu;
    }

    public String getRozszerzeniePlikuZOpisemPoziomu() {
        return rozszerzeniePlikuZOpisemPoziomu;
    }

    public Parsujaca()
    {
        Properties props = new Properties();
        try (Reader r = new BufferedReader(new FileReader("par"))) {
            props.load(r);
        } catch (FileNotFoundException fnfe) {
            //pin.wypiszKomunikatyIZakoncz("Nie znaleziono pliku parametrycznego",
                    //"par.txt", fnfe);
        } catch (IOException ioe) {
            //pin.wypiszKomunikatyIZakoncz("Wystapil blad odczytu pliku parametrycznego",
                    //"par.txt", ioe);
        }

        try {
            numeracjaPoziomowZaczynaSieOd = Integer.parseInt(props.getProperty("numeracjaPoziomowZaczynaSieOd"));
            poczatkowaWysokoscPlanszy = Integer.parseInt(props.getProperty("poczatkowaWysokoscPlanszy"));
            tlo = props.getProperty("tlo");

        // Nie dziala w klasie parsujacej gdy mamy wygenerowane w pliku parametrycznym kolor tla = jednolite, trzeba to zrobic tak
        // by zapisywal nam 3 kolory rgb w tablicy, musisz nad tym posiedziec chwile
        if(tlo.equals("jednolite")) {
            String[] temp = (props.getProperty("klorTla")).split(" ");
            kolorTla= new int[temp.length];
            for(int i=0; i<temp.length;i++)
            {
                kolorTla[i]=Integer.parseInt(temp[i]);
            }
        }

        if(tlo.equals("plikGraficzny")) {
            plikTla=props.getProperty("plikTla");
        }

        obiektyGry=props.getProperty("obiektyGry");

        if(obiektyGry.equals("figuryGeometryczne")) {
            figuraObiektuGry=props.getProperty("figuraObiektuGry");
        }

        if(obiektyGry.equals("plikGraficzny")) {
            plikObiektu=props.getProperty("plikObiektu");
        }

        poczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy=Double.parseDouble(props.getProperty("poczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy"));
        rozszerzeniePlikuZOpisemPoziomu=props.getProperty("rozszerzeniePlikuZOpisemPoziomu");
        poczatkowaSzerokoscPlanszy=Integer.parseInt(props.getProperty("poczatkowaSzerokoscPlanszy"));
        liczbaPoziomow=Integer.parseInt(props.getProperty("liczbaPoziomow"));
        nazwaGry=props.getProperty("nazwaGry");
        liczbaStopniTrudnosci=Integer.parseInt(props.getProperty("liczbaStopniTrudnosci"));
        zmianaStopniaTrudnosci=Integer.parseInt(props.getProperty("zmianaStopniaTrudnosci"));
        nazwaBazowaPlikuZOpisemPoziomu=props.getProperty("nazwaBazowaPlikuZOpisemPoziomu");
        }
        catch (NumberFormatException ex) {System.out.println(ex);}
    }

    public void pobierzZSerwera(String adres, int port){
        Client client = new Client(adres, port);
        tlo = client.wyslijPolecenie("getTlo");
        try
        {
            kolorTla[0] = Integer.parseInt(client.wyslijPolecenie("getKolorTla1"));
            kolorTla[1] = Integer.parseInt(client.wyslijPolecenie("getKolorTla2"));
            kolorTla[2] = Integer.parseInt(client.wyslijPolecenie("getKolorTla3"));
            rozszerzeniePlikuZOpisemPoziomu = client.wyslijPolecenie("getRozszerzenie");
            poczatkowaWysokoscPlanszy = Integer.parseInt(client.wyslijPolecenie("getWysokosc"));
            obiektyGry = client.wyslijPolecenie("getTypObiektow");
            liczbaPoziomow = Integer.parseInt(client.wyslijPolecenie("getLiczbaPoziomow"));
            numeracjaPoziomowZaczynaSieOd = Integer.parseInt(client.wyslijPolecenie("getStartNum"));
            liczbaStopniTrudnosci = Integer.parseInt(client.wyslijPolecenie("getLiczbaStopniTrudnosci"));
            figuraObiektuGry = client.wyslijPolecenie("getFigura");
            plikTla = client.wyslijPolecenie("getPlikTla");
            plikObiektu = client.wyslijPolecenie("getPlikObiektu");
            zmianaStopniaTrudnosci = Integer.parseInt(client.wyslijPolecenie("jakaZmianaStopniaTrudnosci"));
            poczatkowaSzerokoscPlanszy = Integer.parseInt(client.wyslijPolecenie("getSzerokosc"));
            poczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy = Double.parseDouble(client.wyslijPolecenie("getProcent"));
            nazwaGry = client.wyslijPolecenie("getNazwa");
            nazwaBazowaPlikuZOpisemPoziomu = client.wyslijPolecenie("getNazwaOpisu");
            client.wyslijPolecenie("quit");
            client.zamknij();
        }
        catch (NumberFormatException ex) {System.out.println(ex);}
    }

    public static void main(String[] args)
    {
        Parsujaca kl= new Parsujaca();
        System.out.print(
                "Wypis wszystkich pol klasy\n\n" +
                        "GeneratorPlikuParametrycznego:\n" +
                        "NumeracjaPoziomowZaczynaSieOd " + kl.getNumeracjaPoziomowZaczynaSieOd()  + "\n"+
                        "PoczatkowaWysokoscPlanszy " + kl.getPoczatkowaWysokoscPlanszy() + "\n"	+
                        "KolorTla ");

        if(kl.getTlo().equals("jednolite"))
            System.out.print(kl.getKlorTla(0) + kl.getKlorTla(1) + kl.getKlorTla(2) );

        System.out.print(
                "\nfiguraObiektuGry " + kl.getFiguraObiektuGry() + "\n" +
                        "obiektyGry " + kl.getObiektyGry() + "\n" +
                        "figuraObiektuGry " + kl.getFiguraObiektuGry() + "\n" +
                        "poczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy " + kl.getPoczatkowaSzerokoscObiektuGryJakoProcentPoczatkowejSzerokosciPlanszy() + "\n" +
                        "rozszerzeniePlikuZOpisemPoziomu " + kl.getRozszerzeniePlikuZOpisemPoziomu() + "\n" +
                        "poczatkowaSzerokoscPlanszy " + kl.getPoczatkowaSzerokoscPlanszy() + "\n" +
                        "tlo " + kl.getTlo() + "\n" +
                        "liczbaPoziomow " + kl.getLiczbaPoziomow() + "\n" +
                        "nazwaGry " + kl.getNazwaGry() + "\n" +
                        "liczbaStopniTrudnosci " + kl.getLiczbaStopniTrudnosci() + "\n" +
                        "zmianaStopniaTrudnosci " + kl.getZmianaStopniaTrudnosci() + "\n" +
                        "nazwaBazowaPlikuZOpisemPoziomu " + kl.getNazwaBazowaPlikuZOpisemPoziomu() + "\n" +
                        "plikTla " + kl.getPlikTla() + "\n" +
                        "plikObiektu " + kl.getPlikObiektu()
        );
    }

}
