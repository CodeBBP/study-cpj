(ns study_cpj.sheet)

;;||||||||||||||||||||||||||||||||||||||||||||||   Strings 开始   ||||||||||||||||||||||||||||||||||||||||||||||||||||||

;;///format -> 使用java.lang.String.format 进行格式化
(format "Hello there, %s" "bob")
(format "%5d" 3)
(format "Pad with leading zeros %02d" 5)                    ;;用0补全位数
(format "Left justified :%7d||" 4)                          ;;默认为右向
(format "Left justified :%-7d||" 4)                         ;;加“—”为左向
(format "Locale-specific group separators %,d" 123456789)   ;;数字分割
(format "decimal %d  octal %o  hex %x  upper-case hex %X" 63 63 63 63) ;;进值转换 十进制 八进制 十六进制
(format "%3$s  %2$d %1$s" "hello" 23 "Positional arguments") ;;字符到序格式化

;;///count -> 计算字符串长度
(count "string")
(count [])                                                  ;;计算的是coll中的元素个数
(count [1 2 3 "123"])
(count {:one 1 :two 2})                                     ;;当计算map中元素的个数时，计算的是map中键值对各个数

;;///get
(get [4 5 6] 1)                                             ;;当是vector时，通过位置确定元素值
(get {:key1 "bbp" :key2 32} :key2)                          ;;当是map时，通过关键字取值

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

(peek [1 2 3 4])                                            ;; ->  (last [1 2 3 4])
(peek '(1 2 3 4))                                           ;; -> (firet '(1 2 3 4))
;;||||||||||||||||||||||||||||||||||||||||||||||   Strings 结束   ||||||||||||||||||||||||||||||||||||||||||||||||||||||



;;||||||||||||||||||||||||||||||||||||||||||||||   Queues 开始   |||||||||||||||||||||||||||||||||||||||||||||||||||||||

