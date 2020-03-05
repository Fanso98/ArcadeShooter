package Proze.ProjektProze;

import java.io.*;
import java.util.Properties;


public class parserserw{
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


    public void parserserwa(String adres, int port){

        try (InputStream input = new FileInputStream("par"))
        {
            Properties props = new Properties();
            // load a properties file
            props.load(input);
            // get the property value and print it out


            Client client = new Client(adres,port);
            tlo = client.wyslijPolecenie("getTlo");
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
        catch (FileNotFoundException ex) {System.out.println(ex);}
        catch (IOException ex) {System.out.println(ex);}
    }
}
