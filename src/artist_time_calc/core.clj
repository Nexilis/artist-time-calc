(ns artist-time-calc.core
  (:gen-class))
(use 'clojure.pprint)
; Dictionary:
; wr - work
; cr - copyrighted
; h  - hours

(def config {:wr-days 20 :wr-h-per-day 8 :cr-percentage 0.7 :randomization-lvl 10})

(def cr-total-h (int (Math/floor (reduce * (vals config)))))
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

(defn two-various-days [calendar]
  (let [fir-day (rand-nth calendar)
        cal-removed-fir-day (remove (fn [x] (= (x :day) (fir-day :day))) calendar)
        sec-day (rand-nth cal-removed-fir-day)]
    (vector fir-day sec-day)))

(defn single-calendar-randomization
  [calendar days-to-unbalance])
;; TODO: try to unbalance two days (subtract hour from first and add to second)
;; TODO: return new calendar, remember about sorting by :day

(defn randomize-calendar
  "Randomizes passed calendar according to randomization lvl"
  [calendar]
  (loop [i 1
         days-to-unbalance (two-various-days calendar)
         rand-calendar calendar]
    (if (= i (config :randomization-lvl))
      rand-calendar
      (recur (inc i)
             (two-various-days rand-calendar)
             (single-calendar-randomization rand-calendar days-to-unbalance)))))

(defn -main
  [& args]
  (print-table (calc-calendar))
  (println "Total worked hours:" wr-total-h)
  (println "Total copyrighted hours:" cr-total-h)
  (println "Base copyrighted hours per day:" cr-base-h-per-day)
  (println "Surplus copyrighted hours:" cr-surplus-h))
