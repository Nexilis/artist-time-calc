(ns artist-time-calc.init
  (:require [artist-time-calc.conf :refer :all]
            [clojure.spec.alpha :as s]))
; TODO: try to use spec - https://clojure.org/guides/spec

(defn- calc-cr-h-this-day [remaining-surplus-h]
  (+ cr-base-h-per-day
     (if (> remaining-surplus-h 0) 1 0)))

(defn initialize-calendar
  "Returns calendar (in a vector) where each day is represented by
  a dictionary {:day :not-cr-h :cr-h}"
  []
  (loop [i 1
         calendar []
         remaining-surplus-h cr-surplus-h]
    (let [cr-h-this-day (calc-cr-h-this-day remaining-surplus-h)]
      (if (<= i (config :wr-days))
        (recur (inc i)
               (conj calendar {:day i
                               :not-cr-h (- (config :wr-h-per-day) cr-h-this-day)
                               :cr-h cr-h-this-day})
               (dec remaining-surplus-h))
        calendar))))