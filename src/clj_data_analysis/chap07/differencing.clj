(ns clj-data-analysis.chap07.differencing
  (:use clj-data-analysis.helpers)
  (:require
   [incanter.core :as i]
   [incanter.io :as io]))

(def data-file (get-data-src "all_160_in_51.P3.csv"))

(def data (io/read-dataset data-file :header true))

(defn
  replace-empty
  "Replace empty fields with zero"
  [x] (if (empty? x) 0 x))

;; Difference in population between two censuses
(def growth-rates
  (->> data
       (i/$map replace-empty :POP100.2000)
       (i/minus (i/sel data :cols :POP100))
       (i/dataset [:POP.DELTA])
       (i/conj-cols data)))

(i/sel growth-rates
       :cols [:NAME :POP100 :POP100.2000 :POP.DELTA]
       :rows (range 5))

