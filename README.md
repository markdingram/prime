# prime

A Clojure library designed to print a multiplicative grid of prime numbers

## Usage

`lein run` will print the multiplicative grid for the first 10 prime numbers:

````
|    |  2 |  3 |   5 |   7 |  11 |  13 |  17 |  19 |  23 |  29 |
|----+----+----+-----+-----+-----+-----+-----+-----+-----+-----|
|  2 |  4 |  6 |  10 |  14 |  22 |  26 |  34 |  38 |  46 |  58 |
|  3 |  6 |  9 |  15 |  21 |  33 |  39 |  51 |  57 |  69 |  87 |
|  5 | 10 | 15 |  25 |  35 |  55 |  65 |  85 |  95 | 115 | 145 |
|  7 | 14 | 21 |  35 |  49 |  77 |  91 | 119 | 133 | 161 | 203 |
| 11 | 22 | 33 |  55 |  77 | 121 | 143 | 187 | 209 | 253 | 319 |
| 13 | 26 | 39 |  65 |  91 | 143 | 169 | 221 | 247 | 299 | 377 |
| 17 | 34 | 51 |  85 | 119 | 187 | 221 | 289 | 323 | 391 | 493 |
| 19 | 38 | 57 |  95 | 133 | 209 | 247 | 323 | 361 | 437 | 551 |
| 23 | 46 | 69 | 115 | 161 | 253 | 299 | 391 | 437 | 529 | 667 |
| 29 | 58 | 87 | 145 | 203 | 319 | 377 | 493 | 551 | 667 | 841 |
````

A single integer command line argument is supported to control the number of primes in the grid.

`lein run 5` prints the grid for the first 5 prime numbers:

````
|    |  2 |  3 |  5 |  7 |  11 |
|----+----+----+----+----+-----|
|  2 |  4 |  6 | 10 | 14 |  22 |
|  3 |  6 |  9 | 15 | 21 |  33 |
|  5 | 10 | 15 | 25 | 35 |  55 |
|  7 | 14 | 21 | 35 | 49 |  77 |
| 11 | 22 | 33 | 55 | 77 | 121 |
````


Alternate way to run:

````
lein uberjar
java -jar target/prime-0.1.0-SNAPSHOT-standalone.jar
````

## Testing

Development was done in a REPL driven fashion, however the `prime.core/primes` method has been specced,
so `lein test` will test for various input sizes.

To see a nicely formatted expound failure update the spec with `(s/coll-of (completement is-prime?))`

## License

Copyright © 2018 Mark Ingram

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
