(ns clj-data-analysis.chap02.datetime
  (:require [clj-time.core :exclude (extend)]
            [clj-time.format :as ct-format]))

;; List of format
(def ^:dynamic *default-formats*
  [:date
   :date-hour-minute
   :date-hour-minute-second
   :date-hour-minute-second-ms
   :date-time
   :date-time-no-ms
   :rfc822
   "YYYY-MM-dd HH:mm"
   "YYYY-MM-dd HH:mm:ss"
   "dd/MM/YYYY"
   "YYYY/MM/dd"
   "d MMM YYYY"])

;; Protocol booyaa
(defprotocol ToFormatter
  (->formatter [fmt]))

(extend-protocol ToFormatter
  java.lang.String
  (->formatter [fmt]
               (ct-format/formatter fmt))

  clojure.lang.Keyword
  (->formatter [fmt] (ct-format/formatters fmt)))

(defn parse-or-nil
  "Takes a format. Returns nil if error"
  [fmt date-str]
  (try (ct-format/parse (->formatter fmt) date-str)
    (catch Exception ex nil)))


(defn normalize-datetime
  "Parse a date with all formats.
  This is a lazy process"
  [date-str]
  (first (remove nil?
                 (map #(parse-or-nil % date-str)
                      *default-formats*))))

;; Run
;; ok
(normalize-datetime "2014-11-15")
(normalize-datetime "31/01/2014")
(normalize-datetime "22 Sep 1990")
(normalize-datetime "2014-11-21 09:20:22")

;; nil
(normalize-datetime "Sep 22")
(normalize-datetime "22 January")
