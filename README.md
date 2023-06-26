# ProgettoEsame
Fatto da Nadr Abou Moustafa e Andrea Cantone
Abou Moustafa Nadr e Cantone Andrea 				Software architect sez. A


PROGETTO - UFS 06
Documento delle specifiche


Descrizione del progetto ad alto livello 

Il progetto da noi presentato consiste in un videogioco giocabile da due persone in una singola partita. Il gioco si presenta con un sfondo, generalmente di colore grigio, con due barre di cuori che rappresentano la vita rimanente a ciascun giocatore, opzionalmente sarà presente anche un valore numerico che rappresenterà la successione di partite vinte nella sessione di gioco attuale, I due giocatori invece saranno rappresentati da due piccoli carri armati stilizzati posti ognuno ai lati della schermata. Qui sotto riportato un esempio :

![Immagine 2023-06-21 124349](https://github.com/Nadr-Abou/ProgettoEsame/assets/134308276/4b9d7cdf-70db-4bdb-a5b4-45dfca6e99b5)

Interfaccia grafica

I giocatori solo dopo essersi connessi al server potranno effettivamente sfidare l’avversario muovendosi verso l’alto o verso il basso con le freccette e premendo il tasto barra spaziatrice il giocatore potrà sparare tentando di colpire l’avversario che se colpito perderà un cuore. Quando i cuori di uno dei due dei giocatori saranno finiti la partita terminerà, successivamente verrà mostrata un'interfaccia che darà la possibilità, se entrambi sono d’accordo, di ricominciare la partita

Funzionamento

Per il funzionamento del videogioco verranno creati due progetti java differenti uno per il server e uno per il client. Per quanto riguarda il server esso provvederà alla connessione fra i due giocatori e provvederà attraverso le coordinate inviate dai client al server a sincronizzare le interfacce grafiche dei due client, in modo tale che se un giocatore desideri cambiare la sua posizione dovrà mandare la sue nuove coordinate al server che provvederà a sua volta a rendere effettivo il movimento a entrambi i giocatori; oltre a ciò il server avrà il compito di sincronizzare e di gestire lo sparo di uno dei due giocatori controllando se avviene o meno la collisione con il bersaglio ovvero il giocatore nemico. La gestione della vita di ogni singolo giocatore e del numero di vittorie ottenuto in una singola sessione di gioco verrà affidata completamente al server.

Elementi nel codice

Per la creazione dell’effetto dello sparo effettuato da uno dei due giocatori, verrà utilizzato un timer-thread che ciclicamente incrementerà l’asse X del proiettile, precedentemente creato, fino a raggiungere il bersaglio o la fine della finestra.

Per gestire il valore numerico legato al numero di vittorie che verrà mostrato ad entrambi gli utenti verranno utilizzate due variabili ognuna legata al giocatore e incrementata quando avverrà un evento specifico ovvero la vittoria di uno dei due giocatori.

Attraverso le variabili verranno gestiti i cuori e la vita di ciascun giocatore. Usando le coordinate invece verrà gestito il movimento dei giocatori e dei proiettili che verranno incrementati a seguito degli input inseriti dai giocatori. 


![Immagine 2023-06-21 124418](https://github.com/Nadr-Abou/ProgettoEsame/assets/134308276/263cc3c5-ebc7-486d-b6a8-8da7d63eb7c1)



Meccaniche di gioco
