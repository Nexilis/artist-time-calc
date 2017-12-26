(ns artist-time-calc.core
  (:require [artist-time-calc.conf :refer :all]
            [artist-time-calc.init :refer :all]
            [artist-time-calc.rand :refer :all]
            [clojure.string :as string]
            [clojure.pprint :as pp]
            [clojure.tools.cli :as cli])
  (:gen-class))

(def cli-options
  [["-d" "--days DAYS" "Number of work days"
    :default 20
    :parse-fn #(Integer/parseInt %)]
   ["-h" "--hours HOURS" "Hours in a work day"
    :default 8
    :parse-fn #(Integer/parseInt %)]
   ["-p" "--percentage PERCENTAGE" "Percentage of creative work"
    :default 0.7
    :parse-fn #(Float/parseFloat %)]
   [nil "--help"]])

(defn usage [options-summary]
  (->> ["Console app for calculating time spent on creative work to comply with Polish Labor Law."
        ""
        "Usage: artist-time-calc.exe [options]"
        ""
        "Options:"
        options-summary]
       (string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn validate-args
  "Validate command line arguments. Either return a map indicating the program
  should exit (with a error message, and optional ok status), or a map
  indicating the action the program should take and the options provided."
  [args]
  (let [{:keys [options arguments summary errors]} (cli/parse-opts args cli-options)]
    (cond
      (:help options) {:exit-message (usage summary)}
      errors {:exit-message (error-msg errors)}
      options {:options options})))

(defn build-calendar [config]
  (-> (initialize-calendar config) (randomize-calendar config)))

(defn -main
  [& args]
  (let [{:keys [options exit-message]} (validate-args args)]
    (if exit-message
      (println exit-message)
      (pp/print-table (build-calendar (calculate-config options))))))