(ns clj-data-analysis.chap07.rollup
  (:require
   [incanter.core :as i]
   incanter.io
   [incanter.stats :as s])
  (:use
   [clj-data-analysis.chap06.selectingcolumns :only [data-file race-data]]))

;; Generating summary statistics with $rollup

(def census (incanter.io/read-dataset data-file :header true))

;; $rollup to get statistics of groups of data
;; note the use of rational numbers

;; mean
(i/$rollup :mean :POP100 :STATE census)
(i/$rollup s/sd :POP100 :STATE census)

;; sum
(i/$rollup :sum :POP100 :STATE census)

;; min
(i/$rollup :min :POP100 :STATE census)

;; max
(i/$rollup :max :POP100 :STATE census)
