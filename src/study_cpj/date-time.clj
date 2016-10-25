(ns study_cpj.date-taime)

(require '[tools.commons-tools :as tools])

;;获取当前系统时间
;java操作
(defn now []
  (java.util.Date.))

(println (tools/concat-str "java-op: " (now )))

(println (tools/concat-str "time-tamp: " (System/currentTimeMillis)))

;;clojure操作
(require '[clj-time.core :as timec])
(tools/print-plus "clojure-nowtime: " (timec/now))
(require '[clj-time.local :as timel])
(tools/print-plus "clojure-local-nowtime: " (timel/local-now))


;;时间格式转换
(require '[clj-time.format :as tf])
(require '[clj-time.core :as t])
(tools/print-plus "--> " (tf/unparse (tf/formatters :date) (t/now)))

(def my-format (tf/formatter "yyyyMMdd"))
(tf/unparse my-format (timel/local-now))

(def test-case (tools/get-formated-time "yyyyMMdd HH:mm:ss" (tools/get-syscurrtime)))

(tools/print-plus "mydef-test: " test-case)
