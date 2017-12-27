(ns artist-time-calc.cli
  (:require [clojure.tools.cli  :as cli]
            [clojure.string     :as string]
            [clojure.spec.alpha :as s]))

(def cli-options
  [["-d" "--days DAYS" "Number of work days"
    :default 20
    :parse-fn #(Integer/parseInt %)
    :validate [#(s/valid? :artist-time-calc.spec.conf/days %)]]
   ["-h" "--hours HOURS" "Hours in a work day"
    :default 8
    :parse-fn #(Integer/parseInt %)
    :validate [#(s/valid? :artist-time-calc.spec.conf/hours %)]]
   ["-p" "--percentage PERCENTAGE" "Percentage of creative work"
    :default 0.7
    :parse-fn #(Float/parseFloat %)
    :validate [#(s/valid? :artist-time-calc.spec.conf/percentage %)]]
   [nil "--help"]])

(defn- prepare-help [options-summary]
  (->> ["Console app for calculating time spent on creative work to comply with Polish Labor Law."
        ""
        "Usage: artist-time-calc.exe [options]"
        ""
        "Options:"
        options-summary]
       (string/join \newline)))

(defn- prepare-error-msg [errors]
  (str "The following errors occurred while parsing your command:\n * "
       (string/join "\n * " errors)))

(defn validate-args
  "Validate command line arguments. Either return a map indicating the program
  should exit (with a error message, and optional ok status), or a map
  indicating the action the program should take and the options provided."
  [args]
  (let [{:keys [options arguments summary errors]} (cli/parse-opts args cli-options)]
    (cond
      (:help options) {:exit-message (prepare-help summary)}
      errors {:exit-message (prepare-error-msg errors)}
      options {:options options})))