;;/// join -> (join xrel yrel)(join xrel yrel km)
(def man #{{:id "1001" :name "bbp"} {:id "1002" :name "bbc"}})
(def sex #{{:id "1001" :sex "man"} {:id "1002" :gender "felman"}})
(clojure.set/join man sex)                                  ;;连接多个有相同key值的map时，会将有相同key值的map元素进行连接

;;/// clojure.set/select -> (select pred xset) 返回含有符合谓词条件的set中的元素set
(clojure.set/select odd? #{1 2 3})

;;///clojure.set/project -> 按照key返回set中map元素值
(clojure.set/project #{{:name "bbp" :age "23"} {:name "bbc" :age "25" :gender "man"}} [:name :gender])
;;||||||||||||||||||||||||||||||||||||||||||||||   Queues 结束   |||||||||||||||||||||||||||||||||||||||||||||||||||||||



;;||||||||||||||||||||||||||||||||||||||||||||||   Sequences 开始   ||||||||||||||||||||||||||||||||||||||||||||||||||||
(seq [1 2])
(seq {:key1 "value1" :key2 "value2"})                       ;;=> ([:key2 "value 2"] [:key1 "value 1"])

(rseq [1 2 4 3])                                            ;;=> [3 4 1 2] 只是元素的位置发生反转，并非是按照值的大小进行排序
;;(resq ["b" "c" "d" "a"]) 字符串元素的vector不可以作为参数
(rseq (vec (range 1 10)))
(rseq (into (sorted-map) {:a 1 :b 2}))

;;/// subseq -> (subseq sc test key)(subseq sc start-test start-key end-test end-key)
;;//  sc must be a sorted collection, test(s) one of <, <=, > or >=.
;;(subseq [1 2 3 4] > 2) --> vec 不是一个 sorted coll
(subseq (sorted-set 1 2 3 4) > 2)
;;当> < test同事使用时，必须要将 > 放在前面，< 放在后面
(subseq (sorted-set 1 2 3 4 5 6 7 8 9 0) < 9 > 2)           ;;=> ()
(subseq (sorted-set 1 2 3 4 5 6 7 8 9 0) > 2 < 9)           ;;=> (3 4 5 6 7 8)

(rsubseq (sorted-set 1 2 3 4 5) < 3)                        ;;功效与使用subseq后再反序结果一样

;;/// iterate -> (iterate f x) f => 以什么样的方式进行迭代  x => 迭代的起始数字
(take 5 (iterate inc 7))
(take 5 (iterate (partial + 3) 7))
(take 10 (iterate (partial * 2) 1))
(take 200 (map first (iterate (fn [[a b]] [b (+' a b)]) [0 1]))) ;;斐波拉契数列

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

;;/// tree-seq -> (tree-seq branch? children root)
;;Returns a lazy sequence of the nodes in a tree, via a depth-first walk.
;;branch? must be a fn of one arg that returns true if passed a node that can have children (but may not).
;;children must be a fn of one arg that returns a sequence of the children.
;;Will only be called on nodes for which branch? returns true.
;;Root is the root node of thetree.
;;tree-seq =》通过深度优先算法遍历树，返回这个树的一个惰性序列
;;    branch？ => 必须是一个只能接受一个参数的函数，当遍历到的节点有子节点是返回true，否则返回false
;;    children => 必须是一个只能接受一个参数的函数，返回值是子节点的一个序列，
;;    root => 树根
;;当遍历到某个节点，此时的branch？返回值如果为true，则tree-seq被再次调用，并将该节点作为参数
;;并将children的结果cons 返回
(tree-seq seq? identity '((1 2 (3)) (4)))

(keep even? (range 1 10))

(keep #(if (odd? %) %) (range 10))                          ;;=> (1 3 5 7 9)

(map #(if (odd? %) %) (range 10))                           ;;=> (nil 1 nil 3 nil 5 nil 7 nil 9)

(for [x (range 10) :when (odd? x)] x)                       ;;=> (1 3 5 7 9)

(filter odd? (range 10))                                    ;;=> (1 3 5 7 9)

;;//// distinct 将coll中的元素去重
(distinct '(1 2 3 4 4 4 4 5 6 6 1 1 8 8 7))

;;/// filter (filter pred) (filter pred coll)
(filter even? (range 10))
(defn size=1? [x] (= (count x) 1))
(filter size=1? ["a" "aa" "b" "n" "f" "lisp" "clojure" "q" ""]) ;;filter 是返回符合条件的元素组成的seq，对每一个元素操作
(map size=1? ["a" "aa" "b" "n" "f" "lisp" "clojure" "q" ""]) ;;map 是返回函数的结果，对每一个元素操作

(def xf (filter odd?))                                      ;;如果没有传入coll，那么将会产生一个transducer
(transduce xf conj '(1 2 3 4))
(transduce xf + '(1 2 3 4))

(filter (comp #{2 3} last) {:x 1 :y 2 :z 3})                ;;filter有和map对每一个元素都进行操作的一样的效果
(map first (filter (comp #{2 3} last) {:x 1 :y 2 :z 3}))

;;///filterv -> (filterv pred coll)
;;Returns a vector of the items in coll for which (pred item) returns true. pred must be free of side-effects.
;;参见filter，不同是返回的结果为一个vector
(filterv even? (range 10))                                  ;;=> [0 2 4 6 8]

;;/// remove -> (remove pred)(remove pred coll) => 没有提供coll时，返回一个转换器
;;==》包含了filter的功能，并在此基础上执行移除操作，并返回移除符合pred元素后的seq
(remove pos? [1 -2 2 -1 3 7 0])

;;///take-nth (take-nth n)(take-nth n coll) -> n为获取元素的 位置 间隔
;; Returns a statefultransducer when no collection is provided.
(take-nth 10 (range 100))
(take-nth 5 "abcdefghijklmnopqrstuvwxyz")

;;/// for -> (for seq-exprs body-expr)
;;List comprehension. Takes a vector of one or more binding-form/collection-expr pairs, each followed by zero or more
;;modifiers, and yields a lazy sequence of evaluations of expr.
;;Collections are iterated in a nested fashion, rightmost fastest,and nested coll-exprs can refer to bindings created
;;in prior binding-forms.
;;Supported modifiers are: :let [binding-form expr ...],:while test, :when test.
;;(take 100 (for [x (range 100000000) y (range 1000000) :while (< y x)] [x y]))
;;【whiel和when的区别】when -> :when 不会阻止循环提前结束。但是:while就不同了，如果:while(...) 计算出结果为false，则当前循环立刻退出
(for [x [0 1 2 3 4 5]
      :let [y (* x 3)]
      :let [z (/ y 3)]
      :when (even? y)]
  z)

(def digits (seq [1 2 3]))
(for [x1 digits x2 digits] (* x1 x2))
(for [x ['a 'b 'c] y [1 2 3]] [x y])                        ;;有两个绑定参数x,y时，后面的使用函数对x,y同时操作时会产生一中笛卡尔集的效果

(for [[x y] '([:a 1] [:b 2] [:c 0]) :when (= y 0)] x)       ;;操作map，将一个map的键值对绑定到两个参数 x y 实际是将key和value分别绑定到x和y上

(time (dorun (for [x (range 1000) y (range 10000) :when (> x y)] [x y])))
(time (dorun (for [x (range 1000) y (range 10000) :while (> x y)] [x y]))) ;;这个例子展示了when和while的区别

;;///doseq -> (doseq seq-exprs & body)
;;Repeatedly executes body (presumably for side-effects) with bindings and filtering as provided by "for".
;;Does not retain the head of the sequence. Returns nil.
;;
(doseq [x [-1 0 1]
        y [1  2 3]]
  (prn (* x y)))                                            ;;-1  -2  -3  0  0  0  1  2  3

(doseq [[x y] (map list [1 2 3] [1 2 3])]
  (prn (* x y)))                                            ;;1 4 9

;;/// cons -> (cons x seq)
;; Returns a new seq where x is the first element and seq is the rest.
;; cons 会返回一个新的seq ns，x是ns的头元素 seq为尾元素
(cons [1 2 3] '(1 2 3))

;;/// conj -> (conj coll x)(conj coll x & xs) 将x放入到coll中，如果coll是seq，则将x放入coll的首位置；如果coll是vector，则将x放coll的尾位置
(conj [1 2 3] '(1 2 3))
(conj [1 2 3] 6 5 4)
(conj '(4 5 6) 1 2 3)
(conj {:key2 "value2" :key1 "value1"} [:key4 "value4"] [:key3 "value3"]) ;;->like vector
(conj {:firstname "John" :lastname "Doe"} {:age 25 :nationality "Chinese"}) ;;-> like seq

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
(apply str (interpose "," [1 2 3 4]))                       ;->(clojure.string/join "," [1 2 3 4])

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
(flatten 5)                                                 ;;操作的数据结构不是一个seq，返回空序列()
(flatten {:name "Hubert" :age 23})
(flatten (seq {:name "Hubert" :age 23}))

;;///group-by -> (group-by f coll) 返回一个map
;;Returns a map of the elements of coll keyed by the result of f on each element.
;;The value at each key will be a vector of the corresponding elements,
;;in the order they appeared in coll.
;;返回一个按照键值分组后的map，键值是f作用于组元素中的每个元素的求值结果，值是有一个vector，其元素出现的顺序是按照coll中出现的顺序
(group-by count ["a" "b" "aa" "aaa" "bbb"])                 ;;按照字符串长度对元素进行分组
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
(partition 3 (range 10))                                    ;;最后两个元素不能组成一个完整的partition，做删除处理
(partition 3 3 '("pad") (range 20))                         ;;
(partition 4 3 '("pad" "pad1" "pad2") (range 20))           ;;
;; when a pad is supplied, the last partition may not be of the same size as the rest
(partition 4 6 ["a"] (range 20))                            ;;=> ((0 1 2 3) (6 7 8 9) (12 13 14 15) (18 19 "a"))
(partition 4 4 ["pad"] (range 10))

;;/// partition-all ->  (partition-all n)(partition-all n coll)(partition-all n step coll)
;;Returns a lazy sequence of lists like partition, but may include partitions with fewer than n items at the end.
;;Returns a stateful transducer when no collection is provided.
;;① 返回一个以n分割partition序列，并且包含不能构造完整partition的最后几个元素
;;② 如果没有提供coll，则返回一个transducer
(partition 4 [0 1 2 3 4 5 6 7 8 9])
(partition-all 4 [0 1 2 3 4 5 6 7 8 9])
(partition-all 2 4 [0 1 2 3 4 5 6 7 8 9])

;;/// partition-by -> (partition-by f)(partition-by f coll)
;;Applies f to each value in coll, splitting it each time f returns a new value.  Returns a lazy seq of partitions.
;;Returns a stateful transducer when no collection is provided.
;;① 对coll中的每一个元素都使用f进行求值，并将求值结果相同的相邻元素视为一个partition
;;② 如果没有提供coll，将返回一个transducer
(partition-by #(= 3 %) [1 2 3 4 5])
(partition-by #(= 3 %) [1 2 3 3 4 5 3])
(partition-by odd? [1 1 1 2 2 3 3])

(partition-by identity "ABBA")
(partition-by count ["a" "b" "ab" "ac" "c"])

;;/// split-at -> (split-at n coll)
;;Returns a vector of [(take n coll) (drop n coll)]
(split-at 2 [1 2 3 4 5])

;;/// split-with -> (split-with pred coll)
;;Returns a vector of [(take-while pred coll) (drop-while pred coll)]
(split-with (partial >= 3) [1 2 3 4 5])

;;/// shuffle -> (shuffle coll)
;;Return a random permutation of coll (返回一个随机排序的coll)
(repeatedly 5 (partial shuffle [1 2 3]))

;;///reverse -> (reverse coll)
;;Returns a seq of the items in coll in reverse order. Not lazy.
(reverse '(1 2 3))

;;/// sort -> (sort coll)(sort comp coll)
;;Returns a sorted sequence of the items in coll. If no comparator is supplied, uses compare.
;;comparator must implement java.util.Comparator.
;;Guaranteed to be stable: equal elements will not be reordered.
;;If coll is a Java array, it will be modified.To avoid this, sort a copy of the array.
;;按照一定的比较规则comp对coll进行排序
;;① 返回一个 sorted seq，如果没有比较器comp，则使用compare
;;② comp必须实现java.util.Comparator接口
;;③ 如果coll是一个java array，使用seq排序后会改变原有的数据顺序
(sort > (vals {:foo 5, :bar 2, :baz 10}))
(sort #(compare (last %1) (last %2)) {:b 1 :c 3 :a 2})
;;use sort-by instead
(sort-by last {:b 1 :c 3 :a 2})

;;解决方案
(def x (to-array [32 -5 4 11]))
(def y (sort (aclone x)))


;;///sort-by -> (sort-by keyfn coll)(sort-by keyfn comp coll)
;;Returns a sorted sequence of the items in coll, where the sort order is determined by comparing (keyfn item).
;;If no comparator is supplied, uses compare.  comparator must implement java.util.Comparator.
;;Guaranteed to be stable: equal elements will not be reordered.
;;If coll is a Java array, it will be modified. To avoid this, sort a copy of the array.
;keyfun -> 取键值函数   comp -> 比较函数   先去键值 再按照键值进行比较
(sort-by count ["aaa" "bb" "c"])

(sort-by first [[1 2] [2 2] [2 3]])
(sort-by first > [[1 2] [2 2] [2 3]])

;;///compare -> (compare x y)
;;Comparator. Returns a negative number, zero, or a positive number when x is logically 'less than', 'equal to' or
;;'greater than' y.

;;Same as Java x.compareTo(y) except it also works for nil, and compares numbers and collections in a type-independent
;;manner.x must implement Comparable

;;x 是否 大于 y ：① 是 -> 1 ② 小于 -> -1 ③ 等于 -> 0
;;Coll1 [ce11 ce12 ce13 ... ce1n]   Coll2 [ce21 ce22 ce23 ... ce2n]
;;【当元素都是数字时】
;;Coll1中的每个元素是否大于Coll2中的每个元素：
;;ce1n > ce2n  结果是 1    ce1n < ce2n  结果是 -1   ce1n = ce2n 结果是 0
;;将每个元素的比较结果累加就是最终的结果
;;【当元素都是符串时】
;;string comparisons give results of the distance between the first drifferent characters
;;返回的结果是两个比较字符串第一个不同字符间的距离
(compare 1 0)                                               ;; => 1
(compare 1 1)                                               ;; => 0
(compare 1 2)                                               ;; => -1
(compare 1 3)                                               ;; => -1

(compare "B" "A")                                           ;; => 1
(compare "B" "B")                                           ;; => 0
(compare "B" "C")                                           ;; => -1
(compare "AA" "ZZ")                                         ;; => -25

(compare [0 1 2] [0 1 2])                                   ;;=> 0
(compare [1 2 3] [0 1 2 3])                                 ;;=> -1
(compare [0 1 2] [3 4])                                     ;; => 1
(compare nil [1 2 3])                                       ;;=> -1
(compare [1 2 3] nil)                                       ;;=> 1
(compare [2 11] [99 1])                                     ;;=> -1
(compare "abc" "def")                                       ;;=> -3
(compare "abc" "abd")                                       ;;=> -1

;;/// map -> (map f) (map f coll) (map f c1 c2) (map f c1 c2 c3) (map f c1 c2 c3 & colls)
;;Returns a lazy sequence consisting of the result of applying f to the set of first items of each coll,
;;followed by applying f to the set of second items in each coll, until any one of the colls is exhausted.
;;Any remaining items in other colls are ignored.
;;Function f should accept number-of-colls arguments.
;;Returns a transducer when no collection is provided.
;;① 接受一个函数、一个或者多个集合作为参数，返回一个惰性序列。返回的序列是把这个函数应用到所有集合中对应元素所得的一个序列。
;;② 当coll的长度不一致时，以最短的为准
;;③ f 必须要能够接受 coll个数 个参数
;;④ 如果没有coll，则返回一个transducer
(map inc [1 2 3 4 5])
(map + [1 2 3] [4 5 6])                                     ;;=> (5 7 9)
;;如果多个coll长度不一，以第一个coll的长度为准
(map + [1 2 3] [1 2 3 4 5])                                 ;;=> (2 4 6)
(map + [1 2 3] (iterate inc 1))                             ;;=> (2 4 6)

;;/// pmap -> (pmap f coll) (pmap f coll & colls)
;;Like map, except f is applied in parallel. Semi-lazy in that the parallel computation stays ahead of the consumption,
;;but doesn't realize the entire result unless required.
;;Only useful for computationally intensive functions where the time of f dominates the coordination overhead.
;;参见map，但是效率要高于map
(pmap inc [1 2 3 4 5])                                      ;;=> (2 3 4 5 6)

;;/// map-indexed -> (map-indexed f) (map-indexed f coll)
;;Returns a lazy sequence consisting of the result of applying f to 0 and the first item of coll,
;;followed by applying f to 1 and the second item in coll, etc, until coll is exhausted.
;;Thus function f should accept 2 arguments, index and item.
;;Returns a stateful transducer when no collection is provided.
;;返回值是将f应用到coll中的第一个参数和0，第二个参数和1 ...... 第n个参数和n形成的惰性序列
;;map-indexed 的参数 f 应该包含两个参数=> index 和 item.
(map-indexed (fn [idx itm] [idx itm]) "foobar")             ;;=> ([0 \f] [1 \o] [2 \o] [3 \b] [4 \a] [5 \r])
(map-indexed vector "foobar")                               ;;=> ([0 \f] [1 \o] [2 \o] [3 \b] [4 \a] [5 \r])

;;/// mapcat -> (mapcat f) (mapcat f & colls)
;;Returns the result of applying concat to the result of applying map to f and colls.
;;Thus function f should return a collection.
;;Returns a transducer when no collections are provided
;;mapcat的返回值是 先对coll继续 map f ，得出结果 x，然后将concat应用到 x
(mapcat reverse [[3 2 1 0] [6 5 4] [9 8 7]])
;;与下面的等价
(apply concat (map reverse [[3 2 1 0] [6 5 4] [9 8 7]]))

;;///mapv -> (mapv f coll)  (mapv f c1 c2)  (mapv f c1 c2 c3)  (mapv f c1 c2 c3 & colls)
;;Returns a vector consisting of the result of applying f to the set of first items of each coll,
;;followed by applying f to the set of second items in each coll, until any one of the colls is exhausted.
;;Any remaining items in other colls are ignored. Function f should accept number-of-colls arguments.
;;参见map，不同点是返回结果是一个vector
(mapv + [1 2 3] [4 5 6])                                    ;;=> [5 7 9]

;;/// seque -> (seque s) (seque n-or-q s)
;;Creates a queued seq on another (presumably lazy) seq s.
;;The queued seq will produce a concrete seq in the background, and can get up to n items ahead of the consumer.
;;n-or-q can be an integer n buffer size, or an instance of java.util.concurrent BlockingQueue.
;;Note that reading from a seque can block if the reader gets ahead of the producer.

;;;/// first -> (first coll)
;;Returns the first item in the collection.
;;Calls seq on its argument. If coll is nil, returns nil.
(first '(:alpha :bravo :charlie))                           ;;=> :alpha
(first nil)                                                 ;;=> nil
(first [])                                                  ;;=> nil

;;/// ffirst -> (ffirst x)
;;Same as (first (first x))
(ffirst ['(a b c) '(b a c)])                                ;;=> a

;;/// nfirst -> (nfirst x)
;;Same as (next (first x))
(nfirst ['(a b c) '(b a c)])                                ;;=> (b c)

;;/// fnext -> (fnext x)
;;Same as (first (next x))
(fnext ['(a b c) '(b a c)])                                 ;;=>((b a c))

;;/// nnext -> (nnext x)
;;Same as (next (next x))
(nnext '(1 2 3))                                            ;;=> (3)

;;/// second -> (second x)
;;Same as (first (next x))
(second '(:alpha :bravo :charlie))                          ;;=> :bravo
(second nil)                                                ;;=> nil
(second [])                                                 ;;=> nil

;;last -> (last coll)
;;Return the last item in coll, in linear time
(last [1 2 3 4 5])                                          ;;=> 5
(last ["a" "b" "c" "d" "e"])                                ;;=> "e"
(last {:one 1 :two 2 :three 3})                             ;;=> [:three 3]
;; but be careful with what you expect from a map (or set)
;;原因是map和set是无序的
(last {:a 1 :b 2 :c 3 :d 4 :e 5 :f 6 :g 7 :h 8 :i 9})       ;;=> [:a 1]
(last (sorted-map :a 1 :b 2 :c 3 :d 4 :e 5 :f 6 :g 7 :h 8 :i 9)) ;;=> [:i 9]

;;;/// nth -> (nth coll index)(nth coll index not-found)
;;Returns the value at the index.
;;get returns nil if index out of bounds,nth throws an exception unless not-found is supplied.
;;nth also works for strings, Java arrays, regex Matchers and Lists, and, in O(n) time, for sequences.
(def my-seq ["a" "b" "c" "d"])
(nth my-seq 0)                                              ;; => "a"
(nth my-seq 1)                                              ;; => "b"

;;(nth [] 0)                                                  ;; => 异常

(nth [] 0 "nothing found")                                  ;; => "nothing found"

;;/// nthnext -> (nthnext coll n)
;;Returns the nth next of coll, (seq coll) when n is 0.
;;从第n位开始获取coll中的元素
(nthnext (range 10) 3)
;;【比较drop】，
;;看似相同，实则不同：对于获取不了的元素，nthnext返回一个nil，drop返回一个（）
(nthnext (range 10) 5)                                      ;;=> (5 6 7 8 9)
(drop 5 (range 10))                                         ;;=> (5 6 7 8 9)

(nthnext [] 3)                                              ;;=> nil
(drop 3 [])                                                 ;;=> ()   ; a lazy sequence

;;/// rand-nth ->  (rand-nth coll)
;;Return a random element of the (sequential) collection. 随机返回coll中的元素
;;Will have the same performance characteristics as nth for the given collection.
(def food [:ice-cream :steak :apple])
(rand-nth [1 2 3 4 5])                                      ;;每次执行都是随机返回vector中的一个元素


;;/// when-first -> (when-first bindings & body)
;;bindings => x xs
;;Roughly the same as (when (seq xs) (let [x (first xs)] body)) but xs is evaluated only once
;;let的一个语法糖，当xs是一个空的coll时，不进行绑定，返回nil；当xs不为空时，取出xs的第一个元素并绑定到x
(when-first [a [1 2 3]] a)                                  ;; => 1
(when-first [a []] a)                                       ;; => nil


;;/// max-key -> (max-key k x) (max-key k x y) (max-key k x y & more)
;;Returns the x for which (k x), a number, is greatest.
;;将 x y z ... 代入到函数k中求值，返回x y z ... 求值结果最大的一个
;;求值的结果必须是数值！！！
(max-key count "asd" "bsd" "dsd" "long word")               ;;"long word"
(max-key #(+ % 1) 1 2 3 8 4 5)
(key (apply max-key val {:a 3 :b 7 :c 9 :d 9}))             ;;当相同，后面的会覆盖前面的

;; min-key -> (min-key k x) (min-key k x y) (min-key k x y & more)
;;Returns the x for which (k x), a number, is least.
;;参见max-key
(min-key count "asd" "bsd" "dsd" "long word")

;;/// zipmap -> (zipmap keys vals)
;;Returns a map with the keys mapped to the corresponding vals.
;;将键值map到相应的值
(zipmap [:a :b :c :d :e] [1 2 3 4 5])
;;当key-vey 长度不一时，以短的为准
(zipmap [:a :b :c] [1 2])                                   ;; =>{:a 1, :b 2}
(zipmap [:a :b :c] (range 10))                              ;; => {:a 0, :b 1, :c 2}

;;/// into -> (into to from) (into to xform from)
;;Returns a new coll consisting of to-coll with all of the items of from-coll conjoined. A transducer may be supplied.
;;返回一个由from-coll中的item（元素）组成一个to-coll的coll，可以使用transducer
(into (sorted-map) [[:a 1] [:c 3] [:b 2]])                  ;; =>{:a 1, :b 2, :c 3}
(into (sorted-map) [{:a 1} {:c 3} {:b 2}])                  ;; =>{:a 1, :b 2, :c 3}
(into [] {1 2, 3 4})                                        ;; =>[[1 2] [3 4]]
(into () '(1 2 3))                                          ;; =>(3 2 1)
(into [1 2 3] '(4 5 6))                                     ;; =>[1 2 3 4 5 6]

(def xform (comp (map #(+ 2 %)) (filter odd?)))
(into [-1 -2] xform (range 10))
;;或者
(transduce xform conj [-1 -2] (range 10))
;;或者
(reduce conj [-1 -2] (->> (range 10) (map #(+ 2 %)) (filter odd?)))
;;【说明】into 的效率要高于后两种写法，具体测试方法参见 http://clojuredocs.org/clojure.core/into

;;/// reduce ->　(reduce f coll)　(reduce f val coll)
;;f should be a function of 2 arguments.
;;If val is not supplied,returns the result of applying f to the first 2 items in coll,
;;then applying f to that result and the 3rd item, etc.
;;If coll contains no items, f must accept no arguments as well, and reduce returns the result of calling f with no arguments.
;;If coll has only 1 item, it is returned and f is not called.
;;If val is supplied, returns the result of applying f to val and the first item in coll,
;;then applying f to that result and the 2nd item, etc.
;;If val is supplied and coll contains no items, returns val and f is not called.
;;① f 必须是一个可以接受两个参数的函数
;;② 如果val（初始值）没有被提供，返回将 f 应用于coll中前两个元素，得到值 x，然后再将 f 应用到 x 和coll中的第三个元素 ... 直到coll
;;中的最后一个元素
;;③ 如果coll是一个空集合，要求 f 必须可以接受无参，返回的结果是 f 对无参求值的结果
;;④ 如果coll只有一个元素，那么reduce返回的结果就是coll中的那个元素，此时 f 不会被调用
;;⑤ 如果初始值被提供，返回的结果就是将 f 应用到 val 和coll中第一个元素，...... 直到coll中没有元素
;;④ 如果提供了val，并且coll为空集合，那么会直接返回val，f 不会被调用
(reduce + [1 2 3 4 5])                                      ;;=> 15
(reduce + [])                                               ;;=> 0
(reduce + [1])                                              ;;=> 1
(reduce + [1 2])                                            ;;=> 3
(reduce + 1 [])                                             ;;=> 1
(reduce + 1 [2 3])                                          ;;=> 6

;;///reductions -> (reductions f coll)(reductions f init coll
;;Returns a lazy seq of the intermediate values of the reduction (as per reduce) of coll by f, starting with init.
;;返回一个由ruduce f coll 的中间值组成的惰性序列，如果有初始值，则返回的序列的第一个元素是初始值
(reductions + [1 1 1 1])                                    ;;=> (1 2 3 4)
(reductions + [1 2 3])                                      ;;=> (1 3 6)
(reductions + 3 [1 2 3])                                    ;;=> (3 4 6 9)

;;///apply -> (apply f args) (apply f x args) (apply f x y args) (apply f x y z args) (apply f a b c d & args)
;;Applies fn f to the argument list formed by prepending intervening arguments to args.

;;In this example, 'f' = 'map', 'args' = 'vector', and argseq = '[:a :b] [:c :d]', making the above code equivalent to
;;(map vector [:a :b] [:c :d])
;;在这个例子中，map是f，vector 是f的参数，[:a :b][:c :d] 是参数vector的参数列表，这个例子与(map vector [:a :b] [:c :d])等价
(apply map vector [[:a :b] [:c :d]])                        ;; => ([:a :c] [:b :d])

;; only functions can be used with apply.  'and' is a macro because it needs to evaluate its arguments lazily and so
;; does not work with apply.
;;只有函数才能够被用于apply。‘and’是宏，它会对参数进行懒求值，所以不能被用于apply
;;(apply and (list true true false true)                      ;; RuntimeException : cannot take value of a macro

;; 'apply' is used to apply an operator to its operands. 'apply' 被用于将操作对象应用到它的操作域中
(apply + '(1 2))  ; equivalent to (+ 1 2)
;; You can also put operands before the list of operands and they'll be consumed in the list of operands
;; 也可以放一些操作数放在操作域列表前，操作数和原有的操作域将共同组成一个操作域
(apply + 1 2 '(3 4))  ; equivalent to (apply + '(1 2 3 4))

;;some -> (some pred coll)
;;Returns the first logical true value of (pred x) for any x in coll,else nil.
;;One common idiom is to use a set as pred, for example this will return :fred if :fred is in the sequence,
;;otherwise nil: (some #{:fred} coll)
;;① 将pred应用到coll中的元素，直到第一个符合pred的元素为止，并返回pred的结果，如果都不符合pred，那么返回一个nil
;;因为2是偶数，所以‘some’执行到这会停止，3和4不会被testd
(some even? '(1 2 3 4))                                     ;;=> ture
;;应为coll中的元素都不符合pred，所以返回nil
(some even? '(1 3 5 7))                                     ;;=> nil
;;直到符合pred的条件，并返回pred的结果
(some #(when (even? %) %) '(1 2 3 4))                       ;;=> 2
(some #(and (even? %) %) '(1 2 3 4))                        ;;=> 2  使用and代替when

;;这里hash充当一个函数的角色，当key存在时，返回value，并停止，否则返回nil
(some {2 "two" 3 "three"} [nil 3 2])                        ;;=> "three"
(some {nil "nothing" 2 "two" 3 "three"} [nil 3 2])          ;;=> "nothing"

;;这些例子说明了“Returns the first logical true value of (pred x) for any x in coll”
(some {2 "two" 3 nil} [nil 3 2])   ;;=> "two"  当为key是3时，返回的结果在逻辑上不是true，所以some没有停止
(some {4 "four" 3 nil} [nil 3 2])  ;;=> nil
(some {4 "four" 3 nil} [nil 3 4])  ;;=> "four"

;;some can be used as a substitute for (first (filter ...  in most cases.
;;在一般情况下，some 都能替代 (first (filter ...
(first (filter even? [1 2 3 4]))                            ;;=> 2
(some #(if (even? %) %) [1 2 3 4])                          ;;=> 2

;; find a whether a word is in a list of words.
;;【一个应用】查找一个单词列表中是否包含某个单词
(def word "foo")
(def words ["bar" "baz" "foo" ""])
(some (partial = word) words)

(defn ne [n1 n2] (not= n1 n2))
(defn sumlt [limit n1 n2] (> limit (+ n1 n2)))
(some #(% 3 3) (list ne #(sumlt 10 %1 %2)))                 ;;这里把函数当成参数数据，即“代码即数据，数据即代码”的写照


;;///set -> (set coll)
;;Returns a set of the distinct elements of coll.
;;返回一个将coll中元素 去重 的set
(set '(1 1 2 3 2 4 5 5))                             ;;=> #{1 2 3 4 5}
(set nil)                                            ;;=> #{}

;;///vec -> (vec coll)
;;Creates a new vector containing the contents of coll. Java arrays will be aliased and should not be modified.
;;使用coll中的元素创建一个新的vector
(vec '(1 2 3))                                       ;;=> [1 2 3]
(vec '())                                            ;;=> []
(vec nil)                                            ;;=> []

;;///into-array -> (into-array aseq) (into-array type aseq)
;;Returns an array with components set to the values in aseq.
;;The array's component type is type if provided, or the type of the first value in aseq if present, or Object.
;;All values in aseq must be compatible with the component type.
;;Class objects for the primitive types can be obtained using, e.g., Integer/TYPE.
;;① 返回一个由 aseq 中元素组成的 array
;;② 数组中元素的类型的定义是这样的：a) 可以指定类型  b) 如果aseq不为空，则以aseq中第一个元素的类型为array中元素的类型(aseq中的元素类型要相同，不然会报出异常)
;; c) 当aseq中元素类型不一致时，也可以指定成Object

;;(into-array [2 "4" "8" 5])                           ;;报出异常

(into-array Object [2 "4" "8" 5])                    ;;#<Object[] [Ljava.lang.Object;@3aa6d0a4>
(into-array (range 4))                               ;;#<Integer[] [Ljava.lang.Integer;@63d6dc46>
(into-array Byte/TYPE (range 4))

;;/// dorun -> (dorun coll) (dorun n coll)
;;可以强制实例化惰性序列  不保留惰性序列头部 并返回一个nil
(dorun 5 (repeatedly #(println "hi")))

;;/// doall -> (doall coll) (doall n coll)
;;可以强制实例化惰性序列  保留惰性序列头部 并返回一个这个头部
;;同时使全部的lazy-seq 写到内存
(def foo (map println [1 2 3]))
(def foo (doall (map println [1 2 3])))

;;/// realized? -> (realized? x)
;; Create a promise
(def p (promise))                                           ;;#'user/p   ; p is our promise
;; Check if was delivered/realized
(realized? p)                                               ;;false      ; No yet
;; Delivering the promise
(deliver p 42)                                              ;;#<core$promise$reify__5727@47122d: 42>
;; Check again if it was delivered
(realized? p)                                               ;;true       ; Yes!
;; Deref to see what has been delivered
@p                                                          ;;42
;; Note that @ is shorthand for deref
(deref p)                                                   ;;42

;;For lazy sequences
(def r (range 5))                                           ;;
(realized? r)                                               ;;=> false
(first r)                                                   ;;=> 0
(realized? r)                                               ;;=> true

;;||||||||||||||||||||||||||||||||||||||||||||||   Sequences 结束   ||||||||||||||||||||||||||||||||||||||||||||||||||||


;;||||||||||||||||||||||||||||||||||||||||||||||   Functions 开始   ||||||||||||||||||||||||||||||||||||||||||||||||||||
;;/// constantly -> (constantly x)
;;Returns a function that takes any number of arguments and returns x.
;;返回值是一个函数，这个函数可以接受任意数目的参数，并且只返回 x
(reduce + (map (constantly 1) [:a :b :c]))                  ;;这个例子是获取一个coll长度的另一个方法
(map (constantly 9) [1 2 3])                                ;;=> (9 9 9)

;;/// 斐波拉契数列
(defn fib
  ([n] (take n (fib 1 1)))
  ([a b] (lazy-seq (cons a (fib b (+ a b))))))

;;/// 杨辉三角  显示杨辉三角中的第x行中的数
(defn yht-core
  [x y]
  ( if (or (= x y) (= y 1)) 1 ;;如果x=y 或者 y=1
   (+ (yht-core (dec x) (dec y))
      (yht-core (dec x) y)
      )))
(defn YH-triangle
  [row]
  (loop [b 1 r (list)] (if (> b row) r  (recur (inc b) (conj r (yht-core row b))))))


;;/// 递归遍历 mapcat在这里起着一个遍历的作用
(defn my-tree-seq
  [branch?  root]
  (let [walk (fn walk [node] (lazy-seq (cons node (when (branch? node)
                                                    (mapcat walk node)
                                                   )
                                        )
                              )
               )
        ]  (walk root)
  )
)




(defn my-recursion [aconditions?  x]
  (let [myfun (fn myfun [arg] (when (aconditions x) (map my-recursion x)))])
  )
































;;||||||||||||||||||||||||||||||||||||||||||||||   Functions 开始   ||||||||||||||||||||||||||||||||||||||||||||||||||||