(ns advent-of-code.day-1
  (:require [clojure.string :as str]))
;; https://adventofcode.com/2022/day/1
(defn split-by 
  "
   Split by function takes a predicate function to check and will break list/vector up into a 2d vector
   example: 
     [ 1 2 3 nil 4 5 6 nil 10] = nil? => [[123] [4 5 6] [10]]
  "
  [predicate? list]
  (loop [passed-list list coll-list [] result-list []]
    (if (empty? passed-list)
      (if (empty? coll-list) 
        result-list
        (conj result-list coll-list))
      (if (predicate? (first passed-list))
        (recur (rest passed-list) [] (conj result-list coll-list))
        (recur (rest passed-list) (conj coll-list (first passed-list)) result-list)))))

(defn parse-as-int [test-value]
  (if (= "" test-value) nil (Integer/parseInt test-value)))

(defn load-resource [] (map parse-as-int 
                          (-> (clojure.core/slurp "./input-1.txt")
                              (str/split #"\n"))))

(defn puzzle-1a [list]
  (->> list
       (split-by nil?)
       (map (partial apply +))
       (apply max)))

(defn puzzle-1b [list]
  (->> list
       (split-by nil?)
       (map (partial apply +))
       (sort >)
       (take 3)
       (apply +)))

(def resource-list (load-resource))

(def sample [1000 2000 3000 nil 4000 nil 5000 6000 nil 7000 8000 9000 nil 10000])

(comment
  (puzzle-1a resource-list)
  (puzzle-1b resource-list)
  (puzzle-1a sample)
  (puzzle-1b sample)

  
  )
