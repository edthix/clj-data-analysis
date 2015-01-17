(ns clj-data-analysis.chap06.infix
  (:use (incanter core datasets)
        [clj-data-analysis.chap06.datatomatrix :only [va-matrix]]
        clj-data-analysis.helpers))

;; convert prefix notation to infix
($= 7 * 3)
($= 7 * 4 + 3)


;; Perform scalar multiplication of the matrix

($= va-matrix * 4)
($= (first va-matrix) * 4)

($= (sum (first va-matrix)) /
    (count (first va-matrix)))


($= (reduce plus va-matrix) / (count va-matrix))

;; Let's macroexpand-1 the infix notation
(macroexpand-1 '($= 7 * 4))
