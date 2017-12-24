(ns artist-time-calc.conf
  (:require [artist-time-calc.spec.conf :as sc]
            [clojure.spec.alpha :as s]))

(def def-config {:days       20
                 :hours      8
                 :percentage 0.7})

(defn- calculate-sum-of-copyrighted-hours-in-period
  [config]
  (int (Math/floor
         (* (config :days)
            (config :hours)
            (config :percentage)))))

(defn calculate-config
  ([] (calculate-config def-config))
  ([in-config]
   {:pre  [(s/valid? ::sc/in-config in-config)]
    :post [(s/valid? ::sc/out-config %)]}
   (let [out-config (conj in-config {:sum-of-copyrighted-hours-in-period (calculate-sum-of-copyrighted-hours-in-period in-config)})]
     (conj out-config
           {:sum-of-worked-hours-in-period (* (out-config :days) (out-config :hours))}
           {:avg-copyrighted-hours-in-a-day (int (Math/floor (/ (out-config :sum-of-copyrighted-hours-in-period) (out-config :days))))}
           {:sum-of-surplus-copyrighted-hours-in-period (int (mod (out-config :sum-of-copyrighted-hours-in-period) (out-config :days)))}
           {:calendar-randomization-lvl (int (Math/ceil (/ (out-config :days) 2)))}
           out-config))))