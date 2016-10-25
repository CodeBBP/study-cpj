(ns ftpfile.read-config
  (:require [clojure.edn :as edn]))

(defn load-config [filename]
  (edn/read-string (slurp filename)))

(defn config-map [filename]
  (load-config filename)
)
