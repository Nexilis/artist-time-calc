(ns artist-time-calc.core (:gen-class))
(use '[artist-time-calc.config :only [config]])
(use 'artist-time-calc.rand)
(use 'clojure.pprint)
; Dictionary:
; wr - work
; cr - copyrighted
; h  - hours

(def cr-total-h (int (Math/floor (* (config :wr-days)
                                    (config :wr-h-per-day)
                                    (config :cr-percentage)))))
(def cr-base-h-per-day (int (Math/floor (/ cr-total-h (config :wr-days)))))
(def cr-surplus-h (int (mod cr-total-h (config :wr-days))))
(def wr-total-h (* (config :wr-days) (config :wr-h-per-day)))

(defn calc-cr-h-this-day [remaining-surplus-h]
  (+ cr-base-h-per-day
     (if (> remaining-surplus-h 0) 1 0)))

(defn calc-calendar []
  (loop [i 1
         calendar []
         remaining-surplus-h cr-surplus-h]
    (let [cr-h-this-day (calc-cr-h-this-day remaining-surplus-h)]
      (if (<= i (config :wr-days))
        (recur (inc i)
               (conj calendar {:day i
                               :wr-h (- (config :wr-h-per-day) cr-h-this-day)
                               :cr-h cr-h-this-day})
               (dec remaining-surplus-h))
        calendar))))

(defn reduce-column-to-sum [calendar column]
  (reduce (fn [accumulator day] (+ accumulator (day column)))
          0 ;; accumulator's initial value
          calendar))

;; TODO: add possibility to pass config values from terminal
(defn -main
  [& args]
  ;; TODO: add unit tests summing cr-h and comparing with cr-total-h, etc
  (let [calendar (randomize-calendar (calc-calendar) (config :randomization-lvl))]
    (print-table calendar)
    (println "Total worked hours (expected):" wr-total-h)
    (println "Total worked hours (in calendar):" (reduce-column-to-sum calendar :cr-h))
    (println "Total copyrighted hours:" cr-total-h)
    (println "Base copyrighted hours per day:" cr-base-h-per-day)
    (println "Surplus copyrighted hours:" cr-surplus-h)))
