(ns clj-data-analysis.chap10.addinglines
  (:require [incanter.core :as i]
            [incanter.charts :as c]
            [incanter.io :as iio]
            [incanter.stats :as s]
            incanter.datasets))

(def iris (incanter.datasets/get-dataset :iris))

(def iris-petal-scatter
  (c/scatter-plot (i/sel iris :cols :Petal.Width)
                  (i/sel iris :cols :Petal.Length)
                  :title "Irises: Petal Width by Petal Length"
                  :x-label "Width (cm)"
                  :y-label "Length (cm)"))

(def iris-petal-lm
  (s/linear-model
   (i/sel iris :cols :Petal.Length)
   (i/sel iris :cols :Petal.Width)
   :intercept false))

(c/add-lines
 iris-petal-scatter
 (i/sel iris :cols :Petal.Width)
 (:fitted iris-petal-lm)
 :series-label "Linear Relationship")

(i/view iris-petal-scatter)
