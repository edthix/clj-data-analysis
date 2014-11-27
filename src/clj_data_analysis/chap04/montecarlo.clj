(ns clj-data-analysis.chap04.montecarlo
  (:use [criterium core]
        clj-data-analysis.helpers)
  (:import [java.lang Math]))

(defn rand-point
  "Generates random two dimensional point that will fall somewhere in the unit square"
  []
  [(rand) (rand)])

(defn center-dist
  "Returns a points distance from the origin"
  [[x y]]
  (Math/sqrt (+ (* x x) (* y y))))

(defn count-in-circle
  "Takes a number of points to process and creates that many rendom points"
  [n]
  (->>
   (repeatedly n rand-point)
   (map center-dist)
   (filter #(<= % 1.0))
   count))

(defn mc-pi
  ""
  [n]
  (* 4.0 (/ (count-in-circle n) n)))

(defn in-circle-flag
  [p]
  (if (<= (center-dist p) 1.0)
    1
    0))
