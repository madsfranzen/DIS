# Opgave 1
Til X-købing kommune er der oprettet en 9600 bit per sekund linie. 
Kommunen har i øjeblikket 20 skærme og 5 almindelige printere, der anvender denne linie. 
Der anvendes til alle systemer tegnsættet EBCDIC (8-bit karaktersæt).

## Spørgsmål a
> Kommunen har lige hjemkøbt en hurtigprinter (1200 linier per minut) til udprintning af breve i A4- format.     
Kommunen havde tænkt sig, at også denne printer skulle kobles på den samme linie.  
> Kommenter om denne løsning er holdbar.

480 bit pr linje
printeren kan på et minut printe 1200 linjer a 480 bit pr linje
1200 * 480 = 576000
576000 / 60 sekunder = 9600
altså passer printerens hastighed nøjagtig til linjens bps

## Spørgsmål b
Via skærmene kan man køre en række systemer, der alle er fuldskærmsorienterede 
(dvs man udfylder et helt skærmbillede inden dette sendes over linien lige som 
man altid får et helt skærmbillede retur). 
Et typisk skærmbillede fylder 1000 bytes. Hvor meget af brugerens svartid skyldes netværket?

Det hele? Og så selve processeringstiden oveni.
9600 linjens bps / 1000 bits

## Spørgsmål c
Du har sikkert under de to første spørgsmål set bort fra en række faktorer, der ville 
kunne gøre transmissionen langsommere. Nævn disse faktorer. 

Processeringstiden
Tab af bits.
"Kø" på linjen

Der findes faktisk også faktorer, der kunne gøre den hurtigere. Kunne du forestille dig nogle muligheder.

Jeg forestiller mig, at systemet er designet, så kunne "udfyldt" data bliver sendt. 
Altså tomme karakterer vil forhåbentlig ikke blive sendt frem og tilbage.

## Spørgsmål d
Hvad vil der ske, hvis alle 20 skærme sender et skærmbillede samtidigt?

Jeg forestiller mig, at man har implementeret en form for kø.

---

# Opgave 2

> *Denne opgave handler om at analysere mulighederne for at omlægge et system til afregning af 
> lægeregninger fra central til decentral eller distribueret drift. 
> Den centrale drift afvikles i udgangspunktet for opgaven fælles for hele landet på een mainframe. 
> Fakta omkring opgaven præsenteres her kort.*

Når en patient er hos lægen udfyldes en seddel med patientens cpr-nummer, lægens ydernummer 
(et nummer, der entydigt identificerer lægen) og en række koder, der angiver hvad lægen har foretaget på/ved patienten. 
Disse koder er nødvendige, idet lægen får betaling efter hvor meget han faktisk har udført 
(dvs der er koder for øreskylning, receptudskrivning, fjernelse af fremmedlegemer fra næseregionen osv.). 
Hver uge sender lægen lægeregningerne til det amt, hvor lægen bor. Hos amtet tastes regningerne ind. 
I forbindelse med indtastningen kontrollerer systemet at cpr-nummer, ydernummer og behandlingskoder er valide. 
En gang per måned køres en afregningskørsel på de indtastede regninger. 
Denne afregning resulterer i, at der via PBS tilsendes de enkelte læger en betaling svarende til de indsendte regninger. 
Dette betyder, at et amt altid afregner med de læger, der bor i amtet.
Amterne skal altid betale for patienterne i eget amt men naturligvis ikke for patienter fra andre amter.

Hvis en læge i amtet således har behandlet en patient fra et andet amt, betaler lægens hjemamt i første omgang regningen, 
men sammentæller under afregningskørslen hvilke beløb den således har lagt ud for andre amter. 
Disse udlæg amterne imellem opgøres ved en efterfølgende kørsel og evt. tilgodehavende/skyldige 
beløb afregnes via et specielt offentligt refusionssystem.

Basalt er der altså tre funktionaliteter
1. Indberetning af lægeregninger (inklusiv validering)
2. Afregning til de enkelte læger (kører en gang per måned)
3. Udregning af tilgodehavende/udestående mellem amterne (kører en gang per måned)

## Spørgsmål a
*Lav en simpel datamodel (f.eks. et klassediagram) for dette system og omform efterfølgende til et antal relationelle tabeller.*

**Patient** = (PK)cpr,(FK.behandlingn)liste af behandlinger
**Kode** = hvilken type behandling, pris
**Behandling** = (FK.patient)patient, (FK.ydernummer)læge, (FK.kode)kode
**Læge** = (PK)ydernummer
**Amt** = (FK.ydernummer)læge 

|     AMT     |    LÆGE     |        |
| -------- | ------- | ------ |
|          |         |         |
|  a        |         |          |
|m         |         |           |

## Spørgsmål b
Hvilke fordele ville man kunne opnå ved at gå fra central til decentral/distribueret drift.  
Med decentral menes i første omgang en maskine per amt.



## Spørgsmål c
Diskuter mulighederne for rene decentrale løsninger på dette område. Løsningsforslagene skal beskrive hvor hvilke data placeres og hvordan et evt. samspil mellem de decentrale anlæg kunne håndteres. I dette spørgsmål menes der med decentral en maskine per amt.

## Spørgsmål d
Beskriv en eller flere muligheder for distribuerede løsninger. Løsningerne skal igen indeholde angivelse af hvilke data, der placeres hvor. Løsningens konsekvenser skal angives.
