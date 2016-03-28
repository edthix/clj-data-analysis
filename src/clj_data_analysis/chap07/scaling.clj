(ns clj-data-analysis.chap07.scaling
  (:require
   [incanter.core :as i]
   incanter.io
   [incanter.charts :as c])
  (:use
   clj-data-analysis.helpers))

(def data-file (get-data-src "all_160_in_51.P3.csv"))

;; read and sort
(def datasrc
  (i/$order :POP100 :asc
            (incanter.io/read-dataset data-file :header true)))

(def datay
  (->> (i/div (i/sel datasrc :cols :POP100) 1000.0)
       (i/dataset [:POP100.1000])
       (i/conj-cols datasrc)))
