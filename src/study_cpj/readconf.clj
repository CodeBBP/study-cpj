(ns study_cpj.readconf
  (:require [clojure.edn :as edn]))

(defn load-config [filename]
  (edn/read-string (slurp filename)))

(def config-map (load-config "D:\\programofactory\\clojureproj\\study-cpj\\resources\\dataconf.edn"))
;(def config-map (load-config "dataconf.edn"))
(print (config-map :myfilepath))


