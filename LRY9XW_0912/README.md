# 1. konzultacio

`LRY9XW`

## Fajlok

- `LRY9XW_orarend.xml`
- `LRY9XW_xmltohtml.html`
- `LRY9XW.orarendXDM.drawio`
- `LRY9XW.orarendER.drawio`
- `LRY9XW_konferencia.xml`
- `XDMLRY9XW.drawio`
- `LRY9XW_XML.xml`
- `LRY9XW_vendeglatas_chatgpt_prompt.txt`

## Vendeglatas ER

```mermaid
erDiagram
    ETTEREM ||--o{ FOSZAKACS : foglalkoztat
    ETTEREM ||--o{ SZAKACS : foglalkoztat
    ETTEREM ||--o{ GYAKORNOK : foglalkoztat
    ETTEREM ||--o{ RENDELES : fogad
    VENDEG ||--o{ RENDELES : lead

    ETTEREM {
        string id PK
        string nev
        int csillag
    }
    FOSZAKACS {
        string id PK
        string e_f FK
        string nev
        int kor
    }
    SZAKACS {
        string id PK
        string e_sz FK
        string nev
        string reszleg
    }
    GYAKORNOK {
        string id PK
        string e_gy FK
        string nev
    }
    VENDEG {
        string id PK
        string nev
        int kor
    }
    RENDELES {
        string id PK
        string etterem_ref FK
        string vendeg_ref FK
        int osszeg
        string etel
    }
```
