(ns clj-data-analysis.chap10.pca
  (require [incanter.core :as i]
           [incanter.charts :as c]
           [incanter.io :as iio]
           [incanter.stats :as s])
  (use clj-data-analysis.helpers))

(def race-data (iio/read-dataset (get-data-src "all_160.P3.csv") :header true))

(def fields [:P003002 :P003003 :P003004 :P003005
             :P003006 :P003007 :P003008])

(def race-by-state
  (reduce #(i/$join [:STATE :STATE] %1 %2)
          (map #(i/$rollup :sum % :STATE race-data)
               fields)))

(def race-by-state-matrix (i/to-matrix race-by-state))

(def x (i/sel race-by-state-matrix :cols (range 1 8)))

;; the pca
(def pca (s/principal-components x))


(def components (:rotation pca))
(def pc1 (i/sel components :cols 0))
(def pc2 (i/sel components :cols 1))
(def x1 (i/mmult x pc1))
(def x2 (i/mmult x pc2))

(def pca-plot
  (c/scatter-plot
   x1 x2
   :x-label "PC1", :y-label "PC2"
   :title "Census Race Data by State"))

(i/view pca-plot)
