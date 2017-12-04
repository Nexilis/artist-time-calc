(ns artist-time-calc.core
  (:require [artist-time-calc.conf :refer :all]
            [artist-time-calc.init :refer :all]
            [artist-time-calc.rand :refer :all]
            [clojure.pprint :as pp])
  (:gen-class))

(def config (calculate-config))

(defn build-calendar []
  (-> (initialize-calendar config) (randomize-calendar config)))

;; TODO: add possibility to set config values using terminal
(defn -main
  [& args]
  (pp/print-table (build-calendar))
  (println (-> :cr-base-h-per-day config) "avg. copyrighted hours per day +"
           (-> :cr-surplus-h config) "surplus hours (over avg.)"))