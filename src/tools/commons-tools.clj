(ns tools.commons-tools)
(require '[clj-time.core :as core])
(require '[clj-time.format :as format])

;partial apply str --> 将传入的字符串参数合并成一串
(defn concat-str[& x]
  ((partial apply str) x)
  )

;;将传入的字符串参数合并并打印
(defn print-plus[& x]
  (println ((partial apply str) x))
  )

;;获取系统当前时间
;(defn get-syscurrtime []
;  (clj-time.local/local-now)
;  )
;
;;;按照传入的时间格式获取时间
;(defn get-formated-time [format-str time-in]
;  (clj-time.format/unparse (clj-time.format/formatter format-str) time-in)
;  )

(defn get-syscurrtime []
  ;;(core/now)
  (java.util.Date.)
  )

;;按照传入的时间格式获取时间
(defn get-formated-time [format-str time-in]
  ;;(format/unparse (format/formatter [format-str DateTimeZone/]) time-in)
  (.format (java.text.SimpleDateFormat. format-str) time-in)
  )
