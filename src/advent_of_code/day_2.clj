(ns advent-of-code.day-2
  (:require [clojure.string :as str]
            [clojure.set]))

;; Can I make this point free??? I dont know :(
;; https://adventofcode.com/2022/day/2
(def decoder {
 :X :rock
 :Y :paper
 :Z :scissor
 :A :rock
 :B :paper
 :C :scissor
})

(def winner-table
  {
   :rock :scissor
   :scissor :paper
   :paper :rock
  })

(def lose-table 
  (clojure.set/map-invert winner-table))

(defn winner [input-1 input-2] 
  (let [decoded-input-1 (decoder input-1)
        decoded-input-2 (decoder input-2)]
    (cond 
      (= decoded-input-1 decoded-input-2) :draw
      (= (winner-table decoded-input-1) decoded-input-2) :win
      :else :lose
   )))


(def choice-score {
 :rock 1
 :paper 2
 :scissor 3
})

(def score {
 :win 6
 :draw 3
 :lose 0
})

(defn parse [string-input]
  (->> string-input
      (str/split-lines)
      (map #(->> (str/split % #"\W")
                 (map keyword)))))

;; (get-score "A" "X")
(defn inner-map-fn-2a [line] 
  (let [player   (second line)
        opponent (first line)
        score-value (score (winner player opponent))
        choice-value (choice-score (decoder player))
        ]
    (+ score-value choice-value))
  )

(def modify-play 
  {
   :X :lose
   :Y :draw
   :Z :win
  })

(defn player-choice-b [need opponent] 
  (if (= need :draw)
    opponent
    (if (= need :win)
      (lose-table opponent)
      (winner-table opponent)
      )
    ))

;; (get-score "A" "X")
(defn inner-map-fn-2b [line] 
  (let [player (second line)
        opponent (first line)
        player-need (modify-play player)
        player-choice (player-choice-b player-need (decoder opponent))
        score-value (score player-need)
        choice-value (choice-score player-choice)
        ]
    (+ score-value choice-value))
  )

(defn puzzle-2a [data]
  (->> (parse data)
       (map inner-map-fn-2a)
       (apply +)))

(defn puzzle-2b [data]
  (->> (parse data)
       (map inner-map-fn-2b)
       (apply +)))

(def sample "A Y
B X
C Z")

(comment
  (puzzle-2a (clojure.core/slurp "./input-2.txt"))
  (puzzle-2a sample)
  (puzzle-2b sample)
  (puzzle-2b (clojure.core/slurp "./input-2.txt"))
  )