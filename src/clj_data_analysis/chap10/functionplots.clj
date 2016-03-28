(ns clj-data-analysis.chap10.functionplots
  (require [incanter.core :as i]
           [incanter.charts :as c]))

(def f-plot
  (c/function-plot
    #(/ 1.0 (Math/log %)) 0.0 1.0
    :title "Inverse log function."
    :y-label "Inverse log"))

(i/view f-plot)
