(ns clj-data-analysis.chap07.smoothing
  (:require [incanter.core :as i]
            [incanter.stats :as s]
            [incanter.charts :as c]
            [clojure.string :as str])
  (:use clj-data-analysis.helpers))

(defn tokenize
  "takes a text string and pulls the words out of it, or tokenizes it."
  [text]
  (map str/lower-case (re-seq #"\w+" text)))

(defn count-hits
  "takes an item and a collection and returns how many times the item appears in the collection"
  [x coll]
  (get (frequencies coll) x 0))

;; Get data and break into overlapping windows of 500 tokens
(def data-file (get-data-src "pg1661.txt"))

(def windows
  (partition 500 250 (tokenize (slurp data-file))))

(def baker-hits
  (map (partial count-hits "baker") windows))

(defn rolling-fn
  "maps a function to a rolling window of n items from a collection."
  [f n coll]
  (map f (partition n 1 coll)))

;; rolling average for sets of ten windows.
(def baker-avgs (rolling-fn s/mean 10 baker-hits))
