(ns advent-of-code.day-3
  (:require [clojure.string :refer [split index-of]]
            [clojure.set :as set-fns]))
;; https://adventofcode.com/2022/day/3
(def sample "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw")

(def lookup "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
(def lookup-fn (comp inc (partial index-of lookup)))

(def half (partial * (/ 1 2)))

(defn get-collisions [sack]
  (as-> sack input
    (partition ((comp half count) input) input) ; Can i extract the input??? 
    (map set input)
    (apply set-fns/intersection input)
    first))

(defn puzzle-a [lines]
  (->> lines
       (map get-collisions)
       (map lookup-fn)
       (apply +)))

(defn puzzle-b [lines]
  (->> lines
       (partition 3)
       (map (comp  #(map set %) set))
       (map (partial apply set-fns/intersection))
       (map (comp lookup-fn first))
       (apply +)))

(comment
  (puzzle-a (split sample #"\n"))
  (puzzle-b (split sample #"\n"))
  (puzzle-a (split (clojure.core/slurp "./input-3.txt") #"\n"))
  (puzzle-b (split (clojure.core/slurp "./input-3.txt") #"\n")))
