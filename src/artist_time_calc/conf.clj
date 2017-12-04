(ns artist-time-calc.conf)
; Dictionary:
; wr - worked
; cr - copyrighted
; h  - hours

(def def-config {:wr-days 20
                 :wr-h-per-day 8
                 :cr-percentage 0.7
                 :randomization-lvl 10})

(defn calculate-config
  ([] (calculate-config def-config))
  ([in-config] (let [out-config (conj in-config
                                      {:cr-total-h (int (Math/floor (* (in-config :wr-days)
                                                                       (in-config :wr-h-per-day)
                                                                       (in-config :cr-percentage))))})]
                 (conj out-config {:cr-base-h-per-day (int (Math/floor (/ (out-config :cr-total-h)
                                                                          (out-config :wr-days))))}
                                  {:cr-surplus-h (int (mod (out-config :cr-total-h)
                                                           (out-config :wr-days)))}
                                  {:wr-total-h (* (out-config :wr-days)
                                                  (out-config :wr-h-per-day))}
                   out-config))))
