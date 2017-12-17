(ns artist-time-calc.conf
  (:require [clojure.spec.alpha :as s]))

; specifications for config keys
(s/def ::twelve-hours #(and (nat-int? %) (<= % 12)))
(s/def ::percentage #(and (float? %) (> % 0) (<= % 1)))

(s/def ::number-of-days-in-work pos-int?)
(s/def ::working-hours-in-a-day ::twelve-hours)
(s/def ::copyrighted-work-percentage ::percentage)
(s/def ::calendar-randomization-lvl nat-int?)
(s/def ::sum-of-copyrighted-hours-in-period nat-int?)
(s/def ::avg-copyrighted-hours-in-a-day nat-int?)
(s/def ::sum-of-surplus-copyrighted-hours-in-period nat-int?)
(s/def ::sum-of-worked-hours-in-period nat-int?)

; :req-un for required unqualified keys
(s/def ::in-config
  (s/keys :req-un [::number-of-days-in-work
                   ::working-hours-in-a-day
                   ::copyrighted-work-percentage]))

(s/def ::out-config
  (s/keys :req-un [::number-of-days-in-work
                   ::working-hours-in-a-day
                   ::copyrighted-work-percentage
                   ::calendar-randomization-lvl
                   ::sum-of-copyrighted-hours-in-period
                   ::avg-copyrighted-hours-in-a-day
                   ::sum-of-surplus-copyrighted-hours-in-period
                   ::sum-of-worked-hours-in-period]))

(def def-config {:number-of-days-in-work      20
                 :working-hours-in-a-day      8
                 :copyrighted-work-percentage 0.7
                 :calendar-randomization-lvl  10})

(defn calculate-config
  ([] (calculate-config def-config))
  ([in-config]
   {:pre [(s/valid? ::in-config in-config)]
    :post [(s/valid? ::out-config %)]}
   (let [out-config (conj in-config {:sum-of-copyrighted-hours-in-period (int (Math/floor (* (in-config :number-of-days-in-work) (in-config :working-hours-in-a-day) (in-config :copyrighted-work-percentage))))})]
     (conj out-config
           {:sum-of-worked-hours-in-period (* (out-config :number-of-days-in-work) (out-config :working-hours-in-a-day))}
           {:avg-copyrighted-hours-in-a-day (int (Math/floor (/ (out-config :sum-of-copyrighted-hours-in-period) (out-config :number-of-days-in-work))))}
           {:sum-of-surplus-copyrighted-hours-in-period (int (mod (out-config :sum-of-copyrighted-hours-in-period) (out-config :number-of-days-in-work)))}
           {:calendar-randomization-lvl (int (Math/ceil (/ (out-config :number-of-days-in-work) 2)))}
           out-config))))