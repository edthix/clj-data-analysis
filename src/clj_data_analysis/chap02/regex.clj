(ns clj-data-analysis.chap02.regex
  (:use clj-data-analysis.helpers)
  (:require [clojure.string :as string]))

(def phone-regex
  #"(?x)
  (\d{3}) # Area code.
  \D{0,2} # Separator. Probably one of \(, \), \-,
  \space.
  (\d{3}) # Prefix.
  \D? # Separator.
  (\d{4})
  ")

(defn clean-us-phone
  [phone]
  (if-let [[_ area-code prefix post]
           (re-find phone-regex phone)]
    (str \( area-code \) prefix \- post)))

(clean-us-phone "111-222-3333")
(clean-us-phone "123-456-7890")

;; Scrathcpad
(if-let [a true]
        "true" "false")

