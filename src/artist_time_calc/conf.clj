(ns artist-time-calc.conf
  (:require [artist-time-calc.spec.conf :as sc]
            [clojure.spec.alpha :as s]))

(def def-config {:number-of-days-in-work      20
                 :working-hours-in-a-day      8
                 :copyrighted-work-percentage 0.7
                 :calendar-randomization-lvl  10})

(defn calculate-config
  ([] (calculate-config def-config))
  ([in-config]
   {:pre  [(s/valid? ::sc/in-config in-config)]
    :post [(s/valid? ::sc/out-config %)]}
   (let [out-config (conj in-config {:sum-of-copyrighted-hours-in-period (int (Math/floor (* (in-config :number-of-days-in-work) (in-config :working-hours-in-a-day) (in-config :copyrighted-work-percentage))))})]
     (conj out-config
           {:sum-of-worked-hours-in-period (* (out-config :number-of-days-in-work) (out-config :working-hours-in-a-day))}
           {:avg-copyrighted-hours-in-a-day (int (Math/floor (/ (out-config :sum-of-copyrighted-hours-in-period) (out-config :number-of-days-in-work))))}
           {:sum-of-surplus-copyrighted-hours-in-period (int (mod (out-config :sum-of-copyrighted-hours-in-period) (out-config :number-of-days-in-work)))}
           {:calendar-randomization-lvl (int (Math/ceil (/ (out-config :number-of-days-in-work) 2)))}
           out-config))))