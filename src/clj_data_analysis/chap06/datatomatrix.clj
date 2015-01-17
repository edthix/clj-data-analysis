(ns clj-data-analysis.chap06.datatomatrix
  (:use (incanter core io)
        clj-data-analysis.helpers
        [clj-data-analysis.chap03.stm :only [data-file]]))

(def va-data (read-dataset data-file :header true))

;; Convert dataset to matrix (few columns)
(def va-matrix
  (to-matrix ($ [:POP100 :HU100 :P035001] va-data)))

;; we can treat it like a sequence
(first va-matrix)
(take 5 va-matrix)

(count va-matrix)

;; using incanter's matrix operations
(reduce plus va-matrix)
