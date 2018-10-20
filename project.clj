(defproject artist-time-calc "0.3"
  :description "A console app for calculating time spent on creative work to comply with Polish Labor Law."
  :url "https://github.com/Nexilis/artist-time-calc"
  :license {:name "GNU GENERAL PUBLIC LICENSE Version 3"
            :url "https://www.gnu.org/licenses/gpl-3.0.en.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.cli "0.3.5"]]
  :main ^:skip-aot artist-time-calc.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
