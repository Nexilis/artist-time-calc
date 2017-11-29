(ns artist-time-calc.core
  (:require [artist-time-calc.conf :refer :all]
            [artist-time-calc.init :refer :all]
            [artist-time-calc.rand :refer :all]
            [clojure.pprint :as pp])
  (:gen-class))

;; TODO: add possibility to set config values using terminal
(defn -main
  [& args]
  (let [calendar (randomize-calendar (initialize-calendar))]
    (pp/print-table calendar)
    (println cr-base-h-per-day "avg. copyrighted hours per day +" cr-surplus-h "surplus hours (over avg.)")))