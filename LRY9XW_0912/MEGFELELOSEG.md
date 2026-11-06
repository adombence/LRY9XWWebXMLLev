# Megfelelőségi jelentés

| Feladat | Elkészült fájl | Állapot |
|---|---|---|
| 1/a-b Órarend XML | `LRY9XW_orarend.xml` | 20 Slot `VEVENT`, teljes félév |
| 1/c XML -> HTML | `LRY9XW_xmltohtml.html` | XSLT, CSS, táblázat, készítői sor |
| 2 Órarend XDM | `LRY9XW.orarendXDM.drawio` | Szerkeszthető Draw.io |
| 2 Órarend ER | `LRY9XW.orarendER.drawio` | Szerkeszthető Draw.io |
| 3 Konferencia XML | `LRY9XW_konferencia.xml` | A PDF XDM-hierarchiáját követi |
| 4/a Vendéglátás XDM | `XDMLRY9XW.drawio` | ER-entitások, kulcsok, kardinalitások |
| 4/b Vendéglátás XML | `LRY9XW_XML.xml` | Összetett/többértékű attribútumok és N:M kapcsolat |
| 4/c ChatGPT-prompt | `LRY9XW_vendeglatas_chatgpt_prompt.txt` | Elkészült, nem állít végre nem hajtott ChatGPT-lépést |

## Második konzultációs kompatibilitás

A Vendéglátás XML tartalmazza a későbbi DOM-feladathoz szükséges
`vendeg id="v1"` példányt `nev` és `kor` elemmel, a `gyakornok`
elemeken `e_gy` attribútumot, valamint külön `rendeles` elemeket.
A szerkezet alkalmas saját egyszerű típusokat tartalmazó XSD készítésére.

## Nem automatikus műveletek

- GitHub repository nem készült és push nem történt.
- Az oktatónak nem lett email küldve.
- A ChatGPT-prompt külön fájlba került; külső ChatGPT-futtatás nem történt.
