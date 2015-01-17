(ns clj-data-analysis.chap06.savingdataset
  (:use (incanter core io)
        [clj-data-analysis.chap06.selectingcolumns :only [race-data]]
        [clj-data-analysis.helpers])
  (:require [clojure.data.csv :as csv]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))

(def census2010 ($ [:STATE :NAME :POP100 :P003002 :P003003
                    :P003004 :P003005 :P003006 :P003007
                    :P003008]
                   race-data))

(view census2010)

;; Saving to csv file
(with-open [f-out (io/writer (get-data-src "census-2010.csv"))]
  (csv/write-csv f-out [(map name (col-names census2010))])
  (csv/write-csv f-out (to-list census2010)))

;; Saving json
(with-open [f-out (io/writer (get-data-src "census-2010.json"))]
 (json/write (:rows census2010) f-out))
