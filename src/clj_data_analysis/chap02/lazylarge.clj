(ns clj-data-analysis.chap02.lazylarge
  (:use clj-data-analysis.helpers)
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(defn lazy-read-csv
  [csv-file]
  (let [in-file (io/reader csv-file)
        csv-seq (csv/read-csv in-file)
        lazy (fn lazy [wrapped]
               (lazy-seq
                (if-let [s (seq wrapped)]
                  (cons (first s) (lazy (rest s)))
                  (.close in-file))))]
    (lazy csv-seq)))

;; Running
(lazy-read-csv (get-data-src "small-sample.csv"))
