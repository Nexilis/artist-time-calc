(ns artist-time-calc.init
  (:require [clojure.spec.alpha :as s]))
; TODO: try to use spec - https://clojure.org/guides/spec

(defn- calc-cr-h-this-day [remaining-surplus-h cr-base-h-per-day]
  (+ cr-base-h-per-day
     (if (> remaining-surplus-h 0) 1 0)))

(defn initialize-calendar
  "Returns calendar (in a vector) where each day is represented by
  a dictionary {:day :not-cr-h :cr-h}"
  [config]
  (loop [i 1
         calendar []
         remaining-surplus-h (-> :cr-surplus-h config)]
    (let [cr-h-this-day (calc-cr-h-this-day remaining-surplus-h (-> :cr-base-h-per-day config))]
      (if (<= i (-> :wr-days config))
        (recur (inc i)
               (conj calendar {:day i
                               :not-cr-h (- (-> :wr-h-per-day config) cr-h-this-day)
                               :cr-h cr-h-this-day})
               (dec remaining-surplus-h))
        calendar))))