(ns clj-data-analysis.chap04.reducers
  (:use [criterium core]
        clj-data-analysis.helpers
        [clj-data-analysis.chap04.montecarlo :only [rand-point center-dist mc-pi]])
  (:import [java.lang Math])
  (:require [clojure.core.reducers :as r]))

(defn count-items
  [c _] (inc c))

(defn count-in-circle-r
  "Compose processing and spread over the available cores (using reducers)"
  [n]
  (->>
   (repeatedly n rand-point)
   vec
   (r/map center-dist)
   (r/filter #(<= % 1.0))
   (r/fold + count-items)))

(defn mc-pi-r
  [n]
  (* 4.0 (/ (count-in-circle-r n) n)))

(quick-bench (mc-pi 100000))

;; This is 50% faster
(quick-bench (mc-pi-r 100000))
