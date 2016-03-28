(ns clj-data-analysis.chap07.modellinear
  (:require
   [incanter.core :as i]
   incanter.io
   [incanter.stats :as s]
   [incanter.charts :as c])
  (:use clj-data-analysis.helpers))

(def data-file (get-data-src "all_160_in_51.P35.csv"))

(def family-data
  (incanter.io/read-dataset data-file
                            :header true))
(def housing (i/sel family-data :cols [:HU100]))
(def families (i/sel family-data :cols [:P035001]))

;; linear regression takes just one line
;;(def families-lm (s/linear-model housing families))
