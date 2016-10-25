(ns study_cpj.sheet)

;;============Strings=====================

;;///format -> 使用java.lang.String.format 进行格式化
(format "Hello there, %s" "bob")
(format "%5d" 3)
(format "Pad with leading zeros %02d" 5);;用0补全位数
(format "Left justified :%7d||" 4);;默认为右向
(format "Left justified :%-7d||" 4);;加“—”为左向
(format "Locale-specific group separators %,d" 123456789);;数字分割
(format "decimal %d  octal %o  hex %x  upper-case hex %X" 63 63 63 63);;进值转换 十进制 八进制 十六进制
(format "%3$s  %2$d %1$s" "hello" 23  "Positional arguments");;字符到序格式化

;;///count -> 计算字符串长度
(count "string")
(count []);;计算的是coll中的元素个数
(count [1 2 3 "123"])
(count {:one 1 :two 2});;当计算map中元素的个数时，计算的是map中键值对各个数

;;///get
(get [4 5 6] 1) ;;当是vector时，通过位置确定元素值
(get {:key1 "bbp" :key2 32} :key2);;当是map时，通过关键字取值

;;///subs
(subs "Clojure" 1)
(subs "Clojure" 1 4)

;;///join (join coll)(join separator coll)  将coll中的元素用separator分割并转换成字符串
(clojure.string/join ", " [1 2 3])
(clojure.string/join ", " (remove clojure.string/blank? ["spam" nil "eggs" "" "spam"]))

;;///escape
;Return a new string, using cmap to escape each character ch
;; from s as follows:
;; If (cmap ch) is nil, append ch to the new string.
;; If (cmap ch) is non-nil, append (str (cmap ch)) instead.
(clojure.string/escape "I want 1 < 2 as HTML, & other good things."
                       {\< "&lt;", \> "&gt;", \& "&amp;"})
