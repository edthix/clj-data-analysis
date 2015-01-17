(ns clj-data-analysis.chap06.selectingrows
  (:use (incanter core datasets io)
        [clj-data-analysis.chap06.selectingcolumns :only [race-data]]
        clj-data-analysis.helpers))


;; Selecting rows by passing the index of the row we want (0)
($ 0 :all race-data)

;; Multi rows
($ [0 1 2 3 4] :all race-data)

;; Combine two was to slice data for specific col and row
($ 0 [:STATE :POP100 :P003002 :P003003 :P003004 :P003005
      :P003006 :P003007 :P003008]
   race-data)


