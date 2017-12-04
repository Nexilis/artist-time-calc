(ns artist-time-calc.rand
  (:require [artist-time-calc.conf :refer :all]))

(defn- two-various-days []
  (let [num-days (-> :wr-days config range)
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
    (let [rand-calendar (-> (update-in calendar [fir-day :not-cr-h] inc)
                            (update-in [fir-day :cr-h] dec)
                            (update-in [sec-day :not-cr-h] dec)
                            (update-in [sec-day :cr-h] inc))]
      (if (negative-val-in-column? rand-calendar :not-cr-h) calendar rand-calendar))))

(defn randomize-calendar
  "Randomizes passed calendar according to randomization lvl"
  [calendar]
  (loop [i 0
         days-pair (two-various-days)
         rand-calendar calendar]
    (if (->> :randomization-lvl config (= i))
      rand-calendar
      (recur (inc i)
             (two-various-days)
             (single-calendar-randomization rand-calendar days-pair)))))
