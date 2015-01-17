(ns clj-data-analysis.chap06.loading
  (:use (incanter core datasets)))

;; Get datasets from incanter/datasets
(def iris (get-dataset :iris))

;; Column names
(col-names iris)

(nrow iris)

(set ($ :Species iris))
