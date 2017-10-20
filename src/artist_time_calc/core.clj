(ns artist-time-calc.core
  (:gen-class))

(def config {:days 20 :factor 0.7})
(def copyrighted-hours (reduce * (vals config)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println copyrighted-hours))
