(ns clj-data-analysis.chap02.duplicate
  (:use clj-diff.core))

(def fuzzy-max-diff 2)
(def fuzzy-percent-diff 0.1)
(def fuzzy-dist edit-distance)

(defn fuzzy=
  "This returns a fuzzy match."
  [a b]
  (let [dist (fuzzy-dist a b)]
    (or (<= dist fuzzy-max-diff)
        (<= (/ dist (min (count a) (count b)))
            fuzzy-percent-diff))))

(defn records-match
  [key-fn a b]
  (let [kfns (if (sequential? key-fn) key-fn [key-fn])
        rfn (fn [prev next-fn]
              (and prev (fuzzy= (next-fn a)
                                (next-fn b))))]
    (reduce rfn true kfns)))

(def data
  {:mulder {:given-name "Fox" :surname "Mulder"}
   :molder {:given-name "Fox" :surname "Molder"}
   :mulder2 {:given-name "fox" :surname "mulder"}
   :scully {:given-name "Dana" :surname "Scully"}
   :scully2 {:given-name "Dan" :surname "Scully"}})

;; Testing
(records-match [:given-name :surname]
               (data :mulder) (data :molder))

(records-match [:given-name :surname]
               (data :mulder) (data :mulder2))

(records-match [:given-name :surname]
               (data :mulder) (data :scully))
