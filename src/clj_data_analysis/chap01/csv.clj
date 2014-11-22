(ns clj-data-analysis.chap01.csv
  (:use clj-data-analysis.helpers)
  (:require [incanter.io :as io]))

;; Read from http
(def cars (io/read-dataset "https://raw.githubusercontent.com/incanter/incanter/master/data/cars.csv" :header true))
(take 2 cars)

;; Notice - the windows path
(def sample
  (io/read-dataset (get-data-src "small-sample.csv") :header true))

(take 5 sample)
