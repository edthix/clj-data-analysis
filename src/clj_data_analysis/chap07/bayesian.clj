(ns clj-data-analysis.chap07.bayesian
  (:use clj-data-analysis.helpers)
  (:require
   [incanter.core :as i]
   incanter.io
   [incanter.bayes :as b]
   [incanter.stats :as s]
   [incanter.charts :as c]))

(def data-file (get-data-src "all_160_in_51.P3.csv"))


(def census-race
  (i/col-names
   (incanter.io/read-dataset
    data-file
    :header true)
   [:geoid :sumlev :state :county :cbsa :csa :necta
    :cnecta :name :pop :pop2k :housing :housing2k :total
    :total2k :white :white2k :black :black2k :indian
    :indian2k :asian :asian2k :hawaiian :hawaiian2k
    :other :other2k :multiple :multiple2k]))

(def census-sample
  (->> census-race
       i/to-list
       shuffle
       (take 60)
       (i/dataset (i/col-names census-race))))

;; pull out the race columns and total them
(def race-keys
  [:white :black :indian :asian :hawaiian :other :multiple])

(def race-totals
  (into {}
        (map #(vector % (i/sum (i/$ % census-sample)))
             race-keys)))

;; pull out just the sums from the totals map.
(def y (map second (sort race-totals)))

;; we will draw samples for this from the Dirichlet distribution, using sample-multinomial-params, and put those into a new map associated with their original key.
(def theta (b/sample-multinomial-params 2000 y))
(def theta-params
  (into {}
        (map #(vector %1 (i/sel theta :cols %2))
             (sort race-keys)
             (range))))

(s/mean (:black theta-params))

(s/sd (:black theta-params))

(s/quantile (:black theta-params) :probs [0.025 0.975])

(i/view (c/histogram (:black theta-params)))
