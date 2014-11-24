(ns clj-data-analysis.chap03.safesideeffects
  (:use
   clj-data-analysis.helpers
   [clj-data-analysis.chap02.lazylarge :only [lazy-read-csv]]
   [clj-data-analysis.chap03.stm :only [with-header ->int sum-item sum-items thunk-update-totals-for data-file]]
   [clj-data-analysis.chap03.agent :only [accum-sums div-vec force-val]])
  (:import [java.lang Thread]))

(def counter (ref 0))

;; Agents will try to read & inc the counter ref simultaneously
(def a1 (agent :a1))
(def a2 (agent :a2))

