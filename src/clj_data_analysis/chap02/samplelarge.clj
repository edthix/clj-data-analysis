(ns clj-data-analysis.chap02.samplelarge)

(defn sample-percent
  "Rough sampling by percentage"
  [k coll]
  (filter (fn [_] (<= (rand) k)) coll))

;; Sampling by percentage
(sample-percent 0.01 (range 1000))
(count (sample-percent 0.01 (range 1000)))

;; Sampling exactly
;; Using Donald Knuth's algorithm S from The Art of Computer Programming, Vol 2

(defn rand-replace
  "Takes a map and a new k-v pair.
  Removes a random key from the map and inserts the new pair"
  [m k v]
  (assoc (dissoc m (rand-nth (keys m))) k v))

(defn range-from
  "Creates an infinite range beginning at a given place"
  [x]
  (map (partial + x) (range)))

(defn sample-amount
  [k coll]
  (->> coll
       (drop k)
       (map vector (range-from (inc k)))
       (filter #(<= (rand) (/ k (first %))))
       (reduce rand-replace
               (into {} (map vector (range k) (take k coll))))
       (sort-by first)
       (map second)))

;; BUG
;; (sample-amount 10 (range 1000))
