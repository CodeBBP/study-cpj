(ns stydy_cpj.clojure-study)

;本地绑定
(let [x(+ 1 2) y(+ 3 4)] (+ x y ))

;;;;;;;;;;;;;;;;;;;;;;;;;;;; 解构开始 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;基本的顺序解构：集合中的第一个元素绑定到x上，第二个元素绑定到y上，第三个元素绑定到z上
( def v[42 "foo" 99.2 [5 12]])
;[x y z] 表示指定一组名字
(let[[x y z] v] (+ x z))

;解构还支持嵌套形式
(let[[x _ _ [y z]] v](+ x y z))

;用 & 保持解构剩下的元素，说明：这里的rest是一个序列，不是一个vector
(let[[x & rest] v] rest)

;可以在结构的形式中指定 :as 选项来把被解构的原始集合绑定到一个本地绑定
;(_) 表示绑定忽略
(let[[x _ z :as original-vector] v] (conj original-vector (+ x z)))

;map解构
(def m{:a 5 :b 6 :c [7 8 9] :d {:e 10 :f 11} "foo" 88 42 false})
(let[{a :a b :b} m] (+ a b))

;在map解构中用做key的不止是关键字，可以是任何类型
(let[{f "foo"} m] (+ f 12))
(let[{v 42} m] (if v 10))

;如果要进行map解构的是vector、字符串或者数组的话，那么结构key就是数字类型的数组下标
;【说明】上面所说的就是vector、字符串或者数组可以指定位置进行绑定
(def v[12 0 0 -18 44 6 0 0 1])
(let[{x 3 y 8} v] (+ x y))

;解构嵌套map
(let[{{e :e} :d} m] (* 2 e))

;对被解构的集合进行引用 assoc
;(zipmap [:x :y :z](repeatedly (partial rand-int 10)))
;(let[{r1 :x r2 :y :as randoms} (zipmap [:x :y :z](repeatedly (partial rand-int 10)))] (assoc randoms :sum(+ r1 r2)))
(let[{r1 :a r2 :b :as ora} m] (assoc ora :sum (+ r1 r2)))

;设置默认值：当在map中发现不了需要绑定的key，则key被赋默认值
(let[{r1 :a r2 :g :or{r2 3}} m] (+ r1 r2))

;绑定符号到map中同名关键字所对应的元素
;keys --> 表示key的类型是关键字
;strs --> 表示key的类型是字符串
;syms --> 表示key的类型是符号
;上述的三个都是用来表示map的key的类型
(def chas {:name "chas" :age 31 :location "AnHui"})
(let[{:keys [name age location]} chas] (format "%s is %s year old and alives in %s." name age location))

(def brian {"name" "BBP" "age" "24" "location" "AnHui"})
(let[{:strs [name age location]} brian] (format "%s is %s year old and alives in %s." name age location))

