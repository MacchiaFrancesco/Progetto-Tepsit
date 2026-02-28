package ruota.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Frase {

    private String fraseOriginale;
    private char[] fraseMascherata;

    private static final String FILE_FRASI = "frasi.txt";

    public Frase() {
        ArrayList<String> frasi = caricaFrasiDaFile();

        if (frasi.isEmpty()) {
            // fallback di sicurezza
            fraseOriginale = "NESSUNA FRASE DISPONIBILE";
        } else {
            Random random = new Random();
            fraseOriginale = frasi.get(random.nextInt(frasi.size()));
        }

        inizializzaMascherata();
    }

    private ArrayList<String> caricaFrasiDaFile() {
        ArrayList<String> frasi = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_FRASI))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                linea = linea.trim();

                if (!linea.isEmpty()) {
                    frasi.add(linea.toUpperCase());
                }
            }

        } catch (IOException e) {
            System.out.println("Errore lettura file frasi: " + e.getMessage());
        }

        return frasi;
    }

    private void inizializzaMascherata() {

        fraseMascherata = new char[fraseOriginale.length()];

        for (int i = 0; i < fraseOriginale.length(); i++) {

            char c = fraseOriginale.charAt(i);

            if (c == ' ') {
                fraseMascherata[i] = ' ';
            } else {
                fraseMascherata[i] = '_';
            }
        }
    }

    //Controlla lettera
    public int controllaLettera(char lettera) {

        int occorrenze = 0;
        lettera = Character.toUpperCase(lettera);

        for (int i = 0; i < fraseOriginale.length(); i++) {

            if (fraseOriginale.charAt(i) == lettera &&
                fraseMascherata[i] == '_') {

                fraseMascherata[i] = lettera;
                occorrenze++;
            }
        }

        return occorrenze;
    }

    //Controlla soluzione
    public boolean controllaSoluzione(String tentativo) {
        return fraseOriginale.equalsIgnoreCase(tentativo);
    }

    //Frase attuale
    public String getFraseAttuale() {
        return new String(fraseMascherata);
    }

    public boolean completata() {
        return !new String(fraseMascherata).contains("_");
    }

    public String getFraseOriginale() {
        return fraseOriginale;
    }
}
