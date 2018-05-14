(ns thread.thread)

;;/// delay 函数
(def d (delay (println "Running... ") :done!))

;;通过delay来提供可选择的计算
(defn get-document
  [id]
  {
   :url "http://www.mozilla.org/about/mainfesto.en.html"
   :title "The Mozilla Manifesto"
   :mime "text/html"
   :content (delay (slurp "http://www.mozilla.org/about/mainfesto.en.html"))
   })

(def d (get-document "some-id"))

;;/// future 函数
(def long-calculation (future (apply + (range 1e8))))


;;/// promise 函数
(def a (promise))
(def b (promise))
(def c (promise))

(defn opab->c
  []
  (future (deliver c (+ @a @b))
          (print "Deliver Complete!")))


;;/// 简单的并行化
(defn phone-numbers
  [string]
  (re-seq #"(\d{3})[\.-]?(\d{3})[\.-]?(\d{4})" string))
;;创建100个空字符串，每个字符串的大小约为1M，号码在每个字符串的末尾
(def files (repeat 100
                   (apply str
                          (concat (repeat 1000000 \space)
                                  "Mr.BBP : 617.555.2937,Beter : 508:555:2218"))))


(time (dorun (map phone-numbers files)))                    ;;=> "Elapsed time: 2518.820216 msecs"
;;pmap以并行的方式用多线程来把一个函数应用到一个序列的元素上去
(time (dorun (pmap phone-numbers files)))                   ;;=> "Elapsed time: 1723.107501 msecs"

;;用于在不同的表达式里对表达式求值
(defmacro futures
  [n & exprs]
  (vec (for [_ (range n)
             expr exprs]
         '(future ~expr))))

(defmacro wait-futures
  [& agrs]
  '(doseq [f# (futures ~@args)]
     @f#))


(def xs (atom #{1 2 3}))

(wait-futures 1 (swap! xs (fn [v]
                            (Thread/sleep 250)
                            (println "trying 4")
                            (conj v 4)))
              (swap! xs (fn [v]
                          (Thread/sleep 500)
                          (println "trying 5")
                          (conj v 5))))

;;/// 观察器
;定义一个观察器，观察器必须有四个参数：key referce old-state new-state
(def echo-watch
  [key identity old new]
  (println key old "=>" new))

(def sarah (atom {:name "Sarah" :age 25}))

(add-watch sarah :echo echo-watch)

(swap! sarah update-in [:age] inc)

(add-watch sarah :echo2 echo-watch)

(swap! sarah update-in [:age] inc)




































