# artist-time-calc
Console app for calculating time spent on creative work to comply with Polish Labor Law.

## Usage
    $ artist-time-calc.exe [args]

### Args
* -d | --days DAYS | Number of work days | default 20
* -h | --hours HOURS | Hours in a work day | default 8
* -p | --percentage PERCENTAGE | Percentage of creative work | default 0.7
* --help | not implemented yet

## Compilation
1. Install Leiningen from https://leiningen.org/
1. If needed add lein.bat to path
1. git clone
1. $ lein run

### Optionally
1. $ lein uberjar
1. Install Launch4j from http://launch4j.sourceforge.net/
1. If needed add launch4jc.exe to path
1. $ launch4jc.exe launch4j.xml

## Tests
    $ lein test

## License
Copyright © 2017 Bartek Łukasik

Distributed under the GNU GENERAL PUBLIC LICENSE Version 3.