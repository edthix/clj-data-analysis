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
  "Random points in a unit square that fall inside a circle"
  [n]
  (* 4.0 (/ (count-in-circle n) n)))

(defn in-circle-flag
  "Takes a point and return 1 if it's in the circle or 0 if not."
  [p]
  (if (<= (center-dist p) 1.0)
    1
    0))

(defn mc-pi-pmap
  "Add up to find number in circle"
  [n]
  (let [in-circle(->>
                  (repeatedly n rand-point)
                  (pmap in-circle-flag)
                  (reduce + 0))]
    (* 4.0 (/ in-circle n))))

(defn mc-pi-part
  "Sequence that tells how large each partition should be and have pmap walk across that."
  ([n] (mc-pi-part 512 n))
  ([chunk-size n]
   (let [step (int
               (Math/floor (float (/ n chunk-size))))
         remainder (mod n chunk-size)
         parts (lazy-seq
                (cons remainder
                      (repeat step chunk-size)))
         in-circle (reduce + 0
                           (pmap count-in-circle
                                 parts))]
     (* 4.0 (/ in-circle n)))))

;; Test run
(def chunk-size 4096)
(def input-size 1000000)

(quick-bench (mc-pi input-size)) ;; slow
