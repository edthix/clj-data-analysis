(ns clj-data-analysis.chap06.multisets
  (:use [clj-data-analysis.chap06.datatomatrix :only [va-data va-matrix]]
        (incanter core io charts)
        [clojure.set :only (union)]
        clj-data-analysis.helpers)
  (:require [clojure.set :as set1]))

(def family-data (read-dataset
                  (get-data-src "all_160_in_51.P35.csv") :header
                  true))

(def racial-data (read-dataset
                  (get-data-src "all_160_in_51.P3.csv") :header
                  true))

;; Find intersection between two data sets
(set1/intersection (set (col-names family-data))
                   (set (col-names racial-data)))

;; Removing duplicates
(defn dedup-second
  [a b id-col]
  (let [a-cols (set (col-names a))]
    (conj (filter #(not (contains? a-cols %))
                  (col-names b))
          id-col)))

(def racial-short ($ (vec (dedup-second family-data racial-data :GEOID)) racial-data))
(view racial-short)


;; merge using $join
(def all-data
  ($join [:GEOID :GEOID] family-data racial-short))
(view all-data)

(col-names all-data)
