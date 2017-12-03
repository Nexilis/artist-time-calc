(ns artist-time-calc.conf)

; Dictionary:
; wr - worked
; cr - copyrighted
; h  - hours

(def config {:wr-days 20
             :wr-h-per-day 8
             :cr-percentage 0.7
             :randomization-lvl 10})

(defn calculate-config [conf]
  (let [conf (conj conf {:cr-total-h (int (Math/floor (* (conf :wr-days) (conf :wr-h-per-day) (conf :cr-percentage))))})]
    (conj conf {:cr-base-h-per-day (int (Math/floor (/ (conf :cr-total-h) (conf :wr-days))))}
               {:cr-surplus-h (int (mod (conf :cr-total-h) (config :wr-days)))}
               {:wr-total-h (* (config :wr-days) (config :wr-h-per-day))}
      conf)))


(def cr-total-h (int (Math/floor (* (config :wr-days)
                                    (config :wr-h-per-day)
                                    (config :cr-percentage)))))
(def cr-base-h-per-day (int (Math/floor (/ cr-total-h (config :wr-days)))))
(def cr-surplus-h (int (mod cr-total-h (config :wr-days))))
(def wr-total-h (* (config :wr-days) (config :wr-h-per-day)))
