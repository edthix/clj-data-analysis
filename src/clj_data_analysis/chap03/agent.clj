(ns clj-data-analysis.chap03.agent
  (:use clj-data-analysis.helpers
        [clj-data-analysis.chap02.lazylarge :only [lazy-read-csv]]
        [clj-data-analysis.chap03.stm :only [with-header ->int sum-item sum-items]])
  (:require [clojure.java.io :as io]
            [clojure.data.csv :as csv]))

(def data-file (get-data-src "all_160_in_51.P35.csv"))

(defn accum-sums
  "Pair wise addition over agents' outputs"
  [a b]
  (mapv + a b))

(defn div-vec
  "Divides"
  [[a b]]
  (float (/ a b)))

(defn force-val
  [a]
  (await a)
  @a)

(defn main
  ([data-file] (main data-file [:P035001 :HU100] 5 5))
  ([data-file fields agent-count chunk-count]
   (let [mzero (mapv (constantly 0) fields)
         agents (map agent
                     (take agent-count
                           (repeat mzero)))]
     (dorun
      (->>
       (lazy-read-csv data-file)
       with-header
       (partition-all chunk-count)
       (map #(send %1 sum-items fields %2)
            (cycle agents))))
     (->>
      agents
      (map force-val)
      (reduce accum-sums mzero)
      div-vec))))

(main data-file)
