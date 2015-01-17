(ns clj-data-analysis.chap06.where
  (:use (incanter core datasets)
        [clj-data-analysis.chap06.datatomatrix :only [va-matrix va-data]]
        clj-data-analysis.helpers))


;; Using $where for querying datasets
(def richmond ($where {:NAME "Richmond city"}
                      va-data))
(view richmond)

;; Selects small towns where population is < 1000
(def small ($where {:POP100 {:lte 1000}} va-data))
(view ($ :NAME small))
(view ($ [0 1 2 3] :NAME small))

;; How powerful it is
(def pop-enon ($where {:POP100 {:gt 1000 :lt 40000}
                       :NAME "Enon CDP"}
                      va-data))

(view pop-enon)

;; in clause
(view ($where {:NAME {:$in #{"Accomac town" "Alberta town"}}} va-data))

;; fn clause
(def random-half
  ($where {:GEOID {:$fn (fn [_] (< (rand) 0.5))}} va-data))

random-half
