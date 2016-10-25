;;author: bbp
;;date: 2016-10
(ns ftpfile.ftp-file-v1
    (:require [tools.commons-tools :as tools]
              [clojure.java.jdbc :as jdbc];; 引入crud所需要的包
              [java-jdbc.sql :as sql]
              [ftpfile.read-config :as readconf]
              ))

;;读取配置文件
#_(def config-map (readconf/config-map "D:\\programofactory\\clojureproj\\study-cpj\\resources\\dataconf.edn"))

(def config-map (readconf/config-map "resources/dataconf.edn"))

(def mysql-db (config-map :database-mysql))

(defn sqlConPlus []
  (jdbc/query mysql-db ((config-map :query) :query-all)))

(defn do-item [item]
  (->> item  vals (clojure.string/join (config-map :flag)))
  )

(defn write-str[]
  (map do-item (sqlConPlus))
  )

;(spit "C:\\Users\\BBP\\Desktop\\stuff.txt" (clojure.string/join "\n" (write-str)))
;写入到文件中的换行符需要从配置文件中读取，并非是意象放入“\n”

;(with-open [w (clojure.java.io/writer (config-map :filepath))]
;(doseq [line (write-str)]
;  (.write w line)
;  (.newLine w)
;  ))

(defn do-write [write-str] ;(config-map :filepath)
  (with-open [w (clojure.java.io/writer (config-map :filepath))]
    (doseq [line write-str]
      (.write w line)
      (.newLine w)
      ))
  )

;(print (write-str))
