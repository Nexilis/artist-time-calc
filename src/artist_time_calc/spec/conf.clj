(ns artist-time-calc.spec.conf
  (:require [clojure.spec.alpha :as s]))

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