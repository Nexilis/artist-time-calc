(ns artist-time-calc.conf
  (:require [clojure.spec.alpha :as s]))
; Dictionary:
; wr - worked
; cr - copyrighted
; h  - hours

; specifications for config keys
(s/def ::wr-days number?)
(s/def ::wr-h-per-day number?)
(s/def ::cr-percentage float?)
(s/def ::randomization-lvl number?)
(s/def ::cr-total-h number?)
(s/def ::cr-base-h-per-day number?)
(s/def ::cr-surplus-h number?)
(s/def ::wr-total-h number?)

; :req-un for required unqualified keys
(s/def ::in-config
  (s/keys :req-un [::wr-days
                   ::wr-h-per-day
                   ::cr-percentage
                   ::randomization-lvl]))

(s/def ::out-config
  (s/keys :req-un [::wr-days
                   ::wr-h-per-day
                   ::cr-percentage
                   ::randomization-lvl
                   ::cr-total-h
                   ::cr-base-h-per-day
                   ::cr-surplus-h
                   ::wr-total-h]))

(def def-config {:wr-days 20
                 :wr-h-per-day 8
                 :cr-percentage 0.7
                 :randomization-lvl 10})

(defn calculate-config
  ([] (calculate-config def-config))
  ([in-config]
   {:pre [(s/valid? ::in-config in-config)]
    :post [(s/valid? ::out-config %)]}
   (let [out-config (conj in-config {:cr-total-h (int (Math/floor (* (in-config :wr-days) (in-config :wr-h-per-day) (in-config :cr-percentage))))})]
     (conj out-config {:cr-base-h-per-day (int (Math/floor (/ (out-config :cr-total-h) (out-config :wr-days))))}
                      {:cr-surplus-h (int (mod (out-config :cr-total-h) (out-config :wr-days)))}
                      {:wr-total-h (* (out-config :wr-days) (out-config :wr-h-per-day))}
       out-config))))