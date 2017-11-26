(ns artist-time-calc.rand)
(use 'artist-time-calc.conf)

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
