## Opgave 3

Denne opgave handler om at finde en maskinkonfiguration til at afvikle et IT-system til håndtering af TV-bingo-spillet Superchancen (de fleste af jer kan givetvis ikke huske det, men har måske set klip fra det med Suzanne Bjerrehus og nummer 40). Spillet foregik ved at folk, der ønskede at spille, gik på posthuset og indbetalte et girokort, der indeholdt en spilleplade. De spillede plader blev registreret i systemet og på selve spilleaftenen holdt systemet under udtrækningen øje med alle spillede plader og meddelte, hvornår der var gevinst. Meddelelsen skulle komme inden næste nummer blev udtrukket. Systemet skulle kunne klare 500.000 spillede plader.

**spørgsmål a**

Beskriv meget kort de to væsentligste ikke-funktionelle krav, du mener man måtte kunne stille til et sådant system.

**spørgsmål b**

Hvordan ville du programmere en løsning til sådan en opgave? (ingen kode - kun fortælle)

Hvor mange maskiner ville du anvende og hvordan ville du bruge maskinerne.

## Opgave 4

Denne opgave handler om at undersøge muligheder og problemer ved at kommunikere bits på et bestemt fysisk medium.

Jeres fysiske medium er lys via en lommelygte og I skal lave kommunikation af bits mellem to personer, der begge har en lommelygte.

 Hvad skal de to personer være enige om (giv et faktisk forslag til de ting de skal være enige om.)?
    0 = 1000ms (kort blink)
    1 = 2000ms (lang)
    start/slut = 3000ms

 Hvordan startes og afsluttes kommunikationen?
    start/slut = 3000ms
    
 Hvilken dataoverførselshastighed har I?
     26bit / minute
    
 Hvilke fejlkilder vil der være?
    
    
 Hvilke begrænsninger har jeres kommunikationsmedium?
    
    
 Kunne I give et forslag til kompression af data?
    
    

## Opgave 5

Find din egen IP-adresse via kommandoen ipconfig på Command-prompten (cmd).

Prøv også at finde din IP via myip.dk.

Hvorfor er der forskel?

## Opgave 6

I denne opgave skal vi lige repetere tråde. Vi anvender en Consol application.

**Der skal laves to tråde:**

Tråd1 indlæser strenge fra tastatur.

Tråd2 udskriver på skærmen hvert 3. sekund den seneste streng, som tråd1 har indlæst.

De to tråde skal altså ”dele” en String. På grund af Javas måde at håndtere primitive datatyper på i almindelighed og Strings i særdeleshed, er det bedste at placere String’en i en (common) klasse for sig selv og så lave en Get og Sæt metode til den.

Programmer de to tråde samt en main-metode og få det til at køre.
