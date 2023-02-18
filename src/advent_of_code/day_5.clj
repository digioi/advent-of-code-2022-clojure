(ns advent-of-code.day-5
  (:require [clojure.string]))

(def sample 
"    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")



(defn parse-stack [stack-lines]
  (let [columns (apply mapv str stack-lines)]
    (into [()]
          (for [column columns
                :let [letters (re-find #"[A-Z]+" column)]
                :when letters]
            (apply list letters)))))


(defn parse-move-line [move-str] 
  (map parse-long (rest (re-matches #"move (\d+) from (\d+) to (\d+)" move-str))))


(defn parse-moves [move-strs]
  (map parse-move-line move-strs))

(defn parse-input [input]
  (let [[crates _ moves]
        (partition-by empty? (clojure.string/split input #"\n"))
        stacks (parse-stack crates)
        moves (parse-moves moves)
        ]
  [stacks moves]))
  
(defn puzzle-a [stack [pos from to]]
  (loop [source (stack from)
          target (stack to)
          n pos]
    (if (pos? n) 
      (recur (pop source) 
             (conj target (peek source))
             (dec n))
      (assoc stack from source  to target))))


  
(defn puzzle-b [stack [pos from to]]
  (loop [source (stack from)
         target (stack to)
         n pos]
    (println source )
    (println target)
    (println n)
    (if (pos? n)
      (recur (pop source)
             (cons target (peek source))
             (dec n))
      (assoc stack from source  to target))))


(comment 
  (as-> (parse-input sample) input
    (reduce puzzle-a (first input) (last input))
    (map peek input)
    (apply str input))

  (as-> (parse-input (slurp "./input-5.txt")) input
    (reduce puzzle-a (first input) (last input))
    (map peek input)
    (apply str input))

  
  (as-> (parse-input sample) input
    (reduce puzzle-b (first input) (last input))
    (map peek input)
    (apply str input))

  )
