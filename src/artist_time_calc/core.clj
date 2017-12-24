(ns artist-time-calc.core
  (:require [artist-time-calc.conf :refer :all]
            [artist-time-calc.init :refer :all]
            [artist-time-calc.rand :refer :all]
            [clojure.pprint :as pp]
            [clojure.tools.cli :as cli])
  (:gen-class))

(def cli-options
  [["-d" "--days DAYS" "Number of days in work"
    :default 20
    :parse-fn #(Integer/parseInt %)]
   ["-h" "--hours HOURS" "Hours in work during a day"
    :default 8
    :parse-fn #(Integer/parseInt %)]
   ["-p" "--percentage PERCENTAGE" "Percentage of creative work"
    :default 0.7
    :parse-fn #(Float/parseFloat %)]
   [nil "--help"]])

(defn build-calendar [config]
  (-> (initialize-calendar config) (randomize-calendar config)))

(defn -main
  [& args]
  (let [in-config (cli/parse-opts args cli-options)
        out-config (calculate-config (-> :options in-config))]
    (pp/print-table (build-calendar out-config))))