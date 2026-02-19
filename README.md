NEGRI
UML tool: draw.io
Sequence Diagram tool: marmeid.live
source code:
sequenceDiagram
    autonumber
    participant C1 as Client1
    participant S as Server
    participant C2 as Client2
 
%% =======================
%% ROUND 1 – TURNO CLIENT1
%% =======================
 
C1->>S: Gira Ruota
S-->>C1: Esito Ruota
S-->>C2: Esito Ruota (broadcast)
 
C1->>S: Lettera scelta
S-->>C1: Esito Lettera e frase aggiornata
S-->>C2: Esito Lettera e frase aggiornata (broadcast)
 
C1->>S: Tentativo Soluzione
S-->>C1: Soluzione Corretta
S-->>C2: Soluzione Corretta (broadcast)
 
S-->>C1: Annuncio Frase Completa
S-->>C2: Annuncio Frase Completa (broadcast)
 
S-->>C1: Chiusura Round
S-->>C2: Chiusura Round (broadcast)
 
%% =======================
%% FASE FINALE – RUOTA DELLE MERAVIGLIE
%% =======================
 
S-->>C1: Inizio Fase Finale
S-->>C2: Inizio Fase Finale (broadcast)
 
C1->>S: Girare Ruota Meraviglie
 
S-->>C1: Inizio Minigioco 1
S-->>C2: Inizio Minigioco 1 (broadcast)
S-->>C1: Timer Minigioco 1
S-->>C2: Timer Minigioco 1 (broadcast)
C1->>S: Completamento Minigioco 1
 
S-->>C1: Inizio Minigioco 2
S-->>C2: Inizio Minigioco 2 (broadcast)
S-->>C1: Timer Minigioco 2
S-->>C2: Timer Minigioco 2 (broadcast)
C1->>S: Completamento Minigioco 2
 
S-->>C1: Inizio Minigioco 3
S-->>C2: Inizio Minigioco 3 (broadcast)
S-->>C1: Timer Minigioco 3
S-->>C2: Timer Minigioco 3 (broadcast)
C1->>S: Completamento Minigioco 3
 
C1->>S: Selezione Busta
S-->>C1: Premio Finale
S-->>C2: Premio Finale (broadcast)
 
S-->>C1: Vincitore Finale
S-->>C2: Vincitore Finale (broadcast)
