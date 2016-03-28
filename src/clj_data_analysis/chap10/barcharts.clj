(ns clj-data-analysis.chap10.barcharts
  (require [incanter.core :as i]
           [incanter.charts :as c]
           incanter.datasets))

(def chick-weight (incanter.datasets/get-dataset :chick-weight))

(def chick-weight-bar
  (i/with-data
    (i/$order :Diet :asc
              (i/$rollup :sum :weight :Diet chick-weight))
    (c/bar-chart (i/$map int :Diet)
                 :weight
                 :title "Chick Weight"
                 :x-label "Diet"
                 :y-label "Weight")))

(i/view chick-weight-bar)
