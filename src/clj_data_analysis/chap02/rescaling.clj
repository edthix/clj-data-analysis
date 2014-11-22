(ns clj-data-analysis.chap02.rescaling)

;; Rescale by a key's total in a collection.
;; Assigns the scaled value to a new key
(defn rescale-by-total
  [src dest coll]
  (let [total (reduce + (map src coll))
        update (fn [m]
                 (assoc m dest (/ (m src) total)))]
    (map update coll)))

;; Rescale by a group
(defn rescale-by-group
  [src group dest coll]
  (mapcat (partial rescale-by-total src dest)
          (vals (group-by group
                          (sort-by group coll)))))


;; Test data
(def word-counts
  [{:word 'the, :freq 92, :doc 'a}
   {:word 'a, :freq 76,:doc 'a}
   {:word 'jack, :freq 4,:doc 'a}
   {:word 'the, :freq 3,:doc 'b}
   {:word 'a, :freq 2,:doc 'b}
   {:word 'mary, :freq 1,:doc 'b}])

(rescale-by-group :freq :doc :scaled word-counts)
