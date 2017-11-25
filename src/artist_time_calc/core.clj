(ns artist-time-calc.core
  (:gen-class))
(use 'clojure.pprint)
; Dictionary:
; wr - work
; cr - copyrighted
; h  - hours

(def config {:wr-days 20
             :wr-h-per-day 8
             :cr-percentage 0.7
             :randomization-lvl 10})

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

;; TODO: move randomization to separate namespace
(defn- two-various-days []
  (let [num-days (range (config :wr-days))
        fir-day (rand-nth num-days)
        num-days (remove (fn [x] (= x fir-day)) num-days)
        sec-day (rand-nth num-days)]
    (vector fir-day sec-day)))

(defn- negative-val-in-column? [calendar column]
  (some (fn [x] (< (x column) 0)) calendar))

(defn- single-calendar-randomization
  [calendar days-pair]
  (let [fir-day (get days-pair 0)
        sec-day (get days-pair 1)]
    (let [rand-calendar (update-in
                          (update-in
                            (update-in
                              (update-in calendar [fir-day :wr-h] inc)
                              [fir-day :cr-h] dec)
                            [sec-day :wr-h] dec)
                          [sec-day :cr-h] inc)]
      (if (negative-val-in-column? rand-calendar :wr-h) calendar rand-calendar))))

(defn randomize-calendar
  "Randomizes passed calendar according to randomization lvl"
  [calendar]
  (loop [i 0
         days-pair (two-various-days)
         rand-calendar calendar]
    (if (= i (config :randomization-lvl))
      rand-calendar
      (recur (inc i)
             (two-various-days)
             (single-calendar-randomization rand-calendar days-pair)))))

(defn reduce-column-to-sum [calendar column]
  (reduce (fn [accumulator day] (+ accumulator (day column)))
          0 ;; accumulator's initial value
          calendar))

;; TODO: add possibility to pass config values from terminal
(defn -main
  [& args]
  ;; TODO: add unit tests summing cr-h and comparing with cr-total-h, etc
  (let [calendar (randomize-calendar (calc-calendar))]
    (print-table calendar)
    (println "Total worked hours (expected):" wr-total-h)
    (println "Total worked hours (in calendar):" (reduce-column-to-sum calendar :cr-h))
    (println "Total copyrighted hours:" cr-total-h)
    (println "Base copyrighted hours per day:" cr-base-h-per-day)
    (println "Surplus copyrighted hours:" cr-surplus-h)))
