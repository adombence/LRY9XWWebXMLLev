# WebXML - 1. konzultáció

- Hallgató: KITÖLTENDŐ TELJES NÉV
- Neptun-kód: `LRY9XW`
- Szak: KITÖLTENDŐ SZAK

## Fájlok

- `LRY9XW_orarend.xml`: a Slot-export mind a(z) 20 eseménye.
- `LRY9XW_xmltohtml.html`: az órarend XSLT-vel előállított HTML-táblázata.
- `LRY9XW.orarendXDM.drawio`: az órarend szerkeszthető XDM-modellje.
- `LRY9XW.orarendER.drawio`: az órarend szerkeszthető ER-modellje.
- `LRY9XW_konferencia.xml`: a megadott konferencia-XDM példánya.
- `XDMLRY9XW.drawio`: a Vendéglátás ER-modellből készített XDM.
- `LRY9XW_XML.xml`: a Vendéglátás XDM-hez tartozó XML.
- `LRY9XW_vendeglatas_chatgpt_prompt.txt`: reprodukálható 4/c prompt.

## Adatkezelési döntések

- Az eredeti ICS nem része a beadandó mappának és nincs verziókezelésbe véve.
- A Slot `DESCRIPTION` mezője ebben az exportban kizárólag oktatónevet
  tartalmaz. Az óratípus ezért üres, nem kitalált érték.
- Az időpontok UTC-ből `Europe/Budapest` időzónára alakítva szerepelnek.
- A `nap` ISO-dátum, így a teljes félév minden konzultációja egyértelműen
  reprezentálható.
- A Vendéglátás XML az ER-modell kulcsait `id` attribútumként, az 1:N
  kapcsolatokat hivatkozó attribútumokkal (`e_f`, `e_sz`, `e_gy`), az N:M
  Rendelés kapcsolatot külön `rendeles` elemekkel ábrázolja.
- Az összetett attribútumok beágyazott elemek, a többértékű attribútumok
  ismételhető elemek.

## Kiírásbeli névütközések

Az első konzultáció repository-neve `NEPTUNKODWebXMLLev`, a másodiké
`NEPTUNKODWebXML`; távoli repository nem lett létrehozva vagy átnevezve.

A Vendéglátás Draw.io fájlnevére három alak szerepel a PDF-ekben:
`XDMNeptunkod.drawio` (1. konzultáció 4/a), `Neptunkod_XDM.drawio`
(1. konzultáció 4/c) és `NeptunkodXDM.drawio` (2. konzultáció 1/a).
Az első konzultáció explicit 4/a mentési előírása miatt az első alak a
beadandó elsődleges fájlneve: `XDMLRY9XW.drawio`.

## Újragenerálás

A projekt gyökerében:

```bash
mvn clean package
java -jar target/webxml-converter-1.0-SNAPSHOT.jar \
  /abszolut/utvonal/orarend.ics \
  /kimeneti/szulo/mappa \
  LRY9XW \
  "KITÖLTENDŐ TELJES NÉV" \
  "KITÖLTENDŐ SZAK"
```
