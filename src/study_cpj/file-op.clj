(ns study-cpj.file-op
  (:use [clojure.java.io]))

;(with-open [w (clojure.java.io/writer "C:\\Users\\BBP\\Desktop\\stuff.txt")]
;  (doseq [line "some-large-seq-of-strings"]
;    (.write w line)
;    (.newLine w)))
(def myMap {:id "id1" :name "name1" :age "age1"})
(def addFlagV (map #(format "%s@!@" %) (apply vector (vals myMap))))
(with-open [w (clojure.java.io/writer  "C:\\Users\\BBP\\Desktop\\stuff.txt" :append true)]
  (.write w (str addFlagV)))
