# Workshop

# Socket – programmering

Denne workshop har til formål at lære jer om socket-programmering. I skal udvikle et program, der
er i stand til at lave ”talk” med andre programmer via en protokol I selv udvikler (Talk24V). Af
hensyn til mulighederne for at noget til at køre, henstilles det, at ambitionsniveauet for protokollen
holdes nede.

***Krav til Talk-delen:***

1. Talk-programmet skal basere sig på en åben standard kaldet Talk 2 4V
2. Man kan kun tale med en ad gangen.
3. Talk’en foregår ved, at initiativ-tageren sender en eller anden form for ”vil du snakke med
    mig” til en anden Talk-klient. Hvis den anden accepterer, starter der en dialog.
4. Dialog-formen er ”bordtennis-agtig”, dvs. man skiftes pænt til at sende noget til hinanden.
5. Brugeren fortæller, hvem han ønsker at lave talk med ved at indtaste modpartens
    netværksidentifikation f,eks IP-adresse (første version) eller ved at indtaste et (person)navn,
    der så via den nedenfor beskrevne navneservice, kan veksles til en netværksidentifikation
    (senere version).

## Opgave 1 – Specifikation af protokol

Lav specifikationerne til protokollen Talk 2 4V, herunder hvordan talk’en startes og
afsluttes. I første omgang er det kun talk-delen, der skal laves. Denne opgave vil blive
gennemgået inden I går videre. I vil få udleveret resultatet på Canvas.

## Opgave 2 – Programmering af Talk 2 4V version 1

Lav talk-programmet, så det overholder Talk 2 4V-standarden, men uden at det
anvender navneservicen. Programmet må gerne stoppe efter hver talk. Start med at
teste programmet, hvor du ”taler med dig selv”. Det er OK, hvis programmet stopper
lidt voldsomt.

Prøv derefter at teste, hvor du taler med en fra din egen gruppe.

Prøv til slut at teste hvor du taler med en fra en anden gruppe.

Gem din løsning, inden du går videre.

## Opgave 3 – Programmering af Talk 2 4V version 2

I øjeblikket skal man snakke bordtennisagtigt, dvs man skal skiftes til at sende en
besked. Lav programmet om, så denne begrænsning fjernes, dvs så man kan sende
flere gange i træk uden at vente på svar).

## Opgave 4 – Gruppetalk via UDP

Når man arbejder på et lokalnet kan man anvende UDP’s broadcastservice til at lave en gruppetalk.

***Bemærk at lige nu kan man IKKE bruge broadcast på skolens net. Vi skal derfor anvende
vores egne trådløse router.***

SSID : dlink-D6F
Password : hdldo

Vi vil som portnummer anvende 7777.

Tanken er at man kan sende en besked (som en broadcast) og denne besked kan ses af alle andre,
der anvendes programmet.

For at få det til at virke skal der laves to tråde.

En tråd, der i en uendelig løkke læser input fra tastatur og broadcaster det pågældende input.

En anden tråd, der i en uendelig løkke lytter efter broadcast-beskeder og udskriver dem på
skærmen.

Til sidst skal det laves en main til at få det hele til at køre

## Opgave 5 – Navneservice

I denne opgave skal I lave en navneservice, som kan kobles på jeres talk-løsning fra opgave 2 eller
3.

***Krav til navneservicen***

1. Navneservicen skal basere sig på en selvdefineret protokol.
2. Navneservicen kører på en maskine (navneserveren), hvis netværksidentifikation er
    hardkodet ind i talk-klienterne.
3. Man tilmelder sig navneservicen, ved at sende et kaldenavn og den nødvendige
    netværksidentifikation til navneserveren.
4. En klient kan få netværksidentifikationen på en anden person ved at sende navnet på den
    pågældende til navneserveren.

Start med at lave protokollen.

Programmer navneserveren.

Indsæt derefter et kald til navneserveren i din talk-løsning.
