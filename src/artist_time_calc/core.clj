(ns artist-time-calc.core
  (:use artist-time-calc.conf)
  (:use artist-time-calc.init)
  (:use artist-time-calc.rand)
  (:use [clojure.pprint :only [print-table]])
  (:gen-class))
; Dictionary:
; wr - work
; cr - copyrighted
; h  - hours

(defn- reduce-column-to-sum [calendar column]
  (reduce (fn [accumulator day] (+ accumulator (day column)))
          0 ;; accumulator's initial value
          calendar))

;; TODO: add possibility to pass config values from terminal
(defn -main
  [& args]
  (let [calendar (randomize-calendar (initialize-calendar))]
    (print-table calendar)
    (println "Total worked hours (expected):" wr-total-h)
    ;; TODO: add unit tests summing cr-h and comparing with cr-total-h, etc
    (println "Total copyrighted hours (in calendar):" (reduce-column-to-sum calendar :cr-h))
    (println "Total copyrighted hours (expected):" cr-total-h)
    (println "Total not-copyrighted hours (in calendar):" (reduce-column-to-sum calendar :not-cr-h))
    (println "Total not-copyrighted hours (expected):" (- wr-total-h cr-total-h))
    (println "Avg. copyrighted hours per day:" cr-base-h-per-day "+" cr-surplus-h "hours surplus ")))