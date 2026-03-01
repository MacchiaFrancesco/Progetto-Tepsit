package ruota.server;

public class TestFrase {
    public static void main(String[] args) {
    	
    	System.out.println("Cartella corrente: " + System.getProperty("user.dir"));
    	
        System.out.println("=== TEST CLASSE FRASE ===\n");

        
        // Test 1: creazione frase casuale
        System.out.println("-- Test 1: Creazione frase casuale --");
        Frase frase = new Frase();
        System.out.println("Tema:            " + frase.getTema());
        System.out.println("Frase originale: " + frase.getFraseOriginale());
        System.out.println("Frase mascherata: " + frase.getFraseAttuale());
        System.out.println();

        // Test 2: lettera presente nella frase
        System.out.println("-- Test 2: Lettera presente --");
        char letteraPresente = frase.getFraseOriginale().charAt(0);
        int occorrenze = frase.controllaLettera(letteraPresente);
        System.out.println("Lettera testata: " + letteraPresente);
        System.out.println("Occorrenze trovate: " + occorrenze);
        System.out.println("Frase dopo controllo: " + frase.getFraseAttuale());
        System.out.println();

        // Test 3: lettera non presente nella frase
        System.out.println("-- Test 3: Lettera non presente --");
        char letteraAssente = trovaLetteraAssente(frase.getFraseOriginale());
        int occorrenzeAssenti = frase.controllaLettera(letteraAssente);
        System.out.println("Lettera testata: " + letteraAssente);
        System.out.println("Occorrenze trovate: " + occorrenzeAssenti);
        System.out.println();

        // Test 4: soluzione errata
        System.out.println("-- Test 4: Soluzione errata --");
        boolean risultatoErrato = frase.controllaSoluzione("FRASE SBAGLIATA");
        System.out.println("Tentativo: FRASE SBAGLIATA");
        System.out.println("Risultato: " + risultatoErrato);
        System.out.println();

        // Test 5: soluzione corretta
        System.out.println("-- Test 5: Soluzione corretta --");
        boolean risultatoCorretto = frase.controllaSoluzione(frase.getFraseOriginale());
        System.out.println("Tentativo: " + frase.getFraseOriginale());
        System.out.println("Risultato: " + risultatoCorretto);
        System.out.println();

        // Test 6: completamento frase lettera per lettera
        System.out.println("-- Test 6: Completamento frase lettera per lettera --");
        Frase frase2 = new Frase();
        System.out.println("Frase originale: " + frase2.getFraseOriginale());
        String originale = frase2.getFraseOriginale();
        for (char c : originale.toCharArray()) {
            if (c != ' ') {
                frase2.controllaLettera(c);
            }
        }
        System.out.println("Frase dopo tutte le lettere: " + frase2.getFraseAttuale());
        System.out.println("Completata: " + frase2.completata());
        System.out.println();

        System.out.println("=== FINE TEST ===");
    }

    // Trova una lettera che non è presente nella frase
    private static char trovaLetteraAssente(String frase) {
        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (char c : alfabeto.toCharArray()) {
            if (!frase.contains(String.valueOf(c))) {
                return c;
            }
        }
        return '?';
    }
}