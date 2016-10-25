;;author: bbp
;;date: 2016-10-08
(ns ftpfile.ftp-file-v2
    (:require [tools.commons-tools :as tools]
              [clojure.java.jdbc :as jdbc];; 引入crud所需要的包
              [java-jdbc.sql :as sql]
              [ftpfile.read-config :as readconf]
              ))

;;///////////////读取配置文件////////////////////////////
;将配置文件中的内容读入到config-map
(def base-config (readconf/config-map "resources/base-config.edn"))
(def app-config (readconf/config-map "resources/app-config.edn"))
;;///////////////解析配置文件的内容///////////////////////
(def db-spec (base-config :database))

(def commons (app-config :commons))
(def file (commons :file))


(def exports (app-config :exports))
(def sqls (map :sql (app-config :exports)))
(def file-name (map :fileName (app-config :exports)))

;////////////////查询数据库//////////////////////////////
(defn do-query [sql]
  (jdbc/query db-spec sql))

;////////////////在字段间插入分割符/////////////////
(defn do-item [{:keys [content]} item]
  ( map #(->> % vals (clojure.string/join (content :columnSeparator))) item)
  )

;///////////////创建目录////////////////////////
(defn create-dir [export-dir]
  (def created-file-path (tools/concat-str export-dir  (tools/get-formated-time "yyyyMMhh" (tools/get-syscurrtime))))
  ;(.mkdirs (clojure.java.io/file (tools/concat-str exportDir  (tools/get-formated-time "yyyyMMhh" (tools/get-syscurrtime)))))
  (.mkdirs (clojure.java.io/file created-file-path))
  (str created-file-path);使函数求值成路径字串
  )

;///////////////写入到文件//////////////////////
(defn write-file[{:keys [exportDir suffix content]} file-name item]
  ;(create-dir exportDir);;先创建目录
  (with-open [w (clojure.java.io/writer (tools/concat-str (create-dir exportDir) "\\" file-name "." suffix))]
    (doseq [line item]
      (.write w line)
      (.newLine w)
      ))
  )

;////////////////////顺序执行函数/////////////////////////
(defn do-all [sqls file-name]
  (map (fn [x,y] (->> x do-query (do-item file) (write-file file y))) sqls file-name)
  )
