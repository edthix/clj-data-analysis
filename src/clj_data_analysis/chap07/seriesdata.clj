(ns clj-data-analysis.chap07.seriesdata
  (:require
   [incanter.core :as i]
   incanter.io
   [incanter.zoo :as zoo]
   [clj-time.format :as tf])
  (:use clj-data-analysis.helpers))

(def data-file (get-data-src "ibm.csv"))

(def ^:dynamic *formatter* (tf/formatter "dd-MMM-yy"))

(defn parse-date
  "Parse the date"
  [date] (tf/parse *formatter* date))

(def datasrc
  (i/with-data
    (i/col-names
     (incanter.io/read-dataset data-file)
     [:date-str :open :high :low :close :volume])
    (->>
     (i/$map parse-date :date-str)
     (i/dataset [:date])
     (i/conj-cols i/$data))))
