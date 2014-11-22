(ns clj-data-analysis.chap02.synonym
  (:use [clojure.string :only (upper-case)]))


(def state-synonyms
 {"ALABAMA" "AL",
 "ALASKA" "AK",
 "ARIZONA" "AZ",
 "WISCONSIN" "WI",
  "WYOMING" "WY"})

(defn normalize-state
  [state]
  (let [uc-state (upper-case state)]
    (state-synonyms uc-state uc-state)))

(map normalize-state ["Alabama" "OR" "Va" "Fla"])
