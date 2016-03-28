(ns clj-data-analysis.chap07.modelnonlinear
  (:require
   [incanter.core :as i]
   incanter.io
   [incanter.optimize :as o]
   [incanter.stats :as s]
   [incanter.charts :as c])
  (:import [java.lang Math])
  (:use clj-data-analysis.helpers))

(def data-file (get-data-src "accident-fatalities.tsv"))

;; tab delimited file
(def data (incanter.io/read-dataset data-file :header true :delim \tab))

(def fatalities
  "Calculate the number of fatalities per speed limit, and then filter out any invalid speed limits (empty values). We then sort it by the speed limit and create a new dataset."
  (->> data
       (i/$rollup :count :Obs. :spdlim)
       (i/$where {:spdlim {:$ne "."}})
       (i/$where {:spdlim {:$ne 0}})
       (i/$order :spdlim :asc)
       (i/to-list)
       (i/dataset [:speed-limit :fatalities])))

(def speed-limit (i/sel fatalities :cols :speed-limit))
(def fatality-count (i/sel fatalities :cols :fatalities))

speed-limit
fatality-count

(def chart
  (doto
      (c/scatter-plot speed-limit fatality-count
                      :title "Fatalities by Speed Limit (2010)"
                      :x-label "Speed Limit"
                      :y-label "Fatality Count"
                      :legend true)
    i/view))

(defn sine-wave
  "a simple, but very general, sine wave formula"
  [theta x]
  (let [[amp ang-freq phase shift] theta]
    (i/plus
     (i/mult amp (i/sin (i/plus (i/mult ang-freq x) phase)))
     shift)))

;; The non-linear modeling function then determines the parameters that make the function fit the data best.
;; This is a guess
(def start [3500.0 0.07 Math/PI 2500.0])

;; we find the parameter that best fit the function to the data using the non-linear-model function.
(def nlm (o/non-linear-model sine-wave fatality-count speed-limit start))
