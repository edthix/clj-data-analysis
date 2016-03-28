(ns clj-data-analysis.chap10.png
  (:require [incanter.core :as i]
           [incanter.charts :as c]
           incanter.datasets))

(def iris (incanter.datasets/get-dataset :iris))

(def iris-petal-scatter
  (c/scatter-plot (i/sel iris :cols :Petal.Width)
                  (i/sel iris :cols :Petal.Length)
                  :title "Irises: Petal Width by Petal Length"
                  :x-label "Width (cm)"
                  :y-label "Length (cm)"))

(i/view iris-petal-scatter)

;; save to png
(i/save iris-petal-scatter "iris-petal-scatter.png")
