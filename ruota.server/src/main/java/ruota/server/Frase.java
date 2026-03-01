package ruota.server;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Frase {
    private String fraseOriginale;
    private String tema;
    private char[] fraseMascherata;
    private static final String FILE_FRASI = "frasi.csv";

    public Frase() {
        ArrayList<String[]> frasi = caricaFrasiDaFile();
        if (frasi.isEmpty()) {
            fraseOriginale = "NESSUNA FRASE DISPONIBILE";
            tema = "";
        } else {
            Random random = new Random();
            String[] estratta = frasi.get(random.nextInt(frasi.size()));
            fraseOriginale = estratta[0].toUpperCase();
            tema = estratta[1];
        }
        inizializzaMascherata();
    }

    private ArrayList<String[]> caricaFrasiDaFile() {
        ArrayList<String[]> frasi = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_FRASI))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    String[] parti = linea.split(";");
                    if (parti.length == 2) {
                        frasi.add(new String[]{parti[0].trim(), parti[1].trim()});
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Errore lettura file frasi: " + e.getMessage());
        }
        return frasi;
    }

    // getter tema
    public String getTema() {
        return tema;
    }

    private void inizializzaMascherata() {
        fraseMascherata = new char[fraseOriginale.length()];
        for (int i = 0; i < fraseOriginale.length(); i++) {
            char c = fraseOriginale.charAt(i);
            if (c == ' ') {
                fraseMascherata[i] = ' ';
            } else {
                fraseMascherata[i] = '-';
            }
        }
    }

    // Controlla lettera
    public int controllaLettera(char lettera) {
        int occorrenze = 0;
        lettera = Character.toUpperCase(lettera);
        for (int i = 0; i < fraseOriginale.length(); i++) {
            if (fraseOriginale.charAt(i) == lettera &&
                fraseMascherata[i] == '-') {
                fraseMascherata[i] = lettera;
                occorrenze++;
            }
        }
        return occorrenze;
    }

    // Controlla soluzione
    public boolean controllaSoluzione(String tentativo) {
        return fraseOriginale.equalsIgnoreCase(tentativo);
    }

    // Frase attuale
    public String getFraseAttuale() {
        return new String(fraseMascherata);
    }

    public boolean completata() {
        return !new String(fraseMascherata).contains("-");
    }

    public String getFraseOriginale() {
        return fraseOriginale;
    }
}