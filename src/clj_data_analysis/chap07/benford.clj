(ns clj-data-analysis.chap07.benford
  (:use clj-data-analysis.helpers)
  (:require
   [incanter.core :as i]
   incanter.io
   [incanter.stats :as s]))

(def data-file (get-data-src "all_160_in_51.P35.csv"))
(def data (incanter.io/read-dataset data-file :header true))

;; Now we perform the analysis using the incanter.stats/benford-test function. It returns a map containing some interesting tests and values for determining whether the collection conforms to Benford's test, and we can also use it to view a bar chart of the distribution.
(def bt (s/benford-test (i/sel data :cols :POP100)))
