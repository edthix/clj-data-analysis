(ns clj-data-analysis.chap07.bootstraping
  (:require
   [incanter.core :as i]
   [incanter.stats :as s]
   incanter.io
   [incanter.charts :as c])
  (:use clj-data-analysis.helpers))

(def data-file (get-data-src "all_160_in_51.P3.csv"))
(def data (incanter.io/read-dataset data-file :header true))

;; pull out the population column and resample it for the median using the incanter.stats/bootstrap function.
(def pop100 (i/sel data :cols :POP100))
(def samples (s/bootstrap pop100 s/median :size 2000))

(i/view (c/histogram samples))
