package Proze.ProjektProze;

/**
 *
 * Klasa przechowująca listę wyników.
 *
 */
public class Record
{
    public String[] nickname;
    public int[] points;

    public Record()
    {
        nickname = new String[1];
        points = new int[1];
    }

    public Record(int a)
    {
        if ( a > 1)
        {
            nickname = new String[a];
            points = new int[a];
        }
        else
        {
            nickname = new String[1];
            points = new int[1];
        }
    }
}