;;///split
(clojure.string/split "Clojure is awesome!" #" ")

;;///split-lines  以\n 或者\r\n分割字符串
(clojure.string/split-lines "test \n string")

(peek [1 2 3 4]) ;; ->  (last [1 2 3 4])
(peek '(1 2 3 4));; -> (firet '(1 2 3 4))

;;==========================Queues=======================
;;/// join -> (join xrel yrel)(join xrel yrel km)
(def man #{{:id "1001" :name "bbp"} {:id "1002" :name "bbc"}})
(def sex #{{:id "1001" :sex "man"} {:id "1002" :gender "felman"}})
(clojure.set/join man sex);;连接多个有相同key值的map时，会将有相同key值的map元素进行连接

;;/// clojure.set/select -> (select pred xset) 返回含有符合谓词条件的set中的元素set
(clojure.set/select odd? #{1 2 3})

;;///clojure.set/project -> 按照key返回set中map元素值
(clojure.set/project #{{:name "bbp" :age "23"} {:name "bbc" :age "25" :gender "man"}} [:name :gender])

;;======================== Sequences ===============================
(seq [1 2])
(seq {:key1 "value1" :key2 "value2"}) ;;=> ([:key2 "value 2"] [:key1 "value 1"])

(rseq [1 2 4 3]);;=> [3 4 1 2] 只是元素的位置发生反转，并非是按照值的大小进行排序
;;(resq ["b" "c" "d" "a"]) 字符串元素的vector不可以作为参数
(rseq (vec (range 1 10)))
(rseq (into (sorted-map){:a 1 :b 2}))

;;/// subseq -> (subseq sc test key)(subseq sc start-test start-key end-test end-key)
;;//  sc must be a sorted collection, test(s) one of <, <=, > or >=.
;;(subseq [1 2 3 4] > 2) --> vec 不是一个 sorted coll
(subseq (sorted-set 1 2 3 4) > 2)
;;当> < test同事使用时，必须要将 > 放在前面，< 放在后面
(subseq (sorted-set 1 2 3 4 5 6 7 8 9 0) < 9 > 2);;=> ()
(subseq (sorted-set 1 2 3 4 5 6 7 8 9 0) > 2 < 9);;=> (3 4 5 6 7 8)

(rsubseq (sorted-set 1 2 3 4 5) < 3);;功效与使用subseq后再反序结果一样

;;/// iterate -> (iterate f x) f => 以什么样的方式进行迭代  x => 迭代的起始数字
(take 5 (iterate inc 7))
(take 5 (iterate (partial + 3) 7))
(take 10 (iterate (partial * 2) 1))
(take 200 (map first (iterate (fn [[a b]] [b (+' a b)]) [0 1])));;斐波拉契数列

;;/// repeat ->(repeat x)(repeat n x) -> Returns a lazy (infinite!, or length n if supplied) sequence of xs.
(repeat 10 "bbp")

;;/// range -> (range)(range end)(range start end)(range start end step)
(range 1 100)
(range 1 100 3)
(range 0 0.8 1/10)
(range 0 0.8 0.1)

;;///file-seq
(def f (clojure.java.io/file "E:\\mavenbase\\mavenRepository"))
(def fs (file-seq f))
(clojure.pprint/pprint (take 10 fs))

;;/// line-seq -> (line-seq rdr)
;;Returns the lines of text from rdr as a lazy sequence of strings.
;;rdr must implement java.io.BufferedReader.
(with-open [rdr (clojure.java.io/reader "E:\\dir\\dir.txt")] (count (line-seq rdr)))

(tree-seq seq? identity '((1 2 (3)) (4)))

(keep even? (range 1 10))

(keep #(if (odd? %) %) (range 10));;=> (1 3 5 7 9)

(map #(if (odd? %) %) (range 10));;=> (nil 1 nil 3 nil 5 nil 7 nil 9)

(for [ x (range 10) :when (odd? x)] x);;=> (1 3 5 7 9)

(filter odd? (range 10));;=> (1 3 5 7 9)

;;//// distinct 将coll中的元素去重
(distinct '(1 2 3 4 4 4 4 5 6 6 1 1 8 8 7))

;;/// filter (filter pred)(filter pred coll)
(filter even? (range 10))
(defn size=1?[x] (= (count x) 1))
(filter size=1?  ["a" "aa" "b" "n" "f" "lisp" "clojure" "q" ""]);;filter 是返回符合条件的元素组成的seq，对每一个元素操作
(map size=1?  ["a" "aa" "b" "n" "f" "lisp" "clojure" "q" ""]);;map 是返回函数的结果，对每一个元素操作

(def xf (filter odd?));;如果没有传入coll，那么将会产生一个transducer
(transduce xf conj '(1 2 3 4))
(transduce xf + '(1 2 3 4))

(filter (comp #{2 3} last) {:x 1 :y 2 :z 3});;filter有和map对每一个元素都进行操作的一样的效果
(map first (filter (comp #{2 3} last) {:x 1 :y 2 :z 3}))

;;/// remove -> (remove pred)(remove pred coll) => 没有提供coll时，返回一个转换器
;;==》包含了filter的功能，并在此基础上执行移除操作，并返回移除符合pred元素后的seq
(remove pos? [1 -2 2 -1 3 7 0])

;;///take-nth (take-nth n)(take-nth n coll) -> n为获取元素的 位置 间隔
;; Returns a statefultransducer when no collection is provided.
(take-nth 10 (range 100))
(take-nth 5 "abcdefghijklmnopqrstuvwxyz")

;;/// for
;;  Supported modifiers are: :let [binding-form expr ...],:while test, :when test.
;;  (take 100 (for [x (range 100000000) y (range 1000000) :while (< y x)] [x y]))
;; 【whiel和when的区别】when -> :when 不会阻止循环提前结束。但是:while就不同了，如果:while(...) 计算出结果为false，则当前循环立刻退出
(for [x [0 1 2 3 4 5]
      :let [y (* x 3)]
      :let [z (/ y 3)]
      :when (even? y)]
  z)

(def digits (seq [1 2 3]))
(for [x1 digits x2 digits] (* x1 x2))
(for [x ['a 'b 'c] y [1 2 3]] [x y]);;有两个绑定参数x,y时，后面的使用函数对x,y同时操作时会产生一中笛卡尔集的效果

(for [[x y] '([:a 1] [:b 2] [:c 0]) :when (= y 0)] x);;操作map，将一个map的键值对绑定到两个参数 x y 实际是将key和value分别绑定到x和y上

(time (dorun (for [x (range 1000) y (range 10000) :when (> x y)] [x y])))
(time (dorun (for [x (range 1000) y (range 10000) :while (> x y)] [x y])));;这个例子展示了when和while的区别

;;/// cons -> (cons x seq)
;; Returns a new seq where x is the first element and seq is the rest.
;; cons 会返回一个新的seq ns，x是ns的头元素 seq为尾元素
(cons [1 2 3] '(1 2 3))

;;/// conj -> (conj coll x)(conj coll x & xs) 将x放入到coll中，如果coll是seq，则将x放入coll的首位置；如果coll是vector，则将x放coll的尾位置
(conj [1 2 3] '(1 2 3))
(conj [1 2 3] 6 5 4)
(conj '(4 5 6) 1 2 3)
(conj {:key2 "value2" :key1 "value1"} [:key4 "value4"]  [:key3 "value3"]);;->like vector
(conj {:firstname "John" :lastname "Doe"} {:age 25 :nationality "Chinese"});;-> like seq

;;///concat -> (concat)(concat x)(concat x y)(concat x y & zs)
;;Returns a lazy seq representing the concatenation of the elements in the supplied colls.
(concat [1 2] [3 4])

;;///lazy-cat -> (lazy-cat & colls)  Each coll expr is not evaluated until it is needed.
(lazy-cat [1 2 3] [4 5 6])

;;/// mapcat -> (mapcat f)(mapcat f & colls)
;;Returns the result of applying concat to the result of applying map
;;to f and colls.  Thus function f should return a collection.
;;Returns transducer when no collections are provided.
(mapcat reverse [[3 2 1 0] [6 5 4] [9 8 7]])
;;与下面的等价
(apply concat (map reverse [[3 2 1 0] [6 5 4] [9 8 7]]))

;;/// cycle -> (cycle coll)  Returns a lazy (infinite!) sequence of repetitions of the items in coll.
(take 5 (cycle ["a" "b"]))
(mapv #(vector %2 %1) (cycle [1 2 3 4]) [:a :b :c :d :e :f :g :h :i :j :k :l])

;;/// interleave -> (interleave)(interleave c1)(interleave c1 c2)(interleave c1 c2 & colls)
;;Returns a lazy seq of the first item in each coll, then the second etc.
(interleave '(:a :b :c) '(1 2 3))
(apply assoc {} (interleave '(:a :b :c) '(1 2 3)))

;;/// interpose -> (interpose sep)(interpose sep coll)
;;Returns a lazy seq of the elements of coll separated by sep.
;;Returns a stateful transducer when no collection is provided.
(apply str  (interpose "," [1 2 3 4]));->(clojure.string/join "," [1 2 3 4])

;;/// drop -> (drop n)(drop n coll)
;;Returns a lazy sequence of all but the first n items in coll.
;;Returns a stateful transducer when no collection is provided.
(drop 1 [1 2 3 4])

;;///drop-wile -> (drop-while pred)(drop-while pred coll)
;;Returns a lazy sequence of the items in coll starting from the
;;first item for which (pred item) returns logical false.
;;删除coll中的元素直到不符合pred的元素，并返回剩下元素组成的seq
;;Returns a stateful transducer when no collection is provided.
(drop-while neg? [-1 -2 -6 -7 1 2 3 4 -5 -6 0 1])
(drop-while odd? [1 2 3 4 5])

;;/// take-last -> (take-last n coll) 返回coll中最后n个元素
(subseq (sorted-set 1 2 3 4 5) <= 4 >= 2)

;;/// take-while -> (take-while pred)(take-while pred coll)
;;Returns a lazy sequence of successive items from coll while
;;(pred item) returns true. pred must be free of side-effects.
;;Returns a transducer when no collection is provided.
(take-while (partial > 10) (iterate inc 0))

;;///flatten -> (flatten x)
;;Takes any nested combination of sequential things (lists, vectors,etc.)
;;and returns their contents as a single, flat sequence.
;;(flatten nil) returns an empty sequence.
;;对于任意一个嵌套序列,flatten都会将其处理成一个单一的序列
(flatten '(1 2 3 [5 4]))
(flatten [1 [3 2]])
(flatten '(1 2 [3 (4 5)]))
(flatten nil)
(flatten 5);;操作的数据结构不是一个seq，返回空序列()
(flatten {:name "Hubert" :age 23})
(flatten (seq {:name "Hubert" :age 23}))

;;///group-by -> (group-by f coll) 返回一个map
;;Returns a map of the elements of coll keyed by the result of f on each element.
;;The value at each key will be a vector of the corresponding elements,
;;in the order they appeared in coll.
;;返回一个按照键值分组后的map，键值是f作用于组元素中的每个元素的求值结果，值是有一个vector，其元素出现的顺序是按照coll中出现的顺序
(group-by count ["a" "b" "aa" "aaa" "bbb"]);;按照字符串长度对元素进行分组
(group-by odd? (range 10))

(group-by :user-id [{:user-id 1 :uri "/"}
                    {:user-id 2 :uri "/foo"}
                    {:user-id 1 :uri "/account"}])

(def words ["Air" "Bud" "Cup" "Awake" "Break" "Chunk" "Ant" "Big" "Check"])
(group-by (juxt first count) words)
;;///partition -> (partition n coll)(partition n step coll)(partition n step pad coll)
;;Returns a lazy sequence of lists of n items each, at offsets stepapart.
;;If step is not supplied, defaults to n, i.e. the partitions do not overlap.
;;If a pad collection is supplied, use its elements as necessary to complete last partition upto n items.
;;In case there are not enough padding elements, return a partition with less than n items.
;;① 返回一个序列 ② 如果step没有，则默认是n，提供step，则会以step为步长将coll进行划分
;;③ 提供pad，补全剩下元素构成一个完整的partition ④ 没有足够的pad元素，则最后一个parttion的长度会比其他的短
(partition 5 2 (range 20))
(partition 4 6 (range 20))
(partition 4 3 (range 20))
(partition 3 (range 10));;最后两个元素不能组成一个完整的partition，做删除处理
(partition 3 3 '("pad") (range 20));;
(partition 4 3 '("pad" "pad1" "pad2") (range 20));;
;; when a pad is supplied, the last partition may not be of the same size as the rest
(partition 4 6 ["a"] (range 20));;=> ((0 1 2 3) (6 7 8 9) (12 13 14 15) (18 19 "a"))
(partition 4 4 ["pad"] (range 10))

;;partition-all ->  (partition-all n)(partition-all n coll)(partition-all n step coll)
;;Returns a lazy sequence of lists like partition, but may include partitions with fewer than n items at the end.