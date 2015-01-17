(ns clj-data-analysis.chap06.selectingcolumns
  (:use (incanter core datasets io)
        [clj-data-analysis.chap06.datatomatrix :only [va-matrix]]
        clj-data-analysis.helpers))


(def data-file (get-data-src "all_160.P3.csv"))
(def race-data (read-dataset data-file :header true))


;; Selecting columns with $
($ :POP100 race-data)

;; Selecting more than one column
($ [:STATE :POP100 :POP100.2000] race-data)
(def multi-col-data ($ [:STATE :POP100 :POP100.2000] race-data))
(view multi-col-data)

;; $ is just a wrapper over sel