;(def christophe {'name "BBP" 'age 24 'location HeiFei})
;(let[:syms[name age location] christophe] (format "%s is %s year old and alives in %s." name age location))

;对顺序集合剩余的部分使用解构
(def user-info ["robert8900" 2011 :name "bob" :city "Boston"])
(let[[username account-year & {:keys[name city]}] user-info] (format "%s is in %s" name city));(fromat "%s is in %s" name city)
;;;;;;;;;;;;;;;;;;;;;;;;;;;; 解构结束 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;; 定义函数开始 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;把传入参数加10，再返回一个简单函数
;((fn [x ](+ x 10)) 8)
((fn [x y z](+ x y z)) 1 2 3)

(def fun2(fn [x y z](* (+ x y z) 10)) )
(fun2 1 2 3)

;;拥有多参数列表的函数
;;在定义对参列表的函数时，每套函数-函数体都要放在一个单独的括号内，
;;函数调用在决定到底执行哪个函数体的时候是根据传入的参数个数来决定
;;函数名字（这样的函数称为具名函数）可以使得在函数体中引用函数自己，这样可以使我们
(def multiply
  (fn myFun
    ([x](+ x 10))
    ([x y](+ x y 10))
  )
)
( multiply 10)
( multiply 10 12)

;;defn 是一个封装了 def 和 fn 功能的宏
;;下面两个函数是等价的
;(def myDefn(fn myDefn
;       ([x]( myDefn x 1))
;       ([x y](+ x y))
;     )
;)
;
;(defn myDeFN
;  ([x](myDeFN x 1))
;  ([x y](+ x y))
;)

;;解构函数参数列表
;;【可变参函数】函数可以把调用它传入的多余的参数收集到一个列表中去（这些参数通常称为 多余参数 或者 不定参数）
(
 defn concat-rest
  [x & rest]
  (apply str (butlast rest))
)
(concat-rest 0 1 2 3 4)

;;使用起来像没有定义参数的函数
(defn make-user
  [& [user-id]]
  {:user-id (or user-id (str (java.util.UUID/randomUUID)))}
  )

(make-user 1 2)

;;关键字参数
;;【说明】这个是构建在let对于剩余函数的map结构的基础上的
(defn make-user
  [username &{:keys [email join-date] :or {join-date (java.util.Date.)}}]
  {
    :username username
    :join-date join-date
    :email email
    :exp-date (java.util.Date. (long (+ 2.592e9 (.getTime join-date))))
   }
  )
(make-user "BBP")

;;前置条件和后置条件
;放在后面讲

;;函数字面量
(def nonameFun
  (fn [x y]
    (println (str x \^ y))
    (Math/pow x y)
    )
)
(nonameFun 1 2 )

;使用字面量
#(do (println (str %1 \^ %2))
   (Math/pow %1 %2))
;;【关于函数字面量的说明】
;;1. 函数字面量没有隐式的的使用do，这就意味着在使用字面量时需要显式的使用do形式
;;2. 在字面量中使用非命名的占位符来指定函数参数的个数：%1（或者 %） -> 第一个参数   %2 -> 第二个参数  %3 -> 第三个函数

;(defn mytest1 [x & rest](- x (apply + rest)))
;(print str (mytest1 1 2 3 4))

;;条件判断 if
;【说明】
;1. 是clojure中唯一的基本条件判断函数
;2. 符合如下规则：如果if的第一个表达式的值是逻辑true的话，那么整个表达式的值就是第二个表达式的值，以此类推
;3. 如果一个if表达式的值是false，同时没有提供else买有提供，那么这个if的式子就是nil


;;循环：loop 和 recur
(loop [x 5]
  (if (neg? x)
    x
   (recur (dec x))
  )
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;; 定义函数结束 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;;;;;;;;;;;;;; 函数式编程开始 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;map 函数相关
(require 'clojure.string)
(clojure.string/lower-case "ABC")
(map  clojure.string/lower-case
  ["A" "B" "C" "D"]
)

(map +
  [1 2 3]
  [1 2 3]
  )

;;reduce 函数相关
(reduce max [-1 2 3])

(reduce list [1 2 3 4 ])

(list (list (list 1 2) 3) 4)

(reduce list 0 [1 2 3 4 ])

(reduce
  (fn [m v]
    (assoc m v (* v v)))
  {}
  [1 2 3 4]
  )

;;函数应用
;;对于函数在运行时确定，并且传给这个函数的参数不确定的需要使用 apply
(def args [1 2 3 4])
(apply * 0.5 0.4 args);; * 是一个不定参数的函数 0.5,0.4是传递给apply前传递给apply的确定参数

(defn applyTest  ;;自定义的applyTest是一个不定参数的函数
  ([a b c] (+ a b c))
  ([a b c d] (-(+ a b c) d))
  )

(apply applyTest [1 2 3]) ;;--> 6
(apply applyTest [1 2 3 4]);; --> 2

;apply 的用法  使用apply 定义了一个不去定参数的加法函数
(defn atss [& x]
  (apply +  x )
  )

;;偏函数应用
(def only-strings (partial filter string?))
(only-strings ["a" "b" 1 2 3])

(def hundred-times (partial * 100))
(hundred-times 5) ;--> 500
(hundred-times 4 5 6);--> 12000

(defn partialTest [x]
  (print (+ x 1))()
  )

(partial filter string?)

;(defn add-unknow-arg [ & x]
;  ((partial apply + 0) x))

;;函数字面量与偏函数应用
(#(filter string? %) ["a" 1])
(#(map * % %2 %3)[1 2 3] [4 5 6] [7 8 9])
(#(apply map * %&)[1 2 3] [4 5 6] [7 8 9])
(#(apply map * %&)[1 2 3])
((partial map *) [1 2 3][4 5 6])

;partial apply str --> 将传入的字符串参数合并成一串
(defn concat-str[& x]
  ((partial apply str) x)
  )

;;函数（功能）的组合
;例子1：给定一个列表的数字，返回这些数字的总和的负数的字符串形式
(defn negated-sum-str [ & numbers]
  (str (- (apply + numbers)))
  )

;使用 comp 来实现函数组合 （使用comp 来重写例子1）
(def negated-sum-str-comp (comp str - +))

;其他例子
(def concat-and-reverse-comp (comp (partial apply str) reverse str))

(require '[clojure.string :as set-str])
(def camel->keyword(
  comp keyword set-str/join (partial interpose )))

;;;;;;;;;;;;;;;;;;;;;;;;;;;; 函数式编程结束 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;; 集合类与数据结构开始 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;seq
(def v [1 2 3]);定义一个vector
(def seeq (seq v)) ;seq 产生一个的序列
(def m {:a 1 :b 2});定义一个map
(conj v 4);新元素放在vector的尾部
(conj seeq 5);新元素放在seq的首部
(conj m [:c 3] [:d 0])

;;into into是建立在seq 和 conj 之上的
(into v [1 2 3 4])
(into m [[:e 5][:f 6]])

;;empty 获取一个跟   所提供集合 类型  一样的空集合
(defn swap-pairs [sequential];;swqp-pair 交换集合中相邻连个元素的位置
  (into (empty sequential);;获取传入参数的数据结构的具体类型
        (interleave;使两个vector或者seq的元素检查交叉拼接
          (take-nth 2 (drop 1 sequential));;取出vector或者seq中的偶数位的元素
          (take-nth 2 sequential);;取出vector或者seq中的奇数位元素
          ))
  )

(defn map-map [f m]
  (into (empty m)
        (for [[k v] m] [k [f v]])
        )
  )

;;count
(defn count-test[] (count [1 2 3 4 5 1]))

;; first rest next
(first "Mr BBP")                                            ;;取出集合参数中中的第一个元素
(rest "Mr BBP")                                             ;;取出集合参数中除第一个元素外的所有元素
(next "Mr BBP")                                             ;;取出集合参数中除第一个元素外的所有元素
;rest和next的唯一不同是体现在处理 空序列 和 单值序列，这个区别可以使lazy-seq的实现变得容易
(next '())                                                  ;--> nil
(rest '())                                                  ;--> ()
(next '(1))                                                 ;--> nil
(rest '(1))                                                 ;--> ()

;;序列与列表的区别
;① 遍历序列是耗时的，遍历列表是高效的，因为列表会保存它的长度，
; 序列无法保证这一点，因为序列可能是一个惰性的，也可能是个无限的
(defn seq-or-list []
  (let [s (range 1e6)]
    (time (count s)))

  (let [s (apply list (range 1e6))]
    (time (count s)))
  )

;;实现一个惰性序列
(defn random-ints [limit]
  (lazy-seq
    (cons (rand-int limit)
          (random-ints limit))))

(defn test-random-ints []
  (take 10 (random-ints 50)))

;使用标准库函数实现上述功能
;repeatedly 可以通过调用给定的函数参数来创建一个惰性序列
(defn random-ints-plus []
  (repeatedly 10 (partial rand-int 50)))

;;头保持
;split-with -> 参数是一个谓词函数，一个序列（可序列的值），返回两个惰性序列：一个符合谓词的序列，一个是不符合谓词的序列
(defn split-with-test [x,y]
  (println (split-with neg? (range x y)))
  )

(def over-memory
  (let [[t d]])
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;; 集合类与数据结构结束 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;










































































































;
