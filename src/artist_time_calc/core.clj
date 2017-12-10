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
  (println ":day      - Day of work")
  (println ":not-cr-h - Not-copyrighted hours")
  (println ":cr-h     - Copyrighted hours")
  (pp/print-table (build-calendar)))