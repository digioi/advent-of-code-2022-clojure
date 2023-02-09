(ns advent-of-code.day-4
  (:require [clojure.string]
            [clojure.set]))

(def sample "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8")

(defn parse [input]
  (clojure.string/split input #"\n"))

(defn convert-to-ints [input]
  (map (comp parse-long) input))

(defn get-range [tuple]
  (range (first tuple) (inc (second tuple)) ))

(defn process-range [input]
  (-> input
    (clojure.string/split #"-")
    (convert-to-ints)
    get-range
    ))

(defn process-line [line]
  (as-> line input
    (clojure.string/split input #",")
    (map process-range input)
    ))

(defn fully-contains? [[list-1 list-2]]
  (let [set-1 (set list-1)
        set-2 (set list-2)]
    (or
     (and 
      (<= 0 
         (count (clojure.set/difference set-1 set-2)))
      (= 0 
         (count (clojure.set/difference set-2 set-1))))
     (and 
      (= 0 
         (count (clojure.set/difference set-1 set-2)))
      (<= 0 
         (count (clojure.set/difference set-2 set-1))))
    )))
  
(defn overlap? [[list-1 list-2]]
  ((comp not empty?) (clojure.set/intersection (set list-1) (set list-2))))

(defn puzzle-a [input]
  (->> input
      parse
      (map process-line)
      (filter fully-contains?)
      count))

(defn puzzle-b [input]
  (->> input
      parse
      (map process-line)
      (filter overlap?)
      count))


(comment
  (process-range "2-4")
  (process-range "6-8")
  (process-line "2-4,6-8")
  
  (fully-contains? (process-line "3-7,2-8"))
  (fully-contains? (process-line "2-4,6-8"))

  (puzzle-a sample)
  (puzzle-a (slurp "./input-4.txt"))
  
  (puzzle-b sample)
  (puzzle-b (slurp "./input-4.txt"))
  )