(ns clj-data-analysis.chap06.datastructures
  (:use (incanter core datasets)))


;; Making matrices
;; **************************************************

(def matrix-set (to-dataset [[1 2 3] [4 5 6]]))
(nrow matrix-set)
(col-names matrix-set)

;; Maps to matrix
(def map-set (to-dataset {:a 1 :b 2 :c 3}))
(nrow map-set)
(col-names map-set)

;; Sequence of maps to matrix
(def maps-set (to-dataset [{:a 1, :b 2, :c 3},
                           {:a 4, :b 5, :c 6}]))

(nrow maps-set)
(col-names maps-set)

;; Passing col and row separately
(def matrix-set-2
  (dataset [:a :b :c]
           [[1 2 3] [4 5 6]]))

(nrow matrix-set-2)

(col-names matrix-set-2)
