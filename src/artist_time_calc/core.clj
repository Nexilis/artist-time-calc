(ns artist-time-calc.core
  (:gen-class))
(use 'clojure.pprint)
; Dictionary:
; wr - work
; cr - copyrighted
; h  - hours

(def config {:wr-days 20 :wr-h-per-day 8 :cr-percentage 0.7})
(def cr-total-h (Math/floor (reduce * (vals config))))
(def cr-base-h-per-day (Math/floor (/ cr-total-h (config :wr-days))))
(def cr-surplus-h (mod cr-total-h (config :wr-days)))
(def wr-total-h (* (config :wr-days) (config :wr-h-per-day)))

; [{ :day :wr-h x :cr-h (- (config :wr-h-per-day) x)}]
(defn create-wr-calendar []
  (let [calendar []]
    (conj calendar {:day 1 :cr-h 0 :wr-h 0})))

(defn -main
  [& args]
  (println "Total worked hours:" wr-total-h)
  (println "Total copyrighted hours:" cr-total-h)
  (println "Base copyrighted hours per day:" cr-base-h-per-day)
  (println "Surplus copyrighted hours:" cr-surplus-h)
  (print-table (create-wr-calendar)))
