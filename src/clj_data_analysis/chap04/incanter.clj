(ns clj-data-analysis.chap04.incanter
  (:use [incanter core datasets io optimize stats charts]
        clj-data-analysis.helpers
        [clj-data-analysis.chap03.stm :only [data-file]]))

;; Read data and pull population/housing into matrix
(def data (to-matrix
           (sel (read-dataset data-file :header true)
                :cols [:POP100 :HU100])))

(def population (sel data :cols 0))


(def housing-units (sel data :cols 1))

(def lm (linear-model housing-units population))

(def plot (scatter-plot population housing-units :legend true))
(add-lines plot population (:fitted lm))
(view plot)
