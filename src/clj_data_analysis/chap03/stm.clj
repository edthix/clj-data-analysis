(ns clj-data-analysis.chap03.stm
  (:use clj-data-analysis.helpers
        clj-data-analysis.chap02.lazylarge)
  (:require [clojure.java.io :as io]
            [clojure.data.csv :as csv]))

(def data-file (get-data-src "all_160_in_51.P35.csv"))

;; For this recipe, we'll read in the data, break it into chunks, and have separate threads to total
;; the number of housing units and the number of families in each chunk, and each chunk will
;; add its totals to some global references.

;; total housing units
(def total-hu (ref 0))
;; total families
(def total-fams (ref 0))

(defn with-header
  "Use first row to create maps"
  [coll]
  (let [headers (map keyword (first coll))]
    (map (partial zipmap headers) (next coll))))

(defn ->int
  "Converts a string to an integer"
  [i]
  (Integer. i))

(defn sum-item
  "Takes a list of fields, a vector of sums, and a map of data, pull the values
  from the data map, add those to the running totals in the vector, and returns
  a newly updated vector"
  ([fields] (partial sum-item fields))
  ([fields accum item]
   (mapv + accum (map ->int (map item fields)))))

(defn sum-items
  "Calculates the sums of from a collection of data maps"
  [accum fields coll]
  (reduce (sum-item fields) accum coll))

(defn update-totals
  "Total fields from housing unit, family data and collection of items
  and update the STM"
  [fields items]
  (let [mzero (mapv (constantly 0) fields)
        [sum-hu sum-fams] (sum-items mzero fields items)]
    (dosync (alter total-hu #(+ sum-hu %))
            (alter total-fams #(+ sum-fams %)))))

(defn thunk-update-totals-for
  [fields data-thunk]
  (fn [] (update-totals fields data-thunk)))

(defn main
  ([data-file] (main data-file [:HU100 :P035001] 5))
  ([data-file fields chunk-count]
   (doall
    (->>
     (lazy-read-csv data-file)
     with-header
     (partition-all chunk-count)
     (map (partial thunk-update-totals-for fields))
     (map future-call)
     (map deref)))
   (float (/ @total-fams @total-hu))))

(main data-file)
