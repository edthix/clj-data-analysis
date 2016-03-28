(ns clj-data-analysis.chap10.dynamiccharts
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


(def d-plot
  (let [x (range -1 1 0.1)]
    (c/dynamic-xy-plot
      [a (range -1.0 1.0 0.1)
       b (range -1.0 1.0 0.1)
       c (range -1.0 1.0 0.1)]
      [x (i/plus (i/mult a x x) (i/mult b x) c)])))

(i/view d-plot)
