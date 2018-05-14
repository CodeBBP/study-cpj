(ns
  ^{:author "BBP"
    :date "2016-11-07"}
  comways_game_of_life.comways)

;;初始化一个空板子
(defn empty-bord
  "创建一个空矩阵，指定长宽"
  [w h]
  (vec (repeat w (vec (repeat h nil))))
  )

;;初始化一些“活”格子
(defn populate
  "初始化一些“活”格子"
  [board living-cells]
  (reduce (fn [board coordinates]
            (assoc-in board coordinates :on))
          board
          living-cells))
(def glider (populate (empty-bord 6 6) #{[2 0] [2 1] [2 2] [1 2] [0 1]}))
(print glider)

;;index-step函数  接受一个板子的状态作为输入参数，然后根据游戏的规则返回板子的下一个状态
(defn neighbours
  [[x y]]
  (for [dx [-1 0 1] dy [-1 0 1] :when (not= 0 dx dy)]
    [(+ dx x) (+ dy y)]))

;;得到不为nil的邻居元素
(defn count-neighbours
  [board loc]
  (count (filter #(get-in board %) (neighbours loc))))

(defn indexed-setp
  ""
  [board]
  (let [w (count board)]
    (loop [new-board board x 0 y 0]
      (cond
        (>= x w) new-board
        (>= y) h (recur new-board (inc x) 0)
        :else
          (let [new-liveness
                (case (count-neighbours board [x y])
                  2 (get-in board [x y])
                  3 :on
                  nil)]
            (recur (assoc-in new-board [x y] new-livenbess) x (inc y)))))))

;;将 loop 换成 reduce
(defn indexed-setp-usereduce
  [board]
  (let [w (count board)
        h (count (first board))]
    (reduce
      (fn [new-board x]
        (reduce
          (fn [new-board y]
            (let [new-liveness
                  (case (count-neighbours board [x y])
                    2 (get-in board [x y])
                    3 :on
                    nil)]
              (assoc-in new-board [x y] new-liveness)))
          new-board (range h)))
      board (range w))))

;;嵌套的reduce总是可以整合成一个reduce以使代码更简洁
(defn indexed-step-nest-reudce
  [board]
  (let [w (count board)
        h (count (first board))]
    (reduce
      (fn [new-board [x y]]
        (let [new-liveness
              (case (count-neighbours board [x y])
                2 (get-in board [x y])
                3 :on
                nil)]
          (assoc-in new-board [x y] new-liveness)))
      board (for [x (range h) y (range w)] [x y]))))









































