(ns study-cpj.mysql
  (:require [java-jdbc.sql :as sql]
            [clojure.java.jdbc :as jdbc]))

(def mysql-db {:classname "com.mysql.jdbc.Driver"
               :subprotocol "mysql"
               :subname "//127.0.0.1:3306/test"
               :user "root"
               :password "root"})

;(defn mysqlCon []
;  (jdbc/with-connection mysql-db ; 链接数据库
;    (jdbc/with-query-results rows ; 查询结果绑定
;      ["select * from student"] ; 查询用户数据
;      (do (println rows)); 打印
;      )
;    )
;  )

(defn mysqlConPlus[]
  (jdbc/query mysql-db
    (sql/select * :student))
  )

