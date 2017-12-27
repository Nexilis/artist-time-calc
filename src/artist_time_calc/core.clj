(ns artist-time-calc.core
  (:require [artist-time-calc.conf :refer :all]
            [artist-time-calc.init :refer :all]
            [artist-time-calc.rand :refer :all]
            [artist-time-calc.cli  :refer :all]
            [clojure.pprint        :as pp])
  (:gen-class))

(defn build-calendar [config]
  (-> (initialize-calendar config) (randomize-calendar config)))

(defn -main
  [& args]
  (let [{:keys [options exit-message]} (validate-args args)]
    (if exit-message
      (println exit-message)
      (pp/print-table (build-calendar (calculate-config options))))))