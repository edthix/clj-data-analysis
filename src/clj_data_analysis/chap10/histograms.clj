(ns clj-data-analysis.chap10.histograms
  (require [incanter.core :as i]
           [incanter.charts :as c]
           [incanter.io :as iio]))

(def iris (incanter.datasets/get-dataset :iris))

(def iris-petal-length-hist
  (c/histogram (i/sel iris :cols :Petal.Length)
               :title "Iris Petal Lengths"
               :x-label "cm"
               :nbins 20))

(i/view iris-petal-length-hist)
