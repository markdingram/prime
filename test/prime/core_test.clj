(ns prime.core-test
  (:require [prime.core :as prime]

            [clojure.spec.alpha      :as s]
            [clojure.spec.test.alpha :as stest]
            [clojure.string          :as str]
            [clojure.test            :as test]
            [expound.alpha           :as expound]))

;; lifted from: https://gist.github.com/kennyjwilli/8bf30478b8a2762d2d09baabc17e2f10

(defn report-results [check-results]
  (let [checks-passed? (->> check-results (map :failure) (every? nil?))]
    (if checks-passed?
      (do
        (test/do-report {:type    :pass
                         :message (str "Generative tests pass for "
                                       (str/join ", " (map :sym check-results)))}))
      (doseq [failed-check (filter :failure check-results)]
        (let [r       (stest/abbrev-result failed-check)
              failure (:failure r)]
          (test/do-report
            {:type     :fail
             :message  (binding [s/*explain-out* (expound/custom-printer {:theme :figwheel-theme})]
                         (expound/explain-results-str check-results))
             :expected (->> r :spec rest (apply hash-map) :ret)
             :actual   (if (instance? Throwable failure)
                         failure
                         (::stest/val failure))}))))
    checks-passed?))

(defmacro defspec-test
  ([name sym-or-syms] `(defspec-test ~name ~sym-or-syms nil))
  ([name sym-or-syms opts]
   (when test/*load-tests*
     `(defn ~(vary-meta name assoc :test
                        `(fn [] (report-results (stest/check ~sym-or-syms ~opts))))
        [] (test/test-var (var ~name))))))


(defspec-test check-primes-function `prime/primes)

