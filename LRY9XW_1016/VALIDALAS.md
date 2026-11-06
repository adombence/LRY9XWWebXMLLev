# Validálási és futtatási eredmények

Ellenőrzés dátuma: 2026-11-05.

## XML és XSD

Pozitív ellenőrzés:

```bash
xmllint --noout --schema LRY9XW_XMLSchema.xsd LRY9XW_XML.xml
```

Tényleges eredmény:

```text
LRY9XW_XML.xml validates
kilépési kód: 0
```

Negatív ellenőrzés:

```bash
xmllint --noout --schema LRY9XW_XMLSchema.xsd \
  DomParseLRY9XW/src/test/resources/invalid-negative-age.xml
```

Tényleges eredmény:

```text
Element 'kor': '-1' is not a valid value of the atomic type 'KorTipus'.
invalid-negative-age.xml fails to validate
kilépési kód: 3
```

Ez igazolja, hogy a séma nem csak szintaktikailag olvasható, hanem a saját
`KorTipus` tartalmi megszorítását is érvényesíti.

## Java 21 fordítás

```bash
javac -Xlint:all --release 21 \
  -d /private/tmp/domparse-lry9xw-main \
  DomParseLRY9XW/src/main/java/domlry9xw/*.java
```

Eredmény: sikeres, figyelmeztetés nélkül.

## Maven és JUnit

```bash
cd DomParseLRY9XW
mvn -o test
mvn -o package
```

Legutóbbi ellenőrzött eredmény:

```text
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

A tesztek ellenőrzik:

- a teljes XML és mind a 21 entitáspéldány beolvasását;
- a teljes hierarchikus blokkos kimenet reprezentatív mélypontjait;
- a DOCTYPE és külső entitás feldolgozásának tiltását;
- a `v1` vendég nevének és korának módosítását;
- a `gy1/@e_gy` átállítását `e3` értékre;
- mind az 5 `rendeles` eltávolítását;
- a módosított XML újraolvasását és XSD-validálását;
- az eredeti bemeneti fájl bájtszintű változatlanságát;
- a helyes XML elfogadását és a negatív kor elutasítását.

## Parancssori alkalmazások

```bash
java -cp target/classes domlry9xw.DomReadLRY9XW
java -cp target/classes domlry9xw.DomModifyLRY9XW
```

Mindkét belépési pont 0 kilépési kóddal lefutott. A DOMRead a teljes
dokumentumot írta ki. A DOMModify naplója:

```text
Módosítva: vendég v1, gyakornok gy1; törölt rendelések: 5
```

A módosított XML tartalmazta az új nevet és kort, a `gy1 e_gy="e3"`
attribútumot, és nem tartalmazott `rendeles` elemet.

A DOMModify tényleges standard kimenetét külön ideiglenes fájlba irányítva
ismét lefutott az XSD-validálás. Eredmény:

```text
LRY9XWXML_modified_final.xml validates
name=Módosított Vendég; age=35; gy1-e_gy=e3; orders=0
```

## Draw.io és fájlkonzisztencia

- A `LRY9XWXDM.drawio` well-formed, tömörítetlen
  `mxfile/diagram/mxGraphModel` dokumentum.
- A diagram az öt entitást, a három közvetlen kapcsolatot és az N:M
  Rendelés kapcsolatot modellezi.
- A két XML-fájlnév-változat bájtra azonos.
- A diagrams.net alkalmazásbeli vizuális megnyitás nem futott, mert helyi
  Draw.io alkalmazás/CLI nem áll rendelkezésre.
