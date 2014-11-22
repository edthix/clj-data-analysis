(ns clj-data-analysis.chap02.normalize
  (:require [clojure.string :as string]))

(defn normalize-number
  [n]
  (let [v (string/split n #"[,.]")
        [pre post] (split-at (dec (count v)) v)]
    (Double/parseDouble (apply str (concat pre [\.] post)))))

(normalize-number "1,000.00")
(normalize-number "1.000,00")
