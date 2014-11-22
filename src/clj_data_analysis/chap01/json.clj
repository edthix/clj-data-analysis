(ns clj-data-analysis.chap01.json
  (:use clj-data-analysis.helpers
        incanter.core)
  (:require [clojure.data.json :as clj-json]))

(def json-data (to-dataset (get-data-src "small-sample.json")))

(first json-data)
