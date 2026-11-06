# WebXML – 2. konzultáció

Neptun-kód: `LRY9XW`

Ez a mappa a második konzultáció Vendéglátás XDM-, XML-, XSD- és Java DOM
feladatait tartalmazza. Az első konzultáció modelljét változatlan szerkezettel
viszi tovább.

## Leadandó fájlok

- `LRY9XWXDM.drawio`: szerkeszthető, tömörítetlen diagrams.net XDM.
- `LRY9XW_XML.xml`: az XDM-hez tartozó Vendéglátás XML.
- `LRY9XW_XMLSchema.xsd`: saját egyszerű és összetett típusokat használó
  XML Schema.
- `DomParseLRY9XW/`: Java 21 Maven-projekt a két DOM-alkalmazással és
  JUnit-tesztekkel.

## Fájlnév-egységesítés

A PDF három eltérő XML-hivatkozást használ. Az elsődleges leadandó
`LRY9XW_XML.xml`. A `DomParseLRY9XW/LRY9XWXML.xml` ennek bájtra azonos
kompatibilitási példánya, mert a DOM-feladat ezt az aláhúzás nélküli nevet
írja elő. Nincs két eltérő adatszerkezet.

A PDF-ben szereplő magyar fogalmakat az első konzultáció már elkészült,
ékezet nélküli technikai elemneveivel egyeztettük:
`vendeg`, `nev`, `kor`, `gyakornok/@e_gy`, `rendeles`.

## XSD-validálás

A mappa gyökerében:

```bash
xmllint --noout --schema LRY9XW_XMLSchema.xsd LRY9XW_XML.xml
```

A szándékosan negatív életkort tartalmazó ellenpélda:

```bash
xmllint --noout --schema LRY9XW_XMLSchema.xsd \
  DomParseLRY9XW/src/test/resources/invalid-negative-age.xml
```

Az első parancsnak sikerülnie, a másodiknak `KorTipus` hibával el kell
buknia.

## Maven build és tesztek

```bash
cd DomParseLRY9XW
mvn clean test
mvn package
```

Előfeltétel: JDK 21 vagy újabb és Maven 3.9 vagy újabb.

## DOMRead futtatása

```bash
cd DomParseLRY9XW
java -cp target/classes domlry9xw.DomReadLRY9XW
```

Argumentum nélkül a projekt gyökerében lévő `LRY9XWXML.xml` fájlt olvassa.
Más fájl is megadható:

```bash
java -cp target/classes domlry9xw.DomReadLRY9XW /abszolut/utvonal/adat.xml
```

Az alkalmazás rekurzívan bejárja a teljes DOM-fát, és minden elemet,
attribútumot és levélértéket behúzott blokkos formában ír ki.

## DOMModify futtatása

```bash
cd DomParseLRY9XW
java -cp target/classes domlry9xw.DomModifyLRY9XW
```

A program a DOM-fában:

1. a `vendeg id="v1"` nevét `Módosított Vendég`, korát `35` értékre
   állítja;
2. az első gyakornok (`gy1`) `e_gy` attribútumát `e3` értékre állítja;
3. mind az 5 `rendeles` elemet eltávolítja;
4. a módosított, well-formed XML-t a standard kimenetre írja.

Az eredeti fájlt nem írja felül. Külön eredményfájl igény esetén:

```bash
java -cp target/classes domlry9xw.DomModifyLRY9XW \
  LRY9XWXML.xml > LRY9XWXML_modified.xml
```

A módosított fájl is validálható, mert a sémában a `rendeles` ismétlődése
`0..*`.

## Biztonság

A DOM-parser tiltja a DOCTYPE-deklarációt, a külső általános és paraméteres
entitásokat, a külső DTD-ket, az XInclude-ot és a külső sémahozzáférést.
Ennek működését külön JUnit-teszt ellenőrzi.
