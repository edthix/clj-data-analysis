(ns clj-data-analysis.chap06.group
  (:use (incanter core datasets io)
        [clj-data-analysis.chap06.datatomatrix :only [va-matrix]]
        [clj-data-analysis.chap06.selectingcolumns :only [race-data]]
        clj-data-analysis.helpers))

;; Grouping data with group
(def by-state ($group-by :STATE race-data))

;; use take to query (too big)
(take 5 (keys by-state))

;; Querying group map for state 51
(view ($ [0 1 2 3] :all (by-state {:STATE 51})))
