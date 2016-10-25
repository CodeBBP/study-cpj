(ns study_cpj.database
  (:require [clojure.java.jdbc :as jdbc])
  )

(def db {:classname "oracle.jdbc.driver.OracleDriver"
              :subprotocol "oracle"
              :subname "thin:@127.0.0.1:1521:ORCL"
              :user "scott"
              :password "scott"
              })

(jdbc/with-connection db
  (jdbc/with-query-results rs ["select * from emp"]
    ; rs will be a sequence of maps,
    ; one for each record in the result set.
    (dorun (map #(println (:title %)) rs))))