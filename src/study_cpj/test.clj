(ns study_cpj.test)

(def mySeq '({:id "id1" :name "name1" :age "age1"} {:id "id2" :name "name2" :age "age2"} {:id "id3" :name "name3" :age "age3"}))



(defn do-item [item]
   (->> item  vals (clojure.string/join "@!@")))


;(map do-item mySeq)

(def mtdeftest (str (java.util.UUID/randomUUID)))

(println mtdeftest)
(println mtdeftest)
(println mtdeftest)
(println mtdeftest)

(def mps '({:a 1 :b 2} {:a 3 :b 4}))



