(ns clj-data-analysis.chap10.nonnumericdata
  (:use clj-data-analysis.helpers)
  (:require [incanter.core :as i]
            [incanter.charts :as c]
            incanter.datasets
            [incanter.io :as iio]))

(def shrooms (iio/read-dataset (get-data-src "agaricus-lepiota.data") :header true))

(def shroom-cap-bar
  (i/with-data
    (->> shrooms
         (i/$group-by :cap-shape)
         (map (fn [[k v]] (assoc k :count (i/nrow v))))
         (sort-by :cap-shape)
         i/to-dataset)
    (c/bar-chart :cap-shape :count)))

(i/view shroom-cap-bar)
