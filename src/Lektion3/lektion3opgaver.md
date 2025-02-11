## Opgave 6

I denne opgave skal vi lige repetere tråde. Vi anvender en Consol application.

**Der skal laves to tråde:**

Tråd1 indlæser strenge fra tastatur.

Tråd2 udskriver på skærmen hvert 3. sekund den seneste streng, som tråd1 har indlæst.

De to tråde skal altså ”dele” en String. På grund af Javas måde at håndtere primitive datatyper på i almindelighed og Strings i særdeleshed, er det bedste at placere String’en i en (common) klasse for sig selv og så lave en Get og Sæt metode til den.

Programmer de to tråde samt en main-metode og få det til at køre.

### Opgave 7

I denne opgave skal vi kigge på Java-klasser fra java.net.

### Opgave 7.1

Skriv et Java-program, der henter en Web-side fra en bestemt web-server og udskriver den på
skærmen. Du skal gøre det i et simpelt consol-program.

Du kan anvende nedenstående kodestump:

**URL url = new URL("https://dis.students.dk/example1.php");
InputStreamReader r = new InputStreamReader(url.openStream());
BufferedReader in = new BufferedReader(r);
String str;
while ((str = in.readLine()) != null) {
System.out.println(str);
}
in.close();**

Du kan bruge dette program til at lave “screen-scraping”

Denne webside viser hvor mange gange den pågældende side har været vist.

Lav programmet om, så du kun udskriver selve tallet

Vi kan nu tilsvarende lave et lidt mere interessant og også lidt mere kompliceret eksempel.

Prøv web-siden

https://valutakurser.dk

både fra en browser og efterfølgende fra ovenstående programstump.

Lav nu programmet om, så det udelukkende udskriver valuta-kursen mellem US-dollars og den
danske krone.

## Opgave 8

Denne opgave går ud på at omskrive et lille program til flettesortering, så det er i stand til at anvende
flere CPU’er.

Programmet finder du i flettesortering (på CANVAS). Start med at hente programmet ned og kig det
igennem. Prøv at køre det.

Sæt derefter antallet af tal op til 1.000.000, udkommenter de to sysout’er af listerne og kør programmet
nogle gange. Noter køretiderne.

        Køretiden var 1071
        Køretiden var 1033
        Køretiden var 1036

Programmet skal nu konstrueres til at kunne anvende to CPU’er. Vi begrænser os til to CPU’er. Start derfor med at overveje hvilke dele, der skal flyttes til tråde og hvilke dele, der skal forblive i main.

Programmer derefter tråden og test programmet med et lille antal tal og sysout’erne.

Når det virker, sæt antallet af tal op til 1.000.000, udkommenter sysout’erne af listerne og kør
programmet nogle gange. Noter igen køretiderne.

Er det en tilfredsstillende gevinst med to CPU’er.?
