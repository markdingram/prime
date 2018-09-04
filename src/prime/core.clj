(ns prime.core
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.pprint :as pprint])
  (:gen-class))

(defn is-prime?
  "Checks if n is prime"
  [n]
  (if (< n 2)
    false
    (loop [c 2
           max (int (Math/sqrt n))]
      (if (> c max)
        true
        (if (= 0 (mod n c))
          false
          (recur (inc c) max))))))

(defn sieve
  "Sieves out non primes from 1 to n.

   Argument s is the size of the sieve.

   See https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes"
  [s]
  (let [;; stop sieving when c^2 > n
        max (-> s (Math/sqrt) (Math/ceil) (int))]
    (reduce (fn [sieve c]
              (remove #(and
                         (not= % c)
                         (zero? (mod % c)))
                      sieve))
            (range 2 s)
            (range 2 max))))

(defn primes
  "Returns the first n prime numbers.

   The starting-sieve-size value is used, but if that doesn't generate enough
   primes then the sieve size doubles each loop until enough primes are returned"
  [n]
  (let [starting-sieve-size 100]
    (loop [s starting-sieve-size]
      (let [found (sieve s)
            first-n (take n found)]

        (if (= n (count first-n))
          first-n
          ; Otherwise recur, incrementing sieve size by 2
          (do
            ;(println "only found " (count first-n) ", increasing sieve size to " (* 2 s))
            (recur (* 2 s))))))))

(let [max-test-prime 100]
  (s/fdef primes
    :args (s/cat :n (s/with-gen
                      pos-int?
                      #(gen/large-integer*  {:min 0 :max max-test-prime})))
    :ret (s/coll-of is-prime?)
    ;:ret (s/coll-of (complement is-prime?))
    :fn (fn [{:keys [args ret]}]
          (= (:n args) (count ret)))))



(defn- to-grid-rows [ns]
  (conj
    (map (fn [n]
           (-> (into {} (map #(vector % (* % n)) ns))
               ;; assoc to add in the row labels
               (assoc "" n)))
     ns)))


(defn print-grid [ns]
  (pprint/print-table (cons "" ns) (to-grid-rows ns)))


(defn -main
  "Prints the multiplicative grid for the first n primes.

   N is the first argument, 10 is the default if not supplied."
  [& [n _]]
  (-> (or (when n
            (Integer/parseInt n))
          10)
      (primes)
      (print-grid)))


;; for repl driven development
(comment
  (map is-prime? (range 20))
  (sieve 100)
  (primes 100)
  (do
    (require '[clojure.spec.test.alpha :as stest])
    (stest/instrument `primes)
    (stest/check `primes))
  (print-grid-rows [1 3 5])
  (print-grid (primes 10)))

