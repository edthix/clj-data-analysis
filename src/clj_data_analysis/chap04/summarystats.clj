(ns clj-data-analysis.chap04.summarystats
  (:import [java.lang Math])
  (:require [clojure.core.reducers :as r]))


;; Data structure to store the data we want to accumulate and keep track
(def zero-counts
  {:n (long 0), :s 0.0, :mean 0.0, :m2 0.0})


(defn accum-counts
  "Add a datum to the counts and accumulation"
  ([] zero-counts)
  ([{:keys [n mean m2 s] :as accum} x]
   (let [new-n (long (inc n))
         delta (- x mean)
         delta-n (/ delta new-n)
         term-1 (* delta delta-n n)
         new-mean (+ mean delta-n)]
     {:n new-n
      :mean mean
      :s (+ s x)
      :m2 (+ m2 term-1)})))

(defn op-fields
  "A utility function that calls a function on
  the values of a field from two maps."
  [op field item1 item2]
  (op (field item1) (field item2)))

;; How to use
(op-fields + :s {:s 1} {:s 2})

(defn combine-counts
  ([] zero-counts)
  ([xa xb]
   (let [n (long (op-fields + :n xa xb))
         delta (op-fields - :mean xb xa)
         nxa*xb (*' (:n xa) (:n xb))]
     {:n n
      :mean (+ (:mean xa) (* delta (/ (:n xb) n)))
      :s (op-fields + :s xa xb)
      :m2 (+ (:m2 xa) (:m2 xb)
             (* delta delta (/ nxa*xb n)))})))

(defn stats-from-sums
  "Takes accumulated counts and values and turn them into final stats"
  [{:keys [n mean m2 s] :as sums}]
  {:mean (double (/ s n))
   :variance (/ m2 (dec n))})

(defn summary-statistics
  "Get results"
  [coll]
  (stats-from-sums
   (r/fold combine-counts accum-counts coll)))

;; Summary statistics on random 1 million numbers
(summary-statistics (repeatedly 1000000 rand))
