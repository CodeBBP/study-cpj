(ns ftpfile.data-con
  (:require
    [clojure.java.jdbc :as jdbc]
    [java-jdbc.sql :as sql])
)

(def db-spec {:classname "oracle.jdbc.driver.OracleDriver"
              :subprotocol "oracle"
              :subname "thin:@127.0.0.1:1521:ORCL"
              :user "scott"
              :password "scott"
              })

(def db-remote {:classname "oracle.jdbc.driver.OracleDriver"
              :subprotocol "oracle"
              :subname "thin:@127.0.0.1:1521:ORCL"
              :user "scott"
              :password "scott"
              })

(jdbc/with-connection db-spec ; 连接数据库
  (jdbc/with-query-results rows ; 查询结果绑定
    ["select * from emp"] ; 查询用户数据
    (do (println rows)); 打印
    ))








;
