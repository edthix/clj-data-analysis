(ns clj-data-analysis.chap06.view
  (:use (incanter core datasets)
        [clj-data-analysis.chap06.loading :only [iris]]))

;; Looking at our dataset
(view iris)
