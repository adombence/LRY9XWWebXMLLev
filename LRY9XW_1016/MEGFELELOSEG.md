# Követelményenkénti megfelelőségi jelentés

Forrás: Webes adatkezelő környezetek – 2. Consultation, 2026. őszi félév.

| Követelmény | Megvalósítás | Állapot |
|---|---|---|
| `LRY9XW_1016` leadási mappa | Elkülönítve az első konzultáció `LRY9XW_0912` mappájától | Kész |
| Szerkeszthető XDM | `LRY9XWXDM.drawio`, tömörítetlen Draw.io XML | Kész |
| Sémanév | `LRY9XW_Vendeglatas` gyökér | Kész |
| XDM alapján készített XML | `LRY9XW_XML.xml` | Kész |
| Saját egyszerű típusok | név, nem üres szöveg, azonosítók, kor, csillag, összeg, műszak | Kész |
| Összetett típusok | cím, végzettségek, gyakorlat, műszakok és minden entitás | Kész |
| Ismétlődések | entitások és többértékű attribútumok `maxOccurs="unbounded"` | Kész |
| ER-kapcsolatok | `xs:key`, `xs:keyref`, valamint az 1:1 `e_f` egyediség | Kész |
| Pozitív validálás | a teljes XML sikeresen validálható | Kész |
| Negatív validálás | `kor=-1` elutasítva, egyértelmű `KorTipus` hibával | Kész |
| Projekt neve | `DomParseLRY9XW` | Kész |
| Érvényes Java package | `domlry9xw` | Kész |
| DOMRead osztály/fájl | `DomReadLRY9XW.java` | Kész |
| Teljes dokumentum feldolgozása | rekurzív, általános DOM-bejárás | Kész |
| Blokkos konzolkimenet | elemek, rendezett attribútumok és levélértékek | Kész |
| Biztonságos XML-parser | külső entitások, DTD, XInclude és külső hozzáférés tiltva | Kész |
| DOMModify osztály/fájl | `DomModifyLRY9XW.java` | Kész |
| `v1` név és kor módosítása | valódi DOM-csomópontokon | Kész |
| `gyakornok/@e_gy=e3` | az első gyakornok (`gy1`) attribútumán | Kész |
| Összes rendelés törlése | élő DOM `NodeList` elemeinek eltávolításával | Kész |
| Módosított XML kiírása | UTF-8, behúzott XML a standard kimeneten | Kész |
| Eredeti XML megőrzése | a program nem ír fájlt; JUnit bájtszinten ellenőrzi | Kész |
| Eltérő XML-fájlnevek | dokumentált, azonos tartalmú kompatibilitási példány | Kész |
| Java 21 és Maven | fordítás, teszt, JAR-csomagolás | Kész |
| Automatizált tesztek | 5/5 sikeres | Kész |
| GitHub-művelet | nem történt push vagy távoli repository-módosítás | Betartva |

## ER-modell egyeztetése

- `Főszakács`: kulcs, név, életkor, többértékű végzettség.
- `Étterem`: kulcs, név, csillag, összetett cím.
- `Szakács`: kulcs, név, részleg, többértékű végzettség.
- `Gyakornok`: kulcs, név, összetett gyakorlat, többértékű műszak.
- `Vendég`: kulcs, név, életkor, összetett cím.
- `E-F`: 1:1; `E-Sz` és `E-Gy`: 1:N.
- `Rendelés`: Étterem–Vendég N:M kapcsolat, összeg és étel
  kapcsolati attribútumokkal.

## Dokumentált eltérések

Az első konzultáció technikai XML-nevei ékezet nélküliek. A második PDF
tipográfiailag `vendég` és `rendelés` alakot használ, de ugyanannak a
korábban elkészített modellnek a folytatását kéri. Ezért a DOM-kód a tényleges
`vendeg` és `rendeles` elemneveket használja.

A PDF DOMModify részében a „DOM class name” sor tévesen DomRead osztályt
említ; a feladat címe és előírt fájlneve alapján a megvalósított osztály neve
`DomModifyLRY9XW`.

## Hátralévő kézi ellenőrzés

A Draw.io-fájl XML-szerkezete és modellcsomópontjai ellenőrzöttek. A fájlt
beadás előtt érdemes egyszer megnyitni a diagrams.net felületén, mert helyi
Draw.io alkalmazás/CLI ebben a környezetben nem áll rendelkezésre.